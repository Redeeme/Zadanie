package com.example.zadanie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.Model.OfferModelClass
import kotlinx.android.synthetic.main.item_league_layout.view.*
import kotlinx.android.synthetic.main.item_offer_layout.view.*
import kotlinx.android.synthetic.main.item_sport_layout.view.*


class OfferAdapter(private val context: Context, val list: ArrayList<OfferModelClass>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_OFFER = 1
        const val VIEW_TYPE_SPORT = 2
        const val VIEW_TYPE_LEAGUE = 3
    }

    private inner class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEventName: TextView = view.id_match
        val tvEventDate: TextView = view.id_date_time
        val tvRegion: TextView = view.id_region
        val tvOdds1: TextView = view.id_odds1
        val tvOddsX: TextView = view.id_oddsX
        val tvOdds2: TextView = view.id_odds2
        val tvOdds1X: TextView = view.id_odds1X
        val tvOddsX2: TextView = view.id_oddsX2

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

    private inner class SportViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
        val tvSport: TextView = view.id_sport
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            tvSport.text = recyclerViewModel.sport
        }
    }
    private inner class LeagueViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
        val tvLeague: TextView = view.id_league
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            tvLeague.text = recyclerViewModel.league
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            VIEW_TYPE_OFFER -> return OfferViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_offer_layout, parent, false)
            )
            VIEW_TYPE_SPORT -> return SportViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_sport_layout, parent, false)
            )
            else -> {
                return LeagueViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.item_league_layout, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(list[position].viewType){
            VIEW_TYPE_OFFER ->  (holder as OfferViewHolder).bind(position)
            VIEW_TYPE_SPORT ->  (holder as SportViewHolder).bind(position)
            else -> {
                (holder as LeagueViewHolder).bind(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }
}