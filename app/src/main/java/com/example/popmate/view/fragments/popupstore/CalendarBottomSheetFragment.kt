package com.example.popmate.view.fragments.popupstore

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.example.popmate.R
import com.example.popmate.databinding.CalendarDayLayoutBinding
import com.example.popmate.databinding.CalendarMonthHeaderBinding
import com.example.popmate.databinding.FragmentCalendarBottomSheetBinding
import com.example.popmate.util.CalendarDataListener
import com.example.popmate.util.ContinuousSelectionHelper.getSelection
import com.example.popmate.util.ContinuousSelectionHelper.isInDateBetweenSelection
import com.example.popmate.util.ContinuousSelectionHelper.isOutDateBetweenSelection
import com.example.popmate.util.DateSelection
import com.example.popmate.util.dateRangeDisplayText
import com.example.popmate.util.displayText
import com.example.popmate.util.getDrawableCompat
import com.example.popmate.util.makeInVisible
import com.example.popmate.util.makeVisible
import com.example.popmate.util.setTextColorRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalendarBottomSheetFragment : BottomSheetDialogFragment() {


    private val today = LocalDate.now()

    private var selection = DateSelection()

    private val headerDateFormatter = DateTimeFormatter.ofPattern("MM.dd")

    private var _binding: FragmentCalendarBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var dateRangeListener: CalendarDataListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)


        _binding = FragmentCalendarBottomSheetBinding.inflate(inflater, container, false)
        val daysOfWeek = daysOfWeek()

        binding.legendLayout.root.children.forEachIndexed { index, child ->
            (child as TextView).apply {
                text = daysOfWeek[index].displayText()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                setTextColorRes(com.example.popmate.R.color.tx_gray)
            }
        }


        configureBinders()


        val currentMonth = YearMonth.now()
        binding.calendarView.setup(currentMonth, currentMonth.plusMonths(12), daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)

        binding.saveButton.setOnClickListener click@{
            val (startDate, endDate) = selection
            if (startDate != null && endDate != null) {
                val text = dateRangeDisplayText(startDate, endDate)
                dateRangeListener?.onDataSaved(startDate, endDate)
                dismiss()
            }
        }

        binding.resetButton.setOnClickListener click@{
            selection = DateSelection()
            binding.calendarView.notifyCalendarChanged()
            bindSummaryViews()
            true
        }

        return binding.root
    }

    private fun bindSummaryViews() {
        binding.startDateText.apply {
            if (selection.startDate != null) {
                text = headerDateFormatter.format(selection.startDate)
                setTextColorRes(com.example.popmate.R.color.tx_gray)
            } else {
                text = getString(com.example.popmate.R.string.start_date)
                setTextColor(Color.GRAY)
            }
        }

        binding.endDateText.apply {
            if (selection.endDate != null) {
                text = headerDateFormatter.format(selection.endDate)
                setTextColorRes(com.example.popmate.R.color.tx_gray)
            } else {
                text = getString(com.example.popmate.R.string.end_date)
                setTextColor(Color.GRAY)
            }
        }
        binding.saveButton.isEnabled = selection.daysBetween != null
    }

    fun setDataListener(listener: CalendarDataListener) {
        dateRangeListener = listener
    }

    private fun configureBinders() {
        val clipLevelHalf = 5000
        val ctx = requireContext()
        val rangeStartBackground =
            ctx.getDrawableCompat(com.example.popmate.R.drawable.calendar_continuous_selected_bg_satrt).also {
                it.level = clipLevelHalf // Used by ClipDrawable
            }
        val rangeEndBackground =
            ctx.getDrawableCompat(com.example.popmate.R.drawable.calendar_continuous_selected_bg_end).also {
                it.level = clipLevelHalf // Used by ClipDrawable
            }
        val rangeMiddleBackground =
            ctx.getDrawableCompat(com.example.popmate.R.drawable.calendar_continuous_selected_bg_middle)
        val singleBackground = ctx.getDrawableCompat(com.example.popmate.R.drawable.calendar_single_selected_bg)
        val todayBackground = ctx.getDrawableCompat(com.example.popmate.R.drawable.calendar_today)

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val binding = CalendarDayLayoutBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate &&
                        (day.date == today || day.date.isAfter(today))
                    ) {
                        selection = getSelection(
                            clickedDate = day.date,
                            dateSelection = selection,
                        )
                        this@CalendarBottomSheetFragment.binding.calendarView.notifyCalendarChanged()
                        bindSummaryViews()
                    }
                }
            }
        }

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.binding.dayText
                val roundBgView = container.binding.roundBackgroundView
                val continuousBgView = container.binding.continuousBackgroundView

                textView.text = null
                roundBgView.makeInVisible()
                continuousBgView.makeInVisible()
                val (startDate, endDate) = selection

                when (data.position) {
                    DayPosition.MonthDate -> {
                        textView.text = data.date.dayOfMonth.toString()
                        if (data.date.isBefore(today)) {
                            textView.setTextColorRes(com.example.popmate.R.color.tx_gray)
                        } else {
                            when {
                                startDate == data.date && endDate == null -> {
                                    textView.setTextColorRes(com.example.popmate.R.color.white)
                                    roundBgView.applyBackground(singleBackground)
                                }

                                data.date == startDate -> {
                                    textView.setTextColorRes(com.example.popmate.R.color.white)
                                    continuousBgView.applyBackground(rangeStartBackground)
                                    roundBgView.applyBackground(singleBackground)
                                }

                                startDate != null && endDate != null && (data.date > startDate && data.date < endDate) -> {
                                    textView.setTextColorRes(com.example.popmate.R.color.tx_gray)
                                    continuousBgView.applyBackground(rangeMiddleBackground)
                                }

                                data.date == endDate -> {
                                    textView.setTextColorRes(com.example.popmate.R.color.white)
                                    continuousBgView.applyBackground(rangeEndBackground)
                                    roundBgView.applyBackground(singleBackground)
                                }

                                data.date == today -> {
                                    textView.setTextColorRes(R.color.tx_gray)
                                    roundBgView.applyBackground(todayBackground)
                                }

                                else -> textView.setTextColorRes(R.color.tx_gray)
                            }
                        }
                    }

                    DayPosition.InDate ->
                        if (startDate != null && endDate != null &&
                            isInDateBetweenSelection(data.date, startDate, endDate)
                        ) {
                            continuousBgView.applyBackground(rangeMiddleBackground)
                        }

                    DayPosition.OutDate ->
                        if (startDate != null && endDate != null &&
                            isOutDateBetweenSelection(data.date, startDate, endDate)
                        ) {
                            continuousBgView.applyBackground(rangeMiddleBackground)
                        }
                }
            }

            private fun View.applyBackground(drawable: Drawable) {
                makeVisible()
                background = drawable
            }
        }


        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarMonthHeaderBinding.bind(view).headerText
        }
        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    container.textView.text = data.yearMonth.displayText()
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindSummaryViews()
    }
}
