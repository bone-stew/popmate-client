package com.example.popmate.view.adapters.popupstore

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.RowBannerBinding
import com.example.popmate.model.data.local.Banner
import com.example.popmate.view.activities.detail.PopupDetailActivity


class BannerAdapter(
    private val context: Context,
    private val banners: List<Banner>
) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(private val binding: RowBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val banner = banners[adapterPosition]

                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", banner.popupStoreId)
                Log.d("DETAIL", banner.popupStoreId.toString())


                context.startActivity(intent)
            }
        }

        fun setItem(banner: Banner) {
            binding.banner = banner
            setImage(binding.bannerImage, banner.imgUrl)
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


    private fun setImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView)
            .load(imageUrl)
            .into(imageView)
    }

}
