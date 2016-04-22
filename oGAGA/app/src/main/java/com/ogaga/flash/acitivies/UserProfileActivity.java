package com.ogaga.flash.acitivies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.ogaga.flash.Fragments.ProfileBuyerFragment;
import com.ogaga.flash.Fragments.ProfileSallerFragment;
import com.ogaga.flash.R;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //Get the viewpager
        ViewPager vPager = (ViewPager) findViewById(R.id.viewpager);
        vPager.setAdapter(new ProfilePagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip pSliding = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pSliding.setViewPager(vPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }
    public void onProfileView(MenuItem mi){
        /*Intent i = new Intent(this,ProfileActivity.class);
        String userName = MentionsTimelineFragment.getUserName();
        i.putExtra("screen_name", userName);
        startActivity(i);*/
    }

    public class ProfilePagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        private String tabTitles[] = {"Sell","Buy"};
        public ProfilePagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new ProfileBuyerFragment();
            }else if (position == 1){
                return new ProfileSallerFragment();
            }else{
                return null;
            }
        }
        @Override
        public int getCount() {
            return tabTitles.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
