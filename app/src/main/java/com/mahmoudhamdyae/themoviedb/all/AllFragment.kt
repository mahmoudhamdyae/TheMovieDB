package com.mahmoudhamdyae.themoviedb.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mahmoudhamdyae.themoviedb.databinding.FragmentAllBinding

class AllFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAllBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }
}