package com.raftelti.phoneBalance.carriers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.raftelti.phoneBalance.utils.TelephonyUtils;

import java.util.List;

/**
 * Created by Rafael on 01/04/2015.
 */
public class CarrierAdapter extends ArrayAdapter<Carrier> {
    private final boolean singleSim;

    public CarrierAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);

        CarrierFactory factory = new CarrierFactory(context);
        List<Carrier> list = factory.getList();
        addAll(list);

        singleSim = TelephonyUtils.getSimCount(context) == 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
        }

        TextView textView = ((TextView) convertView.findViewById(android.R.id.text1));

        Carrier carrier = getItem(position);
        textView.setText(carrier.getName(singleSim));
        return convertView;
    }


}
