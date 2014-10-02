package com.phunware.sample.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.view.View;

import com.phunware.sample.app.service.LocationService;


public class MainActivity extends ActionBarActivity {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("h:mm:ss a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Phunware", "Stop download!");
        LocationService.getInstance().stopDownloader();
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

    public void onGetTime(View view) {
        Log.i("Phunware","get current time");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        TextView tv = (TextView)findViewById(R.id.timefield);
        if ( tv != null ) {
            tv.setText( formatter.format(date) );
        }
    }

    public void getLocation(View view) {
        Log.i("Phunware","Get location Info");
        EditText latitude = (EditText)findViewById(R.id.latitude);
        EditText longitude = (EditText)findViewById(R.id.longitude);

        if ( latitude != null && longitude != null ) {
            LocationService.getInstance().getLocationInfo(latitude.getText().toString(), longitude.getText().toString());
        }

        Intent intent = new Intent(this, LocationActivity.class);
        this.startActivity(intent);
    }
}
