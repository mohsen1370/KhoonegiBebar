package ir.ghaza_khoonegi.www.khoonegibebar.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;

public class LoginPreference {

    private static final String PREF_USER_LOGIN="pref_user_login";
    private static final String KEY_PHONE_NUMBER="key_phone_number";
    private static final String KEY_NAME="key_name";
    private static final String KEY_FAMILY="key_family";
    private static final String KEY_EMAIL="key_email";
    private static final String KEY_ADDRESS="key_address";

    private SharedPreferences sharedPreferences;


    public LoginPreference(Context context){
        sharedPreferences=context.getSharedPreferences(PREF_USER_LOGIN,Context.MODE_PRIVATE);
    }
    public void exitUser(){
        sharedPreferences.edit().clear().apply();
    }
    public void saveUser(UserModel userModel){
        if(userModel!=null){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(KEY_PHONE_NUMBER,userModel.getPhonenumber());
            editor.putString(KEY_NAME,userModel.getName());
            editor.putString(KEY_FAMILY,userModel.getFamily());
            editor.putString(KEY_EMAIL,userModel.getEmail());
            editor.putString(KEY_ADDRESS,userModel.getAddress());
            editor.apply();
        }
    }
    public UserModel getUser(){
        UserModel userModel=new UserModel();
        userModel.setPhonenumber(sharedPreferences.getString(KEY_PHONE_NUMBER,"null"));
        userModel.setName(sharedPreferences.getString(KEY_NAME,"null"));
        userModel.setFamily(sharedPreferences.getString(KEY_FAMILY,"null"));
        userModel.setEmail(sharedPreferences.getString(KEY_EMAIL,"null"));
        userModel.setAddress(sharedPreferences.getString(KEY_ADDRESS,"null"));
        return userModel;
    }
}
