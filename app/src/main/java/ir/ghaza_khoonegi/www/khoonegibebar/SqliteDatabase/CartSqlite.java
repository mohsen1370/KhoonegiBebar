package ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.EnglishNumber;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;

public class CartSqlite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="db_khoonegibebar";
    public static final int DATABASE_VERSION=1;
    public static final String CART_TABLE_NAME="tbl_cart";

    public static final String COL_ID="col_id";
    public static final String COL_FOODTITLE="col_foodtitle";
    public static final String COL_CHEFTITLE="col_cheftitle";
    public static final String COL_PRICETITLE="col_pricetitle";
    public static final String COL_NUMBERFOOD="col_numberfood";
    public static final String COL_SUMPRICE="col_sumprice";

    public static final String SQL_COMMAND_CART_TABLE="CREATE TABLE IF NOT EXISTS "+CART_TABLE_NAME+"("+COL_ID+" INTEGER, "+COL_FOODTITLE+" TINYTEXT, "+COL_CHEFTITLE+" TINYTEXT, " +COL_PRICETITLE+ " INTEGER, "+COL_NUMBERFOOD+ " INTEGER, "+COL_SUMPRICE+" INTEGER );";

    Context context;
    public CartSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(SQL_COMMAND_CART_TABLE);
        }
        catch (SQLException e){
            ToastMessage.showToast(context,e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addToCartSQL(FoodModel foodModel){

        ContentValues cv=new ContentValues();
        cv.put(COL_ID,foodModel.getId());
        cv.put(COL_FOODTITLE,foodModel.getFoodtitle());
        cv.put(COL_CHEFTITLE,foodModel.getCheftitle());
        cv.put(COL_PRICETITLE,foodModel.getPricetitle());
        cv.put(COL_NUMBERFOOD,foodModel.getNumberfood());
        cv.put(COL_SUMPRICE,foodModel.getNumberfood()*foodModel.getPricetitle());

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert(CART_TABLE_NAME,null,cv);
        sqLiteDatabase.close();
    }
    public int checkCountRowFood(int id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from tbl_cart where col_id="+id,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.close();
            sqLiteDatabase.close();
            return 1;
        }
        else {
            cursor.close();
            sqLiteDatabase.close();
            return 0;
        }

    }
    public int returnSumPrice(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        try {
            Cursor cursor=sqLiteDatabase.rawQuery("select sum("+COL_SUMPRICE+") from tbl_cart",null);
            cursor.moveToFirst();
            int sumPrice=cursor.getInt(0);
            cursor.close();
            sqLiteDatabase.close();
            return sumPrice;
        }catch (Exception e){
            return 0;
        }
    }
    public int ReturnCount(int id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        try {
            Cursor cursor=sqLiteDatabase.rawQuery("select * from tbl_cart where col_id="+id,null);
            cursor.moveToFirst();
            int count=cursor.getInt(4);
            cursor.close();
            sqLiteDatabase.close();
            return count;
        }catch (Exception e){
            return 0;
        }
    }
    public int ReturnPrice(int id){

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        try {
            Cursor cursor=sqLiteDatabase.rawQuery("select * from tbl_cart where col_id="+id,null);
            cursor.moveToFirst();
            int price=cursor.getInt(3);
            cursor.close();
            sqLiteDatabase.close();
            return price;
        }catch (Exception e){
            return 0;
        }
    }
    public int getAllCountInCart(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        try {
            Cursor cursor=sqLiteDatabase.rawQuery("select sum(col_numberfood) from tbl_cart",null);
            cursor.moveToFirst();
            int count=cursor.getInt(0);
            cursor.close();
            sqLiteDatabase.close();
            return count;
        }catch (Exception e){
            return 0;
        }
    }
    public void removeRow(int id){

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.delete(CART_TABLE_NAME, COL_ID+" = "+id, null);
        sqLiteDatabase.close();
    }
    public int setRemoveCountandReturnCount(int id)
    {

        ContentValues cv = new ContentValues();
        int count=ReturnCount(id);
        int price=ReturnPrice(id);
        count=count-1;
        cv.put(COL_NUMBERFOOD,count);
        cv.put(COL_SUMPRICE,count*price);

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.update(CART_TABLE_NAME, cv, COL_ID+" = "+id, null);
        sqLiteDatabase.close();
        return count;
    }
    public int setAddCountandReturnCount(int id)
    {
        ContentValues cv = new ContentValues();
        int count=ReturnCount(id);
        int price=ReturnPrice(id);
        count=count+1;
        cv.put(COL_NUMBERFOOD,count);
        cv.put(COL_SUMPRICE,count*price);

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.update(CART_TABLE_NAME, cv, COL_ID+" = "+id, null);
        sqLiteDatabase.close();
        return count;
    }
    public void removeAllRow(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        sqLiteDatabase.delete(CART_TABLE_NAME, null, null);
        sqLiteDatabase.close();
    }
    public List<FoodModel> getFood(){
        List<FoodModel> foodModels=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from tbl_cart",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            while (!cursor.isAfterLast()){
                FoodModel foodModel=new FoodModel();
                foodModel.setId(cursor.getInt(0));
                foodModel.setFoodtitle(cursor.getString(1));
                foodModel.setCheftitle(cursor.getString(2));
                foodModel.setPricetitle(cursor.getInt(3));
                foodModel.setNumberfood(cursor.getInt(4));
                foodModels.add(foodModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return foodModels;
    }

}
