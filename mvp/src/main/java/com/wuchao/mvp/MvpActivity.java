package com.wuchao.mvp;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/20 9:14
 */
public abstract class MvpActivity <P extends MvpPresenter> extends CacheActivity implements MvpView {

    public P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        mPresenter=initPresenter();
        if(mPresenter!=null){
            mPresenter.attach(this);
        }
        initialize();
    }

    protected void initWindow(){};
    public abstract P initPresenter();
    protected void initialize(){
        initView();
        loadData();
    }
    protected abstract void loadData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        if(mPresenter!=null){
            mPresenter.detach();
        }
        super.onDestroy();
    }
}
