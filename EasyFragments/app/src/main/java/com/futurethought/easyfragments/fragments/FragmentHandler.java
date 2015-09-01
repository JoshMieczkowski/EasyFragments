package com.futurethought.easyfragments.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.futurethought.easyfragments.FragmentSettings;

/**
 * Created by Josh Mieczkowski on 8/29/2015.
 */
public class FragmentHandler {
    private static final int ADD = 0;
    private static final int REPLACE = 1;
    private static final int REMOVE = 2;

    private FragmentManager fragmentManager;

    public FragmentHandler(Activity activity) {
        this.fragmentManager = activity.getFragmentManager();
    }

    private void commitActionForFragment(int mode, FragmentSettings fragmentSettings, FragmentContainer... fragmentContainers){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setAnimations(fragmentTransaction, fragmentSettings);

        for(FragmentContainer fragmentContainer : fragmentContainers){
            if(fragmentContainer != null && fragmentContainer.getFragment() != null){
                switch (mode){
                    case ADD:
                        if(fragmentContainer.getTag() == null){
                            fragmentTransaction.add(fragmentContainer.getHolderID(), fragmentContainer.getFragment());
                        }else{
                            fragmentTransaction.add(fragmentContainer.getHolderID(), fragmentContainer.getFragment(), fragmentContainer.getTag());
                        }
                        break;
                    case REPLACE:
                        if(fragmentContainer.getTag() == null){
                            fragmentTransaction.replace(fragmentContainer.getHolderID(), fragmentContainer.getFragment());
                        }else{
                            fragmentTransaction.replace(fragmentContainer.getHolderID(), fragmentContainer.getFragment(), fragmentContainer.getTag());
                        }
                        break;
                    case REMOVE:
                        fragmentTransaction.remove(fragmentContainer.getFragment());
                        break;
                }
            }
        }

        if(fragmentSettings.isAddToBackStack()){
            fragmentTransaction.addToBackStack(fragmentSettings.getBackStackTag());
        }

        if(fragmentSettings.isAllowStateLoss()){
            fragmentTransaction.commitAllowingStateLoss();
        }else{
            fragmentTransaction.commit();
        }

        if(fragmentSettings.isExecuteImmediately()){
            fragmentManager.executePendingTransactions();
        }

    }

    private void setAnimations(FragmentTransaction fragmentTransaction, FragmentSettings fragmentSettings){
        if(fragmentSettings.getAnimationEnter() != null && fragmentSettings.getAnimationExit() != null){
            if(fragmentSettings.getAnimationPopEnter() != null && fragmentSettings.getAnimationPopExit() != null){
                fragmentTransaction.setCustomAnimations(fragmentSettings.getAnimationEnter(), fragmentSettings.getAnimationExit(),
                        fragmentSettings.getAnimationPopEnter(), fragmentSettings.getAnimationPopExit());
            }else{
                fragmentTransaction.setCustomAnimations(fragmentSettings.getAnimationEnter(), fragmentSettings.getAnimationExit());
            }
        }
    }

    private FragmentContainer[] convertFragmentsToContainers(Fragment... fragments){
        FragmentContainer[] fragmentContainers = new FragmentContainer[fragments.length];
        int index = 0;
        for(Fragment fragment : fragments){
            fragmentContainers[index] = new FragmentContainer(fragment, -1);
            index++;
        }

        return fragmentContainers;
    }

    public Fragment findFragmentByTag(String tag){
        return fragmentManager.findFragmentByTag(tag);
    }

    public Fragment findFragmentByID(int id){
        return fragmentManager.findFragmentById(id);
    }

    public boolean isFragmentVisible(Fragment fragment){
        return fragment != null && fragment.isVisible();
    }

    public int getBackStackCount(){
        return fragmentManager.getBackStackEntryCount();
    }

    public void addBackStackListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener){
        fragmentManager.addOnBackStackChangedListener(onBackStackChangedListener);
    }

    public void removeBackStackListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener){
        fragmentManager.removeOnBackStackChangedListener(onBackStackChangedListener);
    }

    public void add(FragmentSettings fragmentSettings, FragmentContainer... fragmentContainers){
        commitActionForFragment(ADD, fragmentSettings, fragmentContainers);
    }

    public void replace(FragmentSettings fragmentSettings, FragmentContainer... fragmentContainers){
        commitActionForFragment(REPLACE, fragmentSettings, fragmentContainers);
    }

    public void remove(FragmentSettings fragmentSettings, Fragment... fragments){
        commitActionForFragment(REMOVE, fragmentSettings, convertFragmentsToContainers(fragments));
    }

    public void remove(FragmentSettings fragmentSettings, String... tags){
        Fragment[] fragments = new Fragment[tags.length];
        int index = 0;
        for(String tag : tags){
            fragments[index] = findFragmentByTag(tag);
            index++;
        }

        remove(fragmentSettings, fragments);
    }

    public void show(boolean removePrevious, String previousTag, FragmentSettings fragmentSettings, FragmentContainer fragmentContainer){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(removePrevious){
            Fragment previous = findFragmentByTag(previousTag);
            if(previous != null){
                fragmentTransaction.remove(previous);
            }
        }

        if(fragmentSettings.isAddToBackStack()){
            fragmentTransaction.addToBackStack(fragmentSettings.getBackStackTag());
        }

        setAnimations(fragmentTransaction, fragmentSettings);
        ((DialogFragment)fragmentContainer.getFragment()).show(fragmentTransaction, fragmentContainer.getTag());
    }

    public void show(FragmentSettings fragmentSettings, FragmentContainer fragmentContainer){
        show(false, null, fragmentSettings, fragmentContainer);
    }
}
