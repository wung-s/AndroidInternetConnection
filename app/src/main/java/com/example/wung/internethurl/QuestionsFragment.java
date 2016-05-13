package com.example.wung.internethurl;

import android.app.ListFragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Wung on 12/05/16.
 */
public class QuestionsFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("test-app","QuestionsFragment onCreate");
        setRetainInstance(true);
        new LoadThread().start();
    }

    @Override
    public void onResume() {
        super.onResume();


        Log.d("test-app","QuestionsFragment onResume");
        EventBus.getDefault().register(this);
        Log.d("test-app","QuestionsFragment onResume end");
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = ((ItemsAdapter)getListAdapter()).getItem(position);
        EventBus.getDefault().post(new QuestionClickedEvent(item));

    }

    @Subscribe
    public void onEventMainThread(QuestionsLoadedEvent event) {
        Log.d("test-app", "question loaded event");
        setListAdapter(new ItemsAdapter(event.questions.items));
    }

    class ItemsAdapter extends ArrayAdapter<Item> {
        ItemsAdapter(List<Item> items) {
            super(getActivity(), android.R.layout.simple_list_item_1, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("test-app", "in QuestionsFragment#getView");
            View row = super.getView(position, convertView, parent);
            TextView title = (TextView) row.findViewById(android.R.id.text1);
            title.setText(Html.fromHtml(getItem(position).title));
            return row;

        }
    }
}
