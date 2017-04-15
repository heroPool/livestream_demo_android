package cn.ucai.live.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cn.ucai.live.LiveApplication;
import cn.ucai.live.data.model.Gift;

public class LiveDBManager {
    static private LiveDBManager dbMgr = new LiveDBManager();
    private DbOpenHelper dbHelper;

    private LiveDBManager() {
        dbHelper = DbOpenHelper.getInstance(LiveApplication.getInstance().getApplicationContext());
    }

    public static synchronized LiveDBManager getInstance() {
        if (dbMgr == null) {
            dbMgr = new LiveDBManager();
        }
        return dbMgr;
    }

    synchronized public void saveAppGiftList(List<Gift> giftList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(GiftDao.GIFT_TABLE_NAME, null, null);
            for (Gift gift : giftList) {
                ContentValues values = new ContentValues();
                if (gift.getId() != null) {
                    values.put(GiftDao.GIFT_COLUMN_ID, gift.getId());
                }
                if (gift.getGname() != null) {
                    values.put(GiftDao.GIFT_COLUMN_NAME, gift.getId());
                }
                if (gift.getGurl() != null) {
                    values.put(GiftDao.GIFT_COLUMN_URL, gift.getId());
                }
                if (gift.getGprice() != null) {
                    values.put(GiftDao.GIFT_COLUMN_PRICE, gift.getId());
                }
                db.replace(GiftDao.GIFT_TABLE_NAME, null, values);
            }
        }
    }

    synchronized public Map<Integer, Gift> getAppGiftList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Hashtable<Integer, Gift> gifts = new Hashtable<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + GiftDao.GIFT_TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                Gift gift = new Gift();
                gift.setId(cursor.getInt(cursor.getColumnIndex(GiftDao.GIFT_COLUMN_ID)));
                gift.setGname(cursor.getString(cursor.getColumnIndex(GiftDao.GIFT_COLUMN_NAME)));
                gift.setGprice(cursor.getInt(cursor.getColumnIndex(GiftDao.GIFT_COLUMN_PRICE)));
                gift.setGurl(cursor.getString(cursor.getColumnIndex(GiftDao.GIFT_COLUMN_URL)));
                gifts.put(gift.getId(), gift);
            }
            cursor.close();

        }
        return gifts;
    }
    synchronized public void closeDB() {
        if (dbHelper != null) {
            dbHelper.closeDB();

        }
        dbMgr = null;
    }
}
