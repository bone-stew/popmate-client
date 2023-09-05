package com.example.popmate.view.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
//import com.example.popmate.databinding.RowPopupstoreBinding
import com.example.popmate.databinding.RowPopupstoreHorizontalBinding
import com.example.popmate.databinding.RowPopupstoreVerticalBinding
import com.example.popmate.databinding.RowPopupstoreVisitedBinding
import com.example.popmate.model.data.local.PopupStore
import com.bumptech.glide.Glide



class PopupStoreAdapter(private var popupStores: List<PopupStore>, var viewHolderType: ViewHolderType) :
    RecyclerView.Adapter<PopupStoreAdapter.PopupStoreViewHolder>() {

    enum class ViewHolderType {
        HORIZONTAL,
        VERTICAL_LARGE,
        VERTICAL_MEDIUM,
        VERTICAL_SMALL,
        VISITED
    }

    enum class ImageSize {
        LARGE,
        MEDIUM,
        SMALL
    }

    inner class PopupStoreViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val popupStore = popupStores[adapterPosition]
                Toast.makeText(binding.root.context, "클릭된 아이템 = ${popupStore.title}", Toast.LENGTH_LONG).show()
            }
        }

        fun bind(popupStore: PopupStore, imageSize: ImageSize) {
            if (binding is RowPopupstoreHorizontalBinding) {
                binding.popupstore = popupStore
                Glide.with(binding.itemImageView)
                    .load(popupStore.bannerImgUrl)
                    .into(binding.itemImageView)
            } else if (binding is RowPopupstoreVerticalBinding) {
                binding.popupstore = popupStore
                adjustImageSize(binding.itemImageView, imageSize)
                Glide.with(binding.itemImageView)
                    .load(popupStore.bannerImgUrl)
                    .into(binding.itemImageView)
            } else if (binding is RowPopupstoreVisitedBinding) {
                binding.popupstore = popupStore
                Glide.with(binding.itemImageView)
                    .load(popupStore.bannerImgUrl)
                    .into(binding.itemImageView)
            }
//            setImage(binding, popupStore)
        }

//        private fun setImage(binding: ViewDataBinding, popupStore: PopupStore) {
//            binding.popupstore = popupStore
//            Glide.with(binding.itemImageView)
//                .load(popupStore.bannerImgUrl)
//                .into(binding.itemImageView)
//        }

        private fun adjustImageSize(imageView: ImageView, imageSize: ImageSize) {
            val layoutParams = imageView.layoutParams as ViewGroup.LayoutParams
            if (imageSize == ImageSize.LARGE) {
                layoutParams.width = 150.dpToPx()
                layoutParams.height = 150.dpToPx()
            } else if (imageSize == ImageSize.SMALL) {
                layoutParams.width = 90.dpToPx()
                layoutParams.height = 90.dpToPx()
            } else {
                layoutParams.width = 120.dpToPx()
                layoutParams.height = 120.dpToPx()
            }
            imageView.layoutParams = layoutParams
        }

        fun Int.dpToPx(): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (this * scale).toInt()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopupStoreViewHolder {
        val binding = when (viewType) {
            ViewHolderType.HORIZONTAL.ordinal -> RowPopupstoreHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            ViewHolderType.VERTICAL_LARGE.ordinal -> RowPopupstoreVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ViewHolderType.VERTICAL_MEDIUM.ordinal -> RowPopupstoreVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            ViewHolderType.VERTICAL_SMALL.ordinal -> RowPopupstoreVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            ViewHolderType.VISITED.ordinal -> RowPopupstoreVisitedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            else -> RowPopupstoreHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return PopupStoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopupStoreViewHolder, position: Int) {
        val popupStore = popupStores.get(position)
        val imageSize = if (viewHolderType == ViewHolderType.VERTICAL_LARGE) {
            ImageSize.LARGE
        } else if (viewHolderType == ViewHolderType.VERTICAL_SMALL) {
            ImageSize.SMALL
        } else {
            ImageSize.MEDIUM
        }
        popupStore?.let { holder.bind(it, imageSize) }
    }

    override fun getItemCount(): Int {
        return popupStores.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return viewHolderType.ordinal
    }


}
