package com.wuchao.mvvm;

/**
 * Description:<br>
 * Author:wuchao
 * Date:2020/5/22 17:25
 */
public interface IMvvMViewModel<V> {
    void attach(V view);
    void detach();
}
