package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.AsteroidAdapter
import com.udacity.asteroidradar.AsteroidListener
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.DateFilterOptions
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = mainViewModel

        val asteroidAdapter = AsteroidAdapter(AsteroidListener { asteroid ->
            Log.i("MainFragment", "asteroid selected ${asteroid.id}")
            mainViewModel.onAsteroidDetailClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = asteroidAdapter

//        setHasOptionsMenu(true)
        setHasOptionsMenu(true)

        mainViewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                mainViewModel.onAsteroidDetailNavigated()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mainViewModel.asteroidRepository.updateFilter(
            when (item.itemId) {
                R.id.show_all_menu -> DateFilterOptions.SHOW_WEEK
                R.id.show_today_menu -> DateFilterOptions.SHOW_TODAY
                else -> DateFilterOptions.SHOW_ALL
            }
        )
        return true
    }
}
