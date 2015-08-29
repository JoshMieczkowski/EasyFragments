# EasyFragments
Tired of rewriting the same code just to handle fragments? Forgetting the awesome transitions that you can do? Want to make sure the backstack is being handled? Then EasyFragments is probably for you. This library takes all the code used for fragments and simplifies it so you do have to keep rewriting it.


# How to add a fragment
Adding a fragment will cause it to layer. For example, if you have FragmentA and you add FragmentB. FragmentA will still exist and be drawn but will be under FragmentB.
```java
//Recommend making this a global for your activity and initialize this in onCreate()
FragmentHandler fragmentHandler = new FragmentHandler(activity);
fragmentHandler.add(new FragmentSettings("MY_BACKSTACK_TAG"),
		new FragmentContainer(new Fragment(), R.id.placeholder, "FRAGMENT_TAG"));
```

# How to replace a fragment
Replacing a fragment will replace whatever fragment is currently at the place holder. This will call onResume for the new fragment and onPause for the old fragment. If you press the back button and have a backStackTag, the old fragment will have onResume called. This does not happen for add.
```java
//Recommend making this a global for your activity and initialize this in onCreate()
FragmentHandler fragmentHandler = new FragmentHandler(activity);
fragmentHandler.add(new FragmentSettings("MY_BACKSTACK_TAG"),
		new FragmentContainer(new Fragment(), R.id.placeholder, "FRAGMENT_TAG"));
```

# How to remove a fragment
This will remove the fragment as well as remove it from the backStack if it was added to it
```java
//Recommend making this a global for your activity and initialize this in onCreate()
FragmentHandler fragmentHandler = new FragmentHandler(activity);
fragmentHandler.remove(new FragmentSettings(),
                "FRAGMENT_TAG");
```

#Notes
You can pass it x number of FragmentContainers or tags as you like. 