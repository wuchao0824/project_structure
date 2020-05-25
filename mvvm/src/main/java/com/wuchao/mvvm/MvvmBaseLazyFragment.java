package com.wuchao.mvvm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/25 14:12
 */
public abstract class MvvmBaseLazyFragment <V extends ViewDataBinding,VM extends IMvvMViewModel> extends Fragment implements IBaseView {

    public V viewBinding;
    public VM viewModel;
    protected View rootView;
    /**
     *  des: 是否是第一次可见
     */
    private boolean isFirstVisible=true;
    /**
     *  des: 当前可见状态
     */
    protected  boolean currentVisibleState=false;

    protected boolean isViewCreated=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(null==rootView){
            viewBinding=DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
            rootView=viewBinding.getRoot();
        }
        isViewCreated=true;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel=getViewModel();
        if(null!=viewModel){
            viewModel.attach(this);
        }
        if(!isHidden()&&getUserVisibleHint()){
            //可见状态
            dispatchUserVisibleHint(true);
        }
    }

    private void dispatchUserVisibleHint(boolean isVisible) {
        if(isVisible&&isParentInvisible()){
            return;
        }
        //为了代码严谨,如果当前状态与需要设置的状态本来就一致了,就不处理了
        if(currentVisibleState==isVisible){
            return;
        }
        currentVisibleState=isVisible;

        if(isVisible){
            if(isFirstVisible){
                onFragmentFirstVisible();
                isFirstVisible=false;
            }
            onFragmentResume();

            //分发给内嵌的fragment
            dispatchChildVisibleState(true);
        }else{
            onFragmentPause();
            dispatchChildVisibleState(false);
        }
    }


    /**
     * 在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
     * 此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
     * 需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
     */
    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (null != fragments) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof MvvmBaseLazyFragment && !fragment.isHidden()
                        && fragment.getUserVisibleHint()) {
                    ((MvvmBaseLazyFragment) fragment).dispatchUserVisibleHint(visible);
                }
            }
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isViewCreated){
            // 对于情况2,需要分情况考虑,如果是不可见 -> 可见 2.1
            // 如果是可见 -> 不可见 2.2
            // 对于2.1）我们需要如何判断呢？首先必须是可见的（isVisibleToUser
            // 为true）而且只有当可见状态进行改变的时候才需要切换，否则会出现反复调用的情况
            // 从而导致事件分发带来的多次更新
            if (isVisibleToUser && !currentVisibleState) {
                // 从不可见 -> 可见
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(isHidden()){
            dispatchUserVisibleHint(false);
        }else{
            dispatchUserVisibleHint(true);
        }
    }

    protected void onFragmentFirstVisible() {

    }

    protected void onFragmentResume() {

    }

    protected void onFragmentPause() {

    }

    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if(parentFragment instanceof MvvmBaseLazyFragment){
            MvvmBaseLazyFragment fragment= (MvvmBaseLazyFragment) parentFragment;
            return !fragment.isSupportVisible();
        }
        return false;
    }


    private boolean isSupportVisible(){
        return currentVisibleState;
    }


    protected abstract VM getViewModel();

    protected abstract int getLayoutId();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated=false;
    }
}
