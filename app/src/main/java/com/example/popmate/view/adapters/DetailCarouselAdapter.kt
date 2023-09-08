package com.example.popmate.view.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.RowBannerBinding
import com.example.popmate.model.data.local.Banner
import com.example.popmate.model.data.local.PopupStoreImgResponse
import com.example.popmate.view.activities.detail.PopupDetailActivity


class DetailCarouselAdapter(
    private val detailImages: List<PopupStoreImgResponse>
) :
    RecyclerView.Adapter<DetailCarouselAdapter.DetailImageViewHolder>() {
    private var imageList: ArrayList<Banner>? = null


    inner class DetailImageViewHolder(private val binding: RowBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setItem(popupStoreImgResponse: PopupStoreImgResponse) {
            binding.popupStoreImgResponse = popupStoreImgResponse
            setImage(binding.bannerImage, popupStoreImgResponse.imgUrl)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val binding = RowBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailCarouselAdapter.DetailImageViewHolder, position: Int) {
        val detailImage = detailImages.get(position)
        detailImage.let { holder.setItem(it) }
    }




    override fun getItemCount(): Int {
        return detailImages.size!!
    }


    private fun setImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView)
            .load(imageUrl)
            .into(imageView)
    }

}


