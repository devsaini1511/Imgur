package com.example.codingchallengeimgur.ui.home.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.codingchallengeimgur.R
import com.example.codingchallengeimgur.databinding.HolderItemGalleryLayoutBinding
import com.example.codingchallengeimgur.domain.response.ImageData


class GalleryAdapter(
    val listener: (ImageData) -> Unit,
) : ListAdapter<ImageData, GalleryAdapter.GalleryItemHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemHolder {
        val mandateOptionBinding = HolderItemGalleryLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GalleryItemHolder(mandateOptionBinding)
    }

    override fun onBindViewHolder(holder: GalleryItemHolder, position: Int) {
        with(getItem(position)) {
            holder.bind(this) { listener(this) }
        }
    }

    inner class GalleryItemHolder(private val itemBinding: HolderItemGalleryLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: ImageData, listener: () -> Unit) {
            itemBinding.dateTextView.text = data.formattedDate
            itemBinding.titleTextView.text = data.title
            itemBinding.imageView.load(data.imageUrl) {
                error(R.drawable.image_placeholder)
            }
            itemBinding.root.setOnClickListener {
                listener()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageData>() {
            override fun areItemsTheSame(
                oldItem: ImageData,
                newItem: ImageData,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ImageData,
                newItem: ImageData,
            ): Boolean {
                return oldItem.formattedDate == newItem.formattedDate
            }

        }
    }

}
