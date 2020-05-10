package com.example.testseekeritunes.view.main

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import com.example.domain.model.Result
import com.example.domain.util.OFFLINE_ERROR_TEXT
import com.example.testseekeritunes.R
import com.example.testseekeritunes.core.BaseActivity
import com.example.testseekeritunes.core.BaseOnSelectItem
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
        viewModel.initialize()
        resultList.build(this)
        mainAdapter = MainAdapter(this, this)
        setUpSearchView()
    }

    override fun onViewStatusUpdated(viewStatus: MainViewStatus) {
        when {
            viewStatus.isReady -> {
                searchCardEditText.setAdapter(
                    ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_spinner_item,
                        viewStatus.termList
                    )
                )
            }

            viewStatus.isComplete -> {
                mainAdapter.setData(viewStatus.resultList)

                if (viewStatus.resultList.isEmpty()) {
                    toast(OFFLINE_ERROR_TEXT)
                }
            }

            viewStatus.isError && viewStatus.errorMessage.isNotEmpty() -> {
                toast(viewStatus.errorMessage)
            }
        }
    }

    override fun onSelectItem(item: Result) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("collectionViewUrl", item.collectionViewUrl)
        startActivity(intent)
    }

    private fun setUpSearchView() {
        val searchEditText = mainSearchCardView.getEditText()
        searchEditText.setSelection(searchEditText.text.length)
        searchEditText.setHint(R.string.search)
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(searchEditText.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }
}