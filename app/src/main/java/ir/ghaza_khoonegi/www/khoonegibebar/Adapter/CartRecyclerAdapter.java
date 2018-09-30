package ir.ghaza_khoonegi.www.khoonegibebar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Activity.MainActivity;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.EnglishNumber;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Fragment.CartFragment;
import ir.ghaza_khoonegi.www.khoonegibebar.Fragment.FoodFragment;
import ir.ghaza_khoonegi.www.khoonegibebar.R;
import ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase.CartSqlite;

import static ir.ghaza_khoonegi.www.khoonegibebar.Activity.CartActivity.setsumPrice;
import static ir.ghaza_khoonegi.www.khoonegibebar.Activity.MainActivity.updateCounterCartIcon;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder> {
    private Context context;
    private List<FoodModel> foodModels;
    private CartSqlite cartSqlite;
    private PersianPrice persianPrise;
    public CartRecyclerAdapter(Context context,List<FoodModel> foodModels){

        this.context=context;
        this.foodModels=foodModels;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {

        final FoodModel foodModel=foodModels.get(position);
        final PersianPrice persianPrice=new PersianPrice();
        final String numberFoodHolder=persianPrice.getPrice(String.valueOf(foodModel.getNumberfood()));
        String priceHolder=persianPrice.getPrice(String.valueOf(foodModel.getPricetitle()));
        priceHolder=priceHolder+" تومان";

        holder.foodId.setText(String.valueOf(foodModel.getId()));
        holder.foodName.setText(foodModel.getFoodtitle());
        holder.chefName.setText(foodModel.getCheftitle());
        holder.numberFood.setText(numberFoodHolder);
        holder.priceFood.setText(priceHolder);

        holder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persianPrise=new PersianPrice();
                cartSqlite=new CartSqlite(context);
                int count=cartSqlite.setRemoveCountandReturnCount(Integer.parseInt(holder.foodId.getText().toString()));
                String countstr=persianPrice.getNumber(String.valueOf(count));
                if (count>=1){
                    holder.numberFood.setText(countstr);
                }else if (count==0){
                    cartSqlite.removeRow(Integer.parseInt(holder.foodId.getText().toString()));
                    foodModels.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, foodModels.size());
                }
                holder.setSumprice();
            }
        });
        holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                persianPrise=new PersianPrice();
                cartSqlite=new CartSqlite(context);
                int count=cartSqlite.setAddCountandReturnCount(Integer.parseInt(holder.foodId.getText().toString()));
                String countstr=persianPrice.getNumber(String.valueOf(count));
                holder.numberFood.setText(countstr);
                holder.setSumprice();
            }
        });
    }
    @Override
    public int getItemCount() {
        return foodModels.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder{

        private TextView foodId;
        private TextView foodName;
        private TextView chefName;
        private TextView numberFood;
        private TextView priceFood;
        private ImageView imageViewAdd;
        private ImageView imageViewRemove;
        private ConstraintLayout clItemFood;
        private CartSqlite cartSqlite;
        private PersianPrice persianPrise;
        public CartViewHolder(View itemView) {
            super(itemView);
            foodId =(TextView)itemView.findViewById(R.id.txt_id_cart);
            foodName=(TextView)itemView.findViewById(R.id.txt_food_title_cart);
            chefName=(TextView)itemView.findViewById(R.id.txt_chefname_cart);
            numberFood=(TextView)itemView.findViewById(R.id.txt_count_cart);
            priceFood=(TextView)itemView.findViewById(R.id.txt_pricefood_cart);
            clItemFood=(ConstraintLayout)itemView.findViewById(R.id.cl_catr_itemfragment);
            imageViewAdd=(ImageView)itemView.findViewById(R.id.img_add_cart);
            imageViewRemove=(ImageView)itemView.findViewById(R.id.img_remove_cart);
        }
        public void setSumprice(){
            CartSqlite cartSqlite=new CartSqlite(context);
            PersianPrice persianPrice=new PersianPrice();

            int sumPriceint=cartSqlite.returnSumPrice();
            String sumPricestr=persianPrice.getPrice(String.valueOf(sumPriceint));
            sumPricestr=sumPricestr+" تومان";
            setsumPrice(sumPricestr);
        }

    }
}
