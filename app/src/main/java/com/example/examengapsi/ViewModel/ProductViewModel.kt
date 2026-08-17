package com.example.examengapsi.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examengapsi.domain.model.Product
import com.example.examengapsi.domain.repository.SearchHistoryRepository
import com.example.examengapsi.domain.usecase.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())

    val products = _products.asStateFlow()


    private val _totalPages = MutableStateFlow(0)

    val totalPages = _totalPages.asStateFlow()


    private val _currentPage = MutableStateFlow(1)


    private val _isLoading = MutableStateFlow(false)

    val isLoading = _isLoading.asStateFlow()


    private val _error = MutableStateFlow<String?>(null)

    val error = _error.asStateFlow()


    private var currentQuery = ""

    val searchHistory: StateFlow<List<String>> = searchHistoryRepository.getHistory().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )


    fun search(query: String) {
        val cleanQuery = query.trim()

        if (cleanQuery.isBlank()) return
        currentQuery = cleanQuery
        _currentPage.value = 1

        viewModelScope.launch {
            searchHistoryRepository.saveSearch(cleanQuery)
        }



        loadPage(1)
    }


    fun loadPage(page: Int) {

        if (currentQuery.isBlank()) return

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {
                val result = searchProductsUseCase(
                    keyword = currentQuery, page = page
                ).getOrThrow()

                _products.value = result.products
                _currentPage.value = page
                _totalPages.value = result.totalPages

            } catch (e: Exception) {

                _products.value = emptyList()
                _totalPages.value = 0
                _error.value = e.message ?: "Error al buscar productos"

            } finally {
                _isLoading.value = false
            }
        }
    }


    fun retry() {
        loadPage(_currentPage.value)
    }
}