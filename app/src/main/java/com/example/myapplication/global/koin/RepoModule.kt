package com.example.myapplication.global.koin

import com.example.myapplication.model.repo.SearchRepository
import org.koin.dsl.module

val repoModule = module {
    single { SearchRepository(get()) }
}