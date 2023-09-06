package com.example.popmate.view.activities.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupDetailInfoBinding
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.model.data.local.PopupStoreSnsResponse
import com.example.popmate.view.adapters.BannerAdapter
import com.example.popmate.view.adapters.StoreCardAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class PopupDetailInfo(popupStoreId: Long) : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(popupStoreId: Long) = PopupDetailInfo(popupStoreId)
    }

    private lateinit var binding: FragmentPopupDetailInfoBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: PopupDetailViewModel
    private var currentMarker: Marker? = null
    private var popupStoreId = popupStoreId
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[PopupDetailViewModel::class.java]

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_popup_detail_info, container, false)

        viewModel.getStore(popupStoreId).observe(viewLifecycleOwner) {
            binding.run {
                Log.d("kww", "onCreateView: " + it.openDateFormatted)
                store = it
                imageCarousel.adapter = BannerAdapter(it.popupStoreImgResponses)
                imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                indicator.setViewPager(imageCarousel)
                for (sns in it.popupStoreSnsResponses) {
                    when (sns.platform) {
                        "instagram" -> setSnsView(instagram, instagramUrl, sns)
                        "youtube" -> setSnsView(youtube, youtubeUrl, sns)
                        "homePage" -> setSnsView(homePage, homePageUrl, sns)
                    }
                }
                locationDetailText.text = it.placeDetail
            }
        }
        mapView = binding.storeLocationMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@PopupDetailInfo)

        binding.recommendStore.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recommendStore.adapter = StoreCardAdapter(getSampleStores())
        return binding.root;
    }

    private fun setSnsView(
        snsView: LinearLayout, snsUrlView: TextView, sns: PopupStoreSnsResponse
    ) {
        snsView.visibility = View.VISIBLE
        snsUrlView.text = sns.url
    }

    private fun getSampleStores(): List<PopupStore> {
        return listOf(
//            PopupStore(1, "망그러진곰", Date(), Date(),"더현대 서울 지하 2층", "", "url to 1"),
//            PopupStore(1, "망그러진곰", Date(), Date(),"더현대 서울 지하 2층", "", "url to 1")
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        viewModel.getStore(popupStoreId).observe(viewLifecycleOwner) {
            currentMarker = setupMarker(LatLngEntity(it.latitude, it.longitude))
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
