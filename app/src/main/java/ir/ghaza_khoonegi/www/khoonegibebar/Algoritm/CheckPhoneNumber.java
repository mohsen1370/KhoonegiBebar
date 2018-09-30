package ir.ghaza_khoonegi.www.khoonegibebar.Algoritm;

import android.widget.EditText;

public class CheckPhoneNumber {

    private Boolean result=false;
    public  Boolean phoneNumber(EditText editText){

        String s=editText.getText().toString();
        if (s.length()==11){
            s=s.substring(0,2);
            if (s.equals("09")){
                result=true;
            }
            else {
                result=false;
            }
        }
        else{
            result=false;
        }
        return result;
    }
}
