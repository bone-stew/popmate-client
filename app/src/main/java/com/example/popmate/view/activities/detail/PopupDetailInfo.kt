package com.example.popmate.view.activities.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupDetailInfoBinding
import com.example.popmate.model.data.local.PopupStoreSnsResponse
import com.example.popmate.view.adapters.popupstore.DetailCarouselAdapter
import com.example.popmate.view.adapters.popupstore.PopupStoreAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class PopupDetailInfo() : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = PopupDetailInfo()
    }

    private val TAG = "DetailInfo"
    private lateinit var binding: FragmentPopupDetailInfoBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: PopupDetailViewModel
    private var currentMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[PopupDetailViewModel::class.java]

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_popup_detail_info, container, false)

        viewModel.store.observe(viewLifecycleOwner) {
            binding.run {
                Log.d(TAG, "onCreateView: $it")
                store = it
                imageCarousel.adapter = DetailCarouselAdapter(it.popupStoreImgResponses)
                imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                indicator.setViewPager(imageCarousel)
                for (sns in it.popupStoreSnsResponses) {
                    when (sns.platform) {
                        "instagram" -> setSnsView(instagram, instagramUrl, sns)
                        "youtube" -> setSnsView(youtube, youtubeUrl, sns)
                        "homePage" -> setSnsView(homePage, homePageUrl, sns)
                    }
                }
                val address =  it.department.placeDescription + " " + it.placeDetail
                locationDetailText.text = address
                    val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("locationDetail", address)
                locationCopyBtn.setOnClickListener {
                    clipboard.setPrimaryClip(clipData)
                    Toast.makeText(
                        context,
                        "주소가 복사되었습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                locationDetailText.setOnClickListener {
                    clipboard.setPrimaryClip(clipData)
                    Toast.makeText(
                        context,
                        "주소가 복사되었습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                recommendStore.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                recommendStore.adapter =
                    PopupStoreAdapter(requireContext(), it.popupStoresNearBy, PopupStoreAdapter.ViewHolderType.VERTICAL_LARGE)
            }
        }
        mapView = binding.storeLocationMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@PopupDetailInfo)
        return binding.root;
    }

    private fun setSnsView(
        snsView: LinearLayout, snsUrlView: TextView, sns: PopupStoreSnsResponse
    ) {
        snsView.visibility = View.VISIBLE
        snsUrlView.text = sns.url
    }


    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        viewModel.store.observe(viewLifecycleOwner) {
            Log.d(TAG, "onMapReady: ")
            currentMarker = setupMarker(LatLngEntity(it.department.latitude, it.department.longitude))
        }
//        currentMarker?.showInfoWindow()
    }

    private fun setupMarker(locationLatLngEntity: LatLngEntity): Marker? {

        val positionLatLng =
            LatLng(locationLatLngEntity.latitude!!, locationLatLngEntity.longitude!!)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
//            title("위치")
//            snippet("서울역 위치")
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL  // 지도 유형 설정
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))  // 카메라 이동
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))  // 줌의 정도 - 1 일 경우 세계지도 수준, 숫자가 커질 수록 상세지도가 표시됨
        return googleMap.addMarker(markerOption)
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    data class LatLngEntity(
        var latitude: Double?, var longitude: Double?
    )
}
