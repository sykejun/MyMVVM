package com.bagelly.mvvm.ui.search.history

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.HotWord
import com.bagelly.mvvm.ui.base.BaseViewModel

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.history
 * @ClassName: SearchHistoryViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/19 上午11:17
 */

class SearchHistoryViewModel :BaseViewModel(){

    private val searchHistoryRepository by lazy { SearchHistoryRepository()}

    val hotWords= MutableLiveData<List<HotWord>>()

    val searchHistory=MutableLiveData<MutableList<String>>()


     fun getHotSearch(){
         launch(block = {
             hotWords.value=searchHistoryRepository.getHotSearch()
         })
     }


    fun getSearchHistory(){
        searchHistory.value=searchHistoryRepository.getSearchHistory()
    }

    fun addSearchHistory(searchWords:String){
        val history=searchHistory.value?: mutableListOf()
        if (history.contains(searchWords)){
            history.remove(searchWords)
            searchHistoryRepository.saveSearchHistory(searchWords)
        }
    }

    fun deleteSearchHistory(searchWords: String){
        val history=searchHistory.value?: mutableListOf()
        if (history.contains(searchWords)){
            searchHistory.value=history
            searchHistoryRepository.deleteSearchHistory(searchWords)
        }
    }
}