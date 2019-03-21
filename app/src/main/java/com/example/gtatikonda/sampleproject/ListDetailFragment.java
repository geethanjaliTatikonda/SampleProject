package com.example.gtatikonda.sampleproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListDetailFragment extends Fragment {


    public ListDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListActivity listActivity = (ListActivity) getActivity();
        String title = getArguments().getString("title");
        if (listActivity.isLargeScreen())
            listActivity.getSecondaryToolBar().setTitle(title);
        else {
            listActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            listActivity.getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        ListActivity listActivity = (ListActivity) getActivity();
        listActivity.hasRightFrameUpdated=true;
        if (listActivity.isLargeScreen()) {
            listActivity.getSecondaryToolBar().getMenu().clear();
            listActivity.getSecondaryToolBar().inflateMenu(R.menu.rightmenu);
        } else {
            menu.clear();
            inflater.inflate(R.menu.rightmenu, menu);
        }

    }
}
