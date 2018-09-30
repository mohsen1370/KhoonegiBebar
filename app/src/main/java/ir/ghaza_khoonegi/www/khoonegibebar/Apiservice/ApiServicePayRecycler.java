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

public class ApiServicePayRecycler {
    private Context context;
    public  ApiServicePayRecycler(Context context){
        this.context=context;
    }
    public void getPayItem(final JSONArray jsonArray, final ApiServicePayRecycler.OnPayReceived onPayReceived){

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/showpay.php", jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<PayModel> payModels=new ArrayList<>();
                for (int i = 0; i <response.length() ; i++) {
                    PayModel payModel=new PayModel();
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        payModel.setIdPay(jsonObject.getInt("id"));
                        payModel.setSumpricePay(jsonObject.getInt("sumpay"));
                        payModel.setDatepay(jsonObject.getString("date"));
                        payModels.add(payModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onPayReceived.onReceived(payModels,false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                List<ChefModel> chefModels=new ArrayList<>();
                List<PayModel> payModels=new ArrayList<>();
                onPayReceived.onReceived(payModels,true);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnPayReceived{
        void onReceived(List<PayModel> payModels, Boolean erorrswip);
    }
}
