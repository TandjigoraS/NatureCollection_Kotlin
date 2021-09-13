package fr.tandjigora.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.tandjigora.naturecollection.PlantRepository.Singleton.databaseRef
import fr.tandjigora.naturecollection.PlantRepository.Singleton.downloadURI
import fr.tandjigora.naturecollection.PlantRepository.Singleton.plantList
import fr.tandjigora.naturecollection.PlantRepository.Singleton.storageReference
import java.util.*
import javax.security.auth.callback.Callback

class PlantRepository {

    object Singleton{

        private val BUCKET_URL: String = "gs://naturecollection-b15d1.appspot.com/"

        // se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter à la référence "plants"
        val databaseRef = Firebase.database.getReference("plants")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        // contenir le lien de l'image courante
        var downloadURI : Uri? = null

    }

    fun updateData(callback: () -> Unit){
        databaseRef.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                plantList.clear()
                for (ds in snapshot.children) {
                    val plant = ds.getValue(PlantModel::class.java)

                    if (plant!=null) {
                        plantList.add(plant)
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        }

    // envoyer des fichiers sur le storage
    fun uploadImage(file : Uri, callback: () -> Unit){
        // verifier que ce fichier n'est pas null
        if (file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{  task ->

                if(!task.isSuccessful){
                    task.exception?.let { throw it}
                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{task ->
                if(task.isSuccessful){
                    // récupere l'image
                    downloadURI = task.result
                    callback()
                }
            }
        }
    }
    fun insertPlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    fun updatePlant(plant : PlantModel) = databaseRef.child(plant.id).setValue(plant)

    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()
    }



