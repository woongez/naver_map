package com.example.naver_map

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.NonNull
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class WalkMapFragmentActivity : Activity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()
    private val infoWindow1 = InfoWindow()
    private val infoWindow2 = InfoWindow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        val cameraPosition = CameraPosition( // 카메라 위치 변경
            LatLng(37.6281, 127.0905),  // 위치 지정
            12.3 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition // 변경된 위치 반영

        // 현재 위치 마커
        marker.position = LatLng(37.6281, 127.0905)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED // 현재위치 마커 빨간색으로

        // 장소 리스트 마커
        marker1.position = LatLng(37.62444907132257, 127.09321109051345)
        marker1.map = naverMap
        marker1.captionText = "화랑대 철도공원"
        marker2.position = LatLng(37.608990485010956, 127.0703518565252)
        marker2.map = naverMap // 고씨네
        marker2.captionText = "중랑천 산책로"

        infoWindow1.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "공원"  // 정보창 내용 넣기

            }
        }
        infoWindow2.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "산책로"
            }
        }

        infoWindow1.open(marker1)
        infoWindow2.open(marker2)// 정보창 열기

        // 마커를 클릭하면:
        val listener = Overlay.OnClickListener { overlay ->
            val markerA = overlay as Marker

            if (markerA.infoWindow == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow1.open(marker1)
                infoWindow2.open(marker2)
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow1.close()
                infoWindow2.close()
            }

            true
        }

        marker1.onClickListener = listener
        marker2.onClickListener = listener
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}