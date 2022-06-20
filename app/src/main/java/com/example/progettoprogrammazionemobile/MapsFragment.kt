package com.example.progettoprogrammazionemobile

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.appericolo.ui.preferiti.contacts.database.EventoDb
import com.example.progettoprogrammazionemobile.ViewModel.eventViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), OnMapReadyCallback{

    private lateinit var vm: eventViewModel
    private lateinit var mMap : GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var events : List<EventoDb>
    private lateinit var address : List<Address>


    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProviders.of(requireActivity()).get(eventViewModel::class.java)
        mapFragment.onCreate(savedInstanceState)
        mapFragment.onResume()
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map

        mMap.uiSettings.isZoomControlsEnabled = true
        setUpMap()
    }

    private fun setUpMap() {
        Log.d("entroat", "entrato")
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("entroat", "entrato")
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener{ location ->
            if (location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))            }
        }
    }

    private fun placeMarkerOnMap() {
        vm.readEventData.observe(requireActivity(), Observer { evento ->
            this.events = evento
            setData()

//            var getLat: MutableList<Address> = geocode.getFromLocationName(citta_evento, 2)
//            if (getLat.isEmpty()){binding.errorMsg.setText("Questa città non esiste!"); return}
        })

        //mMap.addMarker(MarkerOptions().position(currentLatLong).title("Tu sei qui!"))
    }

    private fun setData() {
        try {
            val geocode = Geocoder(requireContext())
            this.events.forEach() { evento ->
                try {

                    var citta = evento.citta
                    var indirizzo = evento.indirizzo
                    var cittaIndirizzo = "$indirizzo " + ", " + "$citta"
                    Log.d("erroreMappa", "${cittaIndirizzo}")
                    address = geocode.getFromLocationName(cittaIndirizzo, 5);
                    if (address == null) {
                        return;
                    }
                    val location = address.get(0)
                    val currentLatLong = LatLng(location.getLatitude(), location.getLongitude())
                    mMap.addMarker(
                        MarkerOptions().position(currentLatLong).title("${evento.titolo}")
                    )
                } catch (e: Exception) {
                    Log.d("erroreMappa", "${e.message}")
                }
            }
        }catch (e : Exception){
            Log.d("erroreMappa", "${e.message}")
        }
    }

}