package com.rudachenkoroman.astonIntensivFinal.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.rudachenkoroman.astonIntensivFinal.R
import com.rudachenkoroman.astonIntensivFinal.databinding.FragmentDetailNewsBinding
import com.rudachenkoroman.astonIntensivFinal.model.news.Article
import com.bumptech.glide.Glide
import com.rudachenkoroman.astonIntensivFinal.util.getSerializableCompat

const val BUNDLE_KEY = "BUNDLE_KEY"
const val DETAIL_NEWS_FRAGMENT_TAG = "USER_DETAILS_FRAGMENT_TAG"

class DetailNewsFragment : Fragment() {

    private lateinit var binding: FragmentDetailNewsBinding
    private val removedContent = "[Removed]"
    private val targetSymbol = " ["

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(layoutInflater)
        toolbarInit()
        backClicked()
        showTitleNews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = requireArguments().getSerializableCompat(BUNDLE_KEY, Article::class.java)
        if (article != null) {
            setupFields(article)
        }
    }

    private fun setupFields(article: Article) {
        binding.apply {
            Glide.with(requireActivity())
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder_news_item)
                .into(binding.detailImage)
            collapsingToolbarLayout.title = article.title
            detailTitle.text = article.title
            detailSource.text = article.source.name
            showDateNews(article)
            showNotContentImage(article)
        }
    }

    private fun showDateNews(article: Article) {
        binding.apply {
            if (article.publishedAt == null || article.content == removedContent) {
                detailDate.text = removedContent
            } else {
                detailDate.text = article.publishedAt.replace("T", " | ").replace("Z", "")
            }
        }
    }

    private fun showNotContentImage(article: Article) {
        binding.apply {
            if (article.content == null || article.content == removedContent) {
                notContentImage.isVisible = true
            } else {
                clickToLinkNews(article)
            }
        }
    }

    private fun clickToLinkNews(article: Article) {
        var text: String = article.content
        var startRemove = text.lastIndexOf(targetSymbol)
        if (startRemove == -1){
            startRemove = text.length - 1
        }
        text = text.removeRange(startRemove, text.length)
        val contentText = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }
        var start = contentText.lastIndexOf(".")
        start += if (start == -1) {
            1
        } else {
            2
        }
        contentText.setSpan(
            clickableSpan,
            start,
            contentText.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.apply {
            detailContent.text = contentText
            detailContent.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun showTitleNews() {
        binding.apply {
            collapsingToolbarLayout.isTitleEnabled = true
            collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255))
            collapsingToolbarLayout.setExpandedTitleColor(Color.argb(0, 0, 0, 0))
        }
    }

    private fun toolbarInit() {
        binding.apply {
            toolbar.toolbarMain.inflateMenu(R.menu.toolbar_menu_detail_news)
        }
    }

    private fun backClicked() {
        binding.apply {
            toolbar.toolbarMain.setNavigationIcon(R.drawable.back)
            toolbar.toolbarMain.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    companion object {
        fun newInstance(article: Article): DetailNewsFragment {
            return DetailNewsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_KEY, article)
                }
            }
        }
    }
}
