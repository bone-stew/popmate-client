package com.example.popmate.view.fragments

import android.R.menu
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupStoreBinding
import com.example.popmate.model.data.local.Item
import com.example.popmate.view.adapters.ItemAdapter
import java.util.Calendar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PopupStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopupStoreFragment : Fragment(), CalendarBottomSheetFragment.DateRangeCallback {
    private var _binding: FragmentPopupStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbar: Toolbar
    private var isOpeningSoon = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toggleVisitedButton.setOnClickListener {
            isOpeningSoon = !isOpeningSoon
            val buttonImageRes = if (isOpeningSoon) R.drawable.google_logo else R.drawable.kakao_logo
            binding.toggleVisitedButton.setImageResource(buttonImageRes)
        }

        binding.calendarButton.setOnClickListener {
            val bottomSheetFragment = CalendarBottomSheetFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.searchButton.setOnClickListener {
            // Start the search fragment
            val searchFragment = SearchFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, searchFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(com.example.popmate.R.menu.menu_search, menu)

//
//        val searchItem = menu?.findItem(R.id.menu_item_search)
//        val searchView = searchItem?.actionView as SearchView
//
//        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                //adapter.getFilter().filter(newText);
//                return false
//            }
//        })
//
//        toolbar.addView(searchView)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopupStoreBinding.inflate(inflater, container, false)
        val items = getDataFromApi()

        // Inflate the layout for this fragment
        binding.popupstoreRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.popupstoreRecyclerView.adapter = ItemAdapter(items)


//        setHasOptionsMenu(true)
//        setSupportActionBar(toolbar)
        return binding.root
    }

    private fun getDataFromApi():List<Item> {
        val sampleData = mutableListOf<Item>(
            Item("Sample Text 1"),
            Item("Sample Text 2"),
            Item("Sample Text 3"),
            Item("Sample Text 4"),
            Item("Sample Text 5"),
        )
        return sampleData
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PopupStoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PopupStoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
        TODO("Not yet implemented")
    }
}
