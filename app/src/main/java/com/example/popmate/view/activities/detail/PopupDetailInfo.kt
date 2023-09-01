package com.example.popmate.view.activities.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupDetailInfoBinding
import com.example.popmate.model.data.local.Banner
import com.example.popmate.view.adapters.BannerAdapter

class PopupDetailInfo : Fragment() {

    companion object {
        fun newInstance() = PopupDetailInfo()
    }

    private lateinit var binding: FragmentPopupDetailInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopupDetailInfoBinding.inflate(inflater, container, false)
        val bannersFromApi = getSampleBanner()
        binding.imageCarousel.adapter = BannerAdapter(bannersFromApi)
        binding.imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator = binding.indicator
        indicator.setViewPager(binding.imageCarousel)
        return binding.root;
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
}