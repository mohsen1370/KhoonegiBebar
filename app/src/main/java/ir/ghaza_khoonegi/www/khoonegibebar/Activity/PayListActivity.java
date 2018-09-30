package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.PayRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceFood;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServicePayRecycler;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.PayModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class PayListActivity extends AppCompatActivity {
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private PayRecyclerAdapter payAdapter;
    private ImageView imgBack;
    private TextView tvWarning;
    private ConstraintLayout clLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_list);
        context=this;
        view=findViewById(android.R.id.content);
        imgBack=(ImageView)findViewById(R.id.ic_back);
        tvWarning=(TextView)findViewById(R.id.tv_warning);
        clLoginRegister=(ConstraintLayout)findViewById(R.id.cl_signin_register);
        //////////////////////////////////


        /////////////////////////////////
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        clLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayListActivity.this,LoginActivity.class));
            }
        });
    }
    private void setRecyPay(){
        LoginPreference loginPreference=new LoginPreference(context);
        UserModel userModel=loginPreference.getUser();
        JSONObject joPhoneNumber=new JSONObject();
        JSONArray jaPhoneNumber=new JSONArray();
        try {
            joPhoneNumber.put("txtphonenumber",userModel.getPhonenumber());
            jaPhoneNumber.put(0,joPhoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiServicePayRecycler apiService = new ApiServicePayRecycler(context);
        apiService.getPayItem(jaPhoneNumber, new ApiServicePayRecycler.OnPayReceived() {
            @Override
            public void onReceived(List<PayModel> payModels, Boolean erorrswip) {
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_pay);
                recyclerView.setVisibility(View.VISIBLE);
                payAdapter = new PayRecyclerAdapter(context, payModels);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(payAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                if (erorrswip){
                    ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        LoginPreference loginPreference=new LoginPreference(context);
        UserModel userModel=loginPreference.getUser();
        if (!userModel.getPhonenumber().equals("null")){
            tvWarning.setVisibility(View.GONE);
            clLoginRegister.setVisibility(View.GONE);
            setRecyPay();
        }else {
            tvWarning.setVisibility(View.VISIBLE);
            clLoginRegister.setVisibility(View.VISIBLE);
        }
    }
}
