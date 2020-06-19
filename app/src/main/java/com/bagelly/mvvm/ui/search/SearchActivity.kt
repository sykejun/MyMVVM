package com.bagelly.mvvm.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.hideSoftInput
import com.bagelly.mvvm.ui.base.BaseActivity
import com.bagelly.mvvm.ui.search.history.SearchHistoryFragment
import com.bagelly.mvvm.ui.search.result.SearchResultFragment
import com.bagelly.mvvm.util.core.ActivityManger
import kotlinx.android.synthetic.main.activity_detail.ivBack
import kotlinx.android.synthetic.main.activity_search.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search
 * @ClassName: SearchActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午7:12
 */
class SearchActivity:BaseActivity() {
    override fun layoutRes()= R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init(){
        val hitoryFragment= SearchHistoryFragment.newInstance()
        val resultFragment= SearchResultFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer,hitoryFragment)
            .add(R.id.flContainer,resultFragment)
            .show(hitoryFragment)
            .hide(resultFragment)
            .commit()

        ivBack.setOnClickListener {
            if (resultFragment.isVisible){
                supportFragmentManager
                    .beginTransaction()
                    .hide(resultFragment)
                    .commit()
            }else{
                ActivityManger.finish(SearchActivity::class.java)
            }
        }
            ivDone.setOnClickListener{
                val searchWords=acetInput.text.toString()
                if (searchWords.isEmpty()) return@setOnClickListener
                it.hideSoftInput()
                hitoryFragment.addSearchHistory(searchWords)
                resultFragment.doSearch(searchWords)
                supportFragmentManager
                    .beginTransaction()
                    .show(resultFragment)
                    .commit()

            }

            acetInput.run {
               addTextChangedListener(afterTextChanged = {
                   ivClearSearch.isGone=it.isNullOrEmpty()
               })

                setOnEditorActionListener { v, actionId, event ->
                    if (actionId==EditorInfo.IME_ACTION_SEARCH){
                        ivDone.performClick()
                        true
                    }else{
                        false
                    }

                }
            }
            ivClearSearch.setOnClickListener { acetInput.setText("") }

    }

    fun fillSearchInput(keywords:String){
        acetInput.setText(keywords)
        acetInput.setSelection(keywords.length)
    }

    override fun onBackPressed() {
        ivBack.performClick()
    }
}