package com.example.mymoviesapp.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpFragmen : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        btnSignUp()
        action()

    }

    private fun btnSignUp() {

        binding.btnSignUp.setOnClickListener {

            val username = binding.edtUserName.text.toString()
            val email = binding.edtEmailSignUp.text.toString()
            val password = binding.edtPasswordSignUp.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { authResult ->
                        val user = authResult.user
                        if (user != null) {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build()

                            user.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        firebaseAuth.signOut()
                                        Toast.makeText(
                                            requireContext(),
                                            "Registration was successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        findNavController().navigate(R.id.action_SignUpFragment_to_LogInFragment)
                                    }
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Fill in all the information", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
    private fun action(){
        binding.txtLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_SignUpFragment_to_LogInFragment)
        }
    }
}