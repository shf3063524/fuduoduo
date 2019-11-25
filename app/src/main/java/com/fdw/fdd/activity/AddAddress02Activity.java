package com.fdw.fdd.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.fdw.fdd.MainActivity;
import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.JsonBean;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.GetJsonDataUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.ClearEditText;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class AddAddress02Activity extends BaseActivity {
    @BindView(R.id.m_layout_area)
    RelativeLayout mLayoutArea;
    @BindView(R.id.m_iv_return)
    ImageView mIvReturn;
    @BindView(R.id.m_tv_save)
    TextView mTvSave;
    @BindView(R.id.m_tv_write_area)
    TextView mTvWriteArea;
    @BindView(R.id.m_cet_name)
    ClearEditText mCetName;
    @BindView(R.id.m_cet_phone)
    ClearEditText mCetPhone;
    @BindView(R.id.m_cet_address)
    ClearEditText mCetAddress;
    @BindView(R.id.m_sb_show_on_coupon_map)
    SwitchButton mSbShowOnCouponMap;
    @BindColor(R.color.cl_333)
    int cl_333;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    /**
     * 默认地址选择 0不是默认地址，1默认地址
     */
    private int isShowOnCouponMap = 0;
    private static boolean isLoaded = false;
    private String jumpKey;
    private String message;

    public static void openActivity(Context context, String message, String jumpKey) {
        Intent intent = new Intent(context, AddAddress02Activity.class);
        intent.putExtra("message", message);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_add_address02;
    }

    @Override
    protected void initPageData() {
        message = getIntent().getStringExtra("message");
        jumpKey = getIntent().getStringExtra("jumpKey");
    }

    @Override
    protected void initViews() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    @OnClick({R.id.m_iv_return, R.id.m_tv_save, R.id.m_layout_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_save:   // 保存
                if (textIsEmpty(mCetName)) {
                    Toasty.info(AddAddress02Activity.this, "请填写收货人姓名").show();
                    return;
                }
                if (textIsEmpty(mCetPhone)) {
                    Toasty.info(AddAddress02Activity.this, "请填写收货人手机号").show();
                    return;
                }
                if (textIsEmpty(mTvWriteArea)) {
                    Toasty.info(AddAddress02Activity.this, "请选择所在地区").show();
                    return;
                }
                if (textIsEmpty(mCetAddress)) {
                    Toasty.info(AddAddress02Activity.this, "请填写详细地址").show();
                    return;
                }
                requestData();
                break;
            case R.id.m_layout_area:   // 所在地区
                if (isLoaded) {
                    showPickerView();
                }
                break;
        }
    }

    @Override
    protected void actionView() {
        mSbShowOnCouponMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isShowOnCouponMap = 1;
                } else {
                    isShowOnCouponMap = 0;
                }
            }
        });
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            private String opt1tx = null;

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (null != options1Items && options1Items.size() != 0) {
                    opt1tx = options1Items.size() > 0 ?
                            options1Items.get(options1).getPickerViewText() : "";
                }

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + " " + opt2tx + " " + opt3tx;
                mTvWriteArea.setText(tx);
                mTvWriteArea.setTextColor(cl_333);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(AddAddress02Activity.this);
        String name = getTextString(mCetName);
        String mobilephoneNumber = getTextString(mCetPhone);
        String area = getTextString(mTvWriteArea);
        String street = getTextString(mCetAddress);
        OkGo.<AppResponse>get(Api.FREIGHTADDRESS_DOSAVE)//
                .params("id", null)
                .params("userId", userId)
                .params("name", name)
                .params("mobilephoneNumber", mobilephoneNumber)
                .params("area", area)
                .params("street", street)
                .params("defaultAddress", "1")
                .params("type", "0")
                .params("state", "0")
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            MainActivity.openActivity(AddAddress02Activity.this, message, jumpKey);
                            Toasty.normal(AddAddress02Activity.this, "保存成功").show();
                            finish();
                        }
                    }
                });
    }
}
