package com.example.mad.onBoarding.core

import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.mad.R

fun setParallaxTransformation(page: View, position: Float) {
    page.apply {
        val parallaxView = this.findViewById<ImageView>(R.id.img)
        when {
            position < -1 ->
                alpha = 1f

            position <= 1 -> {
                parallaxView.translationX = -position * (width / 2)
            }

            else ->
                alpha = 1f
        }
    }

}