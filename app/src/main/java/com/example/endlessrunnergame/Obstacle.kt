package com.example.endlessrunnergame

import android.view.View
import androidx.appcompat.widget.AppCompatImageView

class Obstacle (private val obstacle: AppCompatImageView) {
        fun visible() {obstacle.visibility = View.VISIBLE }
        fun invisible() {obstacle.visibility = View.INVISIBLE }

}