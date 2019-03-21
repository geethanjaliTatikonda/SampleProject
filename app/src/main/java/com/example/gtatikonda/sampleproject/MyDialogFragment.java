package com.example.gtatikonda.sampleproject;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogFragment extends DialogFragment {
    Context context;
    List<? extends String> list = new ArrayList<>();
    SparseBooleanArray sparseBooleanArray;

    public MyDialogFragment() {
        // Required empty public constructor
    }
    public static MyDialogFragment newInstance(List list){
        MyDialogFragment myDialogFragment=new MyDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("List", (ArrayList<? extends Parcelable>) list);
        myDialogFragment.setArguments(bundle);
        return myDialogFragment;

    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Dialog");
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_my_dialog, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        list=getArguments().getParcelableArrayList("List");
        builder.setView(view);

        sparseBooleanArray=new SparseBooleanArray(list.size());
        for(int i=0;i<sparseBooleanArray.size();i++){
            if(i==2){
                sparseBooleanArray.put(i,true);
            }

        }
        SearchView searchView = view.findViewById(R.id.searchView);

        ListView listView = view.findViewById(R.id.dialogListView);
        final CustomAdapter arrayAdapter = new CustomAdapter(getActivity(), (List<String>) list,sparseBooleanArray);
        listView.setAdapter(arrayAdapter);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),parent.getItemAtPosition(position)+"",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
       /* builder.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        return builder.create();
    }

    public class CustomAdapter extends BaseAdapter implements Filterable {
        private LayoutInflater layoutInflater;
        private Context context;
        private List<String> list;
        private List<String> filterList;
        private  SparseBooleanArray booleanArray;

        CustomAdapter(Context context, List<String> list,SparseBooleanArray sparseBooleanArray) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.list = list;
            filterList=list;
            this.booleanArray=sparseBooleanArray;
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

            viewHolder.updateObject((String) (getItem(position)),position);

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
                ArrayList<String> nlist = new ArrayList<String>();
                if (charSequence == null || charSequence.length() == 0) {
                    results.values = filterList;
                    results.count = filterList.size();
                } else {
                    List<String> list1=filterList;
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).toLowerCase().contains(charSequence.toString().toLowerCase()))
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
            }
        }
        private class ViewHolder {
            TextView attendeeName;
            RadioButton radioButton;

            private ViewHolder(View convertView) {
                attendeeName = (TextView) convertView.findViewById(R.id.dialogListItemText);
                radioButton=convertView.findViewById(R.id.dialogRadio);
            }

            private void updateObject(String item,int position) {
                if (!(item == null)) {
                    attendeeName.setText(item);
                    radioButton.setChecked(booleanArray.get(position));
                } else {
                    attendeeName.setText("");
                }
            }

        }
    }


}
