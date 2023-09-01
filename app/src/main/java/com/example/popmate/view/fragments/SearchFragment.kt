package com.example.popmate.view.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentHomeBinding
import com.example.popmate.databinding.FragmentSearchBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.adapters.PopupStoreAdapter
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

//    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container,false)
        val popupStoresFromApi = getDataFromApi()

        binding.horizontalView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalView.adapter = PopupStoreAdapter(popupStoresFromApi, PopupStoreAdapter.ViewHolderType.VERTICAL_MEDIUM)

        binding.searchView.setIconifiedByDefault(false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getDataFromApi() : List<PopupStore> {
        var sampleData = mutableListOf<PopupStore>(
            PopupStore(
                id = 1,
                title = "팝업스토어 1",
                openDate = Date(), // Replace with actual open date
                closeDate = Date(),
                location = "더현대",// Replace with actual close date
                organizer = "주최자 1",
                imgUrl = "url_to_image_1"
            ),
            PopupStore(
                id = 2,
                title = "팝업스토어 2",
                openDate = Date(), // Replace with actual open date
                closeDate = Date(), // Replace with actual close date
                location = "더현대",// Replace with actual close date
                organizer = "주최자 2",
                imgUrl = "url_to_image_2"
            ),
            PopupStore(
                id = 3,
                title = "팝업스토어 3",
                openDate = Date(), // Replace with actual open date
                closeDate = Date(), // Replace with actual close date
                location = "더현대",// Replace with actual close date
                organizer = "주최자 3",
                imgUrl = "url_to_image_3"
            ),
            PopupStore(
                id = 4,
                title = "팝업스토어 4",
                openDate = Date(), // Replace with actual open date
                closeDate = Date(), // Replace with actual close date
                location = "더현대",// Replace with actual close date
                organizer = "주최자 4",
                imgUrl = "url_to_image_4"
            ),
            PopupStore(
                id = 5,
                title = "팝업스토어 5",
                openDate = Date(), // Replace with actual open date
                closeDate = Date(), // Replace with actual close date
                location = "더현대",// Replace with actual close date
                organizer = "주최자 5",
                imgUrl = "url_to_image_5"
            )
        )


        return sampleData
    }

}
