package com.example.haircare.scanner

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.haircare.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.io.File


class Scanner : AppCompatActivity() {

    var databaseHandler: DB_Helper? = null
    private val FILE_NAME = "text_photo"
    lateinit var loadImage: Button
    lateinit var textView: TextView
    lateinit var imageView: ImageView
    lateinit var listView: ListView
    lateinit var recognizedText: Array<String>
    var name: String? = null
    private var description: String? = null
    var ingredients: MutableList<Ingredients> = mutableListOf()


    private val REQUEST_IMAGE_CAPTURE = 1
    lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        databaseHandler = DB_Helper(this)

        listView = findViewById(R.id.lv_item)
        loadImage = findViewById(R.id.loadImage)
        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)

        // listView.adapter=MyAdapter(this)
        imageView.visibility = View.GONE
        listView.visibility = View.GONE


        loadImage.setOnClickListener { v: View ->
            dispatchTakePictureIntent()

        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(FILE_NAME, ".jpg", storageDir)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageFull = BitmapFactory.decodeFile(photoFile.absolutePath)
            imageView.setImageBitmap(imageFull)
            loadImage.visibility = View.GONE
            textView.text = getString(R.string.checking_text)
            detectTextFromImage()
        }
    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(FILE_NAME)

        val fileProvider = FileProvider.getUriForFile(this, "com.example.haircare.fileprovider", photoFile)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        try {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // error for user
        }

    }

    private fun detectTextFromImage() {
        val image: FirebaseVisionImage

        val fileProvider = FileProvider.getUriForFile(this, "com.example.haircare.fileprovider", photoFile)
        image = FirebaseVisionImage.fromFilePath(this, fileProvider)

        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                // success
                displayTextFromImage(firebaseVisionText)
            }
            .addOnFailureListener { e ->
                // failed
            }
    }

    private fun displayTextFromImage(firebaseVisionText: FirebaseVisionText?) {
        val resultText = firebaseVisionText?.text
        listView.visibility = View.VISIBLE
        loadImage.visibility = View.GONE
        textView.visibility = View.GONE


        val delimiter1 = "-"
        val delimiter2 = ","
        val delimiter3 = "*"
        val delimiter4 = "•"
        val delimiter5 = ":"
        val delimiter6 = "."
        val delimiter7 = "/"

        recognizedText =
            resultText?.toLowerCase()
                ?.split(delimiter1, delimiter2, delimiter3, delimiter4, delimiter5, delimiter6, delimiter7)
                ?.toTypedArray()!!

        for (item in recognizedText) {


            name = databaseHandler!!.viewCosmetic(item.trim()).name

            if (name != "nic") {
                description = databaseHandler!!.viewCosmetic(item.trim()).description
                ingredients.add(Ingredients(name, description))
            }
        }

        listView.adapter = MyAdapter(this, ingredients, R.layout.item_listview)
    }

}
