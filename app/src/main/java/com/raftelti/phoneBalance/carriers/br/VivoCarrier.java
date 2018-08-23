package com.raftelti.phoneBalance.carriers.br;

import com.raftelti.phoneBalance.carriers.Carrier;

/**
 * Created by Rafael on 01/04/2015.
 */
public class VivoCarrier extends Carrier {
    public VivoCarrier() {
        super("BR", "Vivo");
    }

    @Override
    public String getRequestMode() {
        return "sms";
    }

    @Override
    public String getRequestSmsNumber() {
        return "8000";
    }

    @Override
    public String getResponseSmsNumber() {
        return "1515";
    }

    @Override
    public String getSmsText() {
        return "Saldo";
    }
}
