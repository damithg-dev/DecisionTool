package com.damithamarasinghe.oeidecisiontool.Activites;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.damithamarasinghe.oeidecisiontool.Activites.Fragments.VateingSubmitFragment;
import com.damithamarasinghe.oeidecisiontool.Activites.Fragments.VoteingsFragment;
import com.damithamarasinghe.oeidecisiontool.Activites.Util.ZoomOutPageTransformer;
import com.damithamarasinghe.oeidecisiontool.R;

public class VotingActivity extends FragmentActivity implements VoteingsFragment.OnFragmentInteractionListener ,VateingSubmitFragment.OnFragmentInteractionListener  {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        mPager = (ViewPager) findViewById(R.id.pg_voteings);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            VoteingsFragment vf = new  VoteingsFragment();
            VateingSubmitFragment vfs = new VateingSubmitFragment();
            switch (position){
                case 10:
                    return vfs;
                default:
                    Bundle myBundle = new Bundle();
                    myBundle .putInt( "exampleId", position);
                    vf.setArguments(myBundle);
                    return vf;
            }
        }

        @Override
        public int getCount() {
            return 11;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){}

}


