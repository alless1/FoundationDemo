package com.alless.commonlib.manager;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by chengjie on 2019/3/19
 * Description: 管理activity页面栈
 */
public class ActivityStackManager {
    private Stack<Activity> activities;

    private WeakReference<Activity> mCurrentActivityWeakRef;

    private static ActivityStackManager sInstance;

    private ActivityStackManager() {}

    public static ActivityStackManager getInstance() {
        if(sInstance==null){
            synchronized (ActivityStackManager.class){
                if(sInstance==null){
                    sInstance = new ActivityStackManager();
                }
            }
        }
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (mCurrentActivityWeakRef != null) {
            currentActivity = mCurrentActivityWeakRef.get();
        }

        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        mCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new Stack<Activity>();
        }

        if (activities.search(activity) == -1) {
            activities.push(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            activities.remove(activity);
        }
    }

    public void setTopActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            if (activities.search(activity) == -1) {
                activities.push(activity);
                return ;
            }

            int location = activities.search(activity);
            if (location != 1) {
                activities.remove(activity);
                activities.push(activity);
            }
        }
    }

    public Activity getTopActivity() {
        if (activities != null && activities.size() > 0) {
            return activities.peek();
        }

        return null;
    }

    public boolean isTopActivity(Activity activity) {
        return activity.equals(activities.peek());
    }

    public void finishTopActivity() {
        if (activities != null && activities.size() > 0) {
            Activity activity = activities.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void finishAllActivity() {
        if (activities != null && activities.size() > 0) {
            while (!activities.empty()) {
                Activity activity = activities.pop();
                if (activity != null) {
                    activity.finish();
                }
            }

            activities.clear();
            activities = null;
        }
    }

    public Stack<Activity> getActivityStack() {
        return activities;
    }
}
