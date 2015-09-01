package com.futurethought.easyfragments.supportedFragments;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;

import com.futurethought.easyfragments.FragmentSettings;

/**
 * Created by Josh Mieczkowski on 8/29/2015.
 */
public class SupportedFragmentHandler {
    private static final int ADD = 0;
    private static final int REPLACE = 1;
    private static final int REMOVE = 2;

    private FragmentManager fragmentManager;

    public SupportedFragmentHandler(FragmentActivity activity) {
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    private void commitActionForFragment(int mode, FragmentSettings fragmentSettings, SupportedFragmentContainer... supportedFragmentContainers){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setAnimations(fragmentTransaction, fragmentSettings);

        for(SupportedFragmentContainer supportedFragmentContainer : supportedFragmentContainers){
            if(supportedFragmentContainer != null && supportedFragmentContainer.getFragment() != null){
                switch (mode){
                    case ADD:
                        if(supportedFragmentContainer.getTag() == null){
                            fragmentTransaction.add(supportedFragmentContainer.getHolderID(), supportedFragmentContainer.getFragment());
                        }else{
                            fragmentTransaction.add(supportedFragmentContainer.getHolderID(), supportedFragmentContainer.getFragment(), supportedFragmentContainer.getTag());
                        }
                        break;
                    case REPLACE:
                        if(supportedFragmentContainer.getTag() == null){
                            fragmentTransaction.replace(supportedFragmentContainer.getHolderID(), supportedFragmentContainer.getFragment());
                        }else{
                            fragmentTransaction.replace(supportedFragmentContainer.getHolderID(), supportedFragmentContainer.getFragment(), supportedFragmentContainer.getTag());
                        }
                        break;
                    case REMOVE:
                        fragmentTransaction.remove(supportedFragmentContainer.getFragment());
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

    private SupportedFragmentContainer[] convertFragmentsToContainers(Fragment... fragments){
        SupportedFragmentContainer[] supportedFragmentContainers = new SupportedFragmentContainer[fragments.length];
        int index = 0;
        for(Fragment fragment : fragments){
            supportedFragmentContainers[index] = new SupportedFragmentContainer(fragment, -1);
            index++;
        }

        return supportedFragmentContainers;
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

    public void add(FragmentSettings fragmentSettings, SupportedFragmentContainer... supportedFragmentContainers){
        commitActionForFragment(ADD, fragmentSettings, supportedFragmentContainers);
    }

    public void replace(FragmentSettings fragmentSettings, SupportedFragmentContainer... supportedFragmentContainers){
        commitActionForFragment(REPLACE, fragmentSettings, supportedFragmentContainers);
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

    public void show(boolean removePrevious, String previousTag, FragmentSettings fragmentSettings, SupportedFragmentContainer supportedFragmentContainer){
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
        ((DialogFragment) supportedFragmentContainer.getFragment()).show(fragmentTransaction, supportedFragmentContainer.getTag());
    }

    public void show(FragmentSettings fragmentSettings, SupportedFragmentContainer supportedFragmentContainer){
        show(false, null, fragmentSettings, supportedFragmentContainer);
    }
}
