package com.techforge.identityprovider.dto;

public class QrResponse {
    private String qr;

    public QrResponse(String qr){
        this.qr = qr;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
}
