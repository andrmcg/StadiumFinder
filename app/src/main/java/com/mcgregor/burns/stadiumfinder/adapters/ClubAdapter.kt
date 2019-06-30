package com.mcgregor.burns.stadiumfinder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.mcgregor.burns.stadiumfinder.R
import com.mcgregor.burns.stadiumfinder.entities.Club
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.clubs_list.view.*

class ClubAdapter(context: Context, teams:List<Club>): BaseAdapter() {

    private var _teams:List<Club>
    private var _context:Context
    private val _inflater:LayoutInflater

    init {
        this._context = context
        this._teams = teams.sortedBy { c -> c.teamName }
        _inflater = this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater:LayoutInflater = _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.clubs_list, null)
        view.teamnameTextview.setText(_teams.get(position).teamName)
        //  TODO() Return image to image view for club logo
        val imageName = _teams.get(position).logo.substringBefore('.')
        val resourceId= _context.resources.getIdentifier(imageName,"drawable", _context.packageName)
        if (resourceId != 0) {
            Picasso.get().load(resourceId).into(view.clubLogo)
        }
        //view.clubLogo.setImageResource(resourceId)
        return view
    }

    override fun getItem(position: Int): Any {
        return _teams.get(position)
    }

    override fun getItemId(position: Int):Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return _teams.size
    }

}