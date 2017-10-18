package com.yiguohan.easyappstore.factory;

import com.yiguohan.easyappstore.base.BaseFragment;
import com.yiguohan.easyappstore.fragment.AppManagerFragment;
import com.yiguohan.easyappstore.fragment.CategoryFragment;
import com.yiguohan.easyappstore.fragment.MyFragment;
import com.yiguohan.easyappstore.fragment.RecommendFragment;
import com.yiguohan.easyappstore.fragment.TopFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yiguohan on 2017/10/18.
 */

public class FragmentFactory {
    /**
     * 推荐
     */
    public static final int TAB_RECOMMEND = 0;
    /**
     * 分类
     */
    public static final int TAB_CATEGORY = 1;
    /**
     * 排行
     */
    public static final int TAB_TOP = 2;
    /**
     * 管理
     */
    public static final int TAB_APPMANAGER = 3;
    /**
     * 我的
     */
    public static final int TAB_MY = 4;

    private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int index) {
        BaseFragment fragment = mFragments.get(index);
        //如果没有创建就创建一个新的
        if (fragment == null) {
            switch (index) {
                case TAB_RECOMMEND:
                    fragment = new RecommendFragment();
                    break;
                case TAB_CATEGORY:
                    fragment = new CategoryFragment();
                    break;
                case TAB_TOP:
                    fragment = new TopFragment();
                    break;
                case TAB_APPMANAGER:
                    fragment = new AppManagerFragment();
                    break;
                case TAB_MY:
                    fragment = new MyFragment();
                    break;
                default:
                    break;
            }
            //创建完成就存起来
            mFragments.put(index, fragment);
        }
        return fragment;
    }
}
