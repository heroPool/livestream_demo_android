package cn.ucai.live.net;

import android.content.Context;

import java.io.File;

import cn.ucai.live.I;
import cn.ucai.live.utils.OkHttpUtils;


/**
 * Created by Administrator on 2017/3/29.
 */

public class UserRegisterModel implements IUserRegisterModel {
    @Override
    public void register(Context context, String username, String nick, String password, OnCompleteListener listener) {
        OkHttpUtils<String> okHttpUtils = new OkHttpUtils<>(context);
        okHttpUtils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.NICK, nick)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.PASSWORD, password)
                .targetClass(String.class)
                .post()
                .execute(listener);

    }

    @Override
    public void login(Context context, String username, String password, OnCompleteListener listener) {
        OkHttpUtils<String> okHttpUtils = new OkHttpUtils<>(context);
        okHttpUtils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.PASSWORD, password)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void unregister(Context context, String username, OnCompleteListener listener) {
        OkHttpUtils<String> okHttpUtils = new OkHttpUtils<>(context);
        okHttpUtils.setRequestUrl(I.REQUEST_UNREGISTER)
                .addParam(I.User.USER_NAME, username)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void loadUserInfo(Context context, String username, OnCompleteListener<String> listener) {
        OkHttpUtils<String> okHttpUtils = new OkHttpUtils<>(context);
        okHttpUtils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME, username)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void updateUserNick(Context context, String username, String usernick, OnCompleteListener<String> listener) {
        OkHttpUtils<String> okhttp = new OkHttpUtils<>(context);
        okhttp.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.NICK, usernick)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void updateAvatar(Context context, String username, File file, OnCompleteListener<String> listener) {
        OkHttpUtils<String> okhttps = new OkHttpUtils<>(context);
        okhttps.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, username)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    @Override
    public void addContact(Context context, String username, String cname, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CONTACT)
                .addParam(I.Contact.USER_NAME, username)
                .addParam(I.Contact.CU_NAME, cname)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void loadCOntact(Context context, String username, OnCompleteListener<String> listener) {
        OkHttpUtils<String> okhttps = new OkHttpUtils<>(context);
        okhttps.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME, username)
                .targetClass(String.class)
                .execute(listener);

    }

    @Override
    public void delContact(Context context, String username, String cname, OnCompleteListener<String> listener) {
        OkHttpUtils<String> okhttps = new OkHttpUtils<>(context);
        okhttps.setRequestUrl(I.REQUEST_DELETE_CONTACT)
                .addParam(I.Contact.USER_NAME, username)
                .addParam(I.Contact.CU_NAME, cname)
                .targetClass(String.class)
                .execute(listener);

    }
}
