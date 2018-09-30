package ir.ghaza_khoonegi.www.khoonegibebar.Apiservice;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.PayModel;

public class ApiServicePayDetailsRecycler {
    private Context context;
    public  ApiServicePayDetailsRecycler(Context context){
        this.context=context;
    }
    public void getPayDetailsItem(final JSONArray jsonArray, final ApiServicePayDetailsRecycler.OnPayDetailsReceived onPayDetailsReceived){

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/paydetails.php", jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<FoodModel> foodModels=new ArrayList<>();
                for (int i = 0; i <response.length() ; i++) {
                    FoodModel foodModel=new FoodModel();
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        foodModel.setFoodtitle(jsonObject.getString("foodname"));
                        foodModel.setNumberfood(jsonObject.getInt("number"));
                        foodModel.setPricetitle(jsonObject.getInt("foodprice"));
                        foodModel.setFoodimage(jsonObject.getString("foodimgurl"));
                        foodModels.add(foodModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onPayDetailsReceived.onReceived(foodModels,false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                List<FoodModel> foodModels=new ArrayList<>();
                onPayDetailsReceived.onReceived(foodModels,true);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnPayDetailsReceived{
        void onReceived(List<FoodModel> foodModels, Boolean erorrswip);
    }
}
