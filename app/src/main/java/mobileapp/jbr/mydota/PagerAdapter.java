package mobileapp.jbr.mydota;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by womble on 30.03.2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AccountFragment tab1 = new AccountFragment();
                return tab1;
            case 1:
                AccountFragment tab2 = new AccountFragment();
                return tab2;
            case 2:
                AccountFragment tab3 = new AccountFragment();
                return tab3;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
