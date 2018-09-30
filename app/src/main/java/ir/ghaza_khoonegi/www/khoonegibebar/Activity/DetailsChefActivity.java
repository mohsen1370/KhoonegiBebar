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
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.ChefRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.FoodRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceChef;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceFood;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class DetailsChefActivity extends AppCompatActivity {

    private TextView tvChefName;
    private CircularImageView civChef;
    private RecyclerView recyclerView;
    private FoodRecyclerAdapter foodAdapter;
    private ImageView imgBack;
    private View view;
    private Context context;
    /////////////////////////
    private int idChef;
    private String nameChef;
    private String imageChef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.GrayTheme);
        setContentView(R.layout.activity_details_chef);
        context=this;
        view=findViewById(android.R.id.content);
        tvChefName=(TextView)findViewById(R.id.txt_namechef_topdetails);
        civChef=(CircularImageView)findViewById(R.id.img_Chef_details);
        imgBack=(ImageView)findViewById(R.id.ic_back_details_chef);
        ////////////////////////////////////
        Intent intent=getIntent();
        idChef=intent.getIntExtra("ID_CHEF",0);
        imageChef=intent.getStringExtra("IMAGE_CHEF");
        nameChef=intent.getStringExtra("NAME_CHEF");
        /////////////////////////////////////
        setPage();
        setRecyFoods();
        ////////////////////////////////////
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setPage(){
        tvChefName.setText("سرآشپز "+nameChef);
        Picasso.with(context).load(imageChef).placeholder(R.drawable.chef_user_error).error(R.drawable.chef_user_error).into(civChef);

    }
    private void setRecyFoods(){
        ApiServiceFood apiService = new ApiServiceFood(context);
        final JSONObject joChefId=new JSONObject();
        final JSONArray jaChefId=new JSONArray();
        try {
            joChefId.put("txtchefid",idChef);
            jaChefId.put(0,joChefId);
            apiService.getFoodItem(true,jaChefId,null,new ApiServiceFood.OnFoodReceived() {
                @Override
                public void onReceived(final List<FoodModel> foodModels, Boolean erorrswip) {

                    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_chefdetails);
                    foodAdapter = new FoodRecyclerAdapter(context, foodModels);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(foodAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    if (erorrswip){
                        ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
