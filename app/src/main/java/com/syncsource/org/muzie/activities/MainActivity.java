package com.syncsource.org.muzie.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.adapters.PagerAdapter;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.fragments.MyScloudFragment;
import com.syncsource.org.muzie.fragments.MyYtubeFragment;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.ScTrackContent;
import com.syncsource.org.muzie.rests.ScApiClient;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyYtubeFragment.TrackIntetface {

    TabLayout tabLayout;
    List<MyTrack> myTracks = new ArrayList<>();
    ScApiClient scApiClient;
    ScTrackContent newHotBody;
    ScTrackContent topTrackBody;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Youtube"));
        tabLayout.addTab(tabLayout.newTab().setText("SoundCloud"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1) {
                    MyScloudFragment scloudFragment = (MyScloudFragment) pagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());
                    scloudFragment.onReceivedSC(true);
                } else {
                    MyYtubeFragment ytubeFragment = (MyYtubeFragment) pagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());
                    ytubeFragment.onReceivedYT(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds search_items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void myTrackEvent(TrackEvent.OnTrackSyncEvent event) {
        if (event.isSuccess()) {
            myTracks = event.getMyTrack();
        }
    }

    @Override
    public List<MyTrack> getMyTrack() {
        if (myTracks.size() > 0) {
            return myTracks;
        } else {
            return null;
        }
    }
}
