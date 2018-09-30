package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.EnglishNumber;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServicePay;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;
import ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase.CartSqlite;
import saman.zamani.persiandate.PersianDate;

public class FinalPayActivity extends AppCompatActivity {
    private Context context;
    private  Spinner spinnerMonth;
    private  Spinner spinnerDay;
    private  Spinner spinnerClock;
    private  Spinner spinnerMin;
    private TextView textViewprice;
    private PersianPrice persianPrice;
    private EditText editTextAddress;
    private EditText etDisciption;
    private ConstraintLayout constraintLayoutPayment;
    private ImageView imageViewBack;
    private View view;
    ////////////////////////////////
    private int sumprice;
    private String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_pay);
        context=this;
        findViewById(android.R.id.content);
        //////////////////////////////
        persianPrice=new PersianPrice();
        PersianDate pdate = new PersianDate();

        textViewprice=(TextView)findViewById(R.id.txt_sumprice_finalpay);
        spinnerMonth =(Spinner)findViewById(R.id.sp_month_finalpay);
        spinnerDay =(Spinner)findViewById(R.id.sp_day_finalpay);
        spinnerClock=(Spinner)findViewById(R.id.sp_clock_finalpay);
        spinnerMin=(Spinner)findViewById(R.id.sp_min_finalpay);
        editTextAddress=(EditText)findViewById(R.id.et_address_finalpay);
        etDisciption=(EditText)findViewById(R.id.et_discription_finalpay);
        constraintLayoutPayment=(ConstraintLayout)findViewById(R.id.cl_payment);
        imageViewBack=(ImageView)findViewById(R.id.ic_back_finalpay);
        ////////////////////////////////
        Intent intent=getIntent();
        String p="";
        p=String.valueOf(intent.getIntExtra("SUM_PRICE",0));
        if(p.equals("0")){
            ToastMessage.showToast(context,"خطا! مبلغ صفر");
            //finish();
        }
        p=persianPrice.getPrice(p);
        p=p+" تومان";
        textViewprice.setText(p);
        ////////////////////////////
        setMonthSpiner();
        setDaySpiner(pdate.getShDay(),false);
        setClockSpiner();
        setMinSpiner();
        setAddress();
        ////////////////////////////
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        constraintLayoutPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFild()){

                    savePay();

                }else{
                    ToastMessage.showToast(context,"لطفا تمامی اطلاعات را وارد کنید.");
                }
            }
        });
    }
    public void setMonthSpiner(){
        String monthstr;
        int monthint;
        int dayint;


        final PersianDate pdate = new PersianDate();
        final List<String> monthArray = new ArrayList<String>();
        monthArray.add("ماه...");

        monthint=pdate.getShMonth();
        monthstr=pdate.monthName();
        monthArray.add(monthstr);

        dayint=pdate.getShDay();
        dayint=dayint+31;
        pdate.setShDay(dayint);
        monthstr=pdate.monthName();
        if(!monthArray.get(0).equals(monthstr)){
            monthArray.add(monthstr);
        }

        final ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(
                this,R.layout.my_spinner_style,monthArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#FF4081"));

                }
                else {
                    tv.setTextColor(Color.BLACK);

                }
                return view;
            }
        };

        dataAdapterMonth.setDropDownViewResource(R.layout.my_spinner_style);
        spinnerMonth.setAdapter(dataAdapterMonth);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();
                if(index==0){
                    ((TextView) spinnerMonth.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorHint));
                }
                else if(index==1){
                    setDaySpiner(pdate.getShDay(),true);
                }
                else if (index==2){
                    setDaySpiner(0,true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    public void setDaySpiner(int day, final Boolean s){
        String daystr;
        int dayint;
        PersianDate pdate = new PersianDate();
        final List<String> dayArray = new ArrayList<String>();
        dayArray.add("روز...");
        if(s==true && day!=0){
            for (int i=day+1;i<32;i++){
                dayArray.add(persianPrice.getNumber(String.valueOf(i)));
            }
        }
        if(s==true && day==0){
            pdate.setShDay(pdate.getShDay()+31);
            for (int i=day+1;i<pdate.getShDay();i++){
                dayArray.add(persianPrice.getNumber(String.valueOf(i)));
            }
        }


        final ArrayAdapter<String> dataAdapterDay = new ArrayAdapter<String>(
                this,R.layout.my_spinner_style,dayArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#FF4081"));
                    if (s==false){
                        tv.setText("لطفا ابتدا ماه را انتخاب کنید");
                    }
                }
                else {
                    tv.setTextColor(Color.BLACK);

                }
                return view;
            }
        };

        dataAdapterDay.setDropDownViewResource(R.layout.my_spinner_style);
        spinnerDay.setAdapter(dataAdapterDay);
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();
                if(index==0){
                    ((TextView) spinnerDay.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorHint));
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
    public void setClockSpiner(){

        final List<String> clockArray = new ArrayList<String>();
        clockArray.add("ساعت...");
        for(int i=14;i<19;i++) {
            clockArray.add(persianPrice.getNumber(String.valueOf(i)));
        }

        final ArrayAdapter<String> dataAdapterClock = new ArrayAdapter<String>(
                this,R.layout.my_spinner_style,clockArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {
                    return false;
                }
                else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.parseColor("#FF4081"));
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapterClock.setDropDownViewResource(R.layout.my_spinner_style);
        spinnerClock.setAdapter(dataAdapterClock);
        spinnerClock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();
                if(index==0){
                    ((TextView) spinnerClock.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorHint));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    public void setMinSpiner(){

        final List<String> minArray = new ArrayList<String>();
        minArray.add("دقیقه...");
        for(int i=0;i<=50;i+=10) {
            minArray.add(persianPrice.getNumber(String.valueOf(i)));
        }

        final ArrayAdapter<String> dataAdapterMin = new ArrayAdapter<String>(
                this,R.layout.my_spinner_style,minArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {
                    return false;
                }
                else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.parseColor("#FF4081"));
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapterMin.setDropDownViewResource(R.layout.my_spinner_style);
        spinnerMin.setAdapter(dataAdapterMin);
        spinnerMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = parentView.getSelectedItemPosition();
                if(index==0){
                    ((TextView) spinnerMin.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorHint));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    public void setAddress(){
        LoginPreference loginPreference=new LoginPreference(context);
        UserModel userModel=loginPreference.getUser();
        editTextAddress.setText(userModel.getAddress());
    }
    public Boolean checkFild(){
        if (editTextAddress.getText().length()<10){
            return false;
        }
        else if (spinnerMonth.getSelectedItem().toString().equals("ماه...")){
            return false;
        }
        else if (spinnerDay.getSelectedItem().toString().equals("روز...")){
            return false;
        }
        else if (spinnerClock.getSelectedItem().toString().equals("ساعت...")){
            return false;
        }
        else if (spinnerMin.getSelectedItem().toString().equals("دقیقه...")){
            return false;
        }
        return true;
    }
    public void cleareSqlite(){
        CartSqlite cartSqlite=new CartSqlite(context);
        cartSqlite.removeAllRow();
    }
    private int getSumPrice(){
        CartSqlite cartSqlite=new CartSqlite(context);
        return cartSqlite.returnSumPrice();
    }
    private String getPhoneNumber(){
        LoginPreference loginPreference=new LoginPreference(context);
        UserModel userModel=loginPreference.getUser();
        return userModel.getPhonenumber();
    }
    private List<FoodModel> getfoodCart(){
        CartSqlite cartSqlite=new CartSqlite(context);
        List<FoodModel> foodModels=new ArrayList<>();
        foodModels=cartSqlite.getFood();
        return foodModels;
    }
    private void savePay(){
        ApiServicePay apiServicePay=new ApiServicePay(context);
        final JSONObject joSumPrice=new JSONObject();
        final JSONObject joPhoneNumber=new JSONObject();
        final JSONObject joDetailsPay=new JSONObject();
        final JSONArray jaPay=new JSONArray();
        sumprice=getSumPrice();
        phonenumber=getPhoneNumber();
        //jsonarray [0] sum price
        try {
            joSumPrice.put("txtsumprice",sumprice);
            jaPay.put(0,joSumPrice);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //jsonarray [1] phone number
        try {
            joPhoneNumber.put("txtphonenumber",phonenumber);
            jaPay.put(1,joPhoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //jsonarray [2] details pay
        try {
            EnglishNumber englishNumber=new EnglishNumber();
            String day=String.valueOf(englishNumber.getNumber(spinnerDay.getSelectedItem().toString()));
            String clock=String.valueOf(englishNumber.getNumber(spinnerClock.getSelectedItem().toString()));
            String min=String.valueOf(englishNumber.getNumber(spinnerMin.getSelectedItem().toString()));

            joDetailsPay.put("txtaddress",editTextAddress.getText().toString());
            joDetailsPay.put("txtdiscription",etDisciption.getText().toString());
            joDetailsPay.put("txtmonth",spinnerMonth.getSelectedItem().toString());
            joDetailsPay.put("txtday",day);
            joDetailsPay.put("txtclock",clock);
            joDetailsPay.put("txtmin",min);
            jaPay.put(2,joDetailsPay);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //jsonarray [3...n] list foods in cart
        List<FoodModel> foodModels=new ArrayList<>();
        FoodModel foodModel=new FoodModel();
        foodModels=getfoodCart();
        for (int i = 0; i<foodModels.size(); i++) {
            JSONObject joFoodCart=new JSONObject();
            foodModel=foodModels.get(i);
            try {
                joFoodCart.put("txtfoodid",foodModel.getId());
                joFoodCart.put("txtfoodcount",foodModel.getNumberfood());
                jaPay.put(i+3,joFoodCart);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //send jarray for server
        apiServicePay.savePay(jaPay, new ApiServicePay.OnPayReceived() {
            @Override
            public void onReceived(Boolean erorrswip) {
                if(erorrswip){
                    ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");
                }
            }
        });
        CartActivity.fa.finish();
        cleareSqlite();
        finish();
    }
}
