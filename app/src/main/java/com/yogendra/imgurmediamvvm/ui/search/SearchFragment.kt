package com.yogendra.imgurmediamvvm.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.yogendra.imgurmediamvvm.MainActivity
import com.yogendra.imgurmediamvvm.R
import com.yogendra.imgurmediamvvm.ServiceLocator
import com.yogendra.imgurmediamvvm.adapter.PostsLoadStateAdapter
import com.yogendra.imgurmediamvvm.adapter.PostsParentAdapter
import kotlinx.android.synthetic.main.search_fragment.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


class SearchFragment : Fragment() {
    private lateinit var adapter: PostsParentAdapter

    private val viewModel: SearchFragmentViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {

                val repo = ServiceLocator.instance(activity!!.application)
                    .getRepository()
                @Suppress("UNCHECKED_CAST")
                return SearchFragmentViewModel(
                    repo,
                    handle
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        view.parent_recyclerView.addItemDecoration(decoration)

        view.empty_message.visibility = View.VISIBLE
        view.empty_message.append("\n(swipe down to refresh)")
        initAdapter(view)
        initSwipeToRefresh(view)
        setHasOptionsMenu(true)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView =
            SearchView((context as MainActivity).supportActionBar!!.themedContext)

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        item.setActionView(searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.trim().toString().let {
                    if (it.isNotBlank() && viewModel.shouldShowSearchPosts(it)) {
                        viewModel.showSearchPosts(it)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                Toast.makeText(context, "$newText", Toast.LENGTH_SHORT).show()
                return false
            }
        })
    }


    private fun initAdapter(view: View) {
        adapter = PostsParentAdapter()
        view.parent_recyclerView.adapter = adapter.withLoadStateFooter(
            footer = PostsLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                view.swipe_refresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.posts.collectLatest {
                adapter.submitData(it)

                if (adapter.itemCount > 0) {
                    view.empty_message.visibility = View.GONE
                } else {
                    view.empty_message.visibility = View.VISIBLE

                }
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(FlowPreview::class)
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { view.parent_recyclerView.scrollToPosition(0) }

        }


    }

    private fun initSwipeToRefresh(view: View) {
        view.swipe_refresh.setOnRefreshListener { adapter.refresh() }
    }

}