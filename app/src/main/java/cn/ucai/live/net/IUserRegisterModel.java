package cn.ucai.live.net;

import android.content.Context;

import java.io.File;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface IUserRegisterModel {
    void register(Context context, String username, String nick, String password, OnCompleteListener<String> listener);

    void login(Context context, String username, String password, OnCompleteListener<String> listener);

    void unregister(Context context, String username, OnCompleteListener<String> listener);

    void loadUserInfo(Context context, String username, OnCompleteListener<String> listener);

    void updateUserNick(Context context, String username, String usernick, OnCompleteListener<String> listener);

    void updateAvatar(Context context, String username, File file, OnCompleteListener<String> listener);

    void addContact(Context cOntext, String username, String cname, OnCompleteListener<String> listener);

    void loadCOntact(Context context, String username, OnCompleteListener<String> listener);

    void delContact(Context context, String username, String cname, OnCompleteListener<String> listener);
}
