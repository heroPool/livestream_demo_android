package cn.ucai.live.data.local;

import android.os.DropBoxManager;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.User;

import java.util.List;
import java.util.Map;

import cn.ucai.live.data.model.Gift;

/**
 * Created by Administrator on 2017/4/14.
 * private Integer id;
 * <p>
 * private String gname;
 * <p>
 * private String gurl;
 * <p>
 * private Integer gprice;
 * <p>
 * public Integer getId() {
 * return id;
 * }
 */

public class GiftDao {
    public static final String GIFT_TABLE_NAME = "t_live_gift";
    public static final String GIFT_COLUMN_ID = "m_gift_id";
    public static final String GIFT_COLUMN_NAME = "m_gift_name";
    public static final String GIFT_COLUMN_URL = "m_gift_url";
    public static final String GIFT_COLUMN_PRICE = "m_gift_price";


    public GiftDao() {

    }

    /**
     * @param giftList
     */
    public void saveAppGiftList(List<Gift> giftList) {
        LiveDBManager.getInstance().saveAppGiftList(giftList);

    }

    public Map<Integer, Gift> getAppList() {
        return LiveDBManager.getInstance().getAppGiftList();
    }
}
