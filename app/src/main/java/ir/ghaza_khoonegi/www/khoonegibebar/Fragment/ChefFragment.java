package ir.ghaza_khoonegi.www.khoonegibebar.Fragment;

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

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.ChefRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Apiservice.ApiServiceChef;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class ChefFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Context context;
    private SwipeRefreshLayout swip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chef, container, false);

        context = container.getContext();
        setrecy(context);


        swip = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_chef);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setrecy(context);
            }
        });

        return view;
    }
    public void setrecy(final Context context) {
        ApiServiceChef apiService = new ApiServiceChef(context);
        apiService.getChefItem(new ApiServiceChef.OnChefReceived() {
            @Override
            public void onReceived(final List<ChefModel> chefModels, Boolean erorrswip) {


                swip.setRefreshing(true);
                if (erorrswip == true) {
                    showSnak();
                    swip.setRefreshing(false);
                }
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_chef);
                recyclerView.setRotationY(180);
                final ChefRecyclerAdapter chefAdapter = new ChefRecyclerAdapter(context, chefModels);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(chefAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                swip.setRefreshing(false);
            }
        });
    }

    public void showSnak() {
        ToastMessage.showSnackbar(view,"لطفا اتصال خود به اینترنت را بررسی کنید.");
    }

    public static ChefFragment newInstance() {

        Bundle args = new Bundle();

        ChefFragment fragment = new ChefFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
