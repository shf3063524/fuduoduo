package com.fdw.fdd.tool;

import com.fdw.fdd.fragment.HomeFragment;
import com.fdw.fdd.fragment.MineFragment;
import com.fdw.fdd.fragment.ShoppingFragment;
import com.fdw.fdd.fragment.SortFragment;

/**
 * 同一Activity,通过Fragment内控件跳转到另一个Fragment，下面的导航控件也跟着发生变化
 */
public class GlobalParms {
    private static HomeFragment sHomeFragment; //主页fragemnt
    private static SortFragment sSortFragment; //分类fragemnt
    private static ShoppingFragment sShoppingFragment; //购物车fragemnt
    private static MineFragment sMineFragment; //我的fragemnt
    public static ChangeFragment sChangeFragment;  //改变选中Frangment的接口

    /**
     * 获取主页Fragment
     *
     * @return
     */
    public static HomeFragment getHomeFragment() {
        if (sHomeFragment == null) {
            sHomeFragment = new HomeFragment();
        }
        return sHomeFragment;
    }

    /**
     * 分类fragemnt
     *
     * @return
     */
    public static SortFragment getChartsFragment() {
        if (sSortFragment == null) {
            sSortFragment = new SortFragment();
        }
        return sSortFragment;
    }

    /**
     * 购物车fragemnt
     *
     * @return
     */
    public static ShoppingFragment getZiXunFragment() {
        if (sShoppingFragment == null) {
            sShoppingFragment = new ShoppingFragment();
        }
        return sShoppingFragment;
    }

    /**
     * //我的fragemnt
     *
     * @return
     */
    public static MineFragment getOtherFragment() {
        if (sMineFragment == null) {
            sMineFragment = new MineFragment();
        }
        return sMineFragment;
    }

    /**
     * 设置被选中的Fragment
     *
     * @param changeFragment
     */
    public static void setFragmentSelected(ChangeFragment changeFragment) {
        sChangeFragment = changeFragment;

    }

    public interface ChangeFragment {
        void changge(int postion);
    }
}
