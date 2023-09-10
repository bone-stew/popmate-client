package com.example.popmate.view.adapters.popupstore

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.RowBannerBinding
import com.example.popmate.model.data.local.PopupStoreImgResponse


class DetailCarouselAdapter(
    private val detailImages: List<PopupStoreImgResponse>
) :
    RecyclerView.Adapter<DetailCarouselAdapter.DetailImageViewHolder>() {


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

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
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


