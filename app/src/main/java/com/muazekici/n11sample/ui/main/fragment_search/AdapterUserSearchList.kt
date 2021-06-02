package com.muazekici.n11sample.ui.main.fragment_search

import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muazekici.n11sample.R
import com.muazekici.n11sample.databinding.ItemUserSearchBinding
import com.muazekici.n11sample.gateways_adapters.models.UserSimple
import com.muazekici.n11sample.ui.exts.inflateForListView

class AdapterUserSearchList(
    private val favoriteListener: (Int, UserSimple) -> Unit,
    private val selectListener: (Int, UserSimple) -> Unit
) : ListAdapter<UserSimple, AdapterUserSearchList.UserSearchItemViewHolder>(
    UserSimpleDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchItemViewHolder {
        return UserSearchItemViewHolder(inflateForListView(R.layout.item_user_search, parent))
    }

    override fun onBindViewHolder(holder: UserSearchItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserSearchItemViewHolder(private val binding: ItemUserSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.holder = this
        }

        fun bind(user: UserSimple) {
            binding.user = user
            binding.executePendingBindings()
        }

        fun onCheckChanged(buttonView: CompoundButton, isChecked: Boolean) {
            val o = getItem(adapterPosition)
            o.isFavorite = isChecked
            favoriteListener(adapterPosition, o)
        }

        fun onItemSelected(view: View) {
            selectListener(adapterPosition, getItem(adapterPosition))
        }
    }
}

object UserSimpleDiffUtil : DiffUtil.ItemCallback<UserSimple>() {
    override fun areItemsTheSame(oldItem: UserSimple, newItem: UserSimple): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserSimple, newItem: UserSimple): Boolean {
        return oldItem.isFavorite == newItem.isFavorite
    }
}