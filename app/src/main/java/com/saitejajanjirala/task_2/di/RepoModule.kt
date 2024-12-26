package com.saitejajanjirala.task_2.di

import com.saitejajanjirala.task_2.data.repo.ProductsRepoImpl
import com.saitejajanjirala.task_2.domain.repo.ProductsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun providesProductsRepo(productsRepoImpl: ProductsRepoImpl):ProductsRepo
}