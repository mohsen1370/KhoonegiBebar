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

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.CommentModel;

public class ApiServiceComment {
    private Context context;
    public ApiServiceComment(Context context){
        this.context=context;
    }
    public void showComment(JSONArray jsonArray,final ApiServiceComment.OnCommentReceived onCommentReceived){

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/showcomment.php", jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                List<CommentModel> commentModels=new ArrayList<>();
                for (int i=0;i<response.length();i++){
                    CommentModel commentModel=new CommentModel();
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        commentModel.setNameUser(jsonObject.getString("name"));
                        commentModel.setRate(jsonObject.getInt("points"));
                        commentModel.setTextComment(jsonObject.getString("comment"));
                        commentModels.add(commentModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    onCommentReceived.OnReceived(commentModels);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
    public interface OnCommentReceived{
        void OnReceived(List<CommentModel> commentModels);
    }
}
