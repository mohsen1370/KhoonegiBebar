package ir.ghaza_khoonegi.www.khoonegibebar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Activity.DetailsChefActivity;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class ChefRecyclerAdapter extends RecyclerView.Adapter<ChefRecyclerAdapter.ChefViewHolder> {
    private Context context;
    private List<ChefModel> chefModels;

    public ChefRecyclerAdapter(Context context,List<ChefModel> chefModels){

        this.context=context;
        this.chefModels=chefModels;

    }

    @NonNull
    @Override
    public ChefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chef_item_layout,parent,false);
        return new ChefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChefViewHolder holder, int position) {

        final ChefModel chefModel=chefModels.get(position);
        String s=chefModel.getChefimage().trim();
        if (s.length()>0)
            Picasso.with(context).load(chefModel.getChefimage()).placeholder(R.drawable.chef_user_error).error(R.drawable.chef_user_error).into(holder.chefimage);
        holder.chefname.setText("سرآشپز: "+chefModel.getChefname());
        holder.btnDetailsChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsChefActivity.class);
                intent.putExtra("ID_CHEF",chefModel.getId());
                intent.putExtra("IMAGE_CHEF",chefModel.getChefimage());
                intent.putExtra("NAME_CHEF",chefModel.getChefname());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chefModels.size();
    }
    public class ChefViewHolder extends RecyclerView.ViewHolder{
        private CircularImageView chefimage;
        private TextView chefname;
        private Button btnDetailsChef;

        public ChefViewHolder(View itemView) {
            super(itemView);
            chefimage=(CircularImageView)itemView.findViewById(R.id.image_chef);
            chefname=(TextView)itemView.findViewById(R.id.text_chef);
            btnDetailsChef=(Button)itemView.findViewById(R.id.btn_details_chef);
        }
    }
}
