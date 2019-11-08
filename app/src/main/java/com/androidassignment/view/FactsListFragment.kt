package com.androidassignment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidassignment.R
import com.androidassignment.Utils
import com.androidassignment.model.Facts
import com.androidassignment.model.local.FactsLocalRepository
import com.androidassignment.model.remote.FactsRepository
import com.androidassignment.viewmodel.FactsViewModel
import com.androidassignment.viewmodel.ViewModelFactory
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class FactsListFragment : Fragment() {
    private lateinit var viewModel: FactsViewModel
    private lateinit var adapter: FactsListAdapter
    private lateinit var factsRecyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(
            R.layout.fragment_facts_list, container
            , false
        )
        intiView(view)
        setupSwipeContainer()
        setupViewModel()
        setupRecyclerView()
        return view
    }

    /**
     * Initialize all view
     */
    private fun intiView(view: View) {
        factsRecyclerView = view.findViewById(R.id.facts_recycler_view)
        swipeContainer = view.findViewById(R.id.swipe_container)
        emptyTextView = view.findViewById(R.id.text_view_empty)


    }

    /**
     * Initialize recycler view
     */
    private fun setupRecyclerView() {
        factsRecyclerView.setHasFixedSize(true);
        val layoutManager = LinearLayoutManager(context)
        factsRecyclerView.layoutManager = layoutManager

        adapter = FactsListAdapter(viewModel.factsList.value ?: emptyList())
        factsRecyclerView.adapter = adapter
    }

    /**
     * Configure swipe container
     */
    private fun setupSwipeContainer() {
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );
        swipeContainer.setOnRefreshListener {
            viewModel.loadFacts(Utils.isOnline(activity))
        }
    }

    /**
     * initialize view model and observer
     */
    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(
                this@FactsListFragment,
                ViewModelFactory(FactsRepository(), FactsLocalRepository(), Utils.isOnline(activity))
            )
                .get(FactsViewModel::class.java)
        viewModel.factsList.observe(this, renderFactsList)

        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
        viewModel.title.observe(this, titleUpdateObserver)
    }

    /**
     * Update action bar title
     */
    private fun updateTitle(title: String?) {
        activity?.title = title
    }

    //observers
    private val renderFactsList = Observer<List<Facts>> {
        emptyTextView.visibility = View.GONE
        factsRecyclerView.visibility = View.VISIBLE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        swipeContainer.isRefreshing = it
    }

    private val onMessageErrorObserver = Observer<Any> {
        emptyTextView.visibility = View.VISIBLE
        factsRecyclerView.visibility = View.GONE
        emptyTextView.text = it.toString()
    }

    private val emptyListObserver = Observer<Boolean> {
        emptyTextView.visibility = if (it) View.VISIBLE else View.GONE
        factsRecyclerView.visibility = if (it) View.GONE else View.VISIBLE
        emptyTextView.text = getString(R.string.empty_msg)

    }
    private val titleUpdateObserver = Observer<String> {
        updateTitle(it)
    }

}
