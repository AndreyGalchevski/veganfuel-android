package com.andreygalchevski.veganfuel.screens.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.andreygalchevski.veganfuel.MainActivity
import com.andreygalchevski.veganfuel.R
import com.andreygalchevski.veganfuel.databinding.FragmentHomeBinding
import com.andreygalchevski.veganfuel.network.Nutrient

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initButtons()

        setHasOptionsMenu(true)

        (activity as MainActivity).supportActionBar?.title = getString(
            R.string.app_name
        )

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            view!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initButtons() {
        binding.calciumButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("301", "Calcium")
                    )
                )
        }

        binding.ironButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("303", "Iron")
                    )
                )
        }

        binding.magnesiumButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("304", "Magnesium")
                    )
                )
        }

        binding.omega3Button.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("851", "Omega-3")
                    )
                )
        }

        binding.phosphorusButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("305", "Phosphorus")
                    )
                )
        }

        binding.potassiumButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("306", "Potassium")
                    )
                )
        }

        binding.proteinButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("203", "Protein")
                    )
                )
        }

        binding.zincButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToFoodsFragment(
                        Nutrient("309", "Zinc")
                    )
                )
        }
    }

}
