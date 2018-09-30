package ir.ghaza_khoonegi.www.khoonegibebar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.CommentModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class CommRecyclerAdapter extends RecyclerView.Adapter<CommRecyclerAdapter.CommViewHolder> {

    private Context context;
    private List<CommentModel> commentModels;

    public CommRecyclerAdapter(Context context,List<CommentModel> commentModels){

        this.context=context;
        this.commentModels=commentModels;

    }

    @NonNull
    @Override
    public CommViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_item_layout,parent,false);
        return new CommViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommViewHolder holder, int position) {

        CommentModel commentModel=commentModels.get(position);
        holder.tvNameUser.setText(commentModel.getNameUser());
        holder.rbItemComment.setRating(commentModel.getRate());
        holder.tvTextComment.setText(commentModel.getTextComment());
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class CommViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameUser;
        private RatingBar rbItemComment;
        private TextView tvTextComment;
        public CommViewHolder(View itemView) {
            super(itemView);

            tvNameUser=(TextView)itemView.findViewById(R.id.tv_name_user);
            rbItemComment=(RatingBar)itemView.findViewById(R.id.ratingBar_itemcomment);
            tvTextComment=(TextView)itemView.findViewById(R.id.tv_text_comment);
        }
    }
}
