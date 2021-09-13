package fr.tandjigora.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tandjigora.naturecollection.MainActivity
import fr.tandjigora.naturecollection.PlantModel
import fr.tandjigora.naturecollection.PlantRepository
import fr.tandjigora.naturecollection.PlantRepository.Singleton.plantList
import fr.tandjigora.naturecollection.R
import fr.tandjigora.naturecollection.adapter.PlantAdapter
import fr.tandjigora.naturecollection.adapter.PlantItemDecoration

class BasketFragment(private val context : MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_basket,container,false)
        val basketRecyclerView = view.findViewById<RecyclerView>(R.id.basket_recyclerview)
        basketRecyclerView.adapter = PlantAdapter(context,PlantRepository.Singleton.plantList.filter { it.inBasket },R.layout.item_my_basket)
        basketRecyclerView.addItemDecoration(PlantItemDecoration())

        val priceTotalPlants = view.findViewById<TextView>(R.id.total_price_plants)
        val priceDeliver = view.findViewById<TextView>(R.id.price_deliver)
        val priceTotalBasket = view.findViewById<TextView>(R.id.total_basket_price)

        for(plant in plantList){

            priceTotalPlants.text = (plant.price * plant.quantity).toString()
        }
        return view
    }







}
