package com.hyphenate.easeui.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.User;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     *
     */
    public static void setUserAvatar(Context context, String avatarPath, ImageView imageView) {


        if (avatarPath!=null) {

            try {
                int avatarResId = Integer.parseInt(avatarPath);
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(avatarPath).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }



    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNick() != null) {
                textView.setText(user.getNick());
            } else {
                textView.setText(username);
            }
        }
    }

    public static void setCurrentNick(TextView textView) {
        setAppUserNick(EMClient.getInstance().getCurrentUser(), textView);
    }

    /**
     * get User according username
     *
     * @param username
     * @return
     */
    public static User getAppUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getAppUser(username);

        return null;
    }

    public static void setAppUserNick(String username, TextView textView) {
        if (textView != null) {
            User user = getAppUserInfo(username);
            setAppUserNick(user, textView);
        }
    }

    public static void setAppUserNick(User user, TextView textview) {
        if (textview != null && user != null) {
            if (user.getMUserNick() != null) {
                textview.setText(user.getMUserNick());

            } else {
                textview.setText(user.getMUserName());

            }
        }
    }


    public static void setCurrentAvatar(Context context, ImageView imageView) {
        setAppUserAvatar(context, EMClient.getInstance().getCurrentUser(), imageView);
    }

    public static void setAppUserAvatar(Context context, String username, ImageView imageView) {
        User user = getAppUserInfo(username);
        setAppUserAvatar(context, user, imageView);
    }

    public static void setAppUserAvatar(Context context, User user, ImageView imageView) {
        if (user != null) {
            setUserAvatar(context, user.getAvatar(), imageView);
        } else {
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }

}
