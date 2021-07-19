package com.example.android.politicalpreparedness.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.representative.model.Representative


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {

    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)

}

@BindingAdapter("listDataRep")
fun bindRepresentetiveRecyclerView(recyclerView: RecyclerView, data: List<Representative>?) {

    val adapter = recyclerView.adapter as RepresentativeListAdapter
    adapter.submitList(data)

}

@BindingAdapter("url_click")
fun bindingCLickText(textView: TextView, url: String?) {

    textView.setOnClickListener { view: View? ->
        if (url != null && url != "") {
            val context: Context = textView.context
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            context.startActivity(intent)
        }
    }
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?) {
    val uri = (url ?: "").toUri().buildUpon().scheme("https").build()

    Glide.with(view).load(uri)
        .placeholder(R.drawable.ic_profile)
        .error(R.drawable.ic_profile)
        .circleCrop()
        .into(view)
}