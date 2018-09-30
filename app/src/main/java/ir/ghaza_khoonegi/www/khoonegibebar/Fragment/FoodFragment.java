package ir.ghaza_khoonegi.www.khoonegibebar.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.CartRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.FoodRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceFood;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FilterModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.SortModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.FilterPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.LoginPreference;
import ir.ghaza_khoonegi.www.khoonegibebar.Preference.SortPrefrence;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class FoodFragment extends Fragment{
    private RecyclerView recyclerView;
    private View view;
    private Context context;
    private SwipeRefreshLayout swip;
    private View progress;
    private FoodRecyclerAdapter foodAdapter;
    private LinearLayout llFilter;
    private LinearLayout llSort;
    private CheckBox cbAll;
    private CheckBox cbKhoresh;
    private CheckBox cbKabab;
    private CheckBox cbKhorak;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food, container, false);
        View sort = (View) view.findViewById(R.id.filterandsort);
        sort.setRotationY(180);

        llFilter=(LinearLayout) view.findViewById(R.id.ll_filter);
        llFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFilter();
            }
        });
        llSort=(LinearLayout)view.findViewById(R.id.ll_sort);
        llSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSort();
            }
        });

        //progress bar in main activity
        progress = (View) getActivity().findViewById(R.id.progress_bar);


        swip = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_food);
        context = container.getContext();
        setrecy(context,true);

        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setrecy(context,false);
            }
        });

        return view;
    }
    public void showDialogFilter(){
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.filter_dialog);
        Button btnAcceptFilter=(Button)dialog.findViewById(R.id.btn_accept_filter);
        Button btnCancelFilter=(Button)dialog.findViewById(R.id.btn_cancel_filter);
        cbAll=(CheckBox)dialog.findViewById(R.id.cb_allfood);
        cbKhoresh=(CheckBox)dialog.findViewById(R.id.cb_khoreshfood);
        cbKabab=(CheckBox)dialog.findViewById(R.id.cb_kababfood);
        cbKhorak=(CheckBox)dialog.findViewById(R.id.cb_khorakfood);
        btnAcceptFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterPreference filterPreference=new FilterPreference(context);
                FilterModel filterModel=new FilterModel();
                filterModel.setCbAllFood(cbAll.isChecked());
                filterModel.setCbKhoreshFood(cbKhoresh.isChecked());
                filterModel.setCbKababFood(cbKabab.isChecked());
                filterModel.setCbKhorakFood(cbKhorak.isChecked());
                filterPreference.saveFilterFood(filterModel);
                ApiServiceFood apiService = new ApiServiceFood(context);
                refreshRecy();
                dialog.cancel();
            }
        });
        btnCancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        FilterPreference filterPreference=new FilterPreference(context);
        FilterModel filterModel=new FilterModel();
        filterModel=filterPreference.getFilterFood();
        if(filterModel.getCbAllFood()){
            cbAll.setChecked(true);
            cbKhoresh.setChecked(true);
            cbKabab.setChecked(true);
            cbKhorak.setChecked(true);
        }else{
            cbAll.setChecked(false);
            if (filterModel.getCbKhoreshFood()){
                cbKhoresh.setChecked(true);
            }else {
                cbKhoresh.setChecked(false);
            }
            if (filterModel.getCbKababFood()){
                cbKabab.setChecked(true);
            }else {
                cbKabab.setChecked(false);
            }
            if(filterModel.getCbKhorakFood()){
                cbKhorak.setChecked(true);
            }else {
                cbKhorak.setChecked(false);
            }
        }
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckBoxAll();
            }
        });
        cbKhoresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbKhoresh.isChecked() && cbKabab.isChecked() && cbKhorak.isChecked()){
                    cbAll.setChecked(true);
                }
                else {
                    cbAll.setChecked(false);
                }
            }
        });
        cbKabab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbKhoresh.isChecked() && cbKabab.isChecked() && cbKhorak.isChecked()){
                    cbAll.setChecked(true);
                }
                else {
                    cbAll.setChecked(false);
                }
            }
        });
        cbKhorak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbKhoresh.isChecked() && cbKabab.isChecked() && cbKhorak.isChecked()){
                    cbAll.setChecked(true);
                }
                else {
                    cbAll.setChecked(false);
                }
            }
        });
        dialog.show();
    }
    public void setCheckBoxAll(){
        if(cbAll.isChecked()){
            cbAll.setChecked(true);
            cbKhoresh.setChecked(true);
            cbKabab.setChecked(true);
            cbKhorak.setChecked(true);
        }else {
            cbAll.setChecked(false);
            cbKhoresh.setChecked(false);
            cbKabab.setChecked(false);
            cbKhorak.setChecked(false);
        }
    }
    public void showDialogSort(){
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.sort_dialog);
        Button btnAcceptSort=(Button)dialog.findViewById(R.id.btn_accept_sort);
        Button btnCancelSort=(Button)dialog.findViewById(R.id.btn_cancel_sort);
        final RadioButton rbNew=(RadioButton)dialog.findViewById(R.id.rg_sort_new);
        final RadioButton rbLowPay=(RadioButton)dialog.findViewById(R.id.rg_sort_lowpay);
        final RadioButton rbHightPay=(RadioButton)dialog.findViewById(R.id.rg_sort_hightpay);
        final RadioButton rbHightStar=(RadioButton)dialog.findViewById(R.id.rg_sort_hightstar);
        btnAcceptSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortPrefrence sortPrefrence=new SortPrefrence(context);
                SortModel sortModel=new SortModel();
                sortModel.setRbNew(rbNew.isChecked());
                sortModel.setRbLowPay(rbLowPay.isChecked());
                sortModel.setRbhightPay(rbHightPay.isChecked());
                sortModel.setRbRating(rbHightStar.isChecked());
                sortPrefrence.saveSortFood(sortModel);
                refreshRecy();
                dialog.cancel();
            }
        });
        btnCancelSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        SortPrefrence sortPrefrence=new SortPrefrence(context);
        SortModel sortModel=new SortModel();
        sortModel=sortPrefrence.getSortFood();
        if (sortModel.getRbNew()){
            rbNew.setChecked(true);
        }else if (sortModel.getRbLowPay()){
            rbLowPay.setChecked(true);
        }else if(sortModel.getRbhightPay()){
            rbHightPay.setChecked(true);
        }else if(sortModel.getRbRating()){
            rbHightStar.setChecked(true);
        }

        dialog.show();
    }

    public void setrecy(final Context context,boolean b) {
        if(b==true){
            progress.setVisibility(View.VISIBLE);
        }
        ApiServiceFood apiService = new ApiServiceFood(context);
        apiService.getFoodItem(false,null,null,new ApiServiceFood.OnFoodReceived() {
            @Override
            public void onReceived(final List<FoodModel> foodModels, Boolean erorrswip) {

                swip.setRefreshing(true);
                if (erorrswip == true) {
                    progress.setVisibility(View.GONE);
                    showSnak();
                }
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_food);
                recyclerView.setRotationY(180);
                foodAdapter = new FoodRecyclerAdapter(context, foodModels);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(foodAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                progress.setVisibility(View.GONE);
                swip.setRefreshing(false);

            }
        });
    }
    public void showSnak() {
        ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");
    }
    public void refreshRecy(){
        ApiServiceFood apiService = new ApiServiceFood(context);
        apiService.getFoodItem(false,null,null,new ApiServiceFood.OnFoodReceived() {
            @Override
            public void onReceived(final List<FoodModel> foodModels, Boolean erorrswip) {
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_food);
                recyclerView.setRotationY(180);
                foodAdapter = new FoodRecyclerAdapter(context, foodModels);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(foodAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        });
    }


    public static FoodFragment newInstance() {

        Bundle args = new Bundle();
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
