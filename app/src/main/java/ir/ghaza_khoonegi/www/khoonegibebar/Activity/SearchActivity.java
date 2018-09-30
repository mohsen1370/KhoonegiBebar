package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.FoodRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceFood;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Fragment.CartFragment;
import ir.ghaza_khoonegi.www.khoonegibebar.Fragment.FoodFragment;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class SearchActivity extends AppCompatActivity {

    private Context context;
    private FoodRecyclerAdapter foodAdapter;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private ConstraintLayout clIconSerch;
    private ImageView imgclear;
    private ImageView imgBack;
    private ProgressBar progressBar;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.GrayTheme);
        setContentView(R.layout.activity_search);
        context=this;
        view=findViewById(android.R.id.content);
        etSearch = (EditText)findViewById(R.id.et_search);
        clIconSerch=(ConstraintLayout)findViewById(R.id.cl_frame_iconsearch);
        imgclear = (ImageView) findViewById(R.id.img_clear);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_search);
        imgBack=(ImageView)findViewById(R.id.ic_back_search);
        ///////////////////////////////////


        /////////////////////////////////
        imgclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setText("");
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!etSearch.getText().toString().equals("")){
                    imgclear.setVisibility(View.VISIBLE);
                }else {
                    imgclear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    progressBar.setVisibility(View.VISIBLE);
                    String testEmpity=etSearch.getText().toString().replace(" ","");
                    if(!testEmpity.equals("")){
                        setRecySearch(etSearch.getText().toString());
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        ToastMessage.showSnackbar(view,"لطفاً ابتدا متن مورد نظر را وارد فرمایید.");
                    }
                    return true;
                }
                return false;
            }
        });
        clIconSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String testEmpity=etSearch.getText().toString().replace(" ","");
                if(!testEmpity.equals("")){
                    setRecySearch(etSearch.getText().toString());
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    ToastMessage.showSnackbar(view,"لطفاً ابتدا متن مورد نظر را وارد فرمایید.");
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    public void setRecySearch(String foodName){
        ApiServiceFood apiService = new ApiServiceFood(context);
        JSONObject joFoodName=new JSONObject();
        JSONArray jaFoodName=new JSONArray();

        try {
            joFoodName.put("txtfoodname",etSearch.getText().toString());
            jaFoodName.put(0,joFoodName);
            apiService.getFoodItem(false,null,jaFoodName,new ApiServiceFood.OnFoodReceived() {
                @Override
                public void onReceived(final List<FoodModel> foodModels, Boolean erorrswip) {
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
                    foodAdapter = new FoodRecyclerAdapter(context, foodModels);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(foodAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    if (erorrswip){
                        ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");
                    }
                    foodAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
