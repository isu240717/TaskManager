package com.example.taskmanager.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentAuthBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var signInRequest: BeginSignInRequest
    private val REQ_ONE_TAP = 2
    private val showOneTapUI = true
    private lateinit var oneTapCLient: SignInClient
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPhone.setOnClickListener {
            findNavController().navigate(R.id.phoneFragment)
        }

        oneTapCLient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
        binding.btnGmail.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        oneTapCLient.beginSignIn(signInRequest).addOnSuccessListener {
            startIntentSenderForResult(
                it.pendingIntent.intentSender, REQ_ONE_TAP, null, 0, 0, 0, null
            )
        }.addOnFailureListener {
            Log.d("ololo", "signingGoogle: " + it.message)
        }

    }

    //    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?,
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            REQ_ONE_TAP -> try {
//                val credential: SignInCredential = oneTapCLient.getSignInCredentialFromIntent(data)
//                val idToken = credential.googleIdToken
//                if (idToken != null) {
//                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                    auth.signInWithCredential(credential)
//                        .addOnCompleteListener(requireActivity(),
//                            OnCompleteListener<AuthResult?> { task ->
//                                if (task.isSuccessful) {
//                                    findNavController().navigateUp()
//
//                                } else {
//                                    Log.d("olol", "onActivityResult: " + task.exception)
//                                }
//                            })
//                }
//            } catch (_: ApiException) {
//            }
//        }
//    }
    private fun firebaseAuthWithGoogle(IdToken: String) {
        val credential = GoogleAuthProvider.getCredential(IdToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.navigation_home)
            } else {
                Toast.makeText(context, "ERROR GOOGLE SERVER", Toast.LENGTH_SHORT).show()
            }
        }
    }

}