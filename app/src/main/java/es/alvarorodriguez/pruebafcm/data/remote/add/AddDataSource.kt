package es.alvarorodriguez.pruebafcm.data.remote.add

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import es.alvarorodriguez.pruebafcm.data.model.Cars
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class AddDataSource {

    suspend fun addCar(marca: String, description: String, imgCar: Bitmap) {

        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/cars/$randomName")
        val baos = ByteArrayOutputStream()
        imgCar.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()

        user?.let {
                FirebaseFirestore.getInstance().collection("cars").add(
                    Cars(brand = marca, description = description,
                        uid = user.uid, car_image = downloadUrl)).await()
        }
    }
}