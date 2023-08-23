package com.example.popmate.config

import android.app.Application
import com.example.popmate.R
import com.kakao.sdk.common.KakaoSdk


class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // KaKao SDK  초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }
}