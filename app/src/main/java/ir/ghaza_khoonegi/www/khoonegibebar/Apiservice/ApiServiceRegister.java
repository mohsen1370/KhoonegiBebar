package ir.ghaza_khoonegi.www.khoonegibebar.Apiservice;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;

public class ApiServiceRegister {
    private Context context;
    private Boolean resultBool;
    private String result="";
    public ApiServiceRegister(Context context){
        this.context=context;
    }
    public void registerUser(final View view, JSONObject requestjsonobject, final ApiServiceRegister.OnRegisterUser onRegisterUser){

        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/register.php", requestjsonobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result=response.getString("result");
                    if(result.equals("error") || result.equals("phonenumberinvalid")){
                        resultBool=false;
                    }
                    else if (result.equals("existnumber") ){
                        ToastMessage.showToast(context,"این شماره موبایل قبلا ثبت شده است.");
                        resultBool=false;
                    }
                    else if(result.equals("done")) {
                        resultBool=true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,  "error try"  , Toast.LENGTH_LONG).show();
                }
                onRegisterUser.onRegister(resultBool);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnRegisterUser{
        void onRegister(Boolean resultBool);
    }
}
