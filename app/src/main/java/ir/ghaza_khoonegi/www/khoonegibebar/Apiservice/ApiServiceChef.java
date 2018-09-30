package ir.ghaza_khoonegi.www.khoonegibebar.Apiservice;

import android.app.DownloadManager;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;

public class ApiServiceChef {
    private Context context;
    public ApiServiceChef(Context context){
        this.context=context;
    }
    public void getChefItem(final OnChefReceived onChefReceived){

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, "http://ghaza-khoonegi.ir/cheflist.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<ChefModel> chefModels=new ArrayList<>();
                for (int i = 0; i <response.length() ; i++) {
                    ChefModel chefModel=new ChefModel();
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        chefModel.setId(jsonObject.getInt("id"));
                        chefModel.setChefname(jsonObject.getString("chefname"));
                        chefModel.setChefimage(jsonObject.getString("chefimageurl").replace(" ",""));
                        chefModels.add(chefModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                onChefReceived.onReceived(chefModels,false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                List<ChefModel> chefModels=new ArrayList<>();
                onChefReceived.onReceived(chefModels,true);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnChefReceived{
        void onReceived(List<ChefModel> chefModels,Boolean erorrswip);
    }
}
