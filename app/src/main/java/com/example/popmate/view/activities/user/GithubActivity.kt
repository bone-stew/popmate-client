package com.example.popmate.view.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.popmate.R
import com.example.popmate.databinding.ActivityGithubBinding
import com.example.popmate.databinding.ActivityTermsOfUseBinding

class GithubActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGithubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubBinding.inflate(layoutInflater)
        val url = intent.getStringExtra("url")
        val webView = binding.webView
        if (url != null) {
            // 웹뷰 설정
            webView.settings.javaScriptEnabled = true // JavaScript 활성화
            webView.webViewClient = WebViewClient() // 웹뷰에서 페이지 이동을 처리하기 위한 클라이언트 설정

            // URL 로드
            webView.loadUrl(url)
        }
        setContentView(binding.root)
    }
}