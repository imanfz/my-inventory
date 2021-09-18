package com.imanfz.myinventory.ui.home

import android.os.Bundle
import android.view.View
import com.imanfz.myinventory.databinding.FragmentHomeBinding
import com.imanfz.myinventory.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}