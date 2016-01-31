package BotchaHelper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.telegram.messenger.ApplicationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import DataSchema.Greeting;
import DataSchema.Response;

/**
 * Created by shverm on 1/31/2016.
 */
public class Helpers {
    public static void interceptSentMessage(int userID, String message, long channelID) {
        String channelInfo = giveChannelWithToken(channelID);
        String url = "http://botchaapis.appspot.com/sendmessage?channelId=" + channelInfo + "&userId=" + userID + "&message=" + message;
        new HttpRequestTask().execute(url);
    }

    public static void registerToChannel(int userID, int channelID) {
        String channelInfo = giveChannelWithToken(channelID);
        String url = "http://botchaapis.appspot.com/registerchannel?channelId="+channelInfo+"&userId="+userID;
        Log.i("Botcha", String.format("registering to channel %S with user id %d",channelInfo, userID));
        new HttpRequestTask().execute(url);
    }

    private static class HttpRequestTask extends AsyncTask<Void, Void, Response> {
        String urlToCall = "http://rest-service.guides.spring.io/greeting";
        @Override
        protected Response doInBackground(Void... params) {
            try {
                //final String url2 = "http://rest-service.guides.spring.io/greeting";
                //final String url = "http://botchaapis.appspot.com/sendmessage?channelId=192493113:AAEd8UGh8sum7P02Np39m2cGhuFRyT7xkj4&userId=113462548&message=dffd";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Log.i("Botcha urltocall", urlToCall);
                return restTemplate.getForObject(urlToCall, Response.class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Response greeting) {
            //Log.i("botcha boolean response", Boolean.toString(greeting.ok));
            Log.i("botcha executed url", urlToCall);
            //Log.i("botcha", String.format("value of greeting content: %s", greeting.getContent()));
        }

        public void execute(String url) {
            this.urlToCall = url;
            this.execute();
        }
    }

    private static String giveChannelWithToken(long id) {
        if (id == 135483832) {
            return "135483832:AAFMWMgaqIJbe0BAWjZcVxnIDKBAfrpLp9E";
        } else if (id == 171135579) {
            return "171135579:AAE4e1xWLomYb5wG3Bp69TVFue2I1fFeoVE";
        } else if (id == 149007104) {
            return "149007104:AAHtzMtfQIEhDVE5795Ip8JmTa4NY59R0pU";
        } else if (id == 192493113) {
            return "192493113:AAEd8UGh8sum7P02Np39m2cGhuFRyT7xkj4";
        } else if (id == 175641240) {
            return "175641240:AAGayLEwIXjVDI1qWTb6lRUucFSGtZOMWDQ";
        }

        return "Empty";
    }

    public static boolean isChannelAllowed(long id) {
        List<Long> allowedIDs = getAllowedIDs();
        boolean flag = false;
        for(int i = 0; i <allowedIDs.size(); i++) {
            if (id == allowedIDs.get(i)) {
                flag = true;
            }
        }

        return flag;
        //return id == 135483832 || id == 171135579 || id == 149007104 || id == 192493113 || id == 175641240;
    }

    public static List<Long> getAllowedIDs() {
        List<Long> returnList = new ArrayList<Long>();
        /*
        saveToSharedPreference(135483832L);
        saveToSharedPreference(171135579L);
        saveToSharedPreference(149007104L);
        saveToSharedPreference(192493113L);
        saveToSharedPreference(175641240L);
        */
        /*returnList.add(135483832L);
        returnList.add(171135579L);
        returnList.add(149007104L);
        returnList.add(192493113L);
        returnList.add(175641240L);*/
        SharedPreferences sharedPref = ApplicationLoader.applicationContext.getSharedPreferences("Notifications",(Context.MODE_PRIVATE));
        String ids = sharedPref.getString("allowedIDS2", "");
        if(ids.equals("")) {
            saveToSharedPreference(135483832L);
            saveToSharedPreference(171135579L);
            saveToSharedPreference(149007104L);
            saveToSharedPreference(192493113L);
            saveToSharedPreference(175641240L);
        }
        String array[] = ids.split(",");
        for (String anArray : array) {
            if (!anArray.equals("")) {
                returnList.add(Long.parseLong(anArray, 10));
            }
        }
        return returnList;
    }

    public static void saveToSharedPreference(long id) {
        SharedPreferences sharedPref = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", (Context.MODE_PRIVATE));
        String ids = sharedPref.getString("allowedIDS2", "");
        SharedPreferences.Editor editor = sharedPref.edit();
        if (ids.equals("")) {
            editor.putString("allowedIDS2", Long.toString(id));
            editor.commit();
        } else {
            editor.putString("allowedIDS2", ids + "," + Long.toString(id));
            editor.commit();
        }
    }

    public static void removeFromSharedPreference(long id) {
        SharedPreferences sharedPref = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", (Context.MODE_PRIVATE));
        String ids = sharedPref.getString("allowedIDS2", "");
        SharedPreferences.Editor editor = sharedPref.edit();
        String result = ids.replaceAll(Long.toString(id), "");
        editor.putString("allowedIDS2",result);
        editor.commit();
    }

    public static void updateSharedPreference(List<Long> ids) {
        SharedPreferences sharedPref = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", (Context.MODE_PRIVATE));
        SharedPreferences.Editor editor = sharedPref.edit();
        String result="";
        for (int i= 0; i< ids.size(); i++) {
            result = result + ids.toString() + ",";
        }
        editor.putString("allowedIDS2",result);
        editor.commit();
    }
}
