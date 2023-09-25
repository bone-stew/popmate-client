package com.example.popmate.view.fragments.popupstore

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupStoreBinding
import com.example.popmate.util.CalendarDataListener
import com.example.popmate.util.SearchQueryListener
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.adapters.popupstore.PopupStoreAdapter
import com.example.popmate.viewmodel.popupstore.PopupStoreListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class PopupStoreFragment : Fragment(), CalendarDataListener, SearchQueryListener {
    private lateinit var binding: FragmentPopupStoreBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: PopupStoreListViewModel
    private val dateRangeFormatter = DateTimeFormatter.ofPattern("MM.dd")
    private var isSearchResult = false
    private var searchQuery: String? = null

    private var isOpeningSoon = false
    private var startDate: LocalDate = LocalDate.now()
    private var endDate: LocalDate = LocalDate.now().plusYears(1)
    private var offSetRows = null
    private var rowsToGet = null


    inner class GridSpacingDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popup_store, container, false)
        binding.popupstoreRecyclerView.addItemDecoration(GridSpacingDecoration(2, 50))

        viewModel = ViewModelProvider(this)[PopupStoreListViewModel::class.java]
        viewModel.loadList(
            isOpeningSoon,
            startDate.toString(),
            endDate.toString(),
            searchQuery,
            offSetRows,
            rowsToGet
        )

        viewModel.storeList.observe(viewLifecycleOwner) {
            binding.run {
                stores = it
                popupstoreRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                popupstoreRecyclerView.adapter =
                    PopupStoreAdapter(requireContext(), it.popupStores, PopupStoreAdapter.ViewHolderType.VERTICAL_LARGE_GRID)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.shimmerLayout.startShimmer()
                binding.popupstoreRecyclerView.visibility = View.GONE
                binding.shimmerLayout.visibility = View.VISIBLE
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.popupstoreRecyclerView.visibility = View.VISIBLE
                binding.shimmerLayout.visibility = View.GONE

            }
        }

        refreshCalendarText(startDate, endDate.minusYears(1).plusMonths(1))

        binding.run {
            isOpeningSoonBtn.setOnClickListener {
                isOpeningSoon = !isOpeningSoon
                viewModel.loadList(
                    isOpeningSoon,
                    startDate.toString(),
                    endDate.toString(),
                    searchQuery,
                    offSetRows,
                    rowsToGet
                )
            }
            keywordResult.cancelIcon.setOnClickListener {
                searchQuery = null
                viewModel.loadList(
                    isOpeningSoon,
                    startDate.toString(),
                    endDate.toString(),
                    searchQuery,
                    offSetRows,
                    rowsToGet
                )
                isSearchResult = false
                refreshSearchText()
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.setOnClickListener {
            val searchFragment = SearchFragment()
            searchFragment.setSearchQueryListener(this)
            binding.popupstoreRecyclerView.visibility = View.GONE
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, searchFragment)
                .addToBackStack(null)
                .commit()
        }
        val calendarLayout = view.findViewById<ConstraintLayout>(R.id.calendarLayout)
        calendarLayout.setOnClickListener {
            val bottomSheetFragment = CalendarBottomSheetFragment()
            bottomSheetFragment.setDataListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
        refreshSearchText()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onDateRangeSelected(startDateFromCalendar: LocalDate, endDateFromCalendar: LocalDate) {
        startDate = startDateFromCalendar
        endDate = endDateFromCalendar
        refreshCalendarText(startDate, endDate)
        viewModel.loadList(
            isOpeningSoon,
            startDate.toString(),
            endDate.toString(),
            searchQuery,
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
        if (query == "") {
            searchQuery = null
        } else {
            searchQuery = query
        }
        binding.popupstoreRecyclerView.visibility = View.GONE
        isSearchResult = true
        binding.popupstoreRecyclerView.visibility = View.VISIBLE
    }


    private fun refreshSearchText() {
        val maxKeywordLength = 20
        val truncatedQuery = if (searchQuery?.length ?: 0 > maxKeywordLength) {
            searchQuery?.substring(0, maxKeywordLength - 3) + "..."
        } else {
            searchQuery
        }
        binding.keywordResult.keywordText.setText(truncatedQuery)

        if (isSearchResult) {
            binding.keywordResult.keywordText.visibility = View.VISIBLE
            binding.keywordResult.cancelIcon.visibility = View.VISIBLE
        } else {
            binding.keywordResult.keywordText.visibility = View.GONE
            binding.keywordResult.cancelIcon.visibility = View.GONE
        }
    }
}
