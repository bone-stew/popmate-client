package com.example.popmate.view.adapters

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popmate.databinding.*
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.activities.detail.PopupDetailActivity

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
        VISITED
    }

    enum class ImageSize {
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
                Log.d("DETAIL", popupStore.popupStoreId.toString())

                context.startActivity(intent)
                Toast.makeText(binding.root.context, "클릭된 아이템 = ${popupStore.title}", Toast.LENGTH_LONG).show()
            }
        }

        fun bind(popupStore: PopupStore, imageSize: ImageSize) {
            binding.popupstore = popupStore
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
                Log.d("DETAIL", popupStore.popupStoreId.toString())

                context.startActivity(intent)
                Toast.makeText(binding.root.context, "클릭된 아이템 = ${popupStore.title}", Toast.LENGTH_LONG).show()
            }
        }

        fun bind(popupStore: PopupStore, imageSize: ImageSize, position: Int) {
            binding.popupstore = popupStore
            setImage(binding.itemImageView, popupStore.bannerImgUrl)
            adjustImageSize(binding.itemImageView, imageSize)

            if (isGridLayout && position >= popupStores.size - 2) {
                val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = dpToPx(130)
                binding.root.layoutParams = layoutParams
            }

        }
    }

    inner class VisitedViewHolder(private val binding: RowPopupstoreVisitedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val popupStore = popupStores[adapterPosition]
                val intent = Intent(context, PopupDetailActivity::class.java)
                intent.putExtra("id", popupStore.popupStoreId)
                Log.d("DETAIL", popupStore.popupStoreId.toString())

                context.startActivity(intent)
                Toast.makeText(binding.root.context, "클릭된 아이템 = ${popupStore.popupStoreId}", Toast.LENGTH_LONG).show()
            }
        }

        fun bind(popupStore: PopupStore) {
            binding.popupstore = popupStore
            setImage(binding.itemImageView, popupStore.bannerImgUrl)
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
            ViewHolderType.VERTICAL_MEDIUM.ordinal,
            ViewHolderType.VERTICAL_SMALL.ordinal -> {

                val binding = RowPopupstoreVerticalBinding.inflate(inflater, parent, false)
                VerticalViewHolder(binding, false)
            }

            ViewHolderType.VERTICAL_LARGE_GRID.ordinal -> {
                val binding = RowPopupstoreVerticalBinding.inflate(inflater, parent, false)
                VerticalViewHolder(binding, true)
            }

            ViewHolderType.VISITED.ordinal -> {
                val binding = RowPopupstoreVisitedBinding.inflate(inflater, parent, false)
                VisitedViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val popupStore = popupStores[position]
        Log.i("SEARCHRECENT", "===================================================")

        Log.i("SEARCHRECENT", "INSIDE ADAPTER" + popupStores.toString())
        Log.i("SEARCHRECENT", "INSIDE ADAPTER" + position.toString())
        Log.i("SEARCHRECENT", "INSIDE ADAPTER" + popupStore.toString())

        val imageSize = when (viewHolderType) {
            ViewHolderType.VERTICAL_LARGE_GRID -> ImageSize.LARGE
            ViewHolderType.VERTICAL_LARGE -> ImageSize.LARGE
            ViewHolderType.VERTICAL_SMALL -> ImageSize.SMALL
            else -> ImageSize.MEDIUM
        }
        when (holder) {
            is HorizontalViewHolder -> holder.bind(popupStore, imageSize)
            is VerticalViewHolder -> holder.bind(popupStore, imageSize, position)
            is VisitedViewHolder -> holder.bind(popupStore)
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
            ImageSize.LARGE -> 150
            ImageSize.SMALL -> 90
            ImageSize.MEDIUM -> 120
        }
        layoutParams.width = dpToPx(sizeInDp)
        layoutParams.height = dpToPx(sizeInDp)
        imageView.layoutParams = layoutParams
    }

    private fun dpToPx(dp: Int): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale).toInt()
    }
}
