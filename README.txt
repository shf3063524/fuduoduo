************积分**************************
   <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:layout_marginTop="4dp"
                    android:layout_marginBottom="12dp">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="580.00"
                        android:textColor="@color/cl_e51C23"
                        android:textSize="20sp" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="2dp"
                        android:text="积分"
                        android:layout_gravity="center_vertical"
                        android:textColor="@color/cl_e51C23"
                        android:textSize="14sp" />
                </LinearLayout>
************积分**************************

********************** TextView换行/字体省略***********************
        <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:ellipsize="end"
                    android:lines="2"
                    android:maxEms=""
                    android:text="BRITA碧然德滤小型车离开V型才考虑将水进水看电视剧是考虑到就算了"
                    android:textColor="@color/cl_333"
                    android:textSize="16sp"
                    android:textStyle="bold" />
********************** TextView换行/字体省略***********************

*********************点击事件*************************************
 @OnClick({R.id.m_iv_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
               if (!clickBack()) {
                                   finish();
                               }
                break;
        }
    }
*********************点击事件*************************************
**************************假数据****************************************
    if (unbinder != null) {

                mData.clear();
                for (int i = 0; i < 10; i++) {
                    mData.add(new TestBean("item" + i));
                }
                mAdapter.notifyDataSetChanged();
            }
**************************假数据****************************************

************************************跳转Activity*******************
public static void openActivity(Context context) {
        Intent intent = new Intent(context, GlobeActivity.class);
        context.startActivity(intent);
    }
************************************跳转Activity*******************

*******************************适配器单个条目**************************************
 mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
*********************************************************************

**************************适配器多个条目********************************************
  mData = new ArrayList<>();
        mAdapter = new SlidingTabAdapter(R.layout.item_sliding_tab, mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(mAdapter);
**********************************************************************