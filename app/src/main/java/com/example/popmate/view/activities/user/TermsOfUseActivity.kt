package com.example.popmate.view.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.popmate.R
import com.example.popmate.databinding.ActivityTermsOfUseBinding

class TermsOfUseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTermsOfUseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfUseBinding.inflate(layoutInflater)
        val url = intent.getStringExtra("url")
        val webView = binding.webView
        if (url != null) {
            // 웹뷰 설정
            webView.settings.apply {
                builtInZoomControls = true
                domStorageEnabled = true
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                loadsImagesAutomatically = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                setSupportZoom(false)
            }
            webView.webViewClient = WebViewClient() // 웹뷰에서 페이지 이동을 처리하기 위한 클라이언트 설정

            // URL 로드
            webView.loadUrl(url)
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        binding.webView.destroy()
        super.onDestroy()
    }
}