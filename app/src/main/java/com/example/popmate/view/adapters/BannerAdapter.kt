package com.example.popmate.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.RowBannerBinding
import com.example.popmate.model.data.local.Banner


class BannerAdapter(private var banners: List<Banner>) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    private var bannerList: ArrayList<Banner>? = null

    inner class BannerViewHolder(private val binding: RowBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "클릭된 아이템 = ${binding.banner!!.popupStoreImgId}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        fun setItem(banner: Banner) {
            binding.banner = banner
            Glide.with(binding.bannerImage)
                .load(banner.imgUrl)
                .into(binding.bannerImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = RowBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = banners.get(position)
        banner?.let { holder.setItem(it) }
    }

    override fun getItemCount(): Int {
        return banners.size!!
    }


}
