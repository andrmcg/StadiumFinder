package com.mcgregor.burns.stadiumfinder


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mcgregor.burns.stadiumfinder.modules.LeagueViewModel
import kotlinx.android.synthetic.main.fragment_league.*



class LeagueFragment : Fragment() {

    private lateinit var leagueViewModel: LeagueViewModel
    private lateinit var leagueNames: List<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = findNavController()

        leagueViewModel = ViewModelProvider(this).get(LeagueViewModel::class.java)
        leagueViewModel.leagueNames.observe(this, Observer { c ->
            c.let { leagueNames = c }
            adapter = ArrayAdapter(this.context, R.layout.league_list, leagueNames)
            leagueList.adapter = adapter
        })

        leagueList.setOnItemClickListener { parent, view, position, id ->
            //  TODO() Implement code to get chosen league
            val leagueName = parent.getItemAtPosition(position).toString()

            val directions = LeagueFragmentDirections.actionLeagueFragmentToClubsFragment(leagueName)

            navController.navigate(directions)
        }

    }
}
