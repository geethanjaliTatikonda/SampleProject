package com.example.gtatikonda.sampleproject;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, MenuItem.OnMenuItemClickListener {
    private ListView listView;
    ListActivity listActivity;
    SearchView searchView;
    Menu menu;
    CustomAdapter customAdapter;
    String searchString = "";
    List<Item> list;
    boolean isShowAll=false;

    public ListFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "ListActivity");
        setArguments(bundle);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

   /* @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchString", searchString);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listActivity = (ListActivity) getActivity();
        listActivity.getSupportActionBar().setTitle(getArguments().getString("title"));
         list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item=new Item();
            item.title="item " + i;
            if(i%2==0){
                item.isActive=true;
            }
            else {
                item.isActive=false;
            }
            list.add(item);
        }
        customAdapter = new CustomAdapter(getActivity(), list);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FragmentManager fragmentManager = listActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        searchString = searchView.getQuery().toString().toLowerCase().trim();
        bundle.putString("title", ((Item) listView.getItemAtPosition(i)).title.toString());
        ListDetailFragment listDetailFragment = new ListDetailFragment();
        listDetailFragment.setArguments(bundle);
        if (listActivity.isLargeScreen()) {
            fragmentTransaction.replace(R.id.container_frame_right, listDetailFragment, "");
        } else {
            fragmentTransaction.add(R.id.container_frame, listDetailFragment, "").addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.leftmenu, menu);
        searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        menu.findItem(R.id.actionFilter).setIcon(R.drawable.ic_filter_active_white_icon);
        searchView.setOnQueryTextListener(this);
        if (!TextUtils.isEmpty(searchString)) {
            searchView.setIconified(false);
            searchView.setQuery(searchString, false);
        }
        menu.findItem(R.id.activitiesOnly).setOnMenuItemClickListener(this);
        menu.findItem(R.id.showAll).setOnMenuItemClickListener(this);
        getSelectedFilter();
    }

    private void getSelectedFilter() {
        if(isShowAll){
            menu.findItem(R.id.showAll).setChecked(true);
            menu.findItem(R.id.actionFilter).setIcon(R.drawable.ic_filter_white_icon);
        }
        else{
            menu.findItem(R.id.activitiesOnly).setChecked(true);
            menu.findItem(R.id.actionFilter).setIcon(R.drawable.ic_filter_active_white_icon);
        }

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        customAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        customAdapter.getFilter().filter(s);
        listActivity.hasRightFrameUpdated = false;
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.activitiesOnly:
                menu.findItem(R.id.actionFilter).setIcon(R.drawable.ic_filter_active_white_icon);
                menu.findItem(R.id.activitiesOnly).setChecked(true);
                List<Item> items=new ArrayList<>();
                for(Item item1:list){
                    if(item1.isActive){
                        items.add(item1);
                    }
                }
                updateAdapter(items);
                isShowAll=false;
                break;
            case R.id.showAll:
                menu.findItem(R.id.showAll).setChecked(true);
                menu.findItem(R.id.actionFilter).setIcon(R.drawable.ic_filter_white_icon);
                updateAdapter(list);
                isShowAll=true;
                break;
        }
        return false;
    }

    private void updateAdapter(List<Item> items) {
        customAdapter=new CustomAdapter(listActivity,items);
        listView.setAdapter(customAdapter);
    }

    public class CustomAdapter extends BaseAdapter implements Filterable {
        private LayoutInflater layoutInflater;
        private Context context;
        private List<Item> list;
        private List<Item> filterList;

        CustomAdapter(Context context, List<Item> list) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.list = list;
            filterList = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.customer_listviewitem, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.updateObject((Item) (getItem(position)), position);

            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new ItemFilter();
        }

        private class ItemFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                ArrayList<Item> nlist = new ArrayList<Item>();
                if (charSequence == null || charSequence.length() == 0) {
                    results.values = filterList;
                    results.count = filterList.size();
                } else {
                    List<Item> list1 = filterList;
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).title.toLowerCase().contains(charSequence.toString().toLowerCase()))
                            nlist.add(list1.get(i));
                    }
                    results.values = nlist;
                    results.count = nlist.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList) filterResults.values;
                notifyDataSetChanged();
                if (!((ListActivity) getActivity()).hasRightFrameUpdated) {
                    updateRightFrame();
                }
            }
        }

        private class ViewHolder {
            TextView attendeeName;
            RadioButton radioButton;

            private ViewHolder(View convertView) {
                attendeeName = (TextView) convertView.findViewById(R.id.dialogListItemText);
                radioButton = convertView.findViewById(R.id.dialogRadio);
                radioButton.setVisibility(View.GONE);
            }

            private void updateObject(Item item, int position) {
                if (!(item == null)) {
                    attendeeName.setText(item.title);
                } else {
                    attendeeName.setText("");
                }
            }

        }
    }

    private void updateRightFrame() {
        try {
            customAdapter.notifyDataSetChanged();
            if (customAdapter.getCount() > 0 && listActivity.findViewById(R.id.container_frame_right) != null) {
                listView.setAdapter(customAdapter);
                listView.performItemClick(listView, 0, customAdapter.getItemId(0));
                return;
            } else {
                if (listActivity.findViewById(R.id.container_frame_right) != null)
                    listActivity.getSecondaryToolBar().setTitle("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
