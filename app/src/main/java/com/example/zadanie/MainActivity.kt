package com.example.zadanie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zadanie.Model.OfferModelClass
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    companion object {
        const val VIEW_TYPE_OFFER = 1
        const val VIEW_TYPE_SPORT = 2
        const val VIEW_TYPE_LEAGUE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvOffersList.layoutManager = LinearLayoutManager(this)
        val itemAdapter = OfferAdapter(this, readJSON())
        rvOffersList.adapter = itemAdapter

    }

    private fun getJSONFromAssets(): String? {
        val json: String?
        val charset = Charsets.UTF_8
        try {
            val iss = assets.open("offer.json")
            val size = iss.available()
            val buffer = ByteArray(size)
            iss.read(buffer)
            iss.close()
            json = String(buffer, charset)

        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun readJSON(): ArrayList<OfferModelClass> {
        val offersList: ArrayList<OfferModelClass> = ArrayList()

        try {
            val obj = JSONObject(getJSONFromAssets()!!)

            val offersArray = obj.getJSONArray("EventChanceTypes")
            val odds = obj.getJSONObject("Odds")
            val labels = obj.getJSONObject("Labels")
            for (i in 0 until offersArray.length()) {
                val offer = offersArray.getJSONObject(i)

                val eventName = offer.getString("EventName")
                val eventDate = offer.getString("EventDate")
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                val formatter = SimpleDateFormat("dd.MM. HH:mm")
                val output: String = formatter.format(parser.parse(eventDate))

                val league = labels.getJSONObject("LC_${offer.getInt("LeagueCupID")}").getString("Name")
                val region = labels.getJSONObject("RE_${offer.getInt("RegionID")}").getString("Name")
                val sport = labels.getJSONObject("SP_${offer.getInt("SportID")}").getString("Name")

                var oddsRate1 = "-"
                var oddsRateX = "-"
                var oddsRate2 = "-"
                var oddsRate1X = "-"
                var oddsRateX2 = "-"

                oddsRate1 = odds.getJSONObject("${offer.getInt("EventChanceTypeID")}_1").getString("OddsRate")
                oddsRate2 = odds.getJSONObject("${offer.getInt("EventChanceTypeID")}_2").getString("OddsRate")
                if (sport == "Futbal") {
                    oddsRateX = odds.getJSONObject("${offer.getInt("EventChanceTypeID")}_X").getString("OddsRate")
                    oddsRate1X = odds.getJSONObject("${offer.getInt("EventChanceTypeID")}_1X").getString("OddsRate")
                    oddsRateX2 = odds.getJSONObject("${offer.getInt("EventChanceTypeID")}_X2").getString("OddsRate")
                }
                var type = getType2(offersList,i,sport)
                var tmp = false
                if (type == VIEW_TYPE_SPORT) {
                    val sportDetails = OfferModelClass(type, eventName, output, oddsRate1, oddsRateX, oddsRate2, oddsRate1X, oddsRateX2, region, league, sport)
                    tmp = true
                    offersList.add(sportDetails)
                }
                type = getType3(offersList,i,league,tmp)
                if (type == VIEW_TYPE_LEAGUE) {
                    val leagueDetails = OfferModelClass(type, eventName, output, oddsRate1, oddsRateX, oddsRate2, oddsRate1X, oddsRateX2, region, league, sport)
                    offersList.add(leagueDetails)
                }
                type = VIEW_TYPE_OFFER
                val offerDetails = OfferModelClass(type, eventName, output, oddsRate1, oddsRateX, oddsRate2, oddsRate1X, oddsRateX2, region, league, sport)
                offersList.add(offerDetails)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return offersList
    }
    private fun getType2(list: ArrayList<OfferModelClass>, i: Int,sport:String): Int {
        if (i == 0) {
            return VIEW_TYPE_SPORT
        }else if (sport != list[list.size - 1].sport){
            return VIEW_TYPE_SPORT
        }
        return VIEW_TYPE_OFFER
    }

    private fun getType3(list: ArrayList<OfferModelClass>, i: Int,league:String, tmp: Boolean): Int {
        if (i == 0) {
            return VIEW_TYPE_LEAGUE
        }else if (league != list[list.size - 1].league || tmp) {
            return VIEW_TYPE_LEAGUE
        }
        return VIEW_TYPE_OFFER
    }
}






