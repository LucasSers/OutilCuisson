package com.example.outilcuisson;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapteurPage extends FragmentStateAdapter {

    private final static int NB_FRAGMENT = 2;

    public AdapteurPage(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0 :
                return FragmentUn.newInstance();
            case 1 :
                return FragmentDeux.newInstance();
            default :
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NB_FRAGMENT;
    }
}
