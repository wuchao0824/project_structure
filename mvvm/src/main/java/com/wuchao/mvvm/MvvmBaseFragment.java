package com.wuchao.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/25 14:03
 */
public abstract class MvvmBaseFragment<V extends ViewDataBinding,VM extends IMvvMViewModel> extends Fragment implements IBaseView {

    public V viewBinding;
    public VM viewModel;
    protected String mFragmentTag = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding=DataBindingUtil.inflate(inflater,getLayoutId(), container, false);
        return viewBinding.getRoot();
    }

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel=getViewModel();
        if(viewModel!=null){
            viewModel.attach(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(viewModel!=null){
            viewModel.detach();
        }
    }

    protected abstract VM getViewModel();

    @Override
    public void showContent() {

    }

    @Override
    public void showFail() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }
}
