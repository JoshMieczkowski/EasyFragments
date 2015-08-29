package com.futurethought.easyfragments;

/**
 * Created by Josh Mieczkowski on 8/29/2015.
 */
public class FragmentSettings {
    private boolean executeImmediately = false;
    private boolean allowStateLoss = false;
    private boolean addToBackStack = false;
    private String backStackTag = null;

    private Integer animationEnter = null;
    private Integer animationExit = null;
    private Integer animationPopEnter = null;
    private Integer animationPopExit = null;

    public FragmentSettings(String backStackTag) {
        this.addToBackStack = true;
        this.backStackTag = backStackTag;
    }

    public boolean isExecuteImmediately() {
        return executeImmediately;
    }

    public void setExecuteImmediately(boolean executeImmediately) {
        this.executeImmediately = executeImmediately;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }

    public void setAddToBackStack(boolean addToBackStack) {
        this.addToBackStack = addToBackStack;
    }

    public String getBackStackTag() {
        return backStackTag;
    }

    public void setBackStackTag(String backStackTag) {
        this.backStackTag = backStackTag;
    }

    public Integer getAnimationEnter() {
        return animationEnter;
    }

    public void setAnimationEnter(Integer animationEnter) {
        this.animationEnter = animationEnter;
    }

    public Integer getAnimationExit() {
        return animationExit;
    }

    public void setAnimationExit(Integer animationExit) {
        this.animationExit = animationExit;
    }

    public Integer getAnimationPopEnter() {
        return animationPopEnter;
    }

    public void setAnimationPopEnter(Integer animationPopEnter) {
        this.animationPopEnter = animationPopEnter;
    }

    public Integer getAnimationPopExit() {
        return animationPopExit;
    }

    public void setAnimationPopExit(Integer animationPopExit) {
        this.animationPopExit = animationPopExit;
    }

    public boolean isAllowStateLoss() {
        return allowStateLoss;
    }

    public void setAllowStateLoss(boolean allowStateLoss) {
        this.allowStateLoss = allowStateLoss;
    }
}
