package com.example.mymoviesapp.ui.splash

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animation()
    }

    private fun animation() {
        binding.lottieAnimation.repeatCount = 0

        binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationEnd(p0: Animator) {
                lifecycleScope.launch {
                    //delay(1000)
                    findNavController().navigate(R.id.action_splashFragment_to_LogInFragment)
                    //requireActivity().finish()
                }
            }

            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
        })
    }
}