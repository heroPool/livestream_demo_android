package cn.ucai.live;

import android.content.Context;

import cn.ucai.live.utils.PreferenceManager;


public class LiveModel {
    protected Context context = null;

    public LiveModel(Context ctx) {
        context = ctx;
        PreferenceManager.init(context);
    }

    public void setCurrentUserName(String username) {
        PreferenceManager.getInstance().setCurrentUserName(username);
    }

    public String getCurrentUsernName() {
        return PreferenceManager.getInstance().getCurrentUsername();
    }

}
