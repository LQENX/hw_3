package com.mycompany.hw_3_2

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.mycompany.hw_3_2.databinding.ActivityMainBinding
import com.mycompany.hw_3_2.transformations.CircleTransformation
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://picsum.photos"
    private val IMAGE_SIZE = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        initButtonListener()
    }

    private fun initButtonListener() {
        binding.buttonGet.setOnClickListener {
            if (hasConnection())
                loadImage()
            else
                sendToast("No internet connection")
        }
    }

    private fun loadImage() {
        val image = binding.editUrl.text.toString()
        if (image.isNotBlank()) {
            if (image.contains(BASE_URL)) {
                getRandomImage(image)
                sendToast("Get random image")
            } else if (image.isDigitsOnly() && image.length <= 4) {
                getSquareImage(image)
                sendToast("Get square image by id: $image")
            } else sendToast("Type link with 'https://' or id <= 4", Toast.LENGTH_LONG)
        } else
            sendToast("Type link in text field")
    }

    private fun getRandomImage(imageUrl: String) {
        Picasso
                .with(this)
                .load(imageUrl)
                .transform(CircleTransformation())
                .into(binding.image)
    }

    private fun getSquareImage(imageId: String) {
        val squareImageUrl = "$BASE_URL/id/$imageId/$IMAGE_SIZE"
        Picasso
                .with(this)
                .load(squareImageUrl)
                .transform(CircleTransformation())
                .into(binding.image)
    }

    private fun hasConnection(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        return networkInfo?.isConnectedOrConnecting ?: false
    }

    private fun sendToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}