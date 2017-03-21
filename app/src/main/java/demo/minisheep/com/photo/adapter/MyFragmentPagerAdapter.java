package demo.minisheep.com.photo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by minisheep on 17/3/8.
 */

//返回fragment
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> list;

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
