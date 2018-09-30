package ir.ghaza_khoonegi.www.khoonegibebar.Preference;

import android.content.Context;
import android.content.SharedPreferences;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FilterModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.SortModel;

public class SortPrefrence {

    private static final String PREF_SORT_FOOD="pref_sort_food";
    private static final String KEY_NEW_FOOD="key_new_food";
    private static final String KEY_LOWPRICE_FOOD="key_lowprice_food";
    private static final String KEY_HIGHTPRICE_FOOD="key_hightprice_food";
    private static final String KEY_HIGHTSTAR_FOOD="key_hightstar_food";

    private SharedPreferences sharedPreferences;


    public SortPrefrence(Context context){
        sharedPreferences=context.getSharedPreferences(PREF_SORT_FOOD,Context.MODE_PRIVATE);
    }
    public void saveSortFood(SortModel sortModel){
        if(sortModel!=null){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean(KEY_NEW_FOOD,sortModel.getRbNew());
            editor.putBoolean(KEY_LOWPRICE_FOOD,sortModel.getRbLowPay());
            editor.putBoolean(KEY_HIGHTPRICE_FOOD,sortModel.getRbhightPay());
            editor.putBoolean(KEY_HIGHTSTAR_FOOD,sortModel.getRbRating());
            editor.apply();
        }
    }
    public SortModel getSortFood(){
        SortModel sortModel=new SortModel();
        sortModel.setRbNew(sharedPreferences.getBoolean(KEY_NEW_FOOD,false));
        sortModel.setRbLowPay(sharedPreferences.getBoolean(KEY_LOWPRICE_FOOD,false));
        sortModel.setRbhightPay(sharedPreferences.getBoolean(KEY_HIGHTPRICE_FOOD,false));
        sortModel.setRbRating(sharedPreferences.getBoolean(KEY_HIGHTSTAR_FOOD,false));
        return sortModel;
    }
    public void clearPreference(){
        sharedPreferences.edit().clear().commit();
    }
}
