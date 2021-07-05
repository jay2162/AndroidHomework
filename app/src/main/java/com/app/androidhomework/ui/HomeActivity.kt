package com.app.androidhomework.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.app.androidhomework.R
import com.app.androidhomework.network.SessionManager
import com.theartofdev.edmodo.cropper.CropImage
import id.zelory.compressor.Compressor
import java.io.File

class HomeActivity : AppCompatActivity() {

    lateinit var listenr: getProfileImage
    private var broadcastReceiver: BroadcastReceiver? = null
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        session = SessionManager(this)

//        navController().navigate(R.id.splashFragment)

        val meMap = HashMap<String, String>()
        if (intent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras!![key]
                meMap.put(key, value.toString())
                Log.d("MainActivity: ", "Key: $key Value: $value")
            }
        }

        session.setStringDataByKey("noti", "")
        if (meMap.containsKey("body")) {
            if (meMap.get("body")!!.contains("message")) {
                session.setStringDataByKey("noti", "message")
            } else if (meMap.get("body")!!.contains("like")) {
                session.setStringDataByKey("noti", "like")
            } else {
                session.setStringDataByKey("noti", "")
            }
        } else {
            session.setStringDataByKey("noti", "")
        }

    }

    private fun navController() = findNavController(R.id.navHostFragment)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                var profileFile = Compressor(this).compressToFile(File(result.uri.path!!))
                listenr.image(profileFile)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }
    }

    public interface getProfileImage {
        fun image(profileFile: File)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}