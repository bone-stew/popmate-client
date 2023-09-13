package com.example.popmate.view.fragments.user

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.popmate.R
import com.example.popmate.config.BaseFragment
import com.example.popmate.databinding.FragmentMyPageLogoutBinding
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.util.LessonLoginDialog
import com.example.popmate.util.LessonLogoutDialog
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.activities.reservation.MyReservationActivity
import com.example.popmate.viewmodel.user.MyPageLogoutViewModel


class MyPageLogoutFragment : BaseFragment<FragmentMyPageLogoutBinding, MyPageLogoutViewModel>(R.layout.fragment_my_page_logout) {

    override val viewModel: MyPageLogoutViewModel by lazy {
        ViewModelProvider(this)[MyPageLogoutViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
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
                    ApiClient.setJwtToken(null)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onCancelClicked() {

                }
            }
            dialog.start()
        }
    }

}
