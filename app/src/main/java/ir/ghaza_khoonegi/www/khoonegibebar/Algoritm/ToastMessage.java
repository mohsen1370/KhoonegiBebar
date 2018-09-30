package ir.ghaza_khoonegi.www.khoonegibebar.Algoritm;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ToastMessage {
    public static void showToast(Context context,String title){
        Toast toast=Toast.makeText(context,  title  , Toast.LENGTH_LONG);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(10);
        toast.show();
    }
    public static void showSnackbar(View view, String title){

        Snackbar snackbar=Snackbar.make(view, title, Snackbar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(),ViewCompat.LAYOUT_DIRECTION_RTL);
        TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextSize(10);
        snackbar.show();
    }
}
