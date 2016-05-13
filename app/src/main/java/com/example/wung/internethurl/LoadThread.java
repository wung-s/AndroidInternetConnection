package com.example.wung.internethurl;

import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wung on 11/05/16.
 */
public class LoadThread extends Thread {
    static final String SO_URL=
            "https://api.stackexchange.com/2.1/questions?"
                    + "order=desc&sort=creation&site=stackoverflow&tagged=android";

    @Override
    public void run() {
        try {
            HttpURLConnection c = (HttpURLConnection) new URL(SO_URL).openConnection();
            try {
                InputStream in = c.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                SOQuestions questions = new Gson().fromJson(reader, SOQuestions.class);
                reader.close();
                Log.d("test-app", "running thread...");
                EventBus.getDefault().post(new QuestionsLoadedEvent(questions));

            }catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Exception parsing JSON", e);
                Log.d("test-app", "parsing JSON exception", e);
            }finally {
                c.disconnect();
            }

        }catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Exception parsing JSON", e);
            Log.d("test-app", "parsing JSON exception 2ß", e);
        }
    }
}
