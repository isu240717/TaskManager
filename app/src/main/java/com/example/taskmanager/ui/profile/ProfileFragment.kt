package com.example.taskmanager.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.taskmanager.databinding.FragmentProfileBinding
import com.example.taskmanager.extensions.loadImage
import com.example.taskmanager.data.local.Preferences

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        preferences = Preferences(requireContext())

        initViews()
        initListeners()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.etProfile.setText(preferences.getProfileData())

        val photoUri = preferences.getProfilePhotoUri()
        if (!photoUri.isNullOrEmpty()) {
            binding.imgProfile.loadImage(photoUri)
        } else {
            binding.imgProfile.loadImage(Preferences.imgUri)
        }
    }


    private fun initListeners() {
        binding.imgProfile.setOnClickListener {
            openGalleryForImage()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
            selectedImage?.let {
                binding.imgProfile.loadImage(it.toString())
                preferences.saveProfilePhotoUri(it.toString())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etProfile.setText(preferences.getName())
        binding.etProfile.addTextChangedListener {
            preferences.saveName(binding.etProfile.text.toString())
            binding.etProfile.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    preferences.saveName(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }
}