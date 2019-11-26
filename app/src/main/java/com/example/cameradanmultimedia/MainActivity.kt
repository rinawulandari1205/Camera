package com.example.cameradanmultimedia

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Button untuk mengambil gambar dari kamera
        img_view.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(i, 123)
        }

        //button klik
        img_galeri.setOnClickListener {

            //mengecek permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission akses ditolak
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    //permission akses disetujui
                    pickImageFromGallery();
                }
            }
        }
    }
    //methode untuk mengambil image dari galery
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults [0] == PackageManager.PERMISSION_DENIED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 123)
            {
                var bmp = data?.extras?.get("data")as? Bitmap
                imageView.setImageBitmap(bmp)
            }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageView.setImageURI(data?.data)
        }
    }
}


