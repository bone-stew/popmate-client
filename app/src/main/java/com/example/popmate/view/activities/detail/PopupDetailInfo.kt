package com.example.popmate.view.activities.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.popmate.R
import com.example.popmate.databinding.FragmentPopupDetailInfoBinding
import com.example.popmate.model.data.local.Banner
import com.example.popmate.model.data.local.PopupStore
import com.example.popmate.view.adapters.BannerAdapter
import com.example.popmate.view.adapters.PopupStoreAdapter
import com.example.popmate.view.adapters.StoreCardAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Date

class PopupDetailInfo : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = PopupDetailInfo()
    }

    private lateinit var binding: FragmentPopupDetailInfoBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopupDetailInfoBinding.inflate(inflater, container, false)
        val bannersFromApi = getSampleBanner()
        binding.imageCarousel.adapter = BannerAdapter(bannersFromApi)
        binding.imageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator = binding.indicator
        indicator.setViewPager(binding.imageCarousel)

        this.mapView = binding.storeLocationMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@PopupDetailInfo)

        binding.recommendStore.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recommendStore.adapter = StoreCardAdapter(getSampleStores())
        return binding.root;
    }

    private fun getSampleBanner(): List<Banner> {
        val sampleData = mutableListOf<Banner>(
            Banner(
                id = 1, imgUrl = "url to 1"
            ), Banner(
                id = 2, imgUrl = "url to 1"
            ), Banner(
                id = 3, imgUrl = "url to 1"
            ), Banner(
                id = 4, imgUrl = "url to 1"
            ), Banner(
                id = 5, imgUrl = "url to 1"
            )
        )
        return sampleData
    }
    private fun getSampleStores(): List<PopupStore> {
        return listOf(
            PopupStore(1, "망그러진곰", Date(), Date(),"더현대 서울 지하 2층", "", "url to 1"),
            PopupStore(1, "망그러진곰", Date(), Date(),"더현대 서울 지하 2층", "", "url to 1")
        )
    }
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        currentMarker = setupMarker(LatLngEntity(37.5562, 126.9724))  // default 서울역
        currentMarker?.showInfoWindow()
    }

    private fun setupMarker(locationLatLngEntity: LatLngEntity): Marker? {

        val positionLatLng =
            LatLng(locationLatLngEntity.latitude!!, locationLatLngEntity.longitude!!)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title("위치")
            snippet("서울역 위치")
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