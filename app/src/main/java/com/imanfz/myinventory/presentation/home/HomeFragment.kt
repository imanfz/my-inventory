package com.imanfz.myinventory.presentation.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.imanfz.myinventory.R
import com.imanfz.myinventory.databinding.FragmentHomeBinding
import com.imanfz.myinventory.presentation.base.BaseFragment
import com.imanfz.myinventory.presentation.equipment.EquipmentActivity
import com.imanfz.myinventory.presentation.home.adapter.EquipmentAdapter
import com.imanfz.myinventory.utils.hide
import com.imanfz.myinventory.utils.show
import com.imanfz.myinventory.viewmodel.AppViewModel
import com.imanfz.myinventory.viewmodel.ViewModelFactory
import org.jetbrains.anko.startActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private lateinit var equipmentViewModel : AppViewModel
    private lateinit var equipmentAdapter : EquipmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(getString(R.string.app_name))
        setupObserver()
        setupView()
        setupListener()
    }

    private fun setupObserver() {
        equipmentViewModel = obtainViewModel()
        equipmentViewModel.getAllEquipment().observe(
            viewLifecycleOwner, {
                if (it.count() > 0) {
                    binding?.apply {
                        rvHome.show()
                        layoutEmpty.root.hide()
                    }
                    equipmentAdapter.setListEquipment(it)
                } else {
                    binding?.apply {
                        rvHome.hide()
                        layoutEmpty.root.show()
                    }
                }
            }
        )
    }

    private fun setupView() {
        equipmentAdapter = EquipmentAdapter()
        binding?.rvHome?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = equipmentAdapter
        }
    }

    private fun setupListener() {
        binding?.fabAdd?.setOnClickListener {
            activity?.startActivity<EquipmentActivity>()
        }
    }

    private fun obtainViewModel(): AppViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(requireActivity(), factory).get(AppViewModel::class.java)
    }
}