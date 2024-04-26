package com.rudachenkoroman.astonIntensivFinal.util

import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.model.news.Article
import com.rudachenkoroman.astonIntensivFinal.model.source.Source
import java.io.Serializable

fun <T : Serializable> Bundle.getSerializableCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key,clazz)
    } else {
        (this.getSerializable(key) as T)
    }
}

fun FragmentManager.setFragment(@IdRes view: Int, instance: Fragment, name: String) {
    this.commit {
        replace(view, instance)
        setReorderingAllowed(true)
        addToBackStack(name)
    }
}

fun getImageSourceNews(article: Article): Int {
    return when (article.source.name) {
        NameSource.BBC.sourceName -> R.raw.bbc_news
        NameSource.CNN.sourceName -> R.raw.cnn_news
        NameSource.CNBC.sourceName -> R.raw.cnbc_news
        NameSource.ABCNEWS.sourceName -> R.raw.abc_news
        NameSource.ARSTECH.sourceName -> R.raw.ars_technica_news
        NameSource.ALJAZEERA_EN.sourceName -> R.raw.al_jazeera_news
        else -> R.drawable.placeholder_source_item
    }
}

fun getImageSource(source: Source): Int {
    return when (source.name) {
        NameSource.ABCNEWS.sourceName -> R.raw.abc_news
        NameSource.ARSTECH.sourceName -> R.raw.ars_technica_news
        NameSource.ALJAZEERA_EN.sourceName -> R.raw.al_jazeera_news
        else -> R.drawable.placeholder_news_item
    }
}

fun Fragment.finishFragment(){
    val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(
        this,
        callback
    )
}

fun Fragment.setOnBackPressedCallback(){
    requireActivity().onBackPressedDispatcher.onBackPressed()
}