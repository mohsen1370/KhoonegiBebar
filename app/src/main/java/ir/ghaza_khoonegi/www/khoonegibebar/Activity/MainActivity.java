package ir.ghaza_khoonegi.www.khoonegibebar.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.FoodRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.HomeViewpagerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceFood;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.UserModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.FilterPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.SortPrefrence;
import ir.ghaza_khoonegi.www.khoonegibebar.R;
import ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase.CartSqlite;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private long oldCurrentTimeMillis=0;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Animation anim;
    private ArrayList<Integer> icontablayout;
    private ArrayList<String> texttablayout;
    private LinearLayout newTab;
    private LinearLayout newTab2;
    private TextView txttab1;
    private TextView txttab2;
    private ArrayList<String> urlslide;
    private ArrayList<String> nameslide;
    private LoginPreference loginPreference;
    private UserModel userModel=new UserModel();
    private Context context;
    private ImageView imgCartIcon;
    private ImageView imgSearch;
    public static TextView numbrCartIcon;
    public static RelativeLayout rlNumberCart;
    private HomeViewpagerAdapter foodViewpagerAdapter;
    private FoodRecyclerAdapter foodAdapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginPreference=new LoginPreference(this);
        context=this;
        imgSearch=(ImageView)findViewById(R.id.img_search_toolbar);

        /////////////////////////////
        setCart();//cart
        setToolbar();//toolbar
        setViewPager();//viewpager
        setNavigationDrawer();//navigation drawer
        setAnimation();//animation
        setTabLayout();//tab layout
        setSlider();//slider
        ///////////////////////////
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });




    }

    private void setCart(){
        numbrCartIcon=(TextView)findViewById(R.id.txt_cart_number);
        rlNumberCart=(RelativeLayout)findViewById((R.id.rl_number_cart));
        imgCartIcon=(ImageView)findViewById(R.id.img_cart);
        imgCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivityForResult(i, 1);
            }
        });
        int count=0;
        CartSqlite cartSqlite=new CartSqlite(context);
        count=cartSqlite.getAllCountInCart();
        updateCounterCartIcon(count);
    }
    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final ImageView imghamber = (ImageView) findViewById(R.id.img_hamber);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerl);
        imghamber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
    }
    private void setViewPager(){
        tabLayout = (TabLayout) findViewById(R.id.tblmain);
        viewPager = (ViewPager) findViewById(R.id.viewpage_food_chef);
        foodViewpagerAdapter = new HomeViewpagerAdapter(getSupportFragmentManager());
        viewPager.setRotationY(180);
        viewPager.setAdapter(foodViewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setNavigationDrawer(){
        navView = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawerl);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_login:
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(MainActivity.this,CartActivity.class));
                        break;
                    case R.id.nav_pay:
                        startActivity(new Intent(MainActivity.this,PayListActivity.class));
                        break;
                    case R.id.nav_disigner:
                        startActivity(new Intent(MainActivity.this,MohsenActivity.class));
                        break;
                    case R.id.nav_web:
                        try {
                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://khoonegibebar.ir/"));
                            startActivity(myIntent);
                        } catch (ActivityNotFoundException e) {
                            ToastMessage.showToast(context,"خطا!");
                            e.printStackTrace();
                        }
                        break;
                    case R.id.nav_exit:
                        exitProfile();
                        break;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawers();
                    }
                }, 100);

                return true;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {


            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                setItemMenuNavigation();
            }
        });
    }
    private void setAnimation(){
        final ImageView iv = (ImageView) findViewById(R.id.imagenavigation);
        anim = AnimationUtils.loadAnimation(this, R.anim.up_drawer_header);
        iv.startAnimation(anim);
    }
    private void setTabLayout(){
        icontablayout = new ArrayList<>();
        icontablayout.add(R.mipmap.icon_chicken);
        icontablayout.add(R.mipmap.icon_chef);
        texttablayout = new ArrayList<>();
        texttablayout.add("غذا");
        texttablayout.add("آشپز");


        newTab = (LinearLayout) LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.custom_tab, null);
        tabLayout.getTabAt(0).setCustomView(newTab);

        newTab2 = (LinearLayout) LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.custom_tab2, null);
        tabLayout.getTabAt(1).setCustomView(newTab2);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#2a9134"));
        txttab1 = (TextView) findViewById(R.id.txt_tab1);
        txttab1.setTextColor(Color.parseColor("#2a9134"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                txttab1 = (TextView) findViewById(R.id.txt_tab1);
                txttab2 = (TextView) findViewById(R.id.txt_tab2);
                if (tab.getPosition() == 0) {
                    txttab1.setTextColor(Color.parseColor("#2a9134"));
                    txttab2.setTextColor(Color.parseColor("#000000"));
                } else if (tab.getPosition() == 1) {
                    txttab2.setTextColor(Color.parseColor("#2a9134"));
                    txttab1.setTextColor(Color.parseColor("#000000"));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setSlider(){
        SliderLayout mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        urlslide = new ArrayList<>();
        urlslide.add("http://www.ghaza-khoonegi.ir/images/Capture.jpg");
        urlslide.add("http://www.ghaza-khoonegi.ir/images/Capture1.jpg");

        nameslide = new ArrayList<>();
        nameslide.add("اسلاید1");
        nameslide.add("اسلاید2");

        for (int i = 0; i < (Integer) urlslide.size(); i++) {

            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(urlslide.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", nameslide.get(i));

            mDemoSlider.addSlider(textSliderView);
        }
    }
    private void refreshRecyclerview(){
        int id=0;
        ApiServiceFood apiService = new ApiServiceFood(context);
        apiService.getFoodItem(false,null,null,new ApiServiceFood.OnFoodReceived() {
            @Override
            public void onReceived(List<FoodModel> foodModels, Boolean erorrswip) {
                RecyclerView recyclerView;
                recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
                FoodRecyclerAdapter foodAdapter = new FoodRecyclerAdapter(context, foodModels);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(foodAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        });
    }

    //method for slide
    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //end method for slider


    public void setItemMenuNavigation(){
        userModel=loginPreference.getUser();
        String phonenumber=userModel.getPhonenumber();
        String name=userModel.getName();
        String family=userModel.getFamily();
        if (phonenumber.equals("null")){
            navView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navView.getMenu().findItem(R.id.nav_exit).setVisible(false);
        }
        else {
            navView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navView.getMenu().findItem(R.id.nav_profile).setVisible(true);
            navView.getMenu().findItem(R.id.nav_exit).setVisible(true);
            navView.getMenu().findItem(R.id.nav_profile).setTitle(name+" "+family);
        }
    }
    public void exitProfile(){
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_exit_profile);
        Button btnNo=(Button)dialog.findViewById(R.id.btn_no_exit);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        Button btnYes=(Button)dialog.findViewById(R.id.btn_yes_exit);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginPreference loginPreference=new LoginPreference(context);
                loginPreference.exitUser();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public static void updateCounterCartIcon(int value){
        PersianPrice persianPrise=new PersianPrice();
        try{
            if(value>0){
                numbrCartIcon.setVisibility(View.VISIBLE);
                rlNumberCart.setVisibility(View.VISIBLE);
                numbrCartIcon.setText(persianPrise.getNumber(String.valueOf(value)));

            }else {
                numbrCartIcon.setVisibility(View.INVISIBLE);
                rlNumberCart.setVisibility(View.INVISIBLE);
            }
        }
        catch (Exception ex){

        }
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        setCart();
        refreshRecyclerview();

    }
    @Override
    public void onBackPressed()
    {
        if (oldCurrentTimeMillis+2000>System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        else {
            ToastMessage.showToast(context,"برای خروج دوبار روی دکمه برگشت کلیک کنید");
        }
        oldCurrentTimeMillis = System.currentTimeMillis();
    }
    @Override
    public void onStop() {
        super.onStop();
        FilterPreference filterPreference=new FilterPreference(context);
        SortPrefrence sortPrefrence=new SortPrefrence(context);
        filterPreference.clearPreference();
        sortPrefrence.clearPreference();
    }
}


