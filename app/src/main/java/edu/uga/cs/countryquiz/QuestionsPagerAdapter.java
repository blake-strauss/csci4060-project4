package edu.uga.cs.countryquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuestionsPagerAdapter extends FragmentStateAdapter {

    public QuestionsPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        return FragmentActivity.newInstance( position );
    }

    @Override
    public int getItemCount() {
        return FragmentActivity.getLength();
    }
}
