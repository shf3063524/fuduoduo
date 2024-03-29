package com.fdw.fdd.tool.kefutool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;
import java.util.Random;

public class Preferences {
	private static final String TAG = Preferences.class.getSimpleName();
	static private Preferences instance = null;
	static private String PREFERENCE_NAME = "info";
	static private String KEY_APPKEY = "appkey";
	static private String KEY_CUSTOMER_ACCOUNT = "customer_account";
	static private String KEY_NICKNAME = "nickname";
	static private String KEY_TENANT_ID = "tenantId";
	static private String KEY_PROJECT_ID = "projectId";
	
	private SharedPreferences pref = null;
	private SharedPreferences.Editor editor = null;
	
	static public Preferences getInstance(){
		return instance;
	}
	
	public static void init(Context context){
		instance = new Preferences();
		instance.pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE );
		instance.editor = instance.pref.edit();
	}

	//这里随机创建用户,如正式用,应从服务器获取账号
    public String getUserName(){
		return getRandomAccount();

    }
    
    public synchronized void setAppKey(String key) {
        editor.putString(KEY_APPKEY, key);
        editor.commit();

    }
    
    public synchronized String getAppKey(){
        return pref.getString(KEY_APPKEY, Constant.DEFAULT_CUSTOMER_APPKEY);
    }
    
    public synchronized String getTenantId() {
    	return pref.getString(KEY_TENANT_ID, Constant.DEFAULT_TENANT_ID);
    }
    
    public synchronized void setTenantId(String tenantId) {
    	editor.putString(KEY_TENANT_ID, tenantId);
    	editor.commit();
    }
    
    public void setCustomerAccount(String account){
        editor.putString(KEY_CUSTOMER_ACCOUNT, account);
        editor.commit();
    	
    }
    
    public String getCustomerAccount(){
    	return pref.getString(KEY_CUSTOMER_ACCOUNT, Constant.DEFAULT_CUSTOMER_ACCOUNT);
    }
    
    public void setNickName(String nickname){
        editor.putString(KEY_NICKNAME, nickname);
        editor.commit();
    }
    
    public String getNickName(){
       return pref.getString(KEY_NICKNAME, Constant.DEFAULT_NICK_NAME);
    }
    
	public synchronized String getProjectId(){
		return pref.getString(KEY_PROJECT_ID, Constant.DEFAULT_PROJECT_ID);
	}

	public synchronized void setSettingProjectId(String projectId){
		editor.putString(KEY_PROJECT_ID, projectId).commit();
	}


    /**
     * demo为了演示功能，此处随机生成账号。
     * @return
     */
    private String getRandomAccount(){
    	String val = "";
    	Random random = new Random();
    	for(int i = 0; i < 15; i++){
    		String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; //输出字母还是数字
    		if("char".equalsIgnoreCase(charOrNum)){// 字符串
    			int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
    			val += (char) (choice + random.nextInt(26));
    		}else if("num".equalsIgnoreCase(charOrNum)){// 数字
    			val += String.valueOf(random.nextInt(10));
    		}
    	}
    	return val.toLowerCase(Locale.getDefault());
    }
    
    
}
