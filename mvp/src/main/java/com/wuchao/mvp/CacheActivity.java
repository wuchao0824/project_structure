package com.wuchao.mvp;

import android.util.SparseArray;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/20 8:54
 */
public class CacheActivity extends AppCompatActivity {
    private SparseArray<View>mCacheViews;

    @Override
    public <T extends View> T findViewById(int id) {
        if(mCacheViews==null){
            mCacheViews=new SparseArray<>();
        }
        View view = mCacheViews.get(id);
        if(view==null){
            view=getWindow().getDecorView().findViewById(id);
            mCacheViews.put(id, view);
        }
        return (T) view;
    }
}
