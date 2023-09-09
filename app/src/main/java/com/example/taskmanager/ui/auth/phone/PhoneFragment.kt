package com.example.taskmanager.ui.auth.phone

import PhoneNumberTextWatcher
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class PhoneFragment : Fragment() {

    private lateinit var binding: FragmentPhoneBinding

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {}

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            findNavController().navigate(
                R.id.verifyFragment,
                bundleOf(VERIFY_KEY to verificationId)
            )
        }
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNumberEditText = binding.etPhone

        // Add the "+996" prefix initially
        phoneNumberEditText.setText("+996 ")

        val phoneNumberTextWatcher = PhoneNumberTextWatcher(phoneNumberEditText)
        phoneNumberEditText.addTextChangedListener(phoneNumberTextWatcher)

        binding.btnSend.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            if (phoneNumber.isNotBlank() && phoneNumber.length >= 8) {
                val formattedPhoneNumber =
                    phoneNumberTextWatcher.getFormattedPhoneNumber(phoneNumber)
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(formattedPhoneNumber) // Get the formatted phone number (with prefix and spaces)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(requireActivity())
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }
    }

    companion object {
        const val VERIFY_KEY = "ver_key"
    }

}