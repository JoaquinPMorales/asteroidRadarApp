package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao

        val viewModelFactory = MainViewModelFactory(dataSource, application)
        val mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel = mainViewModel

//        setHasOptionsMenu(true)
        hasOptionsMenu()

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
