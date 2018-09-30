package ir.ghaza_khoonegi.www.khoonegibebar.Apiservice;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FilterModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.SortModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.FilterPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.SortPrefrence;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class ApiServiceFood {
    private Context context;
    private JSONArray jsonArray;
    public ApiServiceFood(Context context){
        this.context=context;
    }
    public void getFoodItem(Boolean j,final JSONArray jaChefName,final JSONArray jaFoodName,final OnFoodReceived onFoodReceived){
        if (j){
            jsonArray=jaChefName;
        }else {
            jsonArray=jaFoodName;
        }
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/foodlist.php", jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<FoodModel> foodModels=new ArrayList<>();
                FilterPreference filterPreference=new FilterPreference(context);
                final FilterModel filterModel=filterPreference.getFilterFood();
                SortPrefrence sortPrefrence=new SortPrefrence(context);
                final SortModel sortModel=sortPrefrence.getSortFood();
                for (int i = 0; i <response.length() ; i++) {
                    final FoodModel foodModel=new FoodModel();
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        foodModel.setId(jsonObject.getInt("id"));
                        foodModel.setFoodtitle(jsonObject.getString("foodname"));
                        foodModel.setCheftitle(jsonObject.getString("chefname"));
                        foodModel.setPricetitle(jsonObject.getInt("foodprice"));
                        foodModel.setFoodimage(jsonObject.getString("foodimgurl").replace(" ",""));
                        foodModel.setMaterial(jsonObject.getString("material"));
                        foodModel.setCook(jsonObject.getString("cook"));
                        foodModel.setGroup(jsonObject.getString("group"));
                        foodModel.setRateFood((float) jsonObject.getDouble("avgrate"));
                        if(filterModel.getCbAllFood()==true){
                            foodModels.add(foodModel);
                        }else {
                            if(filterModel.getCbKhoreshFood()){
                                if(jsonObject.getString("group").equals("خورشت")){
                                    foodModels.add(foodModel);
                                }
                            }
                            if(filterModel.getCbKababFood()){
                                if(jsonObject.getString("group").equals("کباب")){
                                    foodModels.add(foodModel);
                                }
                            }
                            if(filterModel.getCbKhorakFood()){
                                if(jsonObject.getString("group").equals("خوراک")){
                                    foodModels.add(foodModel);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.sort(foodModels, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        FoodModel p1 = (FoodModel) o1;
                        FoodModel p2 = (FoodModel) o2;
                        if(sortModel.getRbLowPay()){
                            return Integer.valueOf(p1.getPricetitle()).compareTo(p2.getPricetitle());
                        }else if(sortModel.getRbhightPay()){
                            return Integer.valueOf(p2.getPricetitle()).compareTo(p1.getPricetitle());
                        }else if (sortModel.getRbRating()){
                            return Float.valueOf(p2.getRateFood()).compareTo(p1.getRateFood());
                        }
                        return Integer.valueOf(p2.getId()).compareTo(p1.getId());
                    }
                });
                onFoodReceived.onReceived(foodModels,false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                List<FoodModel> foodModels=new ArrayList<>();
                onFoodReceived.onReceived(foodModels,true);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnFoodReceived{
        void onReceived(List<FoodModel> foodModels,Boolean erorrswip);
    }
}
