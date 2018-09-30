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

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;

public class ApiServicePay {

    private Context context;
    public ApiServicePay(Context context){
        this.context=context;
    }
    public void savePay(JSONArray jsonArray,final ApiServicePay.OnPayReceived onPayReceived){

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/savepay.php", jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                String result;
                JSONObject jsonObject= null;
                try {
                    jsonObject = response.getJSONObject(0);
                    result=jsonObject.getString("result");
                    if(result.equals("done")){
                        ToastMessage.showToast(context,"کاربر گرامی سفارش شما با موفقیت ثبت شد.");
                    }else {
                        ToastMessage.showToast(context,"خطا در ثبت سفارش!");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastMessage.showToast(context,"خطا در ثبت سفارش!");
                }
                onPayReceived.onReceived(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onPayReceived.onReceived(true);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnPayReceived{
        void onReceived(Boolean erorrswip);
    }

}
