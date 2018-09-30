package ir.ghaza_khoonegi.www.khoonegibebar.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ir.ghaza_khoonegi.www.khoonegibebar.Fragment.ChefFragment;
import ir.ghaza_khoonegi.www.khoonegibebar.Fragment.FoodFragment;

public class HomeViewpagerAdapter extends FragmentPagerAdapter{
    public HomeViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FoodFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return ChefFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
