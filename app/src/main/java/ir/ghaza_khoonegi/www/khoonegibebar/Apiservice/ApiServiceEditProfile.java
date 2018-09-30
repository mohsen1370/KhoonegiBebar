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

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;

public class ApiServiceEditProfile {

    private Context context;
    private Boolean resultBool;
    private String result="";
    public ApiServiceEditProfile(Context context){
        this.context=context;
    }
    public void editProfile(final View view, JSONObject requestjsonobject, final ApiServiceEditProfile.OnEditProfile onEditProfile){

        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/edit_profile.php", requestjsonobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result=response.getString("result");
                    if(result.equals("error")){
                        resultBool=false;
                    }
                    else if(result.equals("done")) {
                        resultBool=true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,  "error try"  , Toast.LENGTH_LONG).show();
                }
                onEditProfile.onEdit(resultBool);
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
    public interface OnEditProfile{
        void onEdit(Boolean resultBool);
    }
}
