package com.example.haircare.scanner

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.haircare.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import java.io.File


class Scanner : AppCompatActivity() {

    var databaseHandler: DBHelper? = null
    private val FILE_NAME = "text_photo"
    lateinit var loadImage: ImageView
    lateinit var textView: TextView
    lateinit var imageView: ImageView
    lateinit var layout: LinearLayout
    lateinit var listView: ListView
    lateinit var button: Button
    lateinit var recognizedText: Array<String>
    var name: String? = null
    var peh: String? = null
    private var description: String? = null
    var ingredients: MutableList<Ingredients> = mutableListOf()


    private val REQUEST_IMAGE_CAPTURE = 1
    lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        databaseHandler = DBHelper(this)
        layout = findViewById(R.id.layout_item_list)
        listView = findViewById(R.id.lv_item)
        loadImage = findViewById(R.id.loadImage)
        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)
        button=findViewById(R.id.btn_save_product)

        layout.visibility=View.GONE
        imageView.visibility = View.GONE



        loadImage.setOnClickListener { v: View ->
            dispatchTakePictureIntent()

        }

        button.setOnClickListener {
            //save listView
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
            detectTextFromImage(imageFull)
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

    private fun detectTextFromImage(imageInput: Bitmap) {

       // val fileProvider = FileProvider.getUriForFile(this, "com.example.haircare.fileprovider", photoFile)
        val image = InputImage.fromBitmap(imageInput, 0)
        val recognizer = TextRecognition.getClient()
        val result = recognizer.process(image)
            .addOnSuccessListener { firebaseVisionText ->
                // success
                displayTextFromImage(firebaseVisionText)
            }
            .addOnFailureListener { e ->
                // failed
            }
    }

    private fun displayTextFromImage(resultText: Text) {
        layout.visibility = View.VISIBLE
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
            resultText.text.toLowerCase()
                ?.split(delimiter1, delimiter2, delimiter3, delimiter4, delimiter5, delimiter6, delimiter7)
                ?.toTypedArray()!!

        for (item in recognizedText) {


            name = databaseHandler!!.viewCosmetic(item.trim()).name

            if (name != "nothing") {
                description = databaseHandler!!.viewCosmetic(item.trim()).description
                peh = databaseHandler!!.viewCosmetic(item.trim()).peh
                ingredients.add(Ingredients(name, description,peh))
            }
        }

        listView.adapter = MyAdapter(this, ingredients, R.layout.ingredients_view)
    }

}
