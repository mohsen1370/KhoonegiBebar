package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.CheckEmail;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.CheckPhoneNumber;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceEditProfile;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceRegister;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNmae;
    private EditText editTextFamily;
    private EditText editTextPhoneNumber;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private ConstraintLayout buttonRegisterRegister;
    private Boolean empty;
    private Boolean resultEmpty;
    private Context context;
    private View view;
    private CheckPhoneNumber checkPhoneNumber;
    private CheckEmail checkEmail;
    private ImageView imageViewBack;
    private ApiServiceRegister apiServiceRegister;
    private LoginPreference loginPreference;
    private UserModel userModel=new UserModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context=this;
        view=(View)findViewById(R.id.cl_register);
        loginPreference=new LoginPreference(this);

        editTextNmae=(EditText)findViewById(R.id.et_name_register);
        editTextFamily=(EditText)findViewById(R.id.et_family_register);
        editTextPhoneNumber=(EditText)findViewById(R.id.et_phonenumber_register);
        editTextPassword=(EditText)findViewById(R.id.et_password_register);
        editTextRepeatPassword=(EditText)findViewById(R.id.et_repeatepassword_register);
        editTextEmail=(EditText)findViewById(R.id.et_email_register);
        editTextAddress=(EditText)findViewById(R.id.et_address_register);

        imageViewBack=(ImageView)findViewById(R.id.ic_back_register);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonRegisterRegister=(ConstraintLayout)findViewById(R.id.cl_btnsubmit_register);
        buttonRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                refreshColotEditText();
                checkPhoneNumber=new CheckPhoneNumber();
                checkEmail=new CheckEmail();
                resultEmpty=checkEmpty();
                if(resultEmpty==false){
                    ToastMessage.showToast(context,"لطفا قسمت های مشخص شده را پر کنید.");
                }
                else if(checkPhoneNumber.phoneNumber(editTextPhoneNumber)==false){
                    ToastMessage.showToast(context,"فرمت شماره موبایل اشتباه است.");
                    editTextPhoneNumber.setTextColor(Color.RED);
                }
                else if(editTextPassword.length()<6){
                    ToastMessage.showToast(context,"طول رمز عبور باید بیشتر از شش کاراکتر باشد.");
                    editTextPassword.setTextColor(Color.RED);
                }
                else if(!(editTextPassword.getText().toString().equals(editTextRepeatPassword.getText().toString()))){
                    ToastMessage.showToast(context,"تکرار رمز عبور با رمز عبور برابر نیست.");
                    editTextRepeatPassword.setTextColor(Color.RED);
                }
                else if(checkEmail.email(editTextEmail)==false){
                    ToastMessage.showToast(context,"فرمت ایمیل اشتباه است.");
                    editTextEmail.setTextColor(Color.RED);
                }
                else if(editTextAddress.getText().toString().length()<10){
                    ToastMessage.showToast(context,"لطفا آدرس خود را کامل وارد کنید.");
                    editTextAddress.setTextColor(Color.RED);
                }
                else {
                    sendFormRegister();
                }
            }
        });


        editTextNmae.requestFocus();
    }

    public Boolean checkEmpty(){

        empty=true;
        if(editTextNmae.getText().toString().equals("")){
            editTextNmae.setHintTextColor(Color.RED);
            empty=false;
        }
        if(editTextFamily.getText().toString().equals("")){
            editTextFamily.setHintTextColor(Color.RED);
            empty=false;
        }
        if(editTextPhoneNumber.getText().toString().equals("")){
            editTextPhoneNumber.setHintTextColor(Color.RED);
            empty=false;
        }
        if(editTextPassword.getText().toString().equals("")){
            editTextPassword.setHintTextColor(Color.RED);
            empty=false;
        }
        if(editTextRepeatPassword.getText().toString().equals("")){
            editTextRepeatPassword.setHintTextColor(Color.RED);
            empty=false;
        }
        if(editTextEmail.getText().toString().equals("")){
            editTextEmail.setHintTextColor(Color.RED);
            empty=false;
        }
        if(editTextAddress.getText().toString().equals("")){
            editTextAddress.setHintTextColor(Color.RED);
            empty=false;
        }
        return empty;
    }


    public void refreshColotEditText(){
        editTextNmae.setTextColor(Color.parseColor("#4a4a4a"));
        editTextNmae.setHintTextColor(Color.parseColor("#c2c2c2"));

        editTextFamily.setTextColor(Color.parseColor("#4a4a4a"));
        editTextFamily.setHintTextColor(Color.parseColor("#c2c2c2"));

        editTextPhoneNumber.setTextColor(Color.parseColor("#4a4a4a"));
        editTextPhoneNumber.setHintTextColor(Color.parseColor("#c2c2c2"));

        editTextPassword.setTextColor(Color.parseColor("#4a4a4a"));
        editTextPassword.setHintTextColor(Color.parseColor("#c2c2c2"));


        editTextRepeatPassword.setTextColor(Color.parseColor("#4a4a4a"));
        editTextRepeatPassword.setHintTextColor(Color.parseColor("#c2c2c2"));

        editTextEmail.setTextColor(Color.parseColor("#4a4a4a"));
        editTextEmail.setHintTextColor(Color.parseColor("#c2c2c2"));

        editTextAddress.setTextColor(Color.parseColor("#4a4a4a"));
        editTextAddress.setHintTextColor(Color.parseColor("#c2c2c2"));
    }
    public void sendFormRegister(){

        apiServiceRegister=new ApiServiceRegister(context);
        JSONObject jsonObjectRequest=new JSONObject();
        try {
            jsonObjectRequest.put("txtphonenumber",editTextPhoneNumber.getText());
            jsonObjectRequest.put("txtname",editTextNmae.getText());
            jsonObjectRequest.put("txtfamily",editTextFamily.getText());
            jsonObjectRequest.put("txtpassword",editTextPassword.getText());
            jsonObjectRequest.put("txtemail",editTextEmail.getText());
            jsonObjectRequest.put("txtaddress",editTextAddress.getText());

            apiServiceRegister.registerUser(view, jsonObjectRequest, new ApiServiceRegister.OnRegisterUser() {
                @Override
                public void onRegister(Boolean resultBool) {
                    if(resultBool==true){
                        addToPrefrence();
                    }
                    else {
                        ToastMessage.showToast(context,"خطا!");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addToPrefrence(){

        userModel.setPhonenumber(editTextPhoneNumber.getText().toString());
        userModel.setName(editTextNmae.getText().toString());
        userModel.setFamily(editTextFamily.getText().toString());
        userModel.setEmail(editTextEmail.getText().toString());
        userModel.setAddress(editTextAddress.getText().toString());
        loginPreference.saveUser(userModel);
        ToastMessage.showToast(context,"اطلاعات با موفقیت ثبت شد.");
        finish();
    }
}
