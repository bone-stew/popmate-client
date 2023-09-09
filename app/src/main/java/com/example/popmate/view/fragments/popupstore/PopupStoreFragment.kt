package com.example.popmate.view.fragments.popupstore

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupStoreBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.util.CalendarDataListener
import com.example.popmate.util.SearchQueryListener
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.adapters.PopupStoreAdapter
import com.example.popmate.viewmodel.popupstore.PopupStoreListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class PopupStoreFragment : Fragment(), CalendarDataListener, SearchQueryListener {
    private lateinit var binding: FragmentPopupStoreBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: PopupStoreListViewModel
    private val dateRangeFormatter = DateTimeFormatter.ofPattern("MM.dd")
    private var isSearchResult = false
    private var searchQuery: String? = ""

    private var isOpeningSoon = false
    private var startDate: LocalDate = LocalDate.now()
    private var endDate: LocalDate = LocalDate.now().plusYears(1)
    private var keyword = null
    private var offSetRows = null
    private var rowsToGet = null

    companion object {
        private const val TWO_POPUPSTORES_WIDTH = 362
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val screenWidthInPixels = resources.displayMetrics.widthPixels
        val screenDensity = resources.displayMetrics.density
        val screenWidthInDp = screenWidthInPixels / screenDensity
        val desiredPadding = (screenWidthInDp - TWO_POPUPSTORES_WIDTH) / 2

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popup_store, container, false)
        viewModel = ViewModelProvider(requireActivity())[PopupStoreListViewModel::class.java]
        viewModel.loadList(isOpeningSoon,
            startDate.toString(),
            endDate.toString(),
            null,
            offSetRows,
            rowsToGet)
        viewModel.storeList.observe(viewLifecycleOwner) {
            binding.run {
                stores = it
                popupstoreRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                popupstoreRecyclerView.adapter =
                    PopupStoreAdapter(requireContext(), it.popupStores, PopupStoreAdapter.ViewHolderType.VERTICAL_LARGE_GRID)
                popupstoreRecyclerView.setPadding(desiredPadding.toInt(), 0, 0, 0)
//                popupstoreRecyclerView.setNestedScrollingEnabled(false);

                imgArrow.setOnClickListener {
                    mainActivity.goBack()
                }
            }
        }
        refreshCalendarText(startDate, endDate.minusYears(1).plusMonths(1))

        binding.keywordResult.cancelIcon.setOnClickListener{
            viewModel.loadList(
                isOpeningSoon,
                startDate.toString(),
                endDate.toString(),
                null,
                offSetRows,
                rowsToGet
            )
            isSearchResult = false
            refreshSearchText()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isOpeningSoonBtn = binding.isOpeningSoonBtn

        isOpeningSoonBtn.setOnClickListener {
            isOpeningSoon = !isOpeningSoon
            viewModel.refreshList(
                isOpeningSoon,
                startDate.toString(),
                endDate.toString(),
                keyword,
                offSetRows,
                rowsToGet
            )
        }


        val calendarLayout = view.findViewById<LinearLayout>(R.id.calendarLayout)
        calendarLayout.setOnClickListener {
            val bottomSheetFragment = CalendarBottomSheetFragment()
            bottomSheetFragment.setDataListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

        }

        binding.searchButton.setOnClickListener {
            val searchFragment = SearchFragment()
            searchFragment.setSearchQueryListener(this)
            viewModel.clearList()
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, searchFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onDateRangeSelected(startDateFromCalendar: LocalDate, endDateFromCalendar: LocalDate) {
        startDate = startDateFromCalendar
        endDate = endDateFromCalendar
        refreshCalendarText(startDate, endDate)
        viewModel.refreshList(
            isOpeningSoon,
            startDate.toString(),
            endDate.toString(),
            keyword,
            offSetRows,
            rowsToGet
        )
    }

    override fun refreshCalendarText(startDate: LocalDate, endDate: LocalDate) {
        val startDateText = dateRangeFormatter.format(startDate)
        val endDateText = dateRangeFormatter.format(endDate)
        binding.calendarLayout.dateRangeText.text = startDateText + "~" + endDateText
    }

    override fun onSearchQuerySubmitted(query: String) {
        Log.i("LISTFRAGMENT SEARCHING BACK", query.toString() )
        if (query == ""){
            searchQuery = null
        } else {
            searchQuery = query
        }
        isSearchResult = true
        searchQuery = query
        viewModel.refreshList(
            isOpeningSoon,
            startDate.toString(),
            endDate.toString(),
            searchQuery,
            offSetRows,
            rowsToGet
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshList(
            isOpeningSoon,
            startDate.toString(),
            endDate.toString(),
            searchQuery,
            offSetRows,
            rowsToGet
        )
        refreshSearchText()
        binding.keywordResult.keywordText.setText(searchQuery)
    }

    fun refreshSearchText() {
        if (isSearchResult) {
            binding.keywordResult.keywordText.visibility = View.VISIBLE
            binding.keywordResult.cancelIcon.visibility = View.VISIBLE
        } else {
            binding.keywordResult.keywordText.visibility = View.GONE
            binding.keywordResult.cancelIcon.visibility = View.GONE
        }
    }
}
