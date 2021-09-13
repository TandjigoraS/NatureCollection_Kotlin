package fr.tandjigora.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tandjigora.naturecollection.MainActivity
import fr.tandjigora.naturecollection.PlantModel
import fr.tandjigora.naturecollection.PlantRepository.Singleton.plantList
import fr.tandjigora.naturecollection.R
import fr.tandjigora.naturecollection.adapter.PlantAdapter
import fr.tandjigora.naturecollection.adapter.PlantItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home,container,false)

        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recyclerview)
        horizontalRecyclerView.adapter = PlantAdapter(context,plantList.filter { !it.liked },R.layout.item_horizontal_plant)
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recyclerview)
        verticalRecyclerView.adapter = PlantAdapter(context,plantList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())
        return view
    }



}