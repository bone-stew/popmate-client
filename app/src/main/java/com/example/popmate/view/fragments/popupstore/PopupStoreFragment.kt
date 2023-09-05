package com.example.popmate.view.fragments.popupstore

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupStoreBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.util.CalendarDataListener
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.adapters.PopupStoreAdapter
import java.time.LocalDate


class PopupStoreFragment : Fragment(), CalendarDataListener {
    private var _binding: FragmentPopupStoreBinding? = null
    private val binding get() = _binding!!
    private var isOpeningSoon = false
    private lateinit var mainActivity: MainActivity

    companion object {
        private const val TWO_POPUPSTORES_WIDTH = 362
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isOpeningSoonBtn = binding.isOpeningSoonBtn
        isOpeningSoonBtn.setOnClickListener {
            isOpeningSoon = !isOpeningSoon
        }


        val calendarLayout = view.findViewById<LinearLayout>(R.id.calendarLayout)
    calendarLayout.setOnClickListener{
            val bottomSheetFragment = CalendarBottomSheetFragment()
            bottomSheetFragment.setDataListener(this)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

    }

        binding.searchButton.setOnClickListener {
            val searchFragment = SearchFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, searchFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopupStoreBinding.inflate(inflater, container, false)

        val items = emptyList<PopupStore>()
        val screenWidthInPixels = resources.displayMetrics.widthPixels
        val screenDensity = resources.displayMetrics.density

        val screenWidthInDp = screenWidthInPixels / screenDensity

        val desiredPadding = (screenWidthInDp - TWO_POPUPSTORES_WIDTH) / 2

        binding.popupstoreRecyclerView.setPadding(desiredPadding.toInt(), 0, 0, 0)

        binding.popupstoreRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.popupstoreRecyclerView.adapter = PopupStoreAdapter(items, PopupStoreAdapter.ViewHolderType.VERTICAL_LARGE)

        binding.imgArrow.setOnClickListener {
            mainActivity.goBack()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onDataSaved(startDate: LocalDate, endDate: LocalDate) {
        Log.i("POPMATE", startDate.toString() + endDate.toString())
    }


}
