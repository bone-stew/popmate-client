package com.example.popmate.view.fragments.popupstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.R
import com.example.popmate.databinding.FragmentHomeBinding
import com.example.popmate.model.data.local.Banner
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.adapters.BannerAdapter
import com.example.popmate.view.adapters.PopupStoreAdapter
import com.example.popmate.viewmodel.popupstore.HomeViewModel


class HomeFragment : Fragment() {

   private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
                              override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false )
        viewModel.getHome().observe(viewLifecycleOwner) {
            binding.run {
                home = it
                imageCarousel.adapter = BannerAdapter(it.banners)
                imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                val indicator = binding.indicator
                indicator.setViewPager(binding.imageCarousel)

                visitedRecyclerView.adapter = PopupStoreAdapter(it.popupStoresVisitedBy, PopupStoreAdapter.ViewHolderType.VISITED)
                visitedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                visitedLayout.visibility = if (it.popupStoresVisitedBy.isNullOrEmpty())  View.GONE else View.VISIBLE


                listView.adapter = PopupStoreAdapter(it.popupStoresRecommend, PopupStoreAdapter.ViewHolderType.HORIZONTAL)
                listView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


                endingSoonView.adapter = PopupStoreAdapter(it.popupStoresEndingSoon, PopupStoreAdapter.ViewHolderType.VERTICAL_MEDIUM)
                endingSoonView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        viewModel.refreshHome()
    }





}
