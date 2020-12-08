package net.mz.rb.subdiffusion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

class VisiblePatternAdapter extends ArrayAdapter<VisiblePattern> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<VisiblePattern> productList;
    private Context context;

    VisiblePatternAdapter(Context context, int resource, ArrayList<VisiblePattern> products) {
        super(context, resource, products);
        this.context = context;
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final VisiblePattern VisiblePattern = productList.get(position);

        viewHolder.viewButton.setText(VisiblePattern.getName());
        viewHolder.viewButton.setId(VisiblePattern.getPatternID());
        viewHolder.editButton.setId(VisiblePattern.getEditID());
        viewHolder.removeButton.setId(VisiblePattern.getDeleteID());

        return convertView;
    }

    private class ViewHolder {
        final FancyButton editButton, removeButton, viewButton;
        ViewHolder(View view){
            editButton = (FancyButton) view.findViewById(R.id.editPatternButton);
            removeButton = (FancyButton) view.findViewById(R.id.deletePatternButton);
            viewButton = (FancyButton) view.findViewById(R.id.viewPatternButton);
        }
    }
}