package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.CartRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Fragment.CartFragment;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;
import ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase.CartSqlite;

public class CartActivity extends AppCompatActivity {

    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    public static Activity fa;
    private static TextView textViewSumPrice;
    private Context context;
    private ImageView imageViewBack;
    private ConstraintLayout constraintLayoutEndPay;
    private ConstraintLayout constraintLayoutOpenLogin;
    private UserModel userModel;
    private LoginPreference loginPreference;
    private ImageView imageViewClear;
    private RecyclerView recyclerView;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context=this;fa=this;
        textViewSumPrice=(TextView)findViewById(R.id.txt_sumprice_cart);
        loginPreference=new LoginPreference(context);
        constraintLayoutEndPay=(ConstraintLayout)findViewById(R.id.cl_btnsubmit_cart);
        constraintLayoutOpenLogin=(ConstraintLayout)findViewById(R.id.cl_login_cart);
        imageViewClear=(ImageView)findViewById(R.id.ic_delete_cart);
        imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogClear();
            }
        });
        ceckLogin();


        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_cart,new CartFragment());
        fragmentTransaction.commit();

        setSumPrice();
        imageViewBack=(ImageView)findViewById(R.id.ic_back_cart);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        constraintLayoutOpenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,LoginActivity.class));
            }
        });
        constraintLayoutEndPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartSqlite cartSqlite=new CartSqlite(context);
                if (cartSqlite.returnSumPrice()>0){
                    Intent intent=new Intent(CartActivity.this, FinalPayActivity.class);
                    intent.putExtra("SUM_PRICE",cartSqlite.returnSumPrice());
                    startActivity(intent);
                }else {
                    ToastMessage.showToast(context,"سبد خرید خالی است.");
                }
            }
        });
    }
    public void openDialogClear(){
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_clear_cart);
        Button btnNo=(Button)dialog.findViewById(R.id.btn_no_clear);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Button btnYes=(Button)dialog.findViewById(R.id.btn_yes_clear);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartSqlite cartSqlite=new CartSqlite(context);
                cartSqlite.removeAllRow();
                setSumPrice();
                initRecyclerview();
                dialog.cancel();
            }
        });
        dialog.show();
    }
    public void initRecyclerview(){
        CartSqlite cartSqlite=new CartSqlite(context);
        List<FoodModel> foodModels=cartSqlite.getFood();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_cart);
        CartRecyclerAdapter cartRecyclerAdapter=new CartRecyclerAdapter(context,foodModels);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(cartRecyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    public void setSumPrice(){
        CartSqlite cartSqlite=new CartSqlite(context);
        PersianPrice persianPrice=new PersianPrice();

        int sumPriceint=cartSqlite.returnSumPrice();
        String sumPricestr=persianPrice.getPrice(String.valueOf(sumPriceint));
        sumPricestr=sumPricestr+" تومان";
        textViewSumPrice.setText(sumPricestr);
    }
    public void ceckLogin(){
        userModel=loginPreference.getUser();
        String phonenumber=userModel.getPhonenumber();
        if (phonenumber.equals("null")){
            constraintLayoutEndPay.setVisibility(View.GONE);
            constraintLayoutOpenLogin.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayoutEndPay.setVisibility(View.VISIBLE);
            constraintLayoutOpenLogin.setVisibility(View.GONE);
        }
    }
    public static void setsumPrice(String p){
        textViewSumPrice.setText(p);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        ceckLogin();
    }

}
