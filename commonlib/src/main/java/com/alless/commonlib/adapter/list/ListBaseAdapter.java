package com.alless.commonlib.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alless.commonlib.adapter.list.ViewHolder;

import java.util.List;

/**
 * Created by chengjie on 2019/3/20
 * Description:
 */
public abstract class ListBaseAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mList;

    public ListBaseAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        T t = mList.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(getLayoutId(),parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder,t,position);
        return convertView;
    }

    protected abstract int getLayoutId();

    protected abstract void bindData(ViewHolder holder,T t,int position);
}
