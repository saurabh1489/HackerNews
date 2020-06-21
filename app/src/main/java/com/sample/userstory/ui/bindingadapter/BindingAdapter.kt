package com.sample.userstory.ui.bindingadapter

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sample.userstory.data.repository.Theme


@BindingAdapter("setWebViewClient")
fun setWebViewClient(view: WebView, client: WebViewClient?) {
    view.webViewClient = client
}

@BindingAdapter("loadUrl")
fun loadUrl(view: WebView, url: String?) {
    view.loadUrl(url)
}

@BindingAdapter("theme")
fun theme(textView: TextView, theme: Theme) {
    if (theme == Theme.RED) {
        textView.setTextColor(
            textView.context.getResources().getColor(android.R.color.holo_red_dark)
        )
    } else {
        textView.setTextColor(
            textView.context.getResources().getColor(android.R.color.holo_blue_dark)
        )
    }
}