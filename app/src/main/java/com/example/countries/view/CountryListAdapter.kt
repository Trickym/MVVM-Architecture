package com.example.countries.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.MainActivity
import com.example.countries.R
import com.example.countries.model.Country
import com.example.countries.util.getProgressDrawable
import com.example.countries.util.loadImage


class CountryListAdapter(var countries:ArrayList<Country>):RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries:List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CountryViewHolder {
        val viewHolderItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(viewHolderItem)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])

    }

    override fun getItemCount(): Int {
        return countries.size
    }
    class CountryViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val countryName:TextView = view.findViewById(R.id.name)
        private val countryCapital:TextView = view.findViewById(R.id.capital)
        private val flagImage:ImageView = view.findViewById(R.id.flag_image)
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country){
            countryName.text = country.countryName
            countryCapital.text = country.countryCapital
            flagImage.loadImage(country.flag,progressDrawable)
            itemView.setOnClickListener {
                var builder:AlertDialog.Builder = AlertDialog.Builder(it.rootView.context)
                var dialogView:View = LayoutInflater.from(it.rootView.context).inflate(R.layout.country_dialog,null)
                var country_flag_image:ImageView = dialogView.findViewById(R.id.country_flag)
                var dialog_country_name:TextView = dialogView.findViewById(R.id.dialog_country)
                var dialog_capital:TextView = dialogView.findViewById(R.id.dialog_country_capital)
                dialog_country_name.text = country.countryName
                dialog_capital.text = country.countryCapital
                country_flag_image.loadImage(country.flag,progressDrawable)
                builder.setView(dialogView)
                builder.setCancelable(true)
                builder.show()

            }
        }
    }

}