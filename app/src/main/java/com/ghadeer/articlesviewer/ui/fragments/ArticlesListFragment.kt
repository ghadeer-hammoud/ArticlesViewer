package com.ghadeer.articlesviewer.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ghadeer.articlesviewer.R
import com.ghadeer.articlesviewer.data.enums.Status
import com.ghadeer.articlesviewer.data.models.Article
import com.ghadeer.articlesviewer.databinding.FragmentArticlesListBinding
import com.ghadeer.articlesviewer.ui.adapters.ArticlesRecyclerAdapter
import com.ghadeer.articlesviewer.ui.adapters.OnArticleClickListener
import com.ghadeer.articlesviewer.ui.states.MainState
import com.ghadeer.articlesviewer.utils.Constants.NAV_OPTIONS
import com.ghadeer.articlesviewer.utils.hide
import com.ghadeer.articlesviewer.utils.show
import com.ghadeer.articlesviewer.utils.toast
import com.ghadeer.articlesviewer.viewmodels.ArticlesViewModel
import kotlinx.coroutines.launch


class ArticlesListFragment : Fragment(), MenuProvider, OnArticleClickListener {

    private lateinit var binding: FragmentArticlesListBinding
    private var articleDetailsFragmentContainer: View? = null

    private val viewModel: ArticlesViewModel by activityViewModels()

    private lateinit var articlesAdapter: ArticlesRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArticlesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        observeStates()
        viewModel.getArticlesListState(forceUpdate = true)
    }

    private fun initViews(rootView: View){

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this@ArticlesListFragment, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.setupPeriodSpinnerFilter()
        articleDetailsFragmentContainer = rootView.findViewById(R.id.article_details_fragment_container)
        binding.setupRecyclerView()
    }

    private fun FragmentArticlesListBinding.setupRecyclerView() {
        articlesAdapter = ArticlesRecyclerAdapter(mutableListOf(), this@ArticlesListFragment)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articlesAdapter
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem?.actionView as SearchView?
        setupSearchView(searchView)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.action_refresh -> {
                viewModel.getArticlesListState(forceUpdate = true)
            }
        }
       return false
    }

    private fun setupSearchView(searchView: SearchView?){
        searchView?.apply {
            isIconifiedByDefault = true
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    viewModel.getArticlesListState(searchQuery = query ?: "", forceUpdate = false)
                    return true
                }

            })
        }
        val id = searchView?.context?.resources?.getIdentifier("android:id/search_src_text", null, null)
        val searchEditText = searchView?.findViewById<View>(id?:0) as EditText?
        searchEditText?.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setHintTextColor(ContextCompat.getColor(requireContext(), R.color.grey_300))
        }
    }


    private fun observeStates(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.articlesState.collect {
                   binding.updateState(it)
                }
            }
        }
    }

    private fun FragmentArticlesListBinding.updateState(state: MainState<List<Article>?>){

        showProgress(state.progress)

        when(state.status){
            Status.Success -> {

                updateItemsList(state.data ?: emptyList())
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

    private fun FragmentArticlesListBinding.showProgress(isShow: Boolean){
        when(isShow){
            true -> {
                progressBar.show()
                tvMessage.apply {
                    show()
                    text = getString(R.string.loading_articles)
                }
                spPeriod.isEnabled = false
                recyclerView.hide()
            }
            false -> {
                progressBar.hide()
                tvMessage.hide()
                spPeriod.isEnabled = true
                recyclerView.show()
            }
        }
    }

    private fun updateItemsList(articlesList: List<Article>){
        articlesAdapter.updateItems(articlesList)
        when(articlesList.isEmpty()){
           true -> {
               binding.tvMessage.apply {
                   show()
                   text = getString(R.string.no_results_found)
               }
           }
            false -> binding.tvMessage.hide()
        }
    }

    private fun FragmentArticlesListBinding.setupPeriodSpinnerFilter(){
        spPeriod.setSelection(1,false)
        spPeriod.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val period = when(position){
                    0 -> 1
                    1 -> 7
                    2 -> 30
                    else -> 7
                }
                viewModel.getArticlesListState(period = period, forceUpdate = true)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }

    private fun navigateArticleDetails(articleId: Long){
        val bundle = Bundle()
        bundle.putLong(
            ArticleDetailsFragment.ARG_ARTICLE_ID,
            articleId
        )
        when(articleDetailsFragmentContainer == null){
            true -> findNavController().navigate(R.id.show_article_details, bundle, NAV_OPTIONS)
            false -> articleDetailsFragmentContainer?.findNavController()?.navigate(R.id.fragment_article_details, bundle, NAV_OPTIONS)
        }
    }

    override fun onArticleClicked(articleId: Long) {
        navigateArticleDetails(articleId = articleId)
    }
}