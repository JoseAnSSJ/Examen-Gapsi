package com.example.examengapsi

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examengapsi.databinding.ActivityMainBinding
import com.example.examengapsi.presentation.adapter.ProductAdapter
import com.example.examengapsi.ViewModel.ProductViewModel
import com.example.examengapsi.presentation.adapter.PageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import com.example.examengapsi.presentation.adapter.SearchHistoryAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: ProductViewModel by viewModels()

    private val productAdapter = ProductAdapter()

    private val pageAdapter = PageAdapter { page ->
        viewModel.loadPage(page)
    }

    private val historyAdapter = SearchHistoryAdapter { query ->

        binding.etSearch.setText(query)

        performSearch(query)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupListeners()
        observeViewModel()
        setupHistory()
        setupSearch()
    }


    private fun setupRecyclerViews() {

        binding.rvProducts.apply {

            layoutManager = GridLayoutManager(
                this@MainActivity, 3
            )

            adapter = productAdapter
        }

        binding.rvPages.apply {

            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )

            adapter = pageAdapter
        }
    }


    private fun setupHistory() {

        binding.rvSearchHistory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = historyAdapter
        }

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->

            if (hasFocus && historyAdapter.itemCount > 0) {
                binding.rvSearchHistory.visibility = View.VISIBLE
            } else {
                binding.rvSearchHistory.visibility = View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.searchHistory.collectLatest { history ->

                historyAdapter.submitList(history)

                binding.rvSearchHistory.visibility =
                    if (binding.etSearch.hasFocus() && history.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        }
    }

    private fun setupSearch() {

        binding.etSearch.setOnEditorActionListener { _, actionId, event ->

            val isSearch = actionId == EditorInfo.IME_ACTION_SEARCH

            val isEnter =
                event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN

            if (isSearch || isEnter) {
                performSearch(
                    binding.etSearch.text.toString()
                )

                true
            } else {
                false
            }
        }
    }

    private fun performSearch(query: String) {
        val cleanQuery = query.trim()

        if (cleanQuery.isBlank()) return

        viewModel.search(cleanQuery)

        binding.etSearch.clearFocus()

        binding.rvSearchHistory.visibility = View.GONE

        val imm = getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager

        imm.hideSoftInputFromWindow(
            binding.etSearch.windowToken, 0
        )
    }


    private fun setupListeners() {

        binding.etSearch.setOnClickListener {
            binding.etSearch.requestFocus()

            val imm = getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager

            imm.showSoftInput(
                binding.etSearch, InputMethodManager.SHOW_IMPLICIT
            )
        }

        binding.btnRetry.setOnClickListener {
            viewModel.retry()
        }
    }


    private fun observeViewModel() {

        lifecycleScope.launch {
            viewModel.products.collectLatest { products ->

                productAdapter.submitList(products)

                if (products.isNotEmpty()) {
                    binding.rvProducts.visibility = View.VISIBLE
                    binding.tvNotFind.visibility = View.GONE
                    binding.retry.visibility = View.GONE
                }
            }
        }


        lifecycleScope.launch {

            viewModel.isLoading.collectLatest { loading ->

                binding.loading.visibility = if (loading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }


        lifecycleScope.launch {

            viewModel.error.collectLatest { error ->

                if (error != null) {

                    binding.retry.visibility = View.VISIBLE
                    binding.rvProducts.visibility = View.GONE
                    binding.rvPages.visibility = View.GONE

                    binding.tvError.text = error

                } else {

                    binding.retry.visibility = View.GONE
                    binding.rvProducts.visibility = View.VISIBLE
                }
            }
        }


        lifecycleScope.launch {

            viewModel.totalPages.collectLatest { totalPages ->

                pageAdapter.submitList(
                    (1..totalPages).toList()
                )

                binding.rvPages.visibility = if (totalPages > 1) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.searchHistory.collectLatest { history ->

                historyAdapter.submitList(history)

                binding.rvSearchHistory.visibility =
                    if (binding.etSearch.hasFocus() && history.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        }
    }
}