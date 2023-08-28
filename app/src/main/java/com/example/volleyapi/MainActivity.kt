package com.example.volleyapi

import android.app.DownloadManager
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.volleyapi.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
//calling the API
    //using volley library
    val url:String="https://meme-api.com/gimme"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         getMemeData()

        binding.newMeme.setOnClickListener {
            getMemeData()
        }
    }

     fun getMemeData(){

         //creating progress bar with message
       val progressDialog=ProgressDialog(this)
         progressDialog.setMessage("Please wait while data is fetch")
         progressDialog.show()


// Instantiate the RequestQueue.
         val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
         val stringRequest = StringRequest(
             Request.Method.GET, url,
             { response ->
                 // Display the first 500 characters of the response string.
              Log.e("Response","getMemeData:" + response.toString())

                 var responseObject=JSONObject(response)


                 responseObject.get("postLink")


                 binding.memeTitle.text=responseObject.getString("title")
                 binding.memeAuthor.text= responseObject.getString("author")
                // binding.memeImage
                 //using glidelib to load network image
                 Glide.with(this).load( responseObject.get("url")).into(binding.memeImage)

                 progressDialog.dismiss()



             },
             {
               error -> Toast.makeText(this@MainActivity,"${error.localizedMessage}",Toast.LENGTH_SHORT)
                 progressDialog.dismiss()

             })

// Add the request to the RequestQueue.
         queue.add(stringRequest)
     }
}
