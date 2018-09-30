package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.PayDetailsRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServicePayDetailsRecycler;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServicePayRecycler;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.PayModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class PayDetailsActivity extends AppCompatActivity {
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private PayDetailsRecyclerAdapter payAdapter;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_details);
        context=this;
        view=findViewById(android.R.id.content);
        imgBack=(ImageView)findViewById(R.id.ic_back);
        ///////////////////////////////
        setRecyPayDetails();
        //////////////////////////////
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setRecyPayDetails(){

        JSONObject joPayId=new JSONObject();
        JSONArray jaPayid=new JSONArray();
        Intent intent=getIntent();
        try {
            joPayId.put("txtpayid",intent.getIntExtra("ID_PAY",0));
            jaPayid.put(joPayId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiServicePayDetailsRecycler apiService = new ApiServicePayDetailsRecycler(context);
        apiService.getPayDetailsItem(jaPayid, new ApiServicePayDetailsRecycler.OnPayDetailsReceived() {
            @Override
            public void onReceived(List<FoodModel> foodModels, Boolean erorrswip) {
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_paydetails);
                payAdapter = new PayDetailsRecyclerAdapter(context, foodModels);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(payAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                if (erorrswip){
                    ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");
                }
            }
        });
    }
}
