package com.andreygalchevski.veganfuel.screens.foods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FoodsViewModelFactory(private val nutrientId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoodsViewModel(nutrientId) as T
    }
}