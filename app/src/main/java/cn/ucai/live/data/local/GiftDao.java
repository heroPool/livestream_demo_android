package cn.ucai.live.data.local;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.User;

import java.util.List;
import java.util.Map;

import cn.ucai.live.data.model.Gift;

/**
 * Created by Administrator on 2017/4/14.
 * private Integer id;

 private String gname;

 private String gurl;

 private Integer gprice;

 public Integer getId() {
 return id;
 }
 */

public class GiftDao {
    public static final String TABLE_NAME = "Gifts";


//    public static final String USER_TABLE_NAME = "t_superwechat_user";
//    public static final String USER_COLUMN_NAME = "m_user_name";
//    public static final String USER_COLUMN_NICK = "m_user_nick";
//    public static final String USER_COLUMN_AVATAR_ID = "m_user_avatar_id";
//    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
//    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
//    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
//    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME = "m_user_avatar_lastupdate_time";


    public static final String COLUMN_NAME_ID = "gname";
    public static final String COLUMN_NAME_NICK = "gurl";
    public static final String COLUMN_NAME_AVATAR = "avatar";
    public Map<String, Gift> getAppContactList() {

        return LiveDBManager.getInstance().getAppContactList();
    }

    /**
     * delete a contact
     * @param username
     */
    public void deleteAppContact(String username){
        LiveDBManager.getInstance().deleteAppContact(username);
    }

    /**
     * save a contact
     * @param user
     */
    public void saveAppContact(User user){
        LiveDBManager.getInstance().saveAppContact(user);
    }

    public void saveContactList(List<EaseUser> contactList) {
        LiveDBManager.getInstance().saveContactList(contactList);
    }
}
