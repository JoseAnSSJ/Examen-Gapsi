package com.example.examengapsi.core

import com.example.examengapsi.data.repository.ProductRepositoryImpl
import com.example.examengapsi.data.repository.SearchHistoryRepositoryImpl
import com.example.examengapsi.domain.repository.ProductRepository
import com.example.examengapsi.domain.repository.SearchHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        implementation: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindSearchHistoryRepository(
        implementation: SearchHistoryRepositoryImpl
    ): SearchHistoryRepository
}