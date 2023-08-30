package com.example.popmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.databinding.FragmentHomeBinding
import com.example.popmate.model.data.local.Item
import com.example.popmate.view.adapters.ItemAdapter
import com.example.popmate.view.adapters.ItemAdapterForListView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // get data from api
        val items = getDataFromApi()
        binding.imageCarousel.adapter = ItemAdapter(items)
        binding.imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.listView.layoutManager = LinearLayoutManager(requireContext())
        binding.visitedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.visitedRecyclerView.adapter = ItemAdapter(items)

        binding.visitedRecyclerView.visibility = if (visitedByIsNotNull()) View.VISIBLE else View.GONE


        binding.listView.adapter = ItemAdapterForListView(requireContext(), items)

//        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        val indicator = binding.indicator
        indicator.setViewPager(binding.imageCarousel)

        binding.horizontalView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalView.adapter = ItemAdapter(items)
        // initialize
        return binding.root
    }

    private fun visitedByIsNotNull():Boolean {
        return true
    }
    private fun getDataFromApi() : List<Item> {
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
