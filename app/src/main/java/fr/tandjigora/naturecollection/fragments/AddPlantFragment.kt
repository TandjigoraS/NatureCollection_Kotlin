package fr.tandjigora.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import fr.tandjigora.naturecollection.MainActivity
import fr.tandjigora.naturecollection.PlantModel
import fr.tandjigora.naturecollection.PlantRepository
import fr.tandjigora.naturecollection.PlantRepository.Singleton.downloadURI
import fr.tandjigora.naturecollection.R
import java.util.*

class AddPlantFragment(private val context : MainActivity) : Fragment() {

    private var uploadImage:ImageView? = null
    private var file: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant,container,false)

        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)
        uploadImage = view.findViewById(R.id.preview_image)



        pickupImageButton.setOnClickListener{
            pickupImage()
        }
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)

        confirmButton.setOnClickListener{
           sendForm(view)
        }




        return view
    }

    private fun sendForm(view: View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!){
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl = downloadURI

            // construction du nouvel objet plant
            val plant = PlantModel(
                   UUID.randomUUID().toString(),
                    plantName,
                    plantDescription,
                    downloadImageUrl.toString(),
                    grow,
                    water,
            )

            // envoyer en bdd
            repo.insertPlant(plant)


        }




    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK){

            if (data == null || data.data == null) return

           file = data.data

            uploadImage?.setImageURI(file)


        }
    }
}