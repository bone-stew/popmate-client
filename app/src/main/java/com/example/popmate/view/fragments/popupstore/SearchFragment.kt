package com.example.popmate.view.fragments.popupstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popmate.R
import com.example.popmate.config.PopmateApplication
import com.example.popmate.databinding.FragmentSearchBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.util.SearchQueryListener
import com.example.popmate.view.adapters.popupstore.PopupStoreAdapter


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var searchQueryListener: SearchQueryListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        var recentlyViewedStores = PopmateApplication.prefs.getList()


        binding.run {
           refreshUI(recentlyViewedStores)
            searchView.setIconifiedByDefault(false)
            imgArrow.setOnClickListener {
                val fragmentManager = requireActivity().supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack()
                } else {
                    fragmentManager.beginTransaction()
                        .replace(R.id.flFragment, HomeFragment())
                        .commit()
                }
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchQueryListener?.onSearchQuerySubmitted(query.orEmpty())
                    val fragmentManager = requireActivity().supportFragmentManager
                    fragmentManager.popBackStack()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })

        }

        val search_text =
            binding.searchView.findViewById<TextView>(
                binding.searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null)
            )
        search_text.setTextSize(13F)


        return binding.root
    }

    fun setSearchQueryListener(listener: SearchQueryListener) {
        searchQueryListener = listener
    }

    override fun onResume() {
        super.onResume()
        var recentlyViewedStores = PopmateApplication.prefs.getList()
        refreshUI(recentlyViewedStores)
    }

    private fun refreshUI(recentlyViewedStores: List<PopupStore>?) {
        if (recentlyViewedStores.isNullOrEmpty()) {
            binding.horizontalView.visibility = View.GONE
            binding.closingSoonText.visibility = View.GONE
        } else {
            binding.horizontalView.visibility = View.VISIBLE
            binding.closingSoonText.visibility = View.VISIBLE
            binding.horizontalView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.horizontalView.adapter =
                PopupStoreAdapter(requireContext(), recentlyViewedStores, PopupStoreAdapter.ViewHolderType.VERTICAL_SMALL)
        }

    }


}
