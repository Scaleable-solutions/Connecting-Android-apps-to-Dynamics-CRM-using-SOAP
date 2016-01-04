package com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM;

import java.util.Date;

/**
 * Created by ajku-ajku on 31/12/2015.
 */
public class CRMAuthHeader {

    private String header;

    private Date Expire;

    public CRMAuthHeader() {
    }

    public CRMAuthHeader(String header, Date expire) {
        this.header = header;
        Expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getExpire() {
        return Expire;
    }

    public void setExpire(Date expire) {
        Expire = expire;
    }
}
