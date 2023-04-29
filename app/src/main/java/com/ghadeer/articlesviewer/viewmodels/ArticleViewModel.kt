package com.ghadeer.articlesviewer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghadeer.articlesviewer.data.DataResult
import com.ghadeer.articlesviewer.data.enums.Status
import com.ghadeer.articlesviewer.data.models.Article
import com.ghadeer.articlesviewer.repos.ArticleRepository
import com.ghadeer.articlesviewer.ui.states.MainState
import com.ghadeer.articlesviewer.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
)
: ViewModel() {

    companion object {
        private const val TAG = "ArticleViewModel"
    }

    private val _articleState = MutableStateFlow(MainState<Article>())
    val articleState = _articleState.asStateFlow()

    fun getArticleState(articleId: Long, forceUpdate: Boolean = false) = viewModelScope.launch {

        val conditionToUpdateState =
                       _articleState.value.data == null
                    || (_articleState.value.data != null && _articleState.value.data?.id != articleId)
                    || forceUpdate

        if(conditionToUpdateState){

            // Load Article from Database and update state
            _articleState.update { it.showProgress() }
            when(val res = getArticle(articleId)){
                is DataResult.Success -> {

                    _articleState.update {
                        it.copy(
                            progress = false,
                            status = Status.Success,
                            data = res.data,
                            message = res.message
                        )
                    }
                }
                is DataResult.Error -> {
                    _articleState.update {
                        it.copy(
                            progress = false,
                            status = Status.Failure,
                            message = res.message
                        )
                    }
                }
            }
        } else {
            // State already there -> return it
            _articleState.update { _articleState.value }
        }

    }
    
    private suspend fun getArticle(articleId: Long): DataResult<Article> {
        return when(val res = articleRepository.getArticleById(articleId)){
            is DataResult.Success -> DataResult.Success(Mapper.articleEntityToArticleModel(res.data), message = res.message)
            is DataResult.Error -> DataResult.Error(res.message)
        }
    }
}