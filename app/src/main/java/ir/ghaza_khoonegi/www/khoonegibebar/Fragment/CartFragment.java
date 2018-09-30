package ir.ghaza_khoonegi.www.khoonegibebar.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Activity.MainActivity;
import ir.ghaza_khoonegi.www.khoonegibebar.Adapter.CartRecyclerAdapter;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;
import ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase.CartSqlite;

public class CartFragment extends Fragment {
    private View view;
    private Context context;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_cart,container,false);
        context = container.getContext();
        recyClerViewCart(context);
        return view;
    }
    public void recyClerViewCart(final Context context){
        CartSqlite cartSqlite=new CartSqlite(context);
        List<FoodModel> foodModels=cartSqlite.getFood();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_cart);

        CartRecyclerAdapter cartRecyclerAdapter=new CartRecyclerAdapter(context,foodModels);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(cartRecyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
