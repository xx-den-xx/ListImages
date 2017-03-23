package ru.bda.listimages;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiController {
    private static volatile ApiController instance;
    private final static String API_URL = "http://devcandidates.alef.im/list.php";

    private ApiController () {}

    public static ApiController getInstance() {
        if (instance == null) {
            synchronized (ApiController.class) {
                if (instance == null) {
                    instance = new ApiController();
                }
            }
        }
        return instance;
    }

    public List<String> getListImagesUrl () {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();
            return parseList(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> parseList(InputStream stream) {
        List<String> images = new ArrayList<>();
        String response = convertStreamToString(stream);
        try {
            JSONArray json = new JSONArray(response);
            for (int i = 0; i < json.length(); i++) {
                images.add((String) json.get(i));
            }
            return images;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("log_list", response);
        return images;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
