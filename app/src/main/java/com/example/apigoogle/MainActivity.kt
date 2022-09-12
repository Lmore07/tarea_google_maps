package com.example.apigoogle

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : FragmentActivity(), OnMapReadyCallback {

    lateinit var mMap:GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mapFragment:SupportMapFragment = getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment;
        mapFragment
            .getMapAsync(this);
    }

    override fun onMapReady(googleMap:GoogleMap) {
        mMap = googleMap;
        marcadores()
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true)
        var camUpd1 =
            CameraUpdateFactory.newLatLngZoom(LatLng(-1.080428, -79.501368), 19F)
        mMap.moveCamera(camUpd1)
        mMap.setInfoWindowAdapter(ventana_marcador(this))
    }

    fun marcadores(){
        val queue = Volley.newRequestQueue(this)
        val peticion = JsonObjectRequest(
            GET, "https://facultades-6d46c-default-rtdb.firebaseio.com/data.json",null,
            { response ->
                var facultades = response.getJSONArray("facultades")

                for (i in 0 until facultades.length()) {
                    var elemento: JSONObject = facultades.getJSONObject(i)
                    val coordenadas = LatLng(elemento.getDouble("latitud"), elemento.getDouble("longitud"))
                    val marcador = mMap.addMarker(
                        MarkerOptions()
                            .position(coordenadas)
                            .title(elemento.getString("Nombre")).snippet(elemento.toString())
                    )
                    marcador?.showInfoWindow()
                }
            },
            {  }
        )
        queue.add(peticion)
    }
}