package com.imanfz.myinventory.presentation.home

import android.os.Bundle
import android.view.View
import com.imanfz.myinventory.databinding.FragmentHomeBinding
import com.imanfz.myinventory.presentation.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}