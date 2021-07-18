package com.example.android.politicalpreparedness.election.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val onClickListener: OnClickListener) : ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {

        return ElectionViewHolder(ItemElectionBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {

        val election = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(election)
        }

        holder.bind(election)
    }

    class ElectionViewHolder(private var binding: ItemElectionBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(election: Election) {
            binding.data = election
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (election: Election) -> Unit) {
        fun onClick(election: Election) = clickListener(election)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Election>() {
        override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

