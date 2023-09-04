package com.example.popmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.databinding.FragmentHomeBinding
import com.example.popmate.model.data.local.Banner
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.adapters.BannerAdapter
import com.example.popmate.view.adapters.PopupStoreAdapter
import java.util.Date



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // get data from api
        val popupStoresFromApi = getDataFromApi()
        val bannersFromApi = getSampleBanner()
        binding.imageCarousel.adapter = BannerAdapter(bannersFromApi)
        binding.imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.visitedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.visitedRecyclerView.adapter = PopupStoreAdapter(popupStoresFromApi, PopupStoreAdapter.ViewHolderType.VISITED)

        binding.visitedLayout.visibility = if (visitedByIsNotNull()) View.VISIBLE else View.GONE


        binding.listView.adapter = PopupStoreAdapter(popupStoresFromApi, PopupStoreAdapter.ViewHolderType.HORIZONTAL)
        binding.listView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val indicator = binding.indicator
        indicator.setViewPager(binding.imageCarousel)

        binding.horizontalView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalView.adapter = PopupStoreAdapter(popupStoresFromApi, PopupStoreAdapter.ViewHolderType.VERTICAL_MEDIUM)
        return binding.root
    }

    private fun visitedByIsNotNull():Boolean {
        return true
    }

    private fun getSampleBanner(): List<Banner> {
        val sampleData = mutableListOf<Banner>(
            Banner(
                id= 1,
                imgUrl = "url to 1"
            ),
            Banner(
                id= 2,
                imgUrl = "url to 1"
            ),
            Banner(
                id= 3,
                imgUrl = "url to 1"
            ),
            Banner(
                id= 4,
                imgUrl = "url to 1"
            ),
            Banner(
                id= 5,
                imgUrl = "url to 1"
            )
        )
        return sampleData
    }
    private fun getDataFromApi() : List<PopupStore> {
        var sampleData = mutableListOf<PopupStore>(
                   PopupStore(
                       id = 1,
                       title = "팝업스토어 1",
                       openDate = Date(), // Replace with actual open date
                       closeDate = Date(),
                       location = "더현대",// Replace with actual close date
                       organizer = "주최자 1",
                       imgUrl = "url_to_image_1"
                   ),
                   PopupStore(
                       id = 2,
                       title = "팝업스토어 2",
                       openDate = Date(), // Replace with actual open date
                       closeDate = Date(), // Replace with actual close date
                       location = "더현대",// Replace with actual close date
                       organizer = "주최자 2",
                       imgUrl = "url_to_image_2"
                   ),
                   PopupStore(
                       id = 3,
                       title = "팝업스토어 3",
                       openDate = Date(), // Replace with actual open date
                       closeDate = Date(), // Replace with actual close date
                       location = "더현대",// Replace with actual close date
                       organizer = "주최자 3",
                       imgUrl = "url_to_image_3"
                   ),
                   PopupStore(
                       id = 4,
                       title = "팝업스토어 4",
                       openDate = Date(), // Replace with actual open date
                       closeDate = Date(), // Replace with actual close date
                       location = "더현대",// Replace with actual close date
                       organizer = "주최자 4",
                       imgUrl = "url_to_image_4"
                   ),
                   PopupStore(
                       id = 5,
                       title = "팝업스토어 5",
                       openDate = Date(), // Replace with actual open date
                       closeDate = Date(), // Replace with actual close date
                       location = "더현대",// Replace with actual close date
                       organizer = "주최자 5",
                       imgUrl = "url_to_image_5"
                   )
               )


        return sampleData
    }



}
