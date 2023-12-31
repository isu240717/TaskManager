package com.example.taskmanager.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.data.local.Preferences
import com.example.taskmanager.databinding.FragmentOnBoardingBinding
import com.example.taskmanager.ui.onboarding.adapter.OnBoardingAdapter
import me.relex.circleindicator.CircleIndicator3

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private val adapter = OnBoardingAdapter(this::onClick)
    private val preference: Preferences by lazy {
        Preferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager.adapter = adapter
        setIndicator()

    }

    private fun setIndicator() {
        val indicator: CircleIndicator3 = binding.dotsBoard
        val viewPager = binding.viewpager
        indicator.setViewPager(viewPager)
    }

    private fun onClick() {
        preference.onOnBoardingShowed()
        findNavController().navigateUp()
    }


}