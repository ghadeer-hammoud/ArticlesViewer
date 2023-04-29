package com.ghadeer.articlesviewer.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ghadeer.articlesviewer.R
import com.ghadeer.articlesviewer.data.enums.Status
import com.ghadeer.articlesviewer.data.models.Article
import com.ghadeer.articlesviewer.databinding.FragmentArticleDetailsBinding
import com.ghadeer.articlesviewer.ui.states.MainState
import com.ghadeer.articlesviewer.utils.*
import com.ghadeer.articlesviewer.viewmodels.ArticleViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch


class ArticleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentArticleDetailsBinding

    private val viewModel: ArticleViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ARTICLE_ID)) {
                val articleId = it.getLong(ARG_ARTICLE_ID)
                viewModel.getArticleState(articleId)
            } else {
                binding.showMessage(getString(R.string.undefined_article))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeStates()
    }

    private fun initViews(){
        binding.updateContent(null)
    }

    private fun observeStates(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.articleState.collect {
                    binding.updateState(it)
                }
            }
        }
    }

    private fun FragmentArticleDetailsBinding.updateState(state: MainState<Article>){

        showProgress(state.progress)

        when(state.status){
            Status.Success -> {

                updateContent(state.data)
                if(state.message.isNotEmpty())
                    toast(state.message)
            }
            Status.Failure -> {
                tvMessage.apply {
                    show()
                    text = state.message
                }
            }
            Status.Idle -> {}
        }
    }

    private fun FragmentArticleDetailsBinding.showProgress(isShow: Boolean){
        when(isShow){
            true -> {
                progressBar.show()
                tvMessage.apply {
                    show()
                    text = getString(R.string.loading_article)
                }
            }
            false -> {
                progressBar.hide()
                tvMessage.hide()
            }
        }
    }

    private fun FragmentArticleDetailsBinding.updateContent(article: Article?) {

        when(article){
            null -> clearContent()
            else -> fillContent(article)
        }
    }

    private fun FragmentArticleDetailsBinding.fillContent(article: Article){

        appBar.show()
        dataViews.show()

        article.let {

            if(it.media.isNotEmpty()){
                if(it.media.first().metadata.isNotEmpty())
                    ivImage.loadImage(it.media.first().metadata.last().url)
            }

            toolbarLayout.title = it.title
            tvTitle.text = it.title
            tvAbstract.text = it.abstract
            tvSource.text = getString(R.string.starts_with_bullet_point, it.source)
            tvByline.text = it.byline
            tvPublishedDate.text = it.publishedDate.formatDate()
            chipType.text = it.type
            chipSection.text = it.section
            chipSubsection.apply {
                when(it.subsection.isEmpty()){
                    true -> hide()
                    false -> {
                        show()
                        text = it.subsection
                    }
                }
            }
            chipNytdsection.text = it.nytdSection

            fillChipGroup(chipGroupADXKeywords, it.adxKeywords.split(";"))
            fillChipGroup(chipGroupDesFacet, it.desFacet)
            fillChipGroup(chipGroupOrgFacet, it.orgFacet)
            fillChipGroup(chipGroupPerFacet, it.perFacet)
            fillChipGroup(chipGroupGeoFacet, it.geoFacet)

            fabNavigateUrl.setOnClickListener { _ ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(browserIntent)
            }
        }

    }

    private fun fillChipGroup(chipGroup: ChipGroup, stringList: List<String>){
        stringList.forEach { str ->
            if(str.isNotEmpty())
                chipGroup.addView(
                    Chip(requireContext()).apply {
                        layoutParams = ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT, ChipGroup.LayoutParams.WRAP_CONTENT)
                        text = str
                    }
                )
        }
    }

    private fun FragmentArticleDetailsBinding.clearContent(){
        toolbarLayout.title = ""
        tvTitle.text = ""
        tvAbstract.text = ""
        tvSource.text = ""
        tvByline.text = ""
        tvPublishedDate.text = ""
        chipType.text = ""
        chipSection.text = ""
        chipSubsection.text = ""
        chipNytdsection.text = ""

        chipGroupADXKeywords.removeAllViews()
        chipGroupDesFacet.removeAllViews()
        chipGroupOrgFacet.removeAllViews()
        chipGroupPerFacet.removeAllViews()
        chipGroupGeoFacet.removeAllViews()

        appBar.hide()
        dataViews.hide()
    }

    private fun FragmentArticleDetailsBinding.showMessage(message: String){
        tvMessage.apply {
            show()
            text = message
        }
    }

    companion object {
        const val ARG_ARTICLE_ID = "article_id"
    }
}