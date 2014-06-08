package br.android.mapa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {
	
	private Fragment mContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getSupportFragmentManager();
		
		if (savedInstanceState != null) {
			mContent = fm.getFragment(savedInstanceState, "mContent");
		}
		
		if (mContent == null)
			mContent = new PlaceholderFragment();
		
		
		// Trocando o content pra o placeholder
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fragment_content, mContent);
		ft.commit();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		private GoogleMap mMap; 

		private static final int ZOOM = 12;
		
		private static final LatLng RECIFE = new LatLng(-8.0556, -34.8911);

		
		public PlaceholderFragment() {
		}
		
		private void setUpMapIfNeeded() {
			if (servicesConnected()) {
				// Do a null check to confirm that we have not already instantiated
				// the map.
				if (mMap == null) {
					FragmentManager fm = getChildFragmentManager();
					SupportMapFragment fragmentMap = (SupportMapFragment) fm.findFragmentById(R.id.map);
					if (fragmentMap != null) {
						mMap = fragmentMap.getMap();
							
					} //else { ferrou!! :P }
					
					Fragment fragment = SupportMapFragment.newInstance();
					 
					if (fragment != null) {
						fm.beginTransaction()
						  .replace(R.id.map, fragment)
						  .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						  .commit();
						fm.executePendingTransactions();
						
						mMap = ( (SupportMapFragment) fragment).getMap();
						// Check if we were successful in obtaining the map.
						if (mMap != null) {
							setUpMap();
						} // else { Ferrou :P }
						
					} //else {ferrou :P }
					
				}
			}
		}
		
		@Override
		public void onResume() {
			setUpMapIfNeeded();
			super.onResume();
		}
		
		private void setUpMap() {
			UiSettings mMapSettings = mMap.getUiSettings();		
			mMapSettings.setMyLocationButtonEnabled(true); 
			mMapSettings.setCompassEnabled(true);
			mMapSettings.setZoomGesturesEnabled(true);
			mMapSettings.setZoomControlsEnabled(true);
			mMap.setMyLocationEnabled(true);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RECIFE, ZOOM)); //Coloquei s— para exemplo
			mMap.clear();
			
			
			//mMap.setOnCameraChangeListener(new OnCameraChangeListener() { #TODO S— pra voce ficar curioso
				
			
			//mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() { #TODO S— pra voce ficar curioso
				
			
			
			//mMap.setOnMarkerClickListener(new OnMarkerClickListener() { #TODO S— pra voce ficar curioso
				
				
		}
		
		/**
		 * Verify that Google Play services is available before making a request.
		 * 
		 * @return true if Google Play services is available, otherwise false
		 */
		private boolean servicesConnected() {

			// Check that Google Play services is available
			int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext());

			// If Google Play services is available
			if (ConnectionResult.SUCCESS == resultCode) {
				return true;
			
			// Google Play services was not available for some reason
			} else {
				// #TODO Wally faz um dialog com uma mensagem de erro pra treinar :P 
				return false;
			}
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,false);
			setUpMapIfNeeded();
			
			return rootView;
		}
		
		@Override
		public void onDestroyView() {
			if (getActivity() != null) {
				
				FragmentManager fm = getChildFragmentManager();
				if (fm != null) {
					Fragment fragment = (fm.findFragmentById(R.id.map));
					if (fragment != null) {
						FragmentTransaction ft = fm.beginTransaction();
						if (ft != null) {
							ft.remove(fragment);
							ft.commitAllowingStateLoss();
						}	
					}
				}
			}
			super.onDestroyView();
		}

	}
	
	
}
