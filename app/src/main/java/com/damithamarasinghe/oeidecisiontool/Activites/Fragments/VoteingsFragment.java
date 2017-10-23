package com.damithamarasinghe.oeidecisiontool.Activites.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.damithamarasinghe.oeidecisiontool.Activites.Contents.ApiContent;
import com.damithamarasinghe.oeidecisiontool.R;

import java.security.PrivateKey;

public class VoteingsFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ListView lvValues;
    private ViewHolder viewHolder;
    private AppAdapter appAdapter;
    private  int selectedPosition = -1;
    private String[] numbers;
    private  TextView tvQuetions;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private int id;

    private OnFragmentInteractionListener mListener;

    public VoteingsFragment() {}

    public static VoteingsFragment newInstance(String param1, String param2) {
        VoteingsFragment fragment = new VoteingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_voteings, container, false);
        numbers = getResources().getStringArray(R.array.numbers);
        lvValues = (ListView)rootView.findViewById(R.id.lvvalues);
        btnSubmit = (Button) rootView.findViewById(R.id.btn_vote_submit);
        btnSubmit.setOnClickListener(this);
        tvQuetions = (TextView)rootView.findViewById(R.id.tv_question_vote);
        progressBar = (ProgressBar) rootView.findViewById(R.id.pbvotes);
        setListViewHeightBasedOnChildren(lvValues);
        appAdapter = new AppAdapter();
        lvValues.setAdapter(appAdapter);

        int id = getArguments().getInt("exampleId");
        Log.e("id",""+id);
        try {
            progressBar.setProgress(id);
        }catch (Exception e){
            Log.e("Exception e",""+e);
        }

        return rootView;
    }

    private void submit() {
        Log.e("getSelectedItem",""+getSelectedItem());
        ApiContent.Votings.put(""+id,""+getSelectedItem());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.e("onDetach","");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int getid = v.getId();
        Log.e("getSelectedItem",""+getSelectedItem());
        switch (getid){
            case R.id.btn_vote_submit:
                submit();
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



    class AppAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return numbers.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, final ViewGroup parent) {

            //ViewHolder holder;
//            if (convertView == null) {
                viewHolder = new ViewHolder();

                LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.adapter_vote, null);
                viewHolder.rbvalues = (RadioButton) convertView
                        .findViewById(R.id.rbvotes);
                viewHolder.rbvalues.setChecked(position == selectedPosition);
                viewHolder.rbvalues.setText(numbers[position]);
                viewHolder.rbvalues.setTag(position);
                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//                viewHolder.rbvalues.setChecked(position == selectedPosition);
//            }


            viewHolder.rbvalues.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCheckChanged(v);
                }
            });
            return convertView;
        }
    }

    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        appAdapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(), "Selected Item : " + selectedPosition, Toast.LENGTH_SHORT).show();
    }


    public int getSelectedItem() {
        if (selectedPosition != -1) {
//            Toast.makeText(getActivity(), "Selected Item : " + selectedPosition, Toast.LENGTH_SHORT).show();
            return selectedPosition;
        }
        return selectedPosition;
    }

    public class ViewHolder {
        public RadioButton rbvalues;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
