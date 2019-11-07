package com.androidassignment.view

import android.os.Bundle
import android.util.Log
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
import com.androidassignment.model.Facts
import com.androidassignment.model.remote.FactsRepository
import com.androidassignment.viewmodel.FactsViewModel
import com.androidassignment.viewmodel.ViewModelFactory

class FactsListFragment : Fragment() {
    lateinit var viewModel: FactsViewModel
    lateinit var adapter: FactsListAdapter
    lateinit var factsRecyclerView: RecyclerView
    lateinit var progressLoading: ProgressBar
    lateinit var emptyTextView: TextView
    lateinit var buttonRefresh: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(
            R.layout.fragment_facts_list, container
            , false
        )
        intiView(view)
        setupViewModel()
        setupRecyclerView()
        return view
    }

    /**
     * Initialize all view
     */
    private fun intiView(view: View) {
        factsRecyclerView = view.findViewById(R.id.facts_recycler_view)
        progressLoading = view.findViewById(R.id.progress_loading)
        buttonRefresh = view.findViewById(R.id.button_refresh)
        emptyTextView = view.findViewById(R.id.text_view_empty)
        buttonRefresh.setOnClickListener {
            viewModel.loadFacts()
        }

    }

    /**
     * initialize recycler view
     */
    private fun setupRecyclerView() {
        factsRecyclerView.setHasFixedSize(true);
        val layoutManager = LinearLayoutManager(context)
        factsRecyclerView.layoutManager = layoutManager

        adapter = FactsListAdapter(viewModel.factsList.value ?: emptyList())
        factsRecyclerView.adapter = adapter
    }

    /**
     * initialize view model and observer
     */
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this@FactsListFragment, ViewModelFactory(FactsRepository()))
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
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        progressLoading.visibility = if (it) View.VISIBLE else View.GONE
    }

    private val onMessageErrorObserver = Observer<Any> {
        emptyTextView.visibility = View.VISIBLE
        emptyTextView.text = it.toString()
    }

    private val emptyListObserver = Observer<Boolean> {
        emptyTextView.visibility = if (it) View.VISIBLE else View.GONE
        emptyTextView.text = getString(R.string.empty_msg)

    }
    private val titleUpdateObserver = Observer<String> {
        updateTitle(it)
    }

}
