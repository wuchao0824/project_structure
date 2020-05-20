package com.wuchao.mvp;

import android.content.Context;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/20 9:06
 */
public interface MvpView {
    Context getContext();
    void showLoading();
    void hideLoading();
}
