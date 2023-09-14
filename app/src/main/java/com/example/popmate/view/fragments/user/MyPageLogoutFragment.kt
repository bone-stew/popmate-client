package com.example.popmate.view.fragments.user

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.popmate.R
import com.example.popmate.databinding.FragmentMyPageLogoutBinding
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.util.LessonLogoutDialog
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.activities.reservation.MyReservationActivity
import com.example.popmate.view.activities.user.TermsOfUseActivity
import com.example.popmate.viewmodel.user.MyPageLogoutViewModel


class MyPageLogoutFragment() : Fragment() {
    private lateinit var binding : FragmentMyPageLogoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageLogoutBinding.inflate(layoutInflater)

        val model: MyPageLogoutViewModel by viewModels()
        model.loadUser()
        model.myName.observe(viewLifecycleOwner) { user ->
            val name = user?.userName ?: "DefaultUserName"
            binding.mypageLogin.text = name
        }
        initEvent()

        return binding.root
    }

    private fun initEvent() {

        /**
         * 구매 내역 클릭 시 MyReservationActivity로 이동
         */
        binding.layoutMyPageLogoutPurchase.setOnClickListener {
            val newFragment = MyPagePurchaseFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, newFragment)
                .addToBackStack(null)
                .commit()
        }

        /**
         * 예약 내역 클릭 시 MyReservationActivity로 이동
         */
        binding.layoutMyReservationInformation.setOnClickListener {
            Log.d("MyPageLogoutFragment", "나의 예약 정보 클릭")
            val intent = Intent(requireContext(), MyReservationActivity::class.java)
            requireContext().startActivity(intent)
        }

        /**
         * 로그아웃 클릭 시 MainActivity로 이동
         */
        binding.txtMypageLogout.setOnClickListener {
            val dialog = LessonLogoutDialog(requireContext())
            dialog.listener = object : LessonLogoutDialog.LessonOkDialogClickedListener{
                override fun onOkClicked() {
                    val pref = requireContext().getSharedPreferences("autoLogin", 0)
                    val editor = pref.edit()
                    editor.remove("JwtToken")
                    editor.apply()
                    ApiClient.setJwtToken(null)
                    ApiClient.setJwtToken("")
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onCancelClicked() {

                }
            }
            dialog.start()
        }

        /**
         * 이용약관 클릭 시 Activity로 이동 (이용약관 url)
         */
        binding.layoutMyPageLogoutUse.setOnClickListener {
            val intent = Intent(context, TermsOfUseActivity::class.java)
            intent.putExtra("url", "https://naver.com") // 이동할 웹페이지 URL을 넣어주세요.
            context?.startActivity(intent)
        }

        /**
         * 오픈소스 클릭 시 Activity로 이동 (깃허브)
         */
        binding.layoutOpensource.setOnClickListener {
            val intent = Intent(context, TermsOfUseActivity::class.java)
            intent.putExtra("url", "https://github.com/bone-stew") // 이동할 웹페이지 URL을 넣어주세요.
            context?.startActivity(intent)
        }
    }

}
