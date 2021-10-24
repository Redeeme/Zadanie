package com.example.zadanie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.Model.OfferModelClass
import kotlinx.android.synthetic.main.item_league_layout.view.*
import kotlinx.android.synthetic.main.item_offer_layout.view.*
import kotlinx.android.synthetic.main.item_sport_layout.view.*


class OfferAdapter(val context: Context, val list: ArrayList<OfferModelClass>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_OFFER = 1
        const val VIEW_TYPE_SPORT = 2
        const val VIEW_TYPE_LEAGUE = 3
    }

    private inner class View1ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEventName = view.id_match
        val tvEventDate= view.id_date_time
        val tvRegion= view.id_region
        val tvOdds1= view.id_odds1
        val tvOddsX= view.id_oddsX
        val tvOdds2= view.id_odds2
        val tvOdds1X= view.id_odds1X
        val tvOddsX2= view.id_oddsX2
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            tvEventName.text = recyclerViewModel.eventName
            tvEventDate.text = recyclerViewModel.eventDate
            tvRegion.text = recyclerViewModel.region
            tvOdds1.text = recyclerViewModel.oddsRate1
            tvOddsX.text = recyclerViewModel.oddsRateX
            tvOdds2.text = recyclerViewModel.oddsRate2
            tvOdds1X.text = recyclerViewModel.oddsRate1X
            tvOddsX2.text = recyclerViewModel.oddsRateX2
        }
    }

    private inner class View2ViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
        val tvSport= view.id_sport
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            tvSport.text = recyclerViewModel.sport
        }
    }
    private inner class View3ViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
        val tvLeague= view.id_league
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            tvLeague.text = recyclerViewModel.league
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_OFFER) {
            return View1ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_offer_layout, parent, false)
            )
        } else if (viewType == VIEW_TYPE_SPORT) {
            return View2ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_sport_layout, parent, false)
            )
        } else{
            return View3ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_league_layout, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType == VIEW_TYPE_OFFER) {
            (holder as View1ViewHolder).bind(position)
        } else if(list[position].viewType == VIEW_TYPE_SPORT) {
            (holder as View2ViewHolder).bind(position)
        } else {
            (holder as View3ViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }
}