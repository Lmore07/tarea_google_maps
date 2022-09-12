package com.example.apigoogle

import android.app.Activity
import android.content.Context
import android.util.JsonWriter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.json.JSONObject

class ventana_marcador(context: Context) : GoogleMap.InfoWindowAdapter {

    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.ventana_marcador, null)

    fun marcadores(marker: Marker, view: View){
        val objeto:JSONObject= JSONObject(marker.snippet.toString())
        val facultad = view.findViewById<TextView>(R.id.facultad)
        val carreras = view.findViewById<TextView>(R.id.txt_carreras)
        val decano=view.findViewById<TextView>(R.id.txt_Decano)
        val coordenadas = view.findViewById<TextView>(R.id.txt_coord)
        val logo = view.findViewById<ImageView>(R.id.logo_facultad)
        facultad.text = marker.title
        carreras.text = "Carreras: "+objeto.getString("carreras")
        decano.text="Decano: "+objeto.getString("Decano")
        coordenadas.text = "Latitud:"+objeto.getString("latitud")+", Longitud:"+objeto.getString("longitud")
        Picasso.get().load(objeto.getString("logo")).into(logo)
    }

    override fun getInfoContents(p0: Marker): View? {
        marcadores(p0, mWindow)
        return mWindow    }

    override fun getInfoWindow(p0: Marker): View? {
        marcadores(p0, mWindow)
        return mWindow
    }

}