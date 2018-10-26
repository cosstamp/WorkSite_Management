package ro.lemacons.worksite_management;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SantierMaterialeAdapter extends RecyclerView.Adapter<SantierMaterialeAdapter.MyViewHolder> {

    Context mContext;
    List<SantierMaterialeModel> mData;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_nume_material.setText(mData.get(position).getNume_material());
        holder.tv_cantitate.setText(String.format("%s %s", mData.get(position).getCantitate(), mData.get(position).getUm()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nume_material;
        private TextView tv_cantitate;

        public MyViewHolder (View itemViev) {
            super(itemViev);

            tv_nume_material = (TextView) itemViev.findViewById(R.id.nume_material);
            tv_cantitate = (TextView) itemViev.findViewById(R.id.cantitate_UM);


        }

    }
}

