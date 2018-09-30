package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.CheckEmail;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceEditProfile;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class ProfileActivity extends AppCompatActivity {

    private UserModel userModel=new UserModel();
    private LoginPreference loginPreference;
    private ConstraintLayout btnConstraintLayout;
    private EditText editTextName;
    private EditText editTextFamily;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private Boolean checkUpdate;
    private Context context;
    private View view;
    private ApiServiceEditProfile apiServiceEditProfile;
    private ImageView ivBackButton;
    private Boolean resultCheckEmail;
    private Boolean resultEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        context=this;
        view=(View)findViewById(R.id.cl_profile);

        ivBackButton=(ImageView)findViewById(R.id.ic_back_profile);
        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginPreference=new LoginPreference(this);
        userModel=loginPreference.getUser();

        editTextName=(EditText)findViewById(R.id.et_name_profile);
        editTextFamily=(EditText)findViewById(R.id.et_family_profile);
        editTextEmail=(EditText)findViewById(R.id.et_email_profile);
        editTextAddress=(EditText)findViewById(R.id.et_address_profile);
        setEdittext();

        btnConstraintLayout=(ConstraintLayout)findViewById(R.id.cl_btnsubmit_profile);
        btnConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEmail checkEmail1=new CheckEmail();
                resultCheckEmail=checkEmail1.email(editTextEmail);
                if(checkEmpty()==false){
                    ToastMessage.showToast(context,"لطفا تمام مقادیر را وارد کنید.");
                }
                else if(resultCheckEmail==true){
                    editProfileServer();
                }
                else {
                    ToastMessage.showToast(context,"فرمت ایمیل نادرست است.");
                }

            }
        });

    }
    public Boolean checkEmpty(){
        resultEmpty=true;
        if(editTextName.getText().toString().equals("")){
            resultEmpty=false;
        }
        else if(editTextFamily.getText().toString().equals("")){
            resultEmpty=false;
        }
        else if(editTextEmail.getText().toString().equals("")){
            resultEmpty=false;
        }
        else if(editTextAddress.getText().toString().equals("")){
            resultEmpty=false;
        }
        return resultEmpty;
    }
    public void setEdittext(){

        editTextName.setText(userModel.getName());
        editTextFamily.setText(userModel.getFamily());
        editTextEmail.setText(userModel.getEmail());
        editTextAddress.setText(userModel.getAddress());

        editTextName.requestFocus();
    }
    public void editProfilePreference(){

        userModel.setName(editTextName.getText().toString());
        userModel.setFamily(editTextFamily.getText().toString());
        userModel.setEmail(editTextEmail.getText().toString());
        userModel.setAddress(editTextAddress.getText().toString());
        loginPreference.saveUser(userModel);
    }
    public void editProfileServer(){

        userModel=loginPreference.getUser();
        apiServiceEditProfile=new ApiServiceEditProfile(context);
        JSONObject jsonObjectRequest=new JSONObject();
        try {
            jsonObjectRequest.put("txtphonenumber",userModel.getPhonenumber());
            jsonObjectRequest.put("txtname",editTextName.getText());
            jsonObjectRequest.put("txtfamily",editTextFamily.getText());
            jsonObjectRequest.put("txtemail",editTextEmail.getText());
            jsonObjectRequest.put("txtaddress",editTextAddress.getText());

            apiServiceEditProfile.editProfile(view, jsonObjectRequest, new ApiServiceEditProfile.OnEditProfile() {
                @Override
                public void onEdit(Boolean resultBool) {
                    if(resultBool==true){
                        editProfilePreference();
                        setEdittext();
                        ToastMessage.showToast(context,"اطلاعات با موفقیت تغییر یافت.");

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
}
