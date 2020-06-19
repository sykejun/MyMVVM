package com.bagelly.mvvm.ui.search.history

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.HotWord
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.search.SearchActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_search_history.*
import kotlinx.android.synthetic.main.item_hot_search.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.history
 * @ClassName: SearchHistoryFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/16 上午11:41
 */
class SearchHistoryFragment :BaseVmFragment<SearchHistoryViewModel>(){

    companion object{
        fun newInstance()=SearchHistoryFragment()
    }

     private val searchHistoryAdapter by lazy {SearchHistoryAdapter() }

    override fun layoutRes()= R.layout.fragment_search_history

    override fun viewModelClass()=SearchHistoryViewModel::class.java

    override fun initView() {
        searchHistoryAdapter.apply {
            bindToRecyclerView(rvSearchHistory)
            setOnItemClickListener { _, _, position ->
                (activity as SearchActivity).fillSearchInput(data[position])
            }

            setOnItemChildClickListener { _, _, position ->
                mViewModel.deleteSearchHistory(data[position])
            }
        }
    }

    fun addSearchHistory(searchWords: String) {
        mViewModel.addSearchHistory(searchWords)
    }

    override fun initData() {
        mViewModel.getHotSearch()
        mViewModel.getSearchHistory()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            hotWords.observe(viewLifecycleOwner, Observer {
                tvHotSearch.visibility= View.VISIBLE
                setHotWords(it)
            })


            searchHistory.observe(viewLifecycleOwner, Observer {
                tvSearchHistory.isVisible=it.isEmpty()
                searchHistoryAdapter.setNewData(it)
            })
        }

    }

    private fun setHotWords(hotwords: List<HotWord>) {
        tflHotSearch.run {
            adapter=object :TagAdapter<HotWord>(hotwords){
                override fun getView(parent: FlowLayout?, position: Int, hotword: HotWord?): View {
                    return LayoutInflater.from(parent?.context).
                    inflate(R.layout.item_hot_search,parent,false).apply {
                        this.tvTag.text=hotword?.name
                    }
                }

            }

            setOnTagClickListener { _, position, _ ->
                (activity as? SearchActivity)?.fillSearchInput(hotwords[position].name)
                false
            }
        }
    }
}