package com.example.popmate.viewmodel.login


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


class LoginViewModel : ViewModel() {

    private val _context = MutableLiveData<Context>()
    val context: LiveData<Context>
        get() = _context

    private val _loginSuccess = MutableLiveData<Boolean>(false)

    // LiveData로 감싸기
    private val _loginSuccessLiveData: LiveData<Boolean> = _loginSuccess
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccessLiveData

    fun setContext(context: Context) {
        _context.value = context
    }

    fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val currentContext = _context.value

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (currentContext?.let { UserApiClient.instance.isKakaoTalkLoginAvailable(it) } == true) {
            if (currentContext != null) {
                UserApiClient.instance.loginWithKakaoTalk(currentContext) { token, error ->
                    if (error != null) {
                        Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(currentContext, callback = callback)
                    } else if (token != null) {
                        _loginSuccess.value = true
                        Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            }
        } else {
            if (currentContext != null) {
                UserApiClient.instance.loginWithKakaoAccount(currentContext, callback = callback)
            }
        }
    }
}