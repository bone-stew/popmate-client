package com.example.popmate.view.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentSearchBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.adapters.PopupStoreAdapter
import org.w3c.dom.Text
import java.util.Date


class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container,false)
        val popupStoresFromApi = getDataFromApi()

        binding.horizontalView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalView.adapter = PopupStoreAdapter(popupStoresFromApi, PopupStoreAdapter.ViewHolderType.VERTICAL_MEDIUM)

        binding.searchView.setIconifiedByDefault(false)
    val search_text =
        binding.searchView.findViewById<TextView>(binding.searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null))
    search_text.setTextSize(13F)

    binding.imgArrow.setOnClickListener {
        val fragmentManager = requireActivity().supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.flFragment, HomeFragment())
                .commit()
        }
    }
    return binding.root
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
