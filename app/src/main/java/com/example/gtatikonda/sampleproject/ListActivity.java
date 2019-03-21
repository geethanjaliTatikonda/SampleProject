package com.example.gtatikonda.sampleproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class ListActivity extends AppCompatActivity {
    Toolbar primaryToolBar;
    Toolbar secondaryToolBar;
    public boolean hasRightFrameUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        primaryToolBar = findViewById(R.id.toolbar);
        secondaryToolBar = findViewById(R.id.secondaryToolbar);
        setSupportActionBar(primaryToolBar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, new ListFragment(), "");
        fragmentTransaction.commit();
        // setSecondaryToolBarMenu();
    }


    public Toolbar getPrimaryToolBar() {
        return primaryToolBar;

    }

    public Toolbar getSecondaryToolBar() {
        return secondaryToolBar;
    }

    public boolean isLargeScreen() {
        return findViewById(R.id.container_frame_right) != null;
    }

    public void setSecondaryToolBarMenu() {
        if (isLargeScreen()) {

            getSecondaryToolBar().inflateMenu(R.menu.rightmenu);

        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_frame);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commitNow();
        fragment = getSupportFragmentManager().findFragmentById(R.id.container_frame);
        if(fragment instanceof ListFragment){
          showUpButton(false);
            getSupportActionBar().setTitle(fragment.getArguments().getString("title"));
        }


        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    public void showUpButton(boolean show) {

        if (show) {
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
        }
    }
}
