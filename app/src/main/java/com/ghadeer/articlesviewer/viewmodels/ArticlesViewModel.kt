package com.ghadeer.articlesviewer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghadeer.articlesviewer.data.DataResult
import com.ghadeer.articlesviewer.data.enums.Status
import com.ghadeer.articlesviewer.data.models.Article
import com.ghadeer.articlesviewer.repos.ArticleRepository
import com.ghadeer.articlesviewer.ui.states.MainState
import com.ghadeer.articlesviewer.utils.Constants
import com.ghadeer.articlesviewer.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
)
: ViewModel() {

    companion object {
        private const val TAG = "ArticlesViewModel"
    }

    private val _articlesState = MutableStateFlow(MainState<List<Article>>())
    val articlesState = _articlesState.asStateFlow()

    fun getArticlesListState(period: Int = Constants.DEFAULT_PERIOD,
                             searchQuery: String = "",
                             forceUpdate: Boolean = false) = viewModelScope.launch {

        _articlesState.update { it.showProgress() }

        when(val res = articleRepository.getArticlesList(period = period, searchQuery = searchQuery, forceUpdate = forceUpdate)){
            is DataResult.Success -> {

                val articlesList = res.data.map{
                    Mapper.articleEntityToArticleModel(it)
                }
                _articlesState.update {
                    it.copy(
                        progress = false,
                        status = Status.Success,
                        data = articlesList,
                        message = res.message
                    )
                }
            }
            is DataResult.Error -> {
                _articlesState.update {
                    it.copy(
                        progress = false,
                        status = Status.Failure,
                        message = res.message
                    )
                }
            }
        }

    }
}