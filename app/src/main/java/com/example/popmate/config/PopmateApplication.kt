package com.example.popmate.config

import android.app.Application
import com.example.popmate.R
import com.example.popmate.util.PreferenceManager
import com.kakao.sdk.common.KakaoSdk


class PopmateApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceManager
    }


    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceManager(this)
        prefs.clear()

        // KaKao SDK  초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }
}
