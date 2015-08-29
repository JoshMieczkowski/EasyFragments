package com.futurethought.easyfragments.fragments;

import android.app.Fragment;

/**
 * Created by Josh Mieczkowski on 8/29/2015.
 */
public class FragmentContainer {
    private Fragment fragment;
    private String tag;
    private int holderID;

    public FragmentContainer(Fragment fragment, String tag, int holderID) {
        this.fragment = fragment;
        this.tag = tag;
        this.holderID = holderID;
    }

    public FragmentContainer(Fragment fragment, int holderID) {
        this.fragment = fragment;
        this.holderID = holderID;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTag() {
        return tag;
    }

    public int getHolderID() {
        return holderID;
    }
}
