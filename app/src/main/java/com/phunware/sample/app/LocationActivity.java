package com.phunware.sample.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phunware.sample.app.model.Location;
import com.phunware.sample.app.service.LocationService;


public class LocationActivity extends ActionBarActivity implements LocationService.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Log.i("Phunware", "Add listener!");
        LocationService.getInstance().addListener(this);
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);
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

    @Override
    public void onDownloadCompleted(Location location) {
        if ( location != null ) {
            findViewById(R.id.infocontainer).setVisibility(View.VISIBLE);
            findViewById(R.id.progressBar).setVisibility(View.GONE);

            ImageView locationImage = (ImageView)findViewById(R.id.locationImage);
            if ( locationImage != null ) {
                locationImage.setImageBitmap(location.getImage());
            }

            TextView address = (TextView)findViewById(R.id.address);
            if ( address != null ) {
                address.setText(location.getAddress());
            }
        }
    }
}
