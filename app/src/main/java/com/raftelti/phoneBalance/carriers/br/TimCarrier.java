package com.raftelti.phoneBalance.carriers.br;

import com.raftelti.phoneBalance.carriers.Carrier;

/**
 * Created by Rafael on 01/04/2015.
 */
public class TimCarrier extends Carrier {
    public TimCarrier() {
        super("BR", "TIM");
    }

    @Override
    public String getRequestMode() {
        return "ussd";
    }

    @Override
    public String getRequestSmsNumber() {
        return "222";
    }

    @Override
    public String getResponseSmsNumber() {
        return "222";
    }

    @Override
    public String getSmsText() {
        return "SAL";
    }

    @Override
    public String getUssdCode() {
        return "*222#";
    }
}
