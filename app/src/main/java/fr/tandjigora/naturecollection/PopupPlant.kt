package fr.tandjigora.naturecollection

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import fr.tandjigora.naturecollection.adapter.PlantAdapter
import fr.tandjigora.naturecollection.fragments.BasketFragment

class PopupPlant(private val adapter: PlantAdapter, private val currentPlant: PlantModel) :
    Dialog(adapter.context) {

    private val repo: PlantRepository = PlantRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plants_details)

        setupComponents()
        setupClosePopup()
        setupStarButton()
        setupBasketButton()
    }


    private fun setupBasketButton(addBasket: Button) {

        if (currentPlant.inBasket) {
            addBasket.text = "VIEW TO BASKET"

        } else {
            addBasket.text = "ADD TO BASKET"
        }

    }


    private fun setupBasketButton() {
        val addBasket: Button = findViewById(R.id.add_plant_shopping_button)
        setupBasketButton(addBasket)
        addBasket.setOnClickListener {
            currentPlant.inBasket = true
            if(currentPlant.inBasket){
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)

            }
            repo.updatePlant(currentPlant)
            setupBasketButton(addBasket)

        }

    }

    private fun setupStarButton(button: ImageView) {
        if (currentPlant.liked) {
            button.setImageResource(R.drawable.ic_like)
        } else {
            button.setImageResource(R.drawable.ic_unlike)

        }

    }

    private fun setupStarButton() {
        val starButton = findViewById<ImageView>(R.id.star_button)
        setupStarButton(starButton)

        starButton.setOnClickListener {
            currentPlant.liked = !currentPlant.liked
            repo.updatePlant(currentPlant)
            setupStarButton(starButton)


        }

    }


    private fun setupClosePopup() {
        val closePlantDetails = findViewById<ImageView>(R.id.close_button)
        closePlantDetails.setOnClickListener {
            dismiss()

        }

    }

    private fun setupComponents() {
        // branchement de nos widgets
        val plantImage = findViewById<ImageView>(R.id.image_item)
        val plantName = findViewById<TextView>(R.id.popup_plant_name)
        val plantDescriptionSubtitle = findViewById<TextView>(R.id.popup_plant_description_subtitle)

        val plantGrowSubtitle = findViewById<TextView>(R.id.popup_plant_grow_subtitle)
        val plantWaterSubtitle = findViewById<TextView>(R.id.popup_plant_water_subtitle)

        // mettre à jour les informations de la plante
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)
        plantName.text = currentPlant.name
        plantDescriptionSubtitle.text = currentPlant.description
        plantGrowSubtitle.text = currentPlant.grow
        plantWaterSubtitle.text = currentPlant.water


    }

}