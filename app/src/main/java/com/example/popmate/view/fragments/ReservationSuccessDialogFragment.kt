package com.example.popmate.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.popmate.R
import com.example.popmate.databinding.FragmentReservationSuccessDialogBinding

class ReservationSuccessDialogFragment : DialogFragment() {

    private var _binding: FragmentReservationSuccessDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reservation_success_dialog,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner // 뷰의 라이프사이클을 옵저빙하도록 설정

        return binding.root
    }

    /**
     * onViewCreated()는 뷰가 생성되었을 때 호출되는 메서드로 뷰를 초기화하는 코드를 작성한다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("ReservationSuccessDialogFragment", "onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰가 사라질 때 바인딩 객체도 같이 해제
    }
}
