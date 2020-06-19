package com.bagelly.mvvm.ui.search.history

import com.bagelly.mvvm.model.api.RetrofitClient
import com.bagelly.mvvm.model.store.SearchHistoryStore

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.history
 * @ClassName: SearchHistoryRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/16 下午1:54
 */
class SearchHistoryRepository {

    suspend fun getHotSearch()= RetrofitClient.apiService.getHotWords().apiData()

    fun saveSearchHistory(searchWords:String)= SearchHistoryStore.saveSearchHistory(searchWords)

    fun deleteSearchHistory(searchWords: String){
        SearchHistoryStore.deleteSearchHistory(searchWords)
    }

    fun getSearchHistory()=SearchHistoryStore.getSearchHistory()

}