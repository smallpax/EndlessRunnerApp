package com.example.endlessrunnergame
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

class Car (private val image: AppCompatImageView) {
        fun visible() {image.visibility = View.VISIBLE}
        fun invisible() {image.visibility = View.INVISIBLE}
}