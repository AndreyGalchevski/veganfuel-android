package com.andreygalchevski.veganfuel.screens.foods

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.andreygalchevski.veganfuel.MainActivity
import com.andreygalchevski.veganfuel.R
import com.andreygalchevski.veganfuel.databinding.FragmentFoodsBinding


class FoodsFragment : Fragment() {

    private lateinit var binding: FragmentFoodsBinding
    private lateinit var viewModel: FoodsViewModel
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_foods, container, false)

        val args =
            FoodsFragmentArgs.fromBundle(
                arguments!!
            )

        (activity as MainActivity).supportActionBar?.title = args.nutrient.name

        binding.lifecycleOwner = this

        val foodViewModelFactory = FoodsViewModelFactory(args.nutrient.id)
        viewModel = ViewModelProvider(this, foodViewModelFactory).get(FoodsViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

        })

        viewModel.foods.observe(viewLifecycleOwner, Observer { foodList ->
            foodAdapter = FoodAdapter(foodList)
            binding.foodsList.adapter = foodAdapter

        })

        initSearchEditText(binding.searchText)
        initFoodGroupSpinner(binding.foodGroupSpinner, args.nutrient.id)
        initSortBySpinner(binding.sortBySpinner, args.nutrient.id)

        return binding.root
    }

    private fun initSearchEditText(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun initFoodGroupSpinner(spinner: Spinner, currentNutrientId: String) {
        var options = viewModel.foodGroups.map { it.key }
        val adapter =
            ArrayAdapter(activity as MainActivity, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position)
                viewModel.currentFoodGroupId = selectedItem.toString()
                viewModel.getFoods(currentNutrientId)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }


    private fun initSortBySpinner(spinner: Spinner, currentNutrientId: String) {
        var options = viewModel.sortTypes.map { it.key }
        val adapter =
            ArrayAdapter(activity as MainActivity, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position)

                viewModel.currentSortTypeId = selectedItem.toString()
                viewModel.getFoods(currentNutrientId)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }

    private fun filter(text: String) {
        if (::foodAdapter.isInitialized) {
            val filteredList =
                viewModel.foods.value!!.filter {
                    it.name.toLowerCase().contains(text.toLowerCase())
                }
            foodAdapter.filterList(filteredList)
        }
    }
}
