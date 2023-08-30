package com.example.popmate.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.popmate.databinding.ActivityLoginBinding
import com.example.popmate.model.data.remote.login.GoogleLoginVO
import com.example.popmate.model.data.remote.login.LoginTokenVO
import com.example.popmate.model.repository.service.ApiClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 나중에 key해쉬 알려고 할 때
        //Log.d("aa", "keyhash : ${Utility.getKeyHash(this)}")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onActivityResult(result.resultCode, result.data)
        }
        binding.kakaoButton.setOnClickListener {
            kakaoLogin()
        }
        binding.googleButton.setOnClickListener {
            googleLogin()
        }
    }

    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                // 토큰 얻어오는 함수
                getKakaoToken(token.accessToken)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    getKakaoToken(token.accessToken)
                    nextMainActivity()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    fun kakaoLogout() {
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("TAG", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i("TAG", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun kakaoUnlink(){
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("TAG", "연결 끊기 실패", error)
            }
            else {
                Log.i("TAG", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    private fun googleLogin(){
        // GoogleSignInOptions :  로그인 시 요청할 권한 및 사용자 데이터에 대한 설정을 지정할 수 있는 것
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // GoogleSignIn : Google 로그인 API를 사용하여 사용자가 Google 계정으로 앱에 로그인할 수 있도록 도와주는 클래스 및 API 모음이다.
        gsc = GoogleSignIn.getClient(this, gso)
        // 마지막으로 로그인한 계정 정보를 가져오는 곳
        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val name = acct.displayName
            val email = acct.email
            val provider = "Google"
            val googleLogin = name?.let { GoogleLoginVO(it, email ?: "", provider) }
            getGoogleToken(googleLogin!!)
        }

        // 구글 계정 로그인이 안되어 있는 경우 실행
        val signInIntent = gsc.signInIntent
        resultLauncher.launch(signInIntent)

    }

    //첫 구글 계정 등록할 때 나오는 로그인 창
    private fun onActivityResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val name = task.result.displayName
                val email = task.result.email
                val provider = "Google"
                val googleLogin = name?.let { GoogleLoginVO(it, email ?: "", provider) }
                getGoogleToken(googleLogin!!)
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 구글 유저 정보 넘겨서 JWT토큰 가져오는 곳
    private fun getGoogleToken(googleLoginVO: GoogleLoginVO){
        val apiService = ApiClient.getTokenService
        val call: Call<LoginTokenVO> = apiService.getGoogleToken(googleLoginVO)
        call.enqueue(object : Callback<LoginTokenVO>{
            override fun onResponse(call: Call<LoginTokenVO>, response: Response<LoginTokenVO>) {
                val token = response.body()
                ApiClient.setJwtToken(token)
                nextMainActivity()
            }
            override fun onFailure(call: Call<LoginTokenVO>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    // 카카오 토큰 넘겨서 JWT토큰 가져오는 곳
    private fun getKakaoToken(token : String) {
        val apiService = ApiClient.getTokenService
        val call: Call<LoginTokenVO> = apiService.getKakaoToken(token)
        call.enqueue(object : Callback<LoginTokenVO> {
            override fun onResponse(call: Call<LoginTokenVO>, response: Response<LoginTokenVO>) {
                val token = response.body()
                ApiClient.setJwtToken(token)
                //ApiClient.getJwtToken()?.let { Log.d("ddd", it) }
                nextMainActivity()
            }

            override fun onFailure(call: Call<LoginTokenVO>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }



    // 다음 Activity로 가는 코드
    private fun nextMainActivity() {
        val intent = Intent(this, LoginTestActivity::class.java)
        startActivity(intent)
        finish()
    }


}