package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.CheckPhoneNumber;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceUser;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {

    private ImageView imgback;
    private EditText editTextPass;
    private EditText editTextPhoneNumber;
    private ConstraintLayout btnsubmit;
    private ApiServiceUser apiServiceUser;
    private String pass;
    private String namefamily;
    private View view;
    private Context context;
    private LoginPreference loginPreference;
    private TextView openActivityTextView;
    UserModel userModel=new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;
        view=(View)findViewById(R.id.cl_login);
        imgback=(ImageView)findViewById(R.id.ic_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editTextPass=(EditText)findViewById(R.id.et_password);
        editTextPhoneNumber=(EditText)findViewById(R.id.et_username);
        editTextPass.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                sendFormLogin(editTextPass,editTextPhoneNumber);
                return false;
            }
        });
        btnsubmit=(ConstraintLayout)findViewById(R.id.cl_btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckPhoneNumber checkPhoneNumber=new CheckPhoneNumber();
                Boolean result=checkPhoneNumber.phoneNumber(editTextPhoneNumber);
                if(result==true){
                    sendFormLogin(editTextPass,editTextPhoneNumber);
                }
                else {
                    ToastMessage.showToast(context,"فرمت شماره موبایل صحیح نمی باشد.");
                }
            }
        });
        openActivityTextView=(TextView)findViewById(R.id.open_register);
        openActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }
    public void sendFormLogin(final EditText editTextPass, EditText editTextPhoneNumber){

        //Toast.makeText(context,editTextPhoneNumber.getText()+"\n"+editTextPass.getText(),LENGTH_LONG).show();
        apiServiceUser=new ApiServiceUser(context);
        JSONObject jsonObjectRequest=new JSONObject();
        try {
            jsonObjectRequest.put("txtphonenumber",editTextPhoneNumber.getText());
            jsonObjectRequest.put("txtpassword",editTextPass.getText());
            apiServiceUser.signIn(view,jsonObjectRequest, new ApiServiceUser.OnSingnIn() {
                @Override
                public void onReceived(UserModel userModel, Boolean success) {
                    if (success==true){
                        pass=editTextPass.getText().toString();
                        namefamily=userModel.getName()+" "+userModel.getFamily()+" "+"خوش آمدید.";
                        if(pass.equals(userModel.getPassword())){

                            loginPreference=new LoginPreference(context);
                            loginPreference.saveUser(userModel);
                            ToastMessage.showToast(context,namefamily);
                            finish();
                        }
                        else {
                            ToastMessage.showToast(context,"اطلاعات کاربری نادرست است.");
                        }
                    }
                    else {
                        ToastMessage.showToast(context,"اطلاعات کاربری نادرست است.");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
