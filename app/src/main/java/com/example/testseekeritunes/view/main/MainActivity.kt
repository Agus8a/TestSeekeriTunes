package com.example.testseekeritunes.view.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.domain.model.Result
import com.example.domain.util.OFFLINE_ERROR_TEXT
import com.example.testseekeritunes.R
import com.example.testseekeritunes.core.BaseActivity
import com.example.testseekeritunes.core.BaseOnSelectItem
import com.example.testseekeritunes.util.build
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
                    Toast.makeText(this@MainActivity, OFFLINE_ERROR_TEXT, Toast.LENGTH_SHORT).show()
                }

                viewModel.suggest()
            }

            viewStatus.isError && viewStatus.errorMessage.isNotEmpty() -> {
                Toast.makeText(this@MainActivity, viewStatus.errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onSelectItem(item: Result) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("collectionViewUrl", item.collectionViewUrl)
        startActivity(intent)
    }

    private fun setUpSearchView() {
        val searchEditText = mainSearchCardView.getInput()
        searchEditText.setSelection(searchEditText.text.length)
        searchEditText.setHint(R.string.search)
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(searchEditText.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
        searchEditText.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel.search(s.toString())
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
        }
        searchEditText.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            viewModel.search(searchEditText.text.toString())
        }
    }
}