package com.example.android.politicalpreparedness.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {

    val adapter = recyclerView.adapter as ElectionListAdapter
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