package com.work.service.aidlandroidservice;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/15.
 */

public class AidlService extends Service {
    /**
     * 定义远程服务，并且需要在manifest中定义
     **/
    private String TAG = "Dylan_debug";

    private List<User> loginList = new ArrayList<>();


    private ILoginAidl.Stub myAidl = new ILoginAidl.Stub() {

        @Override
        public boolean isLogin(User user) throws RemoteException {
            Log.e(TAG, "****调用服务端方法isLogin");
            for (User list : loginList) {
                if (list.equals(user)) {
//如果服务端有客户端请求的用户就返回true
                    return true;
                }
            }
            return false;
        }

        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e(TAG, "****调用远程服务端add方法");
            return num1 + num2;
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        init();
        Log.e(TAG, "****远程服务启动,当前进程id" + getCurProcessName(this));
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myAidl;
    }

    private void init() {

        loginList.add(new User("00", "00"));
        loginList.add(new User("yin1", "111111"));
        loginList.add(new User("yin2", "111111"));
        loginList.add(new User("yin3", "111111"));
        loginList.add(new User("yin4", "111111"));
    }

    // 获取当前进程id
    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
