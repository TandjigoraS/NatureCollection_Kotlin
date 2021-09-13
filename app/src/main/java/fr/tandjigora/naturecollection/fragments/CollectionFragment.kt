package fr.tandjigora.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tandjigora.naturecollection.*
import fr.tandjigora.naturecollection.adapter.PlantAdapter
import fr.tandjigora.naturecollection.adapter.PlantItemDecoration

class CollectionFragment (private val context : MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_collection,container,false)

        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recyclerview)
        collectionRecyclerView.adapter = PlantAdapter(context, PlantRepository.Singleton.plantList.filter { it.liked },R.layout.item_vertical_plant)
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())
        return view
    }
}