package com.example.popmate.view.fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.CalendarDayLayoutBinding
import com.example.popmate.databinding.CalendarMonthHeaderBinding
import com.example.popmate.databinding.FragmentCalendarBottomSheetBinding
import com.example.popmate.databinding.FragmentPopupStoreBinding
import com.example.popmate.util.ContinuousSelectionHelper.getSelection
import com.example.popmate.util.ContinuousSelectionHelper.isInDateBetweenSelection
import com.example.popmate.util.ContinuousSelectionHelper.isOutDateBetweenSelection
import com.example.popmate.util.DateSelection
import com.example.popmate.util.dateRangeDisplayText
import com.example.popmate.util.displayText
import com.example.popmate.util.getColorCompat
import com.example.popmate.util.getDrawableCompat
import com.example.popmate.util.makeInVisible
import com.example.popmate.util.makeVisible
import com.example.popmate.util.setTextColorRes
import com.example.popmate.view.adapters.PopupStoreAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.LazyThreadSafetyMode.NONE


class CalendarBottomSheetFragment : BottomSheetDialogFragment() {


    private val today = LocalDate.now()

    private var selection = DateSelection()

    private val headerDateFormatter = DateTimeFormatter.ofPattern("EEE'\n'd MMM")

    private var _binding: FragmentCalendarBottomSheetBinding? = null
    private val binding get() = _binding!!



    class DayViewContainer(view: View) : ViewContainer(view) {
        val textView = view.findViewById<TextView>(R.id.exFourDayText)

        // With ViewBinding
        // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentCalendarBottomSheetBinding.inflate(inflater, container, false)
        val daysOfWeek = daysOfWeek()

        binding.legendLayout.root.children.forEachIndexed { index, child ->
            (child as TextView).apply {
                text = daysOfWeek[index].displayText()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                setTextColorRes(R.color.tx_gray)
            }
        }

        configureBinders()


        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.calendarView.setup(startMonth, endMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)


        return binding.root
//        return inflater.inflate(R.layout.fragment_calendar_bottom_sheet, container, false)
    }

    private fun configureBinders() {
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        addStatusBarColorUpdate(R.color.white)
    }
}
