package ir.ghaza_khoonegi.www.khoonegibebar.Apiservice;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Activity.LoginActivity;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class ApiServiceUser {
    private Context context;
    private Boolean foundnumber;
    private String phonenum="";
    private LoginPreference loginPreference;
    UserModel userModel=new UserModel();
    public ApiServiceUser(Context context){
        this.context=context;
    }
    public void signIn(final View view, JSONObject requestjsonobject, final OnSingnIn onSingnIn){

        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/login.php", requestjsonobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    phonenum=response.getString("phonenumber");
                    if(phonenum.equals("not found")){
                        foundnumber=false;
                    }
                    else {
                        foundnumber=true;
                        userModel.setPhonenumber(response.getString("phonenumber"));
                        userModel.setPassword(response.getString("password"));
                        userModel.setName(response.getString("name"));
                        userModel.setFamily(response.getString("family"));
                        userModel.setEmail(response.getString("email"));
                        userModel.setAddress(response.getString("address"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,  "error try"  , Toast.LENGTH_LONG).show();
                }
                onSingnIn.onReceived(userModel,foundnumber);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String title = "لطفا اتصال خود به اینترنت را بررسی کنید.";
                Snackbar snackbar=Snackbar.make(view, title, Snackbar.LENGTH_LONG);
                ViewCompat.setLayoutDirection(snackbar.getView(),ViewCompat.LAYOUT_DIRECTION_RTL);
                snackbar.show();

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnSingnIn{
        void onReceived(UserModel userModel,Boolean success);
    }
}
