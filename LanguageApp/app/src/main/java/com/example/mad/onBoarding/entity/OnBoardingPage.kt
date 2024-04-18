package com.example.mad.onBoarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mad.R

enum class OnBoardingPage(
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int,
    @DrawableRes val logoResource: Int
) {

    ONE(
        R.string.onboarding_slide1_title,
        R.string.onboarding_slide1_desc,
        R.drawable.first_onboard_image
    ),
    TWO(
        R.string.onboarding_slide2_title,
        R.string.onboarding_slide2_desc,
        R.drawable.second_onboard_image
    ),
    THREE(
        R.string.onboarding_slide3_title,
        R.string.onboarding_slide3_desc,
        R.drawable.third_onboard_image
    )

}