package com.alless.commonlib.adapter.list;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by chengjie on 2019/3/20
 * Description:
 */
public class ViewHolder {
    private SparseArray<View> views;
    private View mContextView;

    public ViewHolder(View contextView){
        mContextView = contextView;
        views = new SparseArray<>();
    }

    private <T extends View> T findView(@IdRes int id){
        return (T)mContextView.findViewById(id);
    }

    public <T extends View> T getView(@IdRes int id){

        View v = views.get(id);
        if(v == null){
            v = findView(id);
            views.put(id,v);
        }

        return (T)v;
    }
}
