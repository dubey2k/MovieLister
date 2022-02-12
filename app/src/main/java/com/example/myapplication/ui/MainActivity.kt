package com.example.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.fragments.MovieFragment
import com.example.myapplication.ui.fragments.MusicFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityMainBinding
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if(it?.resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager())
                            Toast.makeText(this,"Granted",Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(this,"Denied",Toast.LENGTH_LONG).show()
                    }
                }
            })

        val listFrag = MovieFragment()

        val movieFragment = MovieFragment()
        val musicFragment = MusicFragment()
        setCurrentFragment(movieFragment)
        if(!CheckPermission()){
            RequestPermission()
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.bottomContainerFragment,listFrag)
            commit()
        }
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottomNavMovie ->
                    setCurrentFragment(movieFragment)
                R.id.bottomNavMusic ->
                    setCurrentFragment(musicFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.bottomContainerFragment,fragment)
        commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)) {
                    // Permission was granted, yay! Do bind service

                } else {
                    // Permission denied, boo! Error!
                    Toast.makeText(this,"No Permissions",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun RequestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try{
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setData(Uri.parse("format:" + application.packageName))
                activityResultLauncher.launch(intent)
            }catch (e:Exception){
                val intent = Intent()
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                activityResultLauncher.launch(intent)
            }
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),101)
        }
    }
    fun CheckPermission(): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager()
        }else{
            val read = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
            val write = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if(read == write && read == PackageManager.PERMISSION_GRANTED)
                return true
            return false
        }
    }
}