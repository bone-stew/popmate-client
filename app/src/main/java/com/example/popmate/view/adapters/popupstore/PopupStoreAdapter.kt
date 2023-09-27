package com.example.popmate.view.adapters.popupstore

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.R
import com.example.popmate.databinding.*
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.activities.detail.PopupDetailActivity
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

class PopupStoreAdapter(
    private val context: Context,
    private val popupStores: List<PopupStore>,
    private val viewHolderType: ViewHolderType
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewHolderType {
        HORIZONTAL,
        VERTICAL_LARGE_GRID,
        VERTICAL_LARGE,
        VERTICAL_MEDIUM,
        VERTICAL_SMALL,
        VISITED,
        SHIMMER
    }

    enum class ImageSize {
        EXTRA_LARGE,
        LARGE,
        MEDIUM,
        SMALL
    }

    inner class HorizontalViewHolder(private val binding: RowPopupstoreHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val popupStore = popupStores[adapterPosition]
                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", popupStore.popupStoreId)
                context.startActivity(intent)
            }
        }

        fun bind(popupStore: PopupStore, imageSize: ImageSize) {
            binding.popupstore = popupStore
            binding.itemImageView.clipToOutline = true
            setImage(binding.itemImageView, popupStore.bannerImgUrl)
            adjustImageSize(binding.itemImageView, imageSize)
        }
    }

    inner class VerticalViewHolder(private val binding: RowPopupstoreVerticalBinding, private val isGridLayout: Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val popupStore = popupStores[adapterPosition]
                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", popupStore.popupStoreId)
                context.startActivity(intent)
            }
        }

        fun bind(popupStore: PopupStore, imageSize: ImageSize, position: Int) {
            binding.itemImageView.clipToOutline = true
            binding.popupstore = popupStore
            setImage(binding.itemImageView, popupStore.bannerImgUrl)
            adjustImageSize(binding.itemImageView, imageSize)

            if (imageSize == ImageSize.MEDIUM || imageSize == ImageSize.LARGE) {
                val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.leftMargin = dpToPx(16)
                binding.root.layoutParams = layoutParams
            }
            val isOdd = popupStores.size % 2 == 1

            if (isGridLayout) {
                if (isOdd) {
                    if (position >= popupStores.size - 1) {
                        setLayout(binding)
                    }
                } else if (!isOdd) {
                    if (position >= popupStores.size - 2) {
                        setLayout(binding)
                    }
                }
            }
        }

        fun setLayout(binding: RowPopupstoreVerticalBinding) {
            val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            binding.root.layoutParams = layoutParams
        }
    }

    inner class VerticalSmallViewHolder(private val binding: RowPopupstoreVerticalSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val popupStore = popupStores[adapterPosition]
                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", popupStore.popupStoreId)
                context.startActivity(intent)
            }
        }

        fun bind(popupStore: PopupStore, imageSize: ImageSize, position: Int) {
            binding.popupstore = popupStore
            binding.itemImageView.clipToOutline = true
            setImage(binding.itemImageView, popupStore.bannerImgUrl)
            adjustImageSize(binding.itemImageView, imageSize)
        }

    }


    inner class VisitedViewHolder(private val binding: RowPopupstoreVisitedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val popupStore = popupStores[adapterPosition]
                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", popupStore.popupStoreId)
                context.startActivity(intent)
            }
        }

        fun bind(popupStore: PopupStore) {
            binding.popupstore = popupStore
            setImage(binding.itemImageView, popupStore.bannerImgUrl)
        }
    }

    inner class ShimmerViewHolder(private val shimmerLayout: ShimmerFrameLayout) :
        RecyclerView.ViewHolder(shimmerLayout) {

        fun bind() {
            val shimmer = Shimmer.AlphaHighlightBuilder()
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.9f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

            shimmerLayout.setShimmer(shimmer)
            shimmerLayout.startShimmer()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewHolderType.HORIZONTAL.ordinal -> {
                val binding = RowPopupstoreHorizontalBinding.inflate(inflater, parent, false)
                HorizontalViewHolder(binding)
            }

            ViewHolderType.VERTICAL_LARGE.ordinal,
            ViewHolderType.VERTICAL_MEDIUM.ordinal -> {
                val binding = RowPopupstoreVerticalBinding.inflate(inflater, parent, false)
                VerticalViewHolder(binding, false)
            }

            ViewHolderType.VERTICAL_SMALL.ordinal -> {
                val binding = RowPopupstoreVerticalSmallBinding.inflate(inflater, parent, false)
                VerticalSmallViewHolder(binding)
            }

            ViewHolderType.VERTICAL_LARGE_GRID.ordinal -> {
                val binding = RowPopupstoreVerticalBinding.inflate(inflater, parent, false)
                VerticalViewHolder(binding, true)
            }

            ViewHolderType.VISITED.ordinal -> {
                val binding = RowPopupstoreVisitedBinding.inflate(inflater, parent, false)
                VisitedViewHolder(binding)
            }

            ViewHolderType.SHIMMER.ordinal -> {
                val shimmerLayout =
                    inflater.inflate(R.layout.row_popupstore_vertical_shimmer, parent, false) as ShimmerFrameLayout
                ShimmerViewHolder(shimmerLayout)
            }

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShimmerViewHolder) {
            holder.bind()
        } else {
            val popupStore = popupStores[position]

            val imageSize = when (viewHolderType) {
                ViewHolderType.VERTICAL_LARGE_GRID -> ImageSize.EXTRA_LARGE
                ViewHolderType.VERTICAL_LARGE -> ImageSize.LARGE
                ViewHolderType.VERTICAL_SMALL -> ImageSize.SMALL
                ViewHolderType.HORIZONTAL -> ImageSize.LARGE
                else -> ImageSize.MEDIUM
            }
            when (holder) {
                is HorizontalViewHolder -> holder.bind(popupStore, imageSize)
                is VerticalViewHolder -> holder.bind(popupStore, imageSize, position)
                is VisitedViewHolder -> holder.bind(popupStore)
                is VerticalSmallViewHolder -> holder.bind(popupStore, imageSize, position)
            }
        }
    }

    override fun getItemCount(): Int = popupStores.size


    override fun getItemViewType(position: Int): Int = viewHolderType.ordinal

    private fun setImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView)
            .load(imageUrl)
            .into(imageView)
    }

    private fun adjustImageSize(imageView: ImageView, imageSize: ImageSize) {
        val layoutParams = imageView.layoutParams
        val sizeInDp = when (imageSize) {
            ImageSize.EXTRA_LARGE -> 175
            ImageSize.LARGE -> 170
            ImageSize.SMALL -> 90
            ImageSize.MEDIUM -> 190
        }
        layoutParams.width = dpToPx(sizeInDp)
        layoutParams.height = dpToPx(sizeInDp)
        imageView.layoutParams = layoutParams
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale).toInt()
    }

    private fun pxToDp(px: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }
}
