package ro.lemacons.worksite_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Parsania Hardik on 03-Jan-17.
 */
class CustomeAdapter extends BaseAdapter {

    final Context context;
    final ArrayList<MainModel> mainModelArrayList;

    CustomeAdapter(Context context, ArrayList<MainModel> mainModelArrayList) {

        this.context = context;
        this.mainModelArrayList = mainModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mainModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mainModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Objects.requireNonNull(inflater).inflate(R.layout.lv_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvbuget = (TextView) convertView.findViewById(R.id.buget);
            holder.tvbugetbar = (ProgressBar) convertView.findViewById(R.id.BugetProgressBar);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(String.format("Name: %s", mainModelArrayList.get(position).getName()));

        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedBuget = formatter.format( Double.parseDouble(mainModelArrayList.get(position).getBuget()));
        //formattedNumber is equal to 1,000,000

        holder.tvbuget.setText(String.format("Buget folosit: %s%%", formattedBuget));

        holder.tvbugetbar.setProgress( Integer.parseInt(mainModelArrayList.get(position).getBuget()));

        return convertView;
    }

    private class ViewHolder {

        ProgressBar tvbugetbar;
        TextView tvname;
        TextView tvbuget;
    }

}