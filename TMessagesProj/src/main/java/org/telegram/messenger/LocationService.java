package org.telegram.messenger;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.telegram.ui.LaunchActivity;

import java.util.Arrays;
import java.util.List;

import DataSchema.Channel;
/**
 * Created by gaukumar on 30-01-2016.
 */
public class LocationService extends Service {
    public static final String BROADCAST_ACTION = "Hello World";
    private static final int HALF_MINUTE = 1000 * 30 * 1;
    private static String restApiUrl;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;

    Intent intent;
    int counter = 0;

    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 5, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 5, listener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            previousBestLocation = location;
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > HALF_MINUTE;
        boolean isSignificantlyOlder = timeDelta < -HALF_MINUTE;
        boolean isNewer = timeDelta > 0;

        // If it's been more than half minute since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            previousBestLocation = location;
            return true;
            // If the new location is more than half minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            previousBestLocation = location;
            return true;
        } //else if (isNewer && !isLessAccurate) {
//            return true;
//        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
//            return true;
//        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(listener);
    }

    public class MyLocationListener implements LocationListener
    {

        public void onLocationChanged(final Location loc)
        {

            if(isBetterLocation(loc, previousBestLocation)) {
                MessagesController.getInstance().isNewChannelsAvailable = false;
                loc.getLatitude();
                loc.getLongitude();
                int userId = UserConfig.getClientUserId();
                String userTag = UserConfig.getCurrentUser().first_name;
                restApiUrl = "http://botchaapis.appspot.com/getchannels?userId=" + userId + "&userTag=" + userTag + "&lat=" + loc.getLatitude() + "&long=" + loc.getLongitude();
                //"http://botchaapis.appspot.com/getchannels?userId=186345694&userTag=Gaurav&lat=17.429549&long=78.3411581";
                final AsyncTask<Void, Void, List<Channel>> execute;
                execute = new HttpRequestTask().execute();

                intent.putExtra("Latitude", loc.getLatitude());
                intent.putExtra("Longitude", loc.getLongitude());
                intent.putExtra("Provider", loc.getProvider());
                Toast.makeText(getApplicationContext(), "Changed. UserId: " + userId + ", Lat: " + loc.getLatitude() + ", Long: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                sendBroadcast(intent);
            }
        }

        public void onProviderDisabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, List<Channel>> {
        @Override
        protected List<Channel> doInBackground(Void... params) {
            try {
                final String url = restApiUrl;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return Arrays.asList(restTemplate.getForObject(url, Channel[].class));
            } catch (Exception e) {
                Log.e("Http error", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Channel> channels) {
            if (channels == null || channels.size() == 0) {
                return;
            }
            else {
                MessagesController.getInstance().channelIds.clear();
                MessagesController.getInstance().channelTags.clear();
                for (int i = 0; i < channels.size(); i++) {
                    MessagesController.getInstance().channelIds.add(channels.get(i).getChannelID());
                    MessagesController.getInstance().channelTags.add(channels.get(i).getChannelTag());
                }
                showScreenNotification();
            }
            //Toast.makeText(getApplicationContext(), "Id: " + channels.get(0).getChannelTag(), Toast.LENGTH_SHORT).show();
        }

        public void showScreenNotification() {
            if (MessagesController.getInstance().channelTags.size() != 0) {
                StringBuffer channelNamesBuffer = new StringBuffer();
                for (int i = 0; i < MessagesController.getInstance().channelTags.size(); i++) {
                    channelNamesBuffer.append(MessagesController.getInstance().channelTags.get(i));
                }
                String channelNames = channelNamesBuffer.toString();
                NotificationCompat.Builder mBuilder;
                Intent localIntent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                localIntent.setFlags(32768);
                PendingIntent contentIntent = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, localIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder = new NotificationCompat.Builder(ApplicationLoader.applicationContext)
                        .setContentTitle("New channels found")
                        .setSmallIcon(R.drawable.notification)
                        .setAutoCancel(true)
                        .setNumber(1)
                        .setContentIntent(contentIntent)
                        .setGroup("messages")
                        .setGroupSummary(true)
                        .setColor(0xff2ca5e0);
                mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
                mBuilder.setContentText("New channels available: " + channelNames);
                mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, AudioManager.STREAM_NOTIFICATION);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
                notificationManager.notify(1, mBuilder.build());
                MessagesController.getInstance().isNewChannelsAvailable = true;
            }
        }

    }
}
