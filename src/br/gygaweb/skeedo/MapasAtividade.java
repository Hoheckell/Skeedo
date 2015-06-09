package br.gygaweb.skeedo;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.gygaweb.skeedo.entities.Reuniao;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapasAtividade extends FragmentActivity {

	GoogleMap mMap;
	MarkerOptions markerOptions;
	LatLng latLng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapas_atividade);

		Reuniao reuniao = (Reuniao) getIntent().getParcelableExtra("reuniao");
		SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

		mMap = supportMapFragment.getMap();
		mMap.getUiSettings().setZoomControlsEnabled(true);
		Geocoder geocoder = new Geocoder(getBaseContext());
		List<Address> address = null;

		try {
			// Getting a maximum of 3 Address that matches the input text
			address = geocoder.getFromLocationName(reuniao.getEndereco(), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Address addres = (Address) address.get(0);
		latLng = new LatLng(addres.getLatitude(), addres.getLongitude());
		String addressText = String.format("%s, %s",
				addres.getMaxAddressLineIndex() > 0 ? addres.getAddressLine(0) : "",
						addres.getCountryName());
		markerOptions = new MarkerOptions();
		markerOptions.position(latLng);
		markerOptions.title(addressText);
		if(reuniao.getDescricao() != "" && reuniao.getDescricao() != null){
			markerOptions.snippet(reuniao.getDescricao());
		}
		markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.reuniao));
		
		if (mMap!=null){
			mMap.addMarker(markerOptions);
			mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			mMap.animateCamera(CameraUpdateFactory.zoomTo(20));

		}
		/**
		 * map.setMyLocationEnabled(true);

    location = map.getMyLocation();

    if (location != null) {
        myLocation = new LatLng(location.getLatitude(),
                location.getLongitude());
    }
		 */

		//		Uri gmmIntentUri = Uri.parse("geo:0,0?q="+);
		//		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		//		mapIntent.setPackage("com.google.android.apps.maps");
		//		startActivity(mapIntent);


	}


}
