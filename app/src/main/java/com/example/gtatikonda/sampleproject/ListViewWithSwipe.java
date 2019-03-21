package com.example.gtatikonda.sampleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;
import java.util.List;

public class ListViewWithSwipe extends AppCompatActivity implements SwipeActionAdapter.SwipeActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_with_swipe);
        ListView listView = findViewById(R.id.listViewSwipe);
        List<String> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add("listItem   " + i);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        SwipeActionAdapter swipeActionAdapter = new SwipeActionAdapter(arrayAdapter);
        swipeActionAdapter.setListView(listView);
        listView.setAdapter(swipeActionAdapter);
        swipeActionAdapter.setSwipeActionListener(this);
        swipeActionAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.left);
    }

    @Override
    public boolean hasActions(int position, SwipeDirection direction) {
        if(direction.isLeft()) return true; // Change this to false to disable left swipes
        if(direction.isRight()) return true;
        return false;
    }

    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction) {
        if(direction.isLeft()) return true;
        return false;
    }

    @Override
    public void onSwipe(int[] positionList, SwipeDirection[] directionList) {
        for (int i = 0; i < positionList.length; i++) {
            SwipeDirection direction = directionList[i];
            int position = positionList[i];
            String dir = "";
            Toast.makeText(this,"swiped",Toast.LENGTH_SHORT);
        }
    }
}
