package ro.lemacons.worksite_management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.content.ContextCompat.startActivity;

public class SantierMaterialeAdapter extends RecyclerView.Adapter<SantierMaterialeAdapter.MyViewHolder> implements Serializable {

    Context mContext;
    List<SantierMaterialeModel> mData;
    Intent intent;

    public SantierMaterialeAdapter(Context mContext, List<SantierMaterialeModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.santier_materiale_item, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String material = mData.get(position).getNume_material();
        holder.tv_nume_material.setText(material);
        holder.tv_cantitate.setText(String.format("%s / %s %s",
                mData.get(position).getCantitate(),
                mData.get(position).getCantitate_necesara(),
                mData.get(position).getUm())
        );
        holder.tv_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mContext,NecesarMateriale.class);
                //intent.putExtra("data", (Serializable) mData.get(position));
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nume_material;
        private TextView tv_cantitate;
        private FloatingActionButton tv_add_button;

        public MyViewHolder (View itemViev) {
            super(itemViev);

            tv_nume_material = itemViev.findViewById(R.id.nume_material);
            tv_cantitate = itemViev.findViewById(R.id.cantitate_UM);
            tv_add_button = itemView.findViewById(R.id.floatingActionButton);

        }

    }
}

