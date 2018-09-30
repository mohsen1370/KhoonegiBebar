package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.ChefRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.CommRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceComment;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.CommentModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.R;
import ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase.CartSqlite;

public class DetailsFoodActivity extends AppCompatActivity {

    private View view;
    private Context context;
    private TextView tvFoodNameTop;
    private TextView tvFoodName;
    private ImageView imgFood;
    private TextView tvPrice;
    private Button btnAddToCart;
    private Button btnShowCount;
    private Button btnReduceFromCart;
    private TextView tvMaterial;
    private TextView tvCook;
    private TextView tvChefName;
    private TextView tvGroup;
    private ImageView imgBack;
    private RatingBar ratingBar;
    private EditText etComment;
    private Button btnSubmitComm;
    private RecyclerView rcvComment;
    private RecyclerView recyclerView;
    private CommRecyclerAdapter commRecyclerAdapter;
    ////////////////////////////
    private int id;
    private String foodName;
    private String foodImageUrl;
    private int foodPrice;
    private String material;
    private String cook;
    private String chefName;
    private String group;
    private String count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.GrayTheme);
        setContentView(R.layout.activity_details_food);
        context=this;
        view=findViewById(android.R.id.content);
        tvFoodNameTop=(TextView)findViewById(R.id.txt_namefood_topdetails);
        tvFoodName=(TextView)findViewById(R.id.txt_foodname_details);
        imgFood=(ImageView)findViewById(R.id.img_food_details);
        tvPrice=(TextView)findViewById(R.id.txt_price_details);
        btnAddToCart=(Button) findViewById(R.id.btn_addtocart_details);
        btnShowCount=(Button)findViewById(R.id.btn_numberfood_details);
        btnReduceFromCart=(Button)findViewById(R.id.btn_reducefromcart_details);
        tvMaterial=(TextView)findViewById(R.id.txt_materialtext);
        tvCook=(TextView)findViewById(R.id.txt_cooktext);
        tvChefName=(TextView)findViewById(R.id.txt_cheftext);
        tvGroup=(TextView)findViewById(R.id.txt_grouptext);
        imgBack=(ImageView) findViewById(R.id.ic_back_details_food);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar_comment);
        etComment=(EditText)findViewById(R.id.et_comment);
        btnSubmitComm=(Button)findViewById(R.id.btn_submitcomm);
        rcvComment=(RecyclerView)findViewById(R.id.rcv_comment_fooddetails);

        //////////////////////////////////
        Intent intent=getIntent();
        id=intent.getIntExtra("ID_FOOD",0);
        foodName=intent.getStringExtra("NAME_FOOD");
        foodImageUrl=intent.getStringExtra("IMAGE_FOOD");
        foodPrice=intent.getIntExtra("PRICE",0);
        material=intent.getStringExtra("MATERIAL");
        cook=intent.getStringExtra("COOK");
        chefName=intent.getStringExtra("CHEF_NAME");
        group=intent.getStringExtra("GROUP");
        //////////////////////////////////
        setPage();
        setRecy();
        /////////////////////////////////
        final CartSqlite cartSqlite=new CartSqlite(context);
        final PersianPrice persianPrice=new PersianPrice();
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
        btnReduceFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFromCart();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmitComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitComment();
            }
        });

    }
    public void setPage(){
        PersianPrice persianPrice=new PersianPrice();
        CartSqlite cartSqlite=new CartSqlite(context);
        count=String.valueOf(cartSqlite.ReturnCount(id));
        count=persianPrice.getNumber(count);
        tvFoodNameTop.setText(foodName);
        tvFoodName.setText(foodName);
        Picasso.with(context).load(foodImageUrl).placeholder(R.drawable.background_food_item).error(R.drawable.background_food_item).into(imgFood);
        tvPrice.setText(persianPrice.getPrice(String.valueOf(foodPrice))+" تومان");
        tvMaterial.setText(material);
        tvCook.setText(cook);
        tvChefName.setText(chefName);
        tvGroup.setText(group);
        btnShowCount.setText(count);
    }
    public void removeFromCart(){
        int count=0;
        String strCount="";
        CartSqlite cartSqlite=new CartSqlite(context);
        PersianPrice persianPrice=new PersianPrice();
        count=cartSqlite.ReturnCount(id);
        if(count==1){

            count=cartSqlite.setRemoveCountandReturnCount(id);
            strCount=persianPrice.getNumber(String.valueOf(count));
            btnShowCount.setText(strCount);
            cartSqlite.removeRow(id);

        }else if(count>1){
            count=cartSqlite.setRemoveCountandReturnCount(id);
            strCount=persianPrice.getNumber(String.valueOf(count));
            btnShowCount.setText(strCount);
        }
    }
    public void addToCart(){
        int count=0;
        String strCount="";
        CartSqlite cartSqlite=new CartSqlite(context);
        PersianPrice persianPrice=new PersianPrice();

        int check=cartSqlite.checkCountRowFood(id);
        if(check==0){
            FoodModel foodModel=new FoodModel();
            foodModel.setId(id);
            foodModel.setFoodtitle(foodName);
            foodModel.setCheftitle(chefName);
            foodModel.setPricetitle(foodPrice);
            foodModel.setNumberfood(1);
            cartSqlite.addToCartSQL(foodModel);
            count=cartSqlite.ReturnCount(id);
            strCount=persianPrice.getNumber(String.valueOf(count));
            btnShowCount.setText(strCount);
        }
        else {
            count=cartSqlite.setAddCountandReturnCount(id);
            strCount=persianPrice.getNumber(String.valueOf(count));
            btnShowCount.setText(strCount);
        }
    }
    private void submitComment(){
        LoginPreference loginPreference=new LoginPreference(context);
        UserModel userModel=loginPreference.getUser();
        String phoneNumber=userModel.getPhonenumber();
        if(!phoneNumber.equals("null")){
            if(etComment.getText().toString().equals("") || ratingBar.getRating()==0){
                ToastMessage.showSnackbar(view,"لطفاً امتیاز و نظر خود را وارد کنید.");
            }else {
                saveComment(phoneNumber);
            }



        }else {
            ToastMessage.showSnackbar(view,"کاربر گرامی ابتدا وارد حساب کاربری خود شوید.");
        }
    }
    private Boolean saveComment(String phonenumber){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("foodid",id);
            jsonObject.put("phonenumber",phonenumber);
            jsonObject.put("txtcomment",etComment.getText().toString());
            jsonObject.put("rate",ratingBar.getRating());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, "http://ghaza-khoonegi.ir/savecomment.php", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("result").equals("faild")){
                        ToastMessage.showSnackbar(view,"خطا!\n لطفاً مجدد تلاش کنید.");
                    }else if(response.getString("result").equals("done")) {
                        etComment.setText("");
                        ratingBar.setRating(0);
                        ToastMessage.showSnackbar(view,"نظر با موفقیت ثبت شد بعد تایید نمایش خواهد داده شد.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
        return false;
    }
    private void setRecy(){

        ApiServiceComment apiServiceComment=new ApiServiceComment(context);
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            jsonObject.put("foodid",id);
            jsonArray.put(0,jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiServiceComment.showComment(jsonArray, new ApiServiceComment.OnCommentReceived() {
            @Override
            public void OnReceived(List<CommentModel> commentModels) {
                recyclerView = (RecyclerView) view.findViewById(R.id.rcv_comment_fooddetails);
                commRecyclerAdapter = new CommRecyclerAdapter(context, commentModels);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(commRecyclerAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        });
    }
}
