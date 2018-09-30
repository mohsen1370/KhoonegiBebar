package ir.ghaza_khoonegi.www.khoonegibebar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.nineoldandroids.animation.ValueAnimator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Activity.DetailsFoodActivity;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.EnglishNumber;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.FoodModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;
import ir.ghaza_khoonegi.www.khoonegibebar.SqliteDatabase.CartSqlite;

import static android.support.v7.widget.RecyclerView.*;
import static ir.ghaza_khoonegi.www.khoonegibebar.Activity.MainActivity.updateCounterCartIcon;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>  {
    private Context context;
    private List<FoodModel> foodModels;

    public FoodRecyclerAdapter(Context context, List<FoodModel> foodModels){

        this.context = context;
        this.foodModels = foodModels;
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.food_item_layout,parent,false);
        return new FoodViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder holder, int position) {
        //onBindViewHolder(holder, position);
        final FoodModel foodModel=foodModels.get(position);
        String s=foodModel.getFoodimage().trim();
        if (s.length()>1)
            Picasso.with(context).load(foodModel.getFoodimage()).placeholder(R.drawable.background_food_item).error(R.drawable.background_food_item).into(holder.foodimage);
        holder.foodname.setText(foodModel.getFoodtitle());
        holder.chefname.setText("سر آشپز " +foodModel.getCheftitle());
        PersianPrice persianPrise=new PersianPrice();
        String price=persianPrise.getPrice(String.valueOf(foodModel.getPricetitle()));
        holder.price.setText(price+" تومان");
        holder.txtIdFood.setText(String.valueOf(foodModel.getId()));
        holder.rbFood.setRating(foodModel.getRateFood());

        //get count and set in evryone

        CartSqlite cartSqlite=new CartSqlite(context);
        int count=cartSqlite.ReturnCount(foodModel.getId());
        if(count>0){
            String strCount=persianPrise.getNumber(String.valueOf(count));
            holder.marginCart(holder.addCart,170,0);
            holder.removeCart.setVisibility(VISIBLE);
            holder.textViewNumberAdd.setVisibility(VISIBLE);
            holder.textViewNumberAdd.setText(strCount);
        }else {
            holder.marginCart(holder.addCart,30,0);
            holder.removeCart.setVisibility(INVISIBLE);
            holder.textViewNumberAdd.setVisibility(INVISIBLE);
        }
        //*
        holder.foodimage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DetailsFoodActivity.class);
                intent.putExtra("ID_FOOD",foodModel.getId());
                intent.putExtra("NAME_FOOD",foodModel.getFoodtitle());
                intent.putExtra("IMAGE_FOOD",foodModel.getFoodimage());
                intent.putExtra("PRICE",foodModel.getPricetitle());
                intent.putExtra("MATERIAL",foodModel.getMaterial());
                intent.putExtra("COOK",foodModel.getCook());
                intent.putExtra("CHEF_NAME",foodModel.getCheftitle());
                intent.putExtra("GROUP",foodModel.getGroup());
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    public class FoodViewHolder extends ViewHolder implements View.OnClickListener{
        private RoundedImageView foodimage;
        private TextView foodname;
        private TextView chefname;
        private TextView price;
        private RelativeLayout itemFood;
        private ImageView addCart;
        private ImageView removeCart;
        private Animation anim;
        private TextView textViewNumberAdd;
        private TextView txtIdFood;
        private CartSqlite cartSqlite;
        private PersianPrice persianPrise;
        private RatingBar rbFood;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodimage=(RoundedImageView)itemView.findViewById(R.id.img_food);
            foodname=(TextView)itemView.findViewById(R.id.txt_namefood);
            chefname=(TextView)itemView.findViewById(R.id.txt_namechef);
            price=(TextView)itemView.findViewById(R.id.txt_price);
            txtIdFood=(TextView)itemView.findViewById(R.id.txt_id_food);
            rbFood=(RatingBar)itemView.findViewById(R.id.ratingBar_itemfood);

            addCart =(ImageView)itemView.findViewById(R.id.img_addshopping);
            removeCart =(ImageView)itemView.findViewById(R.id.img_removeshopping);
            itemFood=(RelativeLayout)itemView.findViewById(R.id.rl_food);
            textViewNumberAdd=(TextView)itemView.findViewById(R.id.txt_number_add);
            persianPrise=new PersianPrice();

            foodimage.setOnClickListener(this);
            itemFood.setOnClickListener(this);
            addCart.setOnClickListener(this);
            removeCart.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (view.getId() == addCart.getId() || view.getId() == itemFood.getId()) {
                marginCart(addCart,170,300);
                removeCart.setVisibility(VISIBLE);
                addToCart();
                textViewNumberAdd.setVisibility(VISIBLE);
                updateCounterCartIcon(getAllCounterInCart());
            }
            else if (view.getId() == removeCart.getId()) {
                removeFromCart();
                updateCounterCartIcon(getAllCounterInCart());
            }
        }
        final public void marginCart(final View view, int margin,int duration){
            final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            ValueAnimator animator = ValueAnimator.ofInt(params.leftMargin, margin);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {
                    params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
                    view.requestLayout();
                }
            });
            animator.setDuration(duration);
            animator.start();
        }
        public int getAllCounterInCart(){
            int count=0;
            cartSqlite=new CartSqlite(context);
            count=cartSqlite.getAllCountInCart();
            return count;
        }
        public void removeFromCart(){
            int count=0;
            String strCount="";
            cartSqlite=new CartSqlite(context);
            count=cartSqlite.ReturnCount(Integer.parseInt(txtIdFood.getText().toString()));
            if(count==1){
                marginCart(addCart,30,300);
                removeCart.setVisibility(GONE);
                textViewNumberAdd.setVisibility(GONE);
                cartSqlite.removeRow(Integer.parseInt(txtIdFood.getText().toString()));

            }else if(count>1){
                count=cartSqlite.setRemoveCountandReturnCount(Integer.parseInt(txtIdFood.getText().toString()));
                strCount=persianPrise.getNumber(String.valueOf(count));
                textViewNumberAdd.setText(strCount);
            }
        }
        public void addToCart(){
            int count=0;
            String strCount="";
            cartSqlite=new CartSqlite(context);
            EnglishNumber englishNumber=new EnglishNumber();

            int check=cartSqlite.checkCountRowFood(Integer.parseInt(txtIdFood.getText().toString()));
            if(check==0){
                int priceInt=englishNumber.getNumber(price.getText().toString());
                FoodModel foodModel=new FoodModel();
                foodModel.setId(Integer.parseInt(txtIdFood.getText().toString()));
                foodModel.setFoodtitle(foodname.getText().toString());
                foodModel.setCheftitle(chefname.getText().toString());
                foodModel.setPricetitle(priceInt);
                foodModel.setNumberfood(1);
                cartSqlite.addToCartSQL(foodModel);
                count=cartSqlite.ReturnCount(Integer.parseInt(txtIdFood.getText().toString()));
                strCount=persianPrise.getNumber(String.valueOf(count));
                textViewNumberAdd.setText(strCount);
            }
            else {
                count=cartSqlite.setAddCountandReturnCount(Integer.parseInt(txtIdFood.getText().toString()));
                strCount=persianPrise.getNumber(String.valueOf(count));
                textViewNumberAdd.setText(strCount);
            }
        }
    }

}
