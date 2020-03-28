package com.andreygalchevski.veganfuel.screens.foods

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andreygalchevski.veganfuel.network.Food
import com.andreygalchevski.veganfuel.network.UsdaApi
import kotlinx.coroutines.*
import java.lang.Exception

const val DEFAULT_FOOD_GROUP_ID = "9999"
const val DEFAULT_SORT_TYPE_ID = "c"

class FoodsViewModel(nutrientId: String) : ViewModel() {
    val foodGroups = mapOf(
        "All" to "9999",
        "Grains" to "2000",
        "Fruits" to "0900",
        "Legumes" to "1600",
        "Nuts and Seeds" to "1200",
        "Vegetables" to "1100"
    )

    val sortTypes = mapOf(
        "Nutrient content" to "c",
        "Food name" to "f"
    )

    private var _currentFoodGroupId: String = DEFAULT_FOOD_GROUP_ID
    var currentFoodGroupId: String
        get() = _currentFoodGroupId
        set(value) {
            _currentFoodGroupId = foodGroups[value] ?: DEFAULT_FOOD_GROUP_ID
        }

    private var _currentSortTypeId: String = DEFAULT_SORT_TYPE_ID
    var currentSortTypeId: String
        get() = _currentSortTypeId
        set(value) {
            _currentSortTypeId = sortTypes[value] ?: DEFAULT_SORT_TYPE_ID
        }

    val isLoading = MutableLiveData<Boolean>()

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>>
        get() = _foods

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getFoods(nutrientId)
    }

    fun getFoods(nutrientId: String) {
        var foodGroupIds: List<String> = if (currentFoodGroupId == "9999") {
            foodGroups.values.toMutableList()
        } else {
            listOf(currentFoodGroupId)
        }

        coroutineScope.launch {
            try {
                isLoading.value = true
                val usdaResponse =
                    UsdaApi.retrofitService.getFoods(currentSortTypeId, foodGroupIds, nutrientId)
                _foods.value = usdaResponse.report.foods
            } catch (e: Exception) {
                Log.i("FoodsViewModel", "Failed to fetch foods: ${e.message}")
            } finally {
                isLoading.value = false
            }

        }

    }
}