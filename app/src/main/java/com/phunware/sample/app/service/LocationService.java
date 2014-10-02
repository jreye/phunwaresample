package com.phunware.sample.app.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.phunware.sample.app.model.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by julio on 10/1/14.
 */
public class LocationService {
    private static LocationService mInstance = null;



    public interface Callback {
        void onDownloadCompleted(Location location);
    }

    private Location mLocation = null;
    private Downloader mDownloader = null;
    private Callback mCallback = null;

    private LocationService() {
    }

    public static synchronized LocationService getInstance() {
        if ( mInstance == null ) {
            mInstance = new LocationService();
        }
        return mInstance;
    }

    public void getLocationInfo(String latitude, String longitude) {
        if ( mDownloader == null ) {
            mDownloader = new Downloader();
            mDownloader.execute(latitude + "," + longitude);
        }
    }

    public void addListener(Callback callback) {
        this.mCallback = callback;
        if ( mLocation != null ) {
            callback.onDownloadCompleted(mLocation);
        }
    }

    public void stopDownloader() {
        this.mCallback = null;
        if ( this.mDownloader != null ) {
            this.mDownloader.cancel(true);
            this.mDownloader = null;
        }
        this.mLocation = null;
    }

    private class Downloader extends AsyncTask<String,Void,Location> {

        @Override
        protected Location doInBackground(String... params) {

            Location location = new Location();
            String locationParam = params[0];
            String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + locationParam + "&zoom=10&size=400x400";     // 25.7877,-80.2241

            Log.d("Phunware", "Download map from " + url);

            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestProperty("User-Agent", System.getProperties().getProperty("http.agent") + " Phunware");
                location.setImage( BitmapFactory.decodeStream(conn.getInputStream()) );
            } catch (MalformedURLException e) {
                Log.e("Phunware", "MalformedURLException reading bitmap" + e.getMessage());
            } catch (IOException e) {
                Log.e("Phunware", "IOException reading bitmap " + e.getMessage());
            }

            url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + locationParam;
            Log.d("Phunware", "Download address from " + url);

            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestProperty("User-Agent", System.getProperties().getProperty("http.agent") + " Phunware");

                StringBuilder sb = new StringBuilder();

                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()), 1000);
                for (String line = r.readLine(); line != null; line = r.readLine()) {
                    sb.append(line);
                }
                location.setAddress(sb.toString());
                conn.getInputStream().close();
            } catch (MalformedURLException e) {
                Log.e("Phunware", "MalformedURLException reading address " + e.getMessage());
            } catch (IOException e) {
                Log.e("Phunware", "IOException reading address " + e.getMessage());
            }

            return location;
        }

        @Override
        protected void onPostExecute(Location location) {
            mLocation = location;
            if ( location != null && mCallback != null ) {
                mCallback.onDownloadCompleted(location);
            }
        }
    }
}
