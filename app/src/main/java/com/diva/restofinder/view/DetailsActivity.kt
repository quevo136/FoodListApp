package com.diva.restofinder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diva.restofinder.R
import com.diva.restofinder.uitel.loadImage
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //val intss = intent
        val intss = getIntent()
        var nameT = intss.getStringExtra("NAMET")
        var addT = intss.getStringExtra("ADDRESST")
        var phoneT = intss.getStringExtra("PHONET")
        var desT = intss.getStringExtra("DESCRIT")
        var imgT = intss.getStringExtra("IMGURI")

        nameDetailTextView.text = nameT
        addressDetailTextView.text = addT
        phoneNumberTextView.text = phoneT
        descriptionDetailTextView.text = desT

        teacherDetailImageView.loadImage(imgT)


    }
}