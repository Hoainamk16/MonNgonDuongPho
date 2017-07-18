package trinhhoainam.tttn.com.monngonduongpho.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import trinhhoainam.tttn.com.monngonduongpho.item.ItemFood;

/**
 * Created by HoaiNam on 24/02/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {
    public static String DATA_NAME = "MON_NGON.sqlite";
    public static String TABLE_NAME = "MON_AN";
    public SQLiteDatabase db;

    public DataBaseHandler(Context context) {
        super(context, DATA_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<ItemFood> getAllListFood() {
        List<ItemFood> listFood = new ArrayList<ItemFood>();
        String query = "SELECT * FROM MON_AN";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        listFood.clear();
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String title = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            String address = cursor.getString(3);
            String timer = cursor.getString(4);
            String pric = cursor.getString(5);
            String content = cursor.getString(6);
            int fav = cursor.getInt(9);
            String mLatitude = cursor.getString(10);
            String mLongitude = cursor.getString(11);

            listFood.add(new ItemFood(_id, image, title, address, timer, pric, content, fav, mLatitude, mLongitude));
        }
        cursor.close();
        return listFood;
    }


    public int UpdateFavorites(int id, int fav) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_favorites", fav);
        int i = db.update(TABLE_NAME, contentValues, "_id = ?", new String[]{String.valueOf(id)});
        return i;
    }

    public List<ItemFood> getAreaFood(int areaFood) {
        ArrayList<ItemFood> listFood = new ArrayList<ItemFood>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_area_code = ?", new String[]{String.valueOf(areaFood)}, null, null, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String title = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            String address = cursor.getString(3);
            String timer = cursor.getString(4);
            String pric = cursor.getString(5);
            String content = cursor.getString(6);
            int fav = cursor.getInt(9);
            String mLatitude = cursor.getString(10);
            String mLongitude = cursor.getString(11);
            listFood.add(new ItemFood(_id, image, title, address, timer, pric, content, fav, mLatitude, mLongitude));
        }
        return listFood;
    }

    public List<ItemFood> getFoodCode(String nameColumn, int paramter) {
        ArrayList<ItemFood> listFood = new ArrayList<ItemFood>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, nameColumn + " = ?", new String[]{String.valueOf(paramter)}, null, null, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String title = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            String address = cursor.getString(3);
            String timer = cursor.getString(4);
            String pric = cursor.getString(5);
            String content = cursor.getString(6);
            int fav = cursor.getInt(9);
            String mLatitude = cursor.getString(10);
            String mLongitude = cursor.getString(11);
            listFood.add(new ItemFood(_id, image, title, address, timer, pric, content, fav, mLatitude, mLongitude));
        }
        return listFood;
    }
}
