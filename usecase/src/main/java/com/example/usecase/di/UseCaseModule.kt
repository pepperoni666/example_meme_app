package com.example.usecase.di

import com.example.usecase.feed.FeedUseCase
import com.example.usecase.feed.MemeLikedUseCase
import com.example.usecase.profile.ChangeNameUseCase
import com.example.usecase.profile.ProfileUseCase
import org.koin.dsl.module

object UseCaseModule {
    val useCaseModule = module {
        factory { FeedUseCase(get()) }

        factory { ProfileUseCase(get()) }

        factory { MemeLikedUseCase(get()) }

        factory { ChangeNameUseCase(get()) }
    }
}