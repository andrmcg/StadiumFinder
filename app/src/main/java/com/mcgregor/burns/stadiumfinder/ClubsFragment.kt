package com.mcgregor.burns.stadiumfinder


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mcgregor.burns.stadiumfinder.adapters.ClubAdapter
import com.mcgregor.burns.stadiumfinder.entities.Club
import com.mcgregor.burns.stadiumfinder.modules.ClubViewModel
import kotlinx.android.synthetic.main.fragment_clubs.*


class ClubsFragment : Fragment() {

    private lateinit var viewmodel: ClubViewModel
    private lateinit var teams:List<Club>
    private lateinit var navController: NavController
    private var locationPermissonGranted = false
    private val PERMISSION_REQUEST_FINE_ACCESS_LOCATION = 1968
    private lateinit var mapIntent: Intent



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clubs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = findNavController()

        viewmodel = ViewModelProvider(this).get(ClubViewModel::class.java)

        //viewmodel = ViewModelProviders.of(this).get(ClubViewModel::class.java)
        viewmodel.clubs.observe(this, Observer { c ->
            c.let {
                val league = arguments?.getString("leagueName")
                teams = c.filter { it.league == league }
            }
            val adapter = ClubAdapter(this.requireContext().applicationContext,teams)
            clubList.adapter = adapter
        })

        clubList.setOnItemClickListener { parent, view, position, id ->
            val club = parent.getItemAtPosition(position) as Club

            val location = Uri.parse("geo:${club.lat},${club.lon}?z=16")
            mapIntent = Intent(Intent.ACTION_VIEW, location)
            //  verify acivity exists
            val activities = context?.packageManager?.queryIntentActivities(mapIntent,0)
            if (activities!!.isNotEmpty())
            {
                when(ContextCompat.checkSelfPermission(context!!.applicationContext,android.Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    PackageManager.PERMISSION_GRANTED -> {
                        locationPermissonGranted = true
                        startActivity(mapIntent)
                    }
                    else -> requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),PERMISSION_REQUEST_FINE_ACCESS_LOCATION)
                }
            }
        }

    }// End of function

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        locationPermissonGranted = false
        when(requestCode)
        {
            PERMISSION_REQUEST_FINE_ACCESS_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    locationPermissonGranted = true
                    startActivity(mapIntent)
                }
            }
        }
    }// End of function

}// End of class
