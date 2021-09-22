package com.imanfz.myinventory.presentation.friend

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.imanfz.myinventory.databinding.FragmentFriendBinding
import com.imanfz.myinventory.presentation.base.BaseFragment
import com.imanfz.myinventory.presentation.friend.adapter.FriendAdapter
import com.imanfz.myinventory.presentation.friend.detail.DetailFriendActivity
import com.imanfz.myinventory.utils.hide
import com.imanfz.myinventory.utils.show
import com.imanfz.myinventory.viewmodel.AppViewModel
import com.imanfz.myinventory.viewmodel.ViewModelFactory
import org.jetbrains.anko.startActivity

class FriendFragment : BaseFragment<FragmentFriendBinding>(
    FragmentFriendBinding::inflate
) {

    private lateinit var appViewModel: AppViewModel
    private lateinit var friendAdapter: FriendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar("My Friend")
        setupObserver()
        setupView()
        setupListener()
    }

    private fun setupObserver() {
        appViewModel = obtainViewModel()
        appViewModel.getAllFriend().observe(
            viewLifecycleOwner, {
                if (it.count() > 0) {
                    binding?.apply {
                        rvFriend.show()
                        layoutEmpty.root.hide()
                    }
                    friendAdapter.setListFriend(it)
                } else {
                    binding?.apply {
                        rvFriend.hide()
                        layoutEmpty.root.show()
                    }
                }
            }
        )
    }

    private fun setupView() {
        friendAdapter = FriendAdapter()
        binding?.rvFriend?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = friendAdapter
        }
    }

    private fun setupListener() {
        binding?.fabAdd?.setOnClickListener {
            activity?.startActivity<DetailFriendActivity>()
        }
    }

    private fun obtainViewModel(): AppViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(requireActivity(), factory).get(AppViewModel::class.java)
    }
}