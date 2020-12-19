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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.haircare.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_scanner.*
import java.io.File


class Scanner : AppCompatActivity() {
    var databaseHandler: DB_Helper? = null
    private val FILE_NAME = "text_photo"
    lateinit var loadImage: Button
    lateinit var textView: TextView
    lateinit var imageView: ImageView

    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var photoFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        databaseHandler = DB_Helper(this)


        loadImage = findViewById(R.id.loadImage)
        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)

        imageView.visibility = View.GONE


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
            textView.text = "Sprawdzam..."
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


    fun detectTextFromImage() {
        val image: FirebaseVisionImage

        val fileProvider = FileProvider.getUriForFile(this, "com.example.haircare.fileprovider", photoFile)
        image = FirebaseVisionImage.fromFilePath(this, fileProvider)

        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer
        val result = detector.processImage(image)
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
        for (block in firebaseVisionText?.textBlocks!!) {

            val blockText = block.text

            for (line in block.lines) {

                val lineText = line.text
                val data = lineText.split(",").toTypedArray()
              //  val cosmetic: Cosmetics = databaseHandler!!.viewCosmetic("test")
                textView.text = blockText
                DB_name.text = getItem().name + ": " +getItem().description
                //DB_description.text = getItem().description

                for (element in line.elements) {
                    val elementText = element.text

                }
            }
        }
    }

    fun getItem(): Cosmetics {
        //val nameField = textView.text.

        return databaseHandler!!.viewCosmetic(textView.text as String)
    }
}
