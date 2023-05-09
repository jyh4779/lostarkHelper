package com.android.lostarkraid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lostarkraid.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    var _binding : FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.baltanBtn.setOnClickListener {
            if(binding.baltanLayout.getVisibility() == View.INVISIBLE) {
                binding.baltanLayout.setVisibility(View.VISIBLE)
            } else if(binding.baltanLayout.getVisibility() == View.VISIBLE) {
                binding.baltanLayout.setVisibility(View.INVISIBLE)
            }
        }

        binding.biakisBtn.setOnClickListener {
            if(binding.biakisLayout.getVisibility() == View.INVISIBLE) {
                binding.biakisLayout.setVisibility(View.VISIBLE)
            } else if(binding.biakisLayout.getVisibility() == View.VISIBLE) {
                binding.biakisLayout.setVisibility(View.INVISIBLE)
            }
        }
        binding.koukuBtn.setOnClickListener {
            if(binding.koukuLayout.getVisibility() == View.INVISIBLE) {
                binding.koukuLayout.setVisibility(View.VISIBLE)
            } else if(binding.koukuLayout.getVisibility() == View.VISIBLE) {
                binding.koukuLayout.setVisibility(View.INVISIBLE)
            }
        }

        binding.abrelshud.setOnClickListener {
            if(binding.abrelLayout.getVisibility() == View.INVISIBLE) {
                binding.abrelLayout.setVisibility(View.VISIBLE)
            } else if(binding.abrelLayout.getVisibility() == View.VISIBLE) {
                binding.abrelLayout.setVisibility(View.INVISIBLE)
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(this.javaClass.name, "finish view")
        _binding = null
    }
}