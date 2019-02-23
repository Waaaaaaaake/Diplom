package com.example.stanislavlukanov.td.Navigation_Menu_Fragments.DevRes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stanislavlukanov.td.R;

import java.util.ArrayList;
import java.util.List;


        public class DevelopmentsFragment extends Fragment {

            public static DevelopmentsFragment newInstance(){
                Bundle args = new Bundle();
                DevelopmentsFragment fragment = new DevelopmentsFragment();
                fragment.setArguments(args);
                return fragment;
            }

            ViewPager viewPager;
            TabLayout tabLayout;

            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View v = inflater.inflate(R.layout.fr_developments,container, false);

                viewPager = (ViewPager) v.findViewById(R.id.viewpager);
                tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);

                AppCompatActivity activity = (AppCompatActivity) getActivity();
                assert activity.getSupportActionBar() != null;

                return v;
            }

            @Override
            public void onActivityCreated(@Nullable Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);

                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);

                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
            private void setupViewPager(ViewPager viewPager) {
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
                viewPagerAdapter.addFragment(new Tab1(), "События");
                viewPagerAdapter.addFragment(new Tab2(), "Календарь");
                viewPager.setAdapter(viewPagerAdapter);
            }
            private class ViewPagerAdapter extends FragmentPagerAdapter {

                List<Fragment> fragmentList = new ArrayList<>();
                List<String> fragmentTitles = new ArrayList<>();

                public ViewPagerAdapter(FragmentManager fragmentManager) {
                    super(fragmentManager);
                }

                @Override
                public Fragment getItem(int position) {
                    return fragmentList.get(position);
                }

                @Override
                public int getCount() {
                    return fragmentList.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return fragmentTitles.get(position);
                }

                public void addFragment(Fragment fragment, String name) {
                    fragmentList.add(fragment);
                    fragmentTitles.add(name);
                }
            }


        }
