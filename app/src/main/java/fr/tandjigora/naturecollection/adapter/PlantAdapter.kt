package fr.tandjigora.naturecollection.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.tandjigora.naturecollection.*

class PlantAdapter(
    val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId: Int,
    ) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {


    // boite pour ranger tout les composants à contrôler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantDescripton: TextView? = view.findViewById(R.id.descripton_item)
        val plantName: TextView? = view.findViewById(R.id.name_item)
        val starIcon: ImageView? = view.findViewById(R.id.star_icon)
        val pricePlant: TextView? = view.findViewById(R.id.price_plant)
        val addQuantityPlant: ImageView? = view.findViewById(R.id.add_quantity_item_my_basket)
        val removeQuantityPlant: ImageView? = view.findViewById(R.id.remove_quantity_item_my_basket)
        val quantityPlant: TextView? = view.findViewById(R.id.item_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // recuperer les informations de la plante
        val currentPlant = plantList[position]

        val repo = PlantRepository()

        Glide.with(context)
            .load(Uri.parse(currentPlant.imageUrl))
            .into(holder.plantImage)

        holder.plantName?.text = currentPlant.name
        holder.plantDescripton?.text = currentPlant.description

        if (currentPlant.liked) {
            holder.starIcon?.setImageResource(R.drawable.ic_like)
        } else {
            holder.starIcon?.setImageResource(R.drawable.ic_unlike)
        }

        // ajouter une interaction à cette étoile
        holder.starIcon?.setOnClickListener {
            // Inverse si la plant à été liké ou non
            currentPlant.liked = !currentPlant.liked
            // mettre à jour la plant
            repo.updatePlant(currentPlant)
        }

        holder.itemView.setOnClickListener {
            val myPopup = PopupPlant(this, currentPlant)
            myPopup.show()
        }

        holder.quantityPlant?.text = currentPlant.quantity.toString()
        holder.pricePlant?.text = currentPlant.price.toString()
        holder.addQuantityPlant?.setOnClickListener {
            currentPlant.quantity++
            holder.quantityPlant?.text = currentPlant.quantity.toString()

        }
        holder.removeQuantityPlant?.setOnClickListener {

            currentPlant.quantity--
            holder.quantityPlant?.text = currentPlant.quantity.toString()

            if (currentPlant.quantity <= 0) {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Etes-vous sûr de vouloir supprimer cette article du panier ?")
                    .setPositiveButton("Oui", DialogInterface.OnClickListener { dialog, id ->
                        currentPlant.inBasket = false
                        repo.updatePlant(currentPlant)
                        dialog.dismiss()

                    })
                    .setNegativeButton("Non", DialogInterface.OnClickListener { dialog, id ->
                        currentPlant.quantity = 1
                        holder.quantityPlant?.text = currentPlant.quantity.toString()
                        dialog.dismiss()

                    })

                val alert = builder.create()
                alert.setTitle("Supprimer un article du panier")
                alert.show()

            }

        }
    }

        override fun getItemCount(): Int {
            return plantList.size
        }
    }
