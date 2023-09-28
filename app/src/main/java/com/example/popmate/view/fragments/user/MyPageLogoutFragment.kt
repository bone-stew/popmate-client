package com.example.popmate.view.fragments.user

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.popmate.databinding.FragmentMyPageLogoutBinding
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.util.LessonLogoutDialog
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.activities.reservation.MyReservationActivity
import com.example.popmate.view.activities.user.MyPagePurchaseActivity
import com.example.popmate.view.activities.user.TermsOfUseActivity
import com.example.popmate.viewmodel.user.MyPageLogoutViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity


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
            val intent = Intent(requireContext(), MyPagePurchaseActivity::class.java)
            requireContext().startActivity(intent)
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
            intent.putExtra("url", "https://woowon.notion.site/1221bc7c941843f480be4fd7d559c3b6?pvs=4") // 이동할 웹페이지 URL을 넣어주세요.
            context?.startActivity(intent)
        }

        /**
         * 오픈소스 클릭 시 Activity로 이동 (깃허브)
         */
        binding.layoutOpensource.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스 목록")
            startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        }

        binding.layoutPrivacy.setOnClickListener{
            val intent = Intent(context, TermsOfUseActivity::class.java)
            intent.putExtra("url", "https://woowon.notion.site/29bc0080820145d9b4b655e1a534942c")
            context?.startActivity(intent)
        }
    }

}
