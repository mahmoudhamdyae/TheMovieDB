package com.mahmoudhamdyae.themoviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //    private var wentToBrowser : Boolean = false
//    private var viewModelJob = Job()
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        wentToBrowser = false
    }

//    override fun onResume() {
//        super.onResume()
//        if (wentToBrowser) {
//            coroutineScope2.launch {
//                Toast.makeText(application, wentToBrowser.toString(), Toast.LENGTH_SHORT).show()
//                try {
//                    val s = MovieApi.retrofitService.createSessionAsync().await().success.toString()
//                    Toast.makeText(application, s, Toast.LENGTH_SHORT).show()
//                } catch (e: Exception) {
//                    Toast.makeText(application, "Error2: $e", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

//    fun logIn(item: MenuItem) {
//        Toast.makeText(application, "Log In Clicked in Main Activity", Toast.LENGTH_SHORT).show()
//        coroutineScope.launch {
//            try {
//                val requestToken = MovieApi.retrofitService.createRequestTokenAsync().await().requestToken
//                crateSession(requestToken)
//
//                // ERROR HERE
//                val s = MovieApi.retrofitService.createSessionAsync().await().success.toString()
//                Toast.makeText(application, s, Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                Toast.makeText(application, "Error: $e", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    private fun crateSession(requestToken: String) {
//        val url = "https://www.themoviedb.org/authenticate/$requestToken?redirect_to=http://themoviedb.mahmoudhamdyae.com"
//        val i = Intent(Intent.ACTION_VIEW)
//        i.data = Uri.parse(url)
//        startActivity(i)
//    }

//    override fun onStop() {
//        viewModelJob.cancel()
//        super.onStop()
//    }
}