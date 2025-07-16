package com.example.mymoviesapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        btnLogin()
        action()
        checkLogin()

    }

    private fun btnLogin() {
        binding.btnLogIn.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        findNavController().navigate(R.id.action_logInFragment_to_HomeFragment)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Email and password cannot be empty!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun checkLogin() {
        if (firebaseAuth.currentUser != null) {
            findNavController().navigate(R.id.action_logInFragment_to_HomeFragment)
        }
    }

    private fun action() {
        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_SignUpFragment)
        }
    }
}