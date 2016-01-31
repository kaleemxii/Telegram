package BotchaHelper;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import DataSchema.Greeting;

/**
 * Created by shverm on 1/31/2016.
 */
public class Helpers {
    public static void helloWorld() {
        String s = "hola";
    }
    public static void interceptSentMessage(int userID, String message, long channelID) {
        String channelInfo = giveChannelWithToken(channelID);
        String url = "http://botchaapis.appspot.com/sendmessage?channelId=" + channelInfo + "&userId=" + userID + "&message=" + message;
        Log.i("Botcha send message url", url);
        new HttpRequestTask().execute(url);
    }

    private static class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        String urlToCall = "http://rest-service.guides.spring.io/greeting";
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                //final String url2 = "http://rest-service.guides.spring.io/greeting";
                //final String url = "http://botchaapis.appspot.com/sendmessage?channelId=192493113:AAEd8UGh8sum7P02Np39m2cGhuFRyT7xkj4&userId=113462548&message=dffd";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(urlToCall, Greeting.class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            Log.i("botcha executed url", urlToCall);
            //Log.i("botcha", String.format("value of greeting content: %s", greeting.getContent()));
        }

        public void execute(String url) {
            this.urlToCall = url;
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
}
