package ir.ghaza_khoonegi.www.khoonegibebar.Algoritm;

import android.widget.EditText;

public class CheckEmail {
    private Boolean result;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public Boolean email(EditText editText){

        String s=editText.getText().toString().trim();
        if(s.matches(emailPattern) && s.length() > 0){
            result =true;
        }
        else {
            result=false;
        }

        return result;
    }
}
