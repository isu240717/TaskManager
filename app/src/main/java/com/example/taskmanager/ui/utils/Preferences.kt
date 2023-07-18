package com.example.taskmanager.ui.utils

import android.content.Context

class Preferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("preference.txt", Context.MODE_PRIVATE)

    fun setBoardingShowed(isShow: Boolean) {
        sharedPreferences.edit().putBoolean("board", isShow).apply()
    }

    fun isBoardingShowed(): Boolean {
        return sharedPreferences.getBoolean("board", false)
    }

    fun getProfileData(): String {
        return sharedPreferences.getString("profileData", "") ?: ""
    }

    fun saveProfileData(profileData: String) {
        sharedPreferences.edit().putString("profileData", profileData).apply()
    }

    fun getProfilePhotoUri(): String? {
        return sharedPreferences.getString("profilePhotoUri", null)
    }

    fun saveProfilePhotoUri(photoUri: String?) {
        sharedPreferences.edit().putString("profilePhotoUri", photoUri).apply()
    }

    companion object {
        const val imgUri = "https://ih1.redbubble.net/image.343726250.4611/flat,1000x1000,075,f.jpg"
    }
}
