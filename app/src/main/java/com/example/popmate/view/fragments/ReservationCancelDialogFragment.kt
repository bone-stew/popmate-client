package com.example.popmate.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.popmate.R
import com.example.popmate.databinding.FragmentReservationCancelDialogBinding
import com.example.popmate.viewmodel.ReservationSuccessViewModel

class ReservationCancelDialogFragment : DialogFragment() {

    private var _binding: FragmentReservationCancelDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReservationSuccessViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reservation_cancel_dialog,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

//      dialog fragment custom width
        try {
            val windowManager =
                requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val deviceSize = Point()
            display.getSize(deviceSize)
            val params = dialog!!.window!!.attributes
            params.width = (deviceSize.x * 0.9).toInt()
            params.horizontalMargin = 0.0f
            dialog!!.window!!.attributes = params
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * onViewCreated()는 뷰가 생성되었을 때 호출되는 메서드로 뷰를 초기화하는 코드를 작성한다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this)[ReservationSuccessViewModel::class.java]
//        viewModel.reservationId = arguments?.getLong("reservationId", 0) ?: 0

//        initView()
//        initEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰가 사라질 때 바인딩 객체도 같이 해제
    }
}