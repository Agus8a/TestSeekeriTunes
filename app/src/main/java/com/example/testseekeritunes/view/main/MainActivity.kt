package com.example.testseekeritunes.view.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.transition.TransitionManager
import com.example.domain.model.Result
import com.example.domain.util.OFFLINE_ERROR_TEXT
import com.example.testseekeritunes.R
import com.example.testseekeritunes.core.BaseActivity
import com.example.testseekeritunes.core.BaseOnSelectItem
import com.example.testseekeritunes.ui.Stagger
import com.example.testseekeritunes.util.COLLECTION_PREVIEW_KEY
import com.example.testseekeritunes.util.PREVIEW_KEY
import com.example.testseekeritunes.util.build
import com.example.testseekeritunes.util.toast
import com.example.testseekeritunes.view.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_card.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<MainViewStatus, MainViewModel>(), BaseOnSelectItem<Result> {
    private lateinit var mainAdapter: MainAdapter

    override fun initViewModel(): MainViewModel = getViewModel()

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.suggest()
        resultList.build(this)
        mainAdapter = MainAdapter(this, this)
        resultList.adapter = mainAdapter
        setUpSearchView()
    }

    override fun onViewStatusUpdated(viewStatus: MainViewStatus) {
        when {
            viewStatus.isLoading -> {
                showLoading()
            }

            viewStatus.isReady -> {
                hideLoading()
                searchCardEditText.setAdapter(
                    ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_spinner_item,
                        viewStatus.termList
                    )
                )
            }

            viewStatus.isComplete -> {
                TransitionManager.beginDelayedTransition(resultList, Stagger())
                mainAdapter.setData(viewStatus.resultList)
                hideLoading()

                if (mainAdapter.itemCount == 0) {
                    this@MainActivity.toast(OFFLINE_ERROR_TEXT)
                }

                viewModel.suggest()
            }

            viewStatus.isError && viewStatus.errorMessage.isNotEmpty() -> {
                hideLoading()
                this@MainActivity.toast(viewStatus.errorMessage)
            }
        }
    }

    override fun onSelectItem(item: Result) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(COLLECTION_PREVIEW_KEY, item.collectionViewUrl)
        intent.putExtra(PREVIEW_KEY, item.previewUrl)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    private fun setUpSearchView() {
        val searchEditText = mainSearchCardView.getInput()
        searchEditText.setSelection(searchEditText.text.length)
        searchEditText.setHint(R.string.search)
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                showLoading()
                viewModel.search(searchEditText.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
        searchEditText.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            showLoading()
            viewModel.search(searchEditText.text.toString())
        }
    }
}