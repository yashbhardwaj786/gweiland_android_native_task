package com.yashgweiland.nativeandroidtask.data.repository

import com.yashgweiland.nativeandroidtask.data.ResultData
import com.yashgweiland.nativeandroidtask.data.preferences.JokesPreference


class SharedPrefRepository(
    private val jokesPreference: JokesPreference
): BaseRepository() {

//    fun fetchJokesFromSharedPref(): ArrayList<ResultData> {
//        return jokesPreference.getSavedJokes()
//    }
//
//    fun saveJoke(data: ArrayList<ResultData>) {
//        jokesPreference.saveJoke(data)
//    }
}