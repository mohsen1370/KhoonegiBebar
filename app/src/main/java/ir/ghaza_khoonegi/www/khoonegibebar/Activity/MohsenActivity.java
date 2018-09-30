package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class MohsenActivity extends AppCompatActivity {

    private ImageView imgBack;
    private Button btnCall;
    private Button btnTelegram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohsen);
        imgBack=(ImageView)findViewById(R.id.ic_back);
        btnCall=(Button)findViewById(R.id.btn_call);
        btnTelegram=(Button)findViewById(R.id.btn_telegram);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:09151568576"));
                startActivity(intent);;
            }
        });
        btnTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/bestpf"));
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
