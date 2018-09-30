package ir.ghaza_khoonegi.www.khoonegibebar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.ghaza_khoonegi.www.khoonegibebar.Activity.PayDetailsActivity;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.PersianPrice;
import ir.ghaza_khoonegi.www.khoonegibebar.Algoritm.ToastMessage;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.ChefModel;
import ir.ghaza_khoonegi.www.khoonegibebar.Datamodel.PayModel;
import ir.ghaza_khoonegi.www.khoonegibebar.R;

public class PayRecyclerAdapter extends RecyclerView.Adapter<PayRecyclerAdapter.PayViewHolder> {

    private Context context;
    private List<PayModel> payModels;

    public PayRecyclerAdapter(Context context,List<PayModel> payModels){

        this.context=context;
        this.payModels=payModels;

    }




    @NonNull
    @Override
    public PayRecyclerAdapter.PayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.pay_item_layout,parent,false);
        return new PayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayRecyclerAdapter.PayViewHolder holder, int position) {
        final PayModel payModel=payModels.get(position);
        PersianPrice persianPrice=new PersianPrice();


        holder.tvPayId.setText("KB-"+String.valueOf(payModel.getIdPay()));
        holder.tvDatepay.setText(persianPrice.getNumber(payModel.getDatepay()));
        holder.tvSumPrice.setText(persianPrice.getPrice(String.valueOf(payModel.getSumpricePay()))+" تومان");
        holder.clDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PayDetailsActivity.class);
                intent.putExtra("ID_PAY",payModel.getIdPay());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return payModels.size();
    }

    public class PayViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPayId;
        private TextView tvDatepay;
        private TextView tvSumPrice;
        private ConstraintLayout clDetails;
        public PayViewHolder(View itemView) {
            super(itemView);
            tvPayId=(TextView)itemView.findViewById(R.id.tv_payid);
            tvDatepay=(TextView)itemView.findViewById(R.id.tv_datepay);
            tvSumPrice=(TextView)itemView.findViewById(R.id.tv_sumprice);
            clDetails=(ConstraintLayout)itemView.findViewById(R.id.cl_details);
        }
    }
}
