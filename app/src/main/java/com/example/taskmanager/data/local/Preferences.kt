package com.example.taskmanager.data.local

import android.content.Context

class Preferences(private val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("preference.txt", Context.MODE_PRIVATE)


    fun getProfileData(): String {
        return sharedPreferences.getString("profileData", "") ?: ""
    }

    fun isSeen(): Boolean {
        return sharedPreferences.getBoolean(SHOWED_KEY, false)
    }

    fun onOnBoardingShowed() {
        sharedPreferences.edit().putBoolean(SHOWED_KEY, true).apply()
    }

    fun getProfilePhotoUri(): String? {
        return sharedPreferences.getString("profilePhotoUri", null)
    }

    fun saveProfilePhotoUri(photoUri: String?) {
        sharedPreferences.edit().putString("profilePhotoUri", photoUri).apply()
    }

    fun saveName(name: String) {
        sharedPreferences.edit().putString(NAME_KEY, name).apply()
    }

    fun getName(): String? {
        return sharedPreferences.getString(NAME_KEY, null)
    }

    companion object {
        const val NAME_KEY = "name.key"
        const val SHOWED_KEY = "seen.key"
        const val imgUri =
            "https://ih1.redbubble.net/image.343726250.4611/flat,1000x1000,075,f.jpg"
    }
}
