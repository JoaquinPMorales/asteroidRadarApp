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
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

//    private val viewModel: MainViewModel by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java)
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
//        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao

        val viewModelFactory = MainViewModelFactory(application)
        val mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel = mainViewModel

        val asteroidAdapter = AsteroidAdapter(AsteroidListener { asteroid ->
            Log.i("MainFragment", "asteroid selected ${asteroid.id}")
            mainViewModel.onAsteroidDetailClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = asteroidAdapter

//        setHasOptionsMenu(true)
        hasOptionsMenu()

        mainViewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                //Add the asteroids
                Log.i("MainFragment", "first asteroid name: ${it.get(0).codename}")
                asteroidAdapter.submitList(it)
            }
        })

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
        return true
    }
}
