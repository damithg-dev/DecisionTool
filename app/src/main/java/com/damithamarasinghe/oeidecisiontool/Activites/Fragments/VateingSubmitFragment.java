package com.damithamarasinghe.oeidecisiontool.Activites.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.damithamarasinghe.oeidecisiontool.Activites.Contents.ApiContent;
import com.damithamarasinghe.oeidecisiontool.R;

public class VateingSubmitFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ListView lvValues;
    private ViewHolder viewHolder;
    private AppAdapter appAdapter;
    private OnFragmentInteractionListener mListener;

    public VateingSubmitFragment() {
    }

    public static VateingSubmitFragment newInstance(String param1, String param2) {
        VateingSubmitFragment fragment = new VateingSubmitFragment();
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

        lvValues = (ListView)rootView.findViewById(R.id.lvfinalvalues);
        appAdapter = new AppAdapter();
        lvValues.setAdapter(appAdapter);
        Log.e("ApiContent",""+ApiContent.Votings.size());
        return rootView;

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
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class AppAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ApiContent.Votings.size();
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

            viewHolder = new ViewHolder();

            LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_final_vote, null);

            viewHolder.indextv = (TextView) convertView.findViewById(R.id.tv_index);
            viewHolder.ratetv = (TextView) convertView.findViewById(R.id.tv_rate);

            return convertView;
        }
    }


    public class ViewHolder {
        public TextView indextv,ratetv;
    }
}
