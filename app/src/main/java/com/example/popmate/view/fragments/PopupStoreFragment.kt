package com.example.popmate.view.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupStoreBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.activities.MainActivity
import com.example.popmate.view.adapters.PopupStoreAdapter
import java.util.Calendar
import java.util.Date


class PopupStoreFragment : Fragment() {
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
        val items = getDataFromApi()

        val screenWidthInPixels = resources.displayMetrics.widthPixels // Get the screen width in pixels
        val screenDensity = resources.displayMetrics.density // Get the screen density

// Calculate the screen width in dp
        val screenWidthInDp = screenWidthInPixels / screenDensity
//        val screenWidth = resources.displayMetrics.widthPixels  // Get the screen width

        Log.i("POPUPSTORE", screenWidthInDp.toString())
        // Calculate the desired left padding
        val desiredPadding = (screenWidthInDp - TWO_POPUPSTORES_WIDTH) / 2
        Log.i("POPUPSTORE", desiredPadding.toString())

        // Apply the left padding to popupstoreRecyclerView
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

    private fun getDataFromApi(): List<PopupStore> {
        val sampleData = mutableListOf<PopupStore>(
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
            ),
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
            ),
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
