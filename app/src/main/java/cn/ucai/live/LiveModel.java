package cn.ucai.live;

import android.content.Context;

import java.util.List;
import java.util.Map;

import cn.ucai.live.data.local.GiftDao;
import cn.ucai.live.data.model.Gift;
import cn.ucai.live.utils.PreferenceManager;


public class LiveModel {
    protected Context context = null;
    GiftDao dao = null;

    public LiveModel(Context ctx) {
        context = ctx;
        PreferenceManager.init(context);
        dao = new GiftDao();
    }

    public void setCurrentUserName(String username) {
        PreferenceManager.getInstance().setCurrentUserName(username);
    }

    public String getCurrentUsernName() {
        return PreferenceManager.getInstance().getCurrentUsername();
    }

    public void setGift(List<Gift> list) {
        dao.saveAppGiftList(list);

    }

    public Map<Integer, Gift> getGiftList() {
        return dao.getAppList();
    }
}
