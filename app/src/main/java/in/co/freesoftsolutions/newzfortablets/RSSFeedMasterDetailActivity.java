package in.co.freesoftsolutions.newzfortablets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class RSSFeedMasterDetailActivity extends AppCompatActivity
                                         implements RSSFeedsListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed_master_detail);

        FragmentManager fm = getSupportFragmentManager();

        Fragment listFragment = fm.findFragmentById(R.id.fragment_container);

        if (listFragment == null) {
            listFragment = new RSSFeedsListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void onRSSFeedClicked(UUID rssFeedUUID) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment detailFragment = fm.findFragmentById(R.id.detail_fragment_container);

        if (detailFragment == null) {
            detailFragment = RSSFeedFragment.newInstance(rssFeedUUID);
            fm.beginTransaction()
                    .add(R.id.detail_fragment_container, detailFragment)
                    .commit();
        } else {
            detailFragment = RSSFeedFragment.newInstance(rssFeedUUID);
            fm.beginTransaction()
                    .replace(R.id.detail_fragment_container, detailFragment)
                    .commit();
        }
    }

    @Override
    public void onRSSFeedRemoved() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment detailFragment = fm.findFragmentById(R.id.detail_fragment_container);
        if (detailFragment != null) {
            fm.beginTransaction()
                    .remove(detailFragment)
                    .commit();
        }
    }
}
