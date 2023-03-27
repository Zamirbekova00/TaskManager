package com.example.taskmanager.utils
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide


fun ImageView.loadImage(uri: String?){
    Glide.with(this).load(uri).into(this)
}