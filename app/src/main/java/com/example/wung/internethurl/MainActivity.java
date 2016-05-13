package com.example.wung.internethurl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        if(getFragmentManager().findFragmentById(android.R.id.content) == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, new QuestionsFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test-app","mainactivity onResume");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        Log.d("test-app","mainactivity onPause");
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onEventMainThread(QuestionClickedEvent event) {
        Log.d("test-app", "questiong clicked event");
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(event.item.link)));
    }
}
