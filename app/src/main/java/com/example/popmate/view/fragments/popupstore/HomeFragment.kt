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
import com.example.popmate.view.adapters.popupstore.BannerAdapter
import com.example.popmate.view.adapters.popupstore.PopupStoreAdapter
import com.example.popmate.viewmodel.popupstore.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.loadHome()
        viewModel.home.observe(viewLifecycleOwner) {
            binding.run {
                home = it
                imageCarousel.adapter = BannerAdapter(requireContext(), it.banners)
                imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                val indicator = binding.indicator
                indicator.setViewPager(binding.imageCarousel)

                visitedRecyclerView.adapter =
                    PopupStoreAdapter(requireContext(), it.popupStoresVisitedBy, PopupStoreAdapter.ViewHolderType.VISITED)
                visitedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                visitedLayout.visibility = if (it.popupStoresVisitedBy.isNullOrEmpty()) View.GONE else View.VISIBLE

                listView.adapter =
                    PopupStoreAdapter(requireContext(), it.popupStoresRecommend, PopupStoreAdapter.ViewHolderType.HORIZONTAL)
                listView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                endingSoonView.adapter = PopupStoreAdapter(
                    requireContext(),
                    it.popupStoresEndingSoon,
                    PopupStoreAdapter.ViewHolderType.VERTICAL_MEDIUM
                )
                endingSoonView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.homeShimmerLayout.startShimmer()
                binding.carouselShimmerLayout.startShimmer()
                binding.openingSoonShimmerLayout.startShimmer()
                binding.imageCarousel.visibility = View.GONE
                binding.listView.visibility = View.GONE
                binding.endingSoonView.visibility = View.GONE
                binding.homeShimmerLayout.visibility = View.VISIBLE
                binding.carouselShimmerLayout.visibility = View.VISIBLE
                binding.openingSoonShimmerLayout.visibility = View.VISIBLE
            } else {
                binding.homeShimmerLayout.stopShimmer()
                binding.carouselShimmerLayout.stopShimmer()
                binding.openingSoonShimmerLayout.stopShimmer()
                binding.imageCarousel.visibility = View.VISIBLE
                binding.listView.visibility = View.VISIBLE
                binding.endingSoonView.visibility = View.VISIBLE
                binding.homeShimmerLayout.visibility = View.GONE
                binding.carouselShimmerLayout.visibility = View.GONE
                binding.openingSoonShimmerLayout.visibility = View.GONE
            }
        }
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.home.removeObservers(viewLifecycleOwner)
    }


}
