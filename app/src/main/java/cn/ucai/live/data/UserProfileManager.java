package cn.ucai.live.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.User;

import java.io.File;

import cn.ucai.live.I;
import cn.ucai.live.net.IUserRegisterModel;
import cn.ucai.live.net.OnCompleteListener;
import cn.ucai.live.net.UserRegisterModel;
import cn.ucai.live.utils.PreferenceManager;
import cn.ucai.live.utils.Result;
import cn.ucai.live.utils.ResultUtils;

public class UserProfileManager {

    protected Context appContext = null;

    private boolean sdkInited = false;

    private User currentAppUser;
    IUserRegisterModel userRegisterModel;

    public UserProfileManager() {
    }

    public synchronized boolean init(Context context) {
        if (sdkInited) {
            return true;
        }
        appContext = context;
        sdkInited = true;
        userRegisterModel = new UserRegisterModel();
        return true;
    }





    public synchronized void reset() {
        currentAppUser = null;
        PreferenceManager.getInstance().removeCurrentUserInfo();
    }

    public synchronized User getAppCurrentUserInfo() {
        if (currentAppUser == null || currentAppUser.getMUserName() == null) {

            String username = EMClient.getInstance().getCurrentUser();
            currentAppUser = new User(username);
            String nick = getCurrentUserNick();
            currentAppUser.setMUserNick((nick != null) ? nick : username);
        }
        return currentAppUser;
    }

    public boolean updateCurrentUserNickName(final String nickname) {
        userRegisterModel.updateUserNick(appContext, EMClient.getInstance().getCurrentUser(), nickname, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                boolean updateNick = false;
                if (result != null) {
                    Result resultFromJson = ResultUtils.getResultFromJson(result, User.class);
                    if (resultFromJson != null && resultFromJson.isRetMsg()) {
                        User user = (User) resultFromJson.getRetData();
                        if (user != null) {
                            updateNick = true;
                            setCurrentAppUserNick(user.getMUserNick());
                        }
                    }
                }
                appContext.sendBroadcast(new Intent(I.REQUEST_UPDATE_USER_NICK).putExtra(I.User.NICK, updateNick));
            }

            @Override
            public void onError(String error) {
                appContext.sendBroadcast(new Intent(I.REQUEST_UPDATE_USER_NICK).putExtra(I.User.NICK, false));
            }
        });
        return false;
    }

    public void uploadUserAvatar(File file) {

        userRegisterModel.updateAvatar(appContext, EMClient.getInstance().getCurrentUser(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                boolean isSuccess = false;
                if (result != null) {
                    Result resultFromJson = ResultUtils.getResultFromJson(result, User.class);
                    if (resultFromJson != null && resultFromJson.isRetMsg()) {
                        User user = (User) resultFromJson.getRetData();
                        if (user != null) {
                            isSuccess = true;
                            setCurrentAppUserAvatar(user.getAvatar());
                        }
                    }
                }
                appContext.sendBroadcast(new Intent(I.REQUEST_UPDATE_AVATAR).putExtra(I.Avatar.UPDATE_TIME, isSuccess));
            }

            @Override
            public void onError(String error) {
                appContext.sendBroadcast(new Intent(I.REQUEST_UPDATE_AVATAR).putExtra(I.Avatar.UPDATE_TIME, false));
            }
        });
    }

    public void asyncGetAppCurrentUserInfo() {
        userRegisterModel.loadUserInfo(appContext, EMClient.getInstance().getCurrentUser(), new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Result resultFromJson = ResultUtils.getResultFromJson(result, User.class);
                    if (resultFromJson != null && resultFromJson.isRetMsg()) {
                        User user = (User) resultFromJson.getRetData();
                        Log.i("UserProfileManager", user.toString());
                        if (user != null) {
                            updateCurrentAppUserInfo(user);
                        }
//                        setCurrentAppUserNick(user.getMUserNick());
//                        setCurrentAppUserAvatar(user.getAvatar());
//                        LiveHelper.getInstance().saveAppContact(user);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void updateCurrentAppUserInfo(User user) {
        currentAppUser = user;
        setCurrentAppUserNick(user.getMUserNick());
        setCurrentAppUserAvatar(user.getAvatar());
    }






    private void setCurrentAppUserNick(String nickname) {
        getAppCurrentUserInfo().setAvatar(nickname);
        PreferenceManager.getInstance().setCurrentUserNick(nickname);

    }

    private void setCurrentAppUserAvatar(String avatar) {
        getAppCurrentUserInfo().setAvatar(avatar);
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private void setCurrentUserAvatar(String avatar) {
        getAppCurrentUserInfo().setAvatar(avatar);
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private String getCurrentUserNick() {
        return PreferenceManager.getInstance().getCurrentUserNick();
    }

    private String getCurrentUserAvatar() {
        return PreferenceManager.getInstance().getCurrentUserAvatar();
    }

}
