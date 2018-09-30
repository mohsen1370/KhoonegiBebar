package ir.ghaza_khoonegi.www.khoonegibebar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.PayModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class PayDetailsRecyclerAdapter extends RecyclerView.Adapter<PayDetailsRecyclerAdapter.PayDeatilsViewHolder>{

    private Context context;
    private List<FoodModel> foodModels;

    public PayDetailsRecyclerAdapter(Context context,List<FoodModel> foodModels){

        this.context=context;
        this.foodModels=foodModels;

    }

    @NonNull
    @Override
    public PayDetailsRecyclerAdapter.PayDeatilsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.paydetails_item_layout,parent,false);
        return new PayDeatilsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayDetailsRecyclerAdapter.PayDeatilsViewHolder holder, int position) {

        final FoodModel foodModel=foodModels.get(position);
        PersianPrice persianPrice=new PersianPrice();

        holder.tvFoodName.setText("نام غذا: "+foodModel.getFoodtitle());
        holder.tvCountFood.setText("تعداد غذا: "+persianPrice.getNumber(String.valueOf(foodModel.getNumberfood()))+" عدد");
        holder.tvPriceFood.setText("قیمت هر واحد: "+persianPrice.getPrice(String.valueOf(foodModel.getPricetitle()))+" تومان");
        Picasso.with(context).load(foodModel.getFoodimage()).placeholder(R.drawable.background_food_item).error(R.drawable.background_food_item).into(holder.rivImageFood);

    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    public class PayDeatilsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFoodName;
        private TextView tvCountFood;
        private TextView tvPriceFood;
        private RoundedImageView rivImageFood;
        public PayDeatilsViewHolder(View itemView) {
            super(itemView);

            tvFoodName=(TextView)itemView.findViewById(R.id.tv_titlefood);
            tvCountFood=(TextView)itemView.findViewById(R.id.tv_titlecount);
            tvPriceFood=(TextView)itemView.findViewById(R.id.tv_titleprice);
            rivImageFood=(RoundedImageView) itemView.findViewById(R.id.img_food);
        }
    }
}
