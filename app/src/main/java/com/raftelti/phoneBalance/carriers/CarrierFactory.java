package com.raftelti.phoneBalance.carriers;

import android.content.Context;

import com.raftelti.phoneBalance.carriers.br.TimCarrier;
import com.raftelti.phoneBalance.carriers.br.VivoCarrier;
import com.raftelti.phoneBalance.utils.TelephonyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 01/04/2015.
 */
public class CarrierFactory {
    private final Context mContext;

    public CarrierFactory(Context context) {
        mContext = context;
    }

    public List<Carrier> getList() {
        Class[] classes = new Class[]{
                TimCarrier.class,
                VivoCarrier.class};

        List<Carrier> carriers = new ArrayList<>();
        int simCount = TelephonyUtils.getSimCount(mContext);

        for (int j = 1; j <= simCount; j++) {
            for (int i = 0; i < classes.length; i++) {
                try {
                    Carrier carrier = (Carrier) classes[i].newInstance();
                    carrier.setSimSlot(j - 1);
                    String carrierName = TelephonyUtils.getCarrierName(mContext, j);
                    if (carrier.match(TelephonyUtils.getCountryCode(mContext), carrierName)) {
                        carriers.add(carrier);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return carriers;
    }
}
