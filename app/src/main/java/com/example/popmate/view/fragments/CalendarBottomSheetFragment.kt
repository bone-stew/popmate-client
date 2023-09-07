package com.example.popmate.view.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popmate.databinding.FragmentCalendarBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarBottomSheetFragment : BottomSheetDialogFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCalendarBottomSheetBinding

    interface DateRangeCallback {
        fun onDateRangeSelected(startDate: Calendar, endDate: Calendar)
    }

    private var dateRangeCallback: DateRangeCallback? = null

    fun setCallback(callback: DateRangeCallback) {
        dateRangeCallback = callback
    }

    override fun onDetach() {
        super.onDetach()
        dateRangeCallback = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, startYear, startMonth, startDay ->
                val startDate = Calendar.getInstance()
                startDate.set(startYear, startMonth, startDay)

                // Handle the start date
                showEndDatePicker(startDate)
            },
            year, month, day
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        return datePickerDialog
    }

    private fun showEndDatePicker(startDate: Calendar) {
        val endYear = startDate.get(Calendar.YEAR)
        val endMonth = startDate.get(Calendar.MONTH)
        val endDay = startDate.get(Calendar.DAY_OF_MONTH)

        val endDatePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, endYear, endMonth, endDay ->
                val endDate = Calendar.getInstance()
                endDate.set(endYear, endMonth, endDay)

                // Handle the end date
                dateRangeCallback?.onDateRangeSelected(startDate, endDate)
            },
            endYear, endMonth, endDay
        )

        endDatePickerDialog.datePicker.minDate = startDate.timeInMillis

        endDatePickerDialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarBottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
