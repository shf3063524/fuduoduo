package com.hjkj.fuduoduo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hjkj.fuduoduo.activity.mine_fragment.OrderDetails04Activity;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.fragment.HomeFragment;
import com.hjkj.fuduoduo.fragment.MineFragment;
import com.hjkj.fuduoduo.fragment.ShoppingFragment;
import com.hjkj.fuduoduo.fragment.SortFragment;
import com.hjkj.fuduoduo.tool.GlobalParms;
import com.hjkj.fuduoduo.tool.SharedPrefUtil;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.utils.TabEntity;
import com.hjkj.fuduoduo.utils.ViewFindUtils;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.common_tab)
    CommonTabLayout commonTab;
    @BindView(R.id.fl_change)
    FrameLayout flChange;
    @BindColor(R.color.cl_fff)
    int cl_fff;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private View mDecorView;
    private CommonTabLayout mTabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private String[] mTitles = {"首页", "分类", "购物车", "个人中心"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home, R.mipmap.ic_sort,
            R.mipmap.ic_shopping, R.mipmap.ic_mine};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_red, R.mipmap.ic_sort_red,
            R.mipmap.ic_shopping_red, R.mipmap.ic_mine_red};
    private ArrayList<Fragment> mFragments;

    /**
     * 0：新用户还是1：老用户
     */
    private String message;
    private ArrayList<String> mPageData;
    private String jumpKey;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    public static void openActivity(Context context, String jumpKey) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }
    /**
     * @param context
     * @param message 判断是否是0：新用户还是1：老用户
     */
    public static void openActivity(Context context, String message, String jumpKey) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBar() {

    }

    @Override
    protected void initPageData() {
        jumpKey = getIntent().getStringExtra("jumpKey");
    }

    @Override
    protected void initViews() {
        initTabLayout();
        if ("LoginActivity".equals(jumpKey)) {
            message = getIntent().getStringExtra("message");
        } else if ("ProductDetailsActivity".equals(jumpKey)) {
            replaceFragment(R.id.fl_change, ShoppingFragment.newInstance());
            mTabLayout.setCurrentTab(2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initTabLayout() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance(message));
        mFragments.add(SortFragment.newInstance());
        mFragments.add(ShoppingFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.common_tab);
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
    }

    @Override
    protected void actionView() {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
//                        replaceFragment(R.id.fl_change, HomeFragment.newInstance(message));
                        StatusBarUtil.setColor(MainActivity.this, cl_fff, 1);
                        break;
                    case 1:
//                        replaceFragment(R.id.fl_change, SortFragment.newInstance());
                        StatusBarUtil.setColor(MainActivity.this, cl_fff, 1);
                        break;
                    case 2:
//                        replaceFragment(R.id.fl_change, ShoppingFragment.newInstance());
                        StatusBarUtil.setColor(MainActivity.this, cl_fff, 1);
                        break;
                    case 3:
//                        replaceFragment(R.id.fl_change, MineFragment.newInstance());
                        StatusBarUtil.setColor(MainActivity.this, cl_e51C23, 1);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        GlobalParms.setFragmentSelected(new GlobalParms.ChangeFragment() {
            @Override
            public void changge(int postion) {
                //调用BottomNavigationBar的setlecTab方法来改变Tab
                mTabLayout.setCurrentTab(postion);
            }
        });
    }
}
