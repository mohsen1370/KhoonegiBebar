package ir.ghaza_khoonegi.www.khoonegibebar.Preference;

import android.content.Context;
import android.content.SharedPreferences;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FilterModel;

public class FilterPreference {
    private static final String PREF_FILTER_FOOD="pref_filter_food";
    private static final String KEY_ALL_FOOD="key_all_food";
    private static final String KEY_KHORESH_FOOD="key_khoresh_food";
    private static final String KEY_KABAB_FOOD="key_kabab_food";
    private static final String KEY_KHORAK_FOOD="key_khorak_food";

    private SharedPreferences sharedPreferences;


    public FilterPreference(Context context){
        sharedPreferences=context.getSharedPreferences(PREF_FILTER_FOOD,Context.MODE_PRIVATE);
    }
    public void saveFilterFood(FilterModel filterModel){
        if(filterModel!=null){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean(KEY_ALL_FOOD,filterModel.getCbAllFood());
            editor.putBoolean(KEY_KHORESH_FOOD,filterModel.getCbKhoreshFood());
            editor.putBoolean(KEY_KABAB_FOOD,filterModel.getCbKababFood());
            editor.putBoolean(KEY_KHORAK_FOOD,filterModel.getCbKhorakFood());
            editor.apply();
        }
    }
    public FilterModel getFilterFood(){
        FilterModel filterModel=new FilterModel();
        filterModel.setCbAllFood(sharedPreferences.getBoolean(KEY_ALL_FOOD,true));
        filterModel.setCbKhoreshFood(sharedPreferences.getBoolean(KEY_KHORESH_FOOD,false));
        filterModel.setCbKababFood(sharedPreferences.getBoolean(KEY_KABAB_FOOD,false));
        filterModel.setCbKhorakFood(sharedPreferences.getBoolean(KEY_KHORAK_FOOD,false));
        return filterModel;
    }
    public void clearPreference(){
        sharedPreferences.edit().clear().commit();
    }
}
