package com.edisonkz.asistenciaintegrador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class InformacionPagerAdapter extends FragmentStateAdapter {

    private final List<InformacionFragment> fragments;

    public InformacionPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<InformacionFragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}