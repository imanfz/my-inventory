package com.imanfz.myinventory.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.imanfz.myinventory.R
import com.imanfz.myinventory.data.local.entity.EquipmentEntity
import com.imanfz.myinventory.data.local.entity.FriendEntity
import com.imanfz.myinventory.data.local.entity.LoanEntity
import com.imanfz.myinventory.databinding.FragmentLoanBinding
import com.imanfz.myinventory.viewmodel.AppViewModel
import com.imanfz.myinventory.viewmodel.ViewModelFactory

class LoanDialogFragment : DialogFragment() {

    private var _binding: FragmentLoanBinding? = null
    val binding get() = _binding

    companion object {
        @JvmStatic
        fun newInstance(equipmentEntity: EquipmentEntity) = LoanDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_EQUIPMENT, equipmentEntity)
            }
        }
    }

    private lateinit var appViewModel: AppViewModel
    private var equipmentEntity: EquipmentEntity? = null
    private var loanEntity: LoanEntity? = null
    private var selectedFriend: FriendEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoanBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupObserver()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.setLayout(width, height)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init() {
        binding?.btnSave?.btnText?.text = getString(R.string.ok)
        appViewModel = obtainViewModel()
        equipmentEntity = arguments?.getParcelable(EXTRA_EQUIPMENT)
        if (equipmentEntity != null) {
            binding?.equipmentName?.setText(equipmentEntity?.name)
        } else {
            this.dismiss()
        }
    }

    private fun setupObserver() {
        appViewModel.getAllFriend().observe(
            viewLifecycleOwner, {
                if (it.count() > 0) {
                    val friendName = mutableListOf<String>()
                    it?.forEach { friend ->
                        friend.name?.let { it1 -> friendName.add(it1) }
                    }
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        friendName
                    ).also { arrayAdapter ->
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding?.personName?.apply {
                            setAdapter(arrayAdapter)

                            setOnItemClickListener { _, _, position, _ ->
                                selectedFriend = it[position]
                                equipmentEntity?.id?.let { it1 ->
                                    selectedFriend?.id?.let { it2 ->
                                        appViewModel.checkLoan(
                                            it1,
                                            it2
                                        ).observe(
                                            viewLifecycleOwner, { loanEntity ->
                                                this@LoanDialogFragment.loanEntity = loanEntity
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    private fun setupListener() {
        binding?.apply {
            personName.apply {
                onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) showDropDown() else dismissDropDown()
                }

                setOnClickListener {
                    showDropDown()
                }
            }
            btnSave.root.setOnClickListener {
                setLoan()
            }
        }
    }

    private fun setLoan() {
        if (loanEntity != null) {
            if (equipmentEntity?.quantity != 0) {
                val data = loanEntity?.id?.let {
                    loanEntity?.friendId?.let { it1 ->
                        loanEntity?.equipmentId?.let { it2 ->
                            loanEntity?.count?.plus(1)?.let { it3 ->
                                LoanEntity(
                                    id = it,
                                    friendId = it1,
                                    equipmentId = it2,
                                    count = it3
                                )
                            }
                        }
                    }
                }
                if (data != null) {
                    appViewModel.updateLoan(data)
                    equipmentEntity?.id?.let {
                        EquipmentEntity(
                            id = it,
                            name = equipmentEntity?.name,
                            quantity = equipmentEntity?.quantity?.minus(1),
                            image = equipmentEntity?.image
                        )
                    }?.let {
                        appViewModel.updateEquipment(
                            it
                        )
                    }
                    context?.showToast(
                        "Success, ${selectedFriend?.name} borrowed another" +
                                "${equipmentEntity?.name}"
                    )
                    dismiss()
                }
            }
        } else {
            val data = equipmentEntity?.id?.let { it1 ->
                selectedFriend?.id?.let { it2 ->
                    LoanEntity(
                        equipmentId = it1,
                        friendId = it2,
                        count = 1
                    )
                }
            }
            if (data != null) {
                appViewModel.insertLoan(data)
                equipmentEntity?.id?.let {
                    EquipmentEntity(
                        id = it,
                        name = equipmentEntity?.name,
                        quantity = equipmentEntity?.quantity?.minus(1),
                        image = equipmentEntity?.image
                    )
                }?.let {
                    appViewModel.updateEquipment(
                        it
                    )
                }
                context?.showToast(
                    "Success, ${selectedFriend?.name} " +
                            "managed to borrow a motorbike"
                )
                dismiss()
            }
        }
    }

    private fun obtainViewModel(): AppViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(requireActivity(), factory).get(AppViewModel::class.java)
    }
}