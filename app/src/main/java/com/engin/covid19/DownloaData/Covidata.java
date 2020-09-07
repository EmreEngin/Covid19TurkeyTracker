package com.engin.covid19.DownloaData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Covidata {

    @SerializedName("tarih")
    @Expose
    private String tarih;
    @SerializedName("gunluk_test")
    @Expose
    private String gunlukTest;
    @SerializedName("gunluk_vaka")
    @Expose
    private String gunlukVaka;
    @SerializedName("gunluk_vefat")
    @Expose
    private String gunlukVefat;
    @SerializedName("gunluk_iyilesen")
    @Expose
    private String gunlukIyilesen;
    @SerializedName("toplam_test")
    @Expose
    private String toplamTest;
    @SerializedName("toplam_vaka")
    @Expose
    private String toplamVaka;
    @SerializedName("toplam_vefat")
    @Expose
    private String toplamVefat;
    @SerializedName("toplam_iyilesen")
    @Expose
    private String toplamIyilesen;
    @SerializedName("toplam_yogun_bakim")
    @Expose
    private String toplamYogunBakim;
    @SerializedName("toplam_entube")
    @Expose
    private String toplamEntube;
    @SerializedName("hastalarda_zaturre_oran")
    @Expose
    private String hastalardaZaturreOran;
    @SerializedName("agir_hasta_sayisi")
    @Expose
    private String agirHastaSayisi;

    public String getTarih() {
        return tarih;
    }

    public String getGunlukTest() {
        return gunlukTest;
    }


    public Integer getGunlukVaka() {
        if(gunlukVaka.length() <=3){
            return Integer.parseInt(gunlukVaka.trim());
        }
        else{
            return Integer.parseInt(gunlukVaka.replace(".", "").trim());
        }
    }

    public Integer getGunlukVefat() {
        if (gunlukVefat.length()<=3){
            return  Integer.parseInt(gunlukVefat.trim());
        }
        else{
            return Integer.parseInt(gunlukVefat.replace(".","").trim());
        }

    }


    public Integer getGunlukIyilesen() {
        if(gunlukIyilesen.length() <=3){
            return Integer.parseInt(gunlukIyilesen.trim());
        }else {
            return Integer.parseInt(gunlukIyilesen.replace(".","").trim());
        }
    }

    public String getToplamTest() {
        return toplamTest;
    }

    public String getToplamVaka() {
        return toplamVaka;
    }



    public String getToplamVefat() {
        return toplamVefat;
    }


    public String getToplamIyilesen() {
        return toplamIyilesen;
    }


    public String getToplamYogunBakim() {
        return toplamYogunBakim;
    }

    public String getToplamEntube() {
        return toplamEntube;
    }

    public String getHastalardaZaturreOran() {
        return hastalardaZaturreOran;
    }

    public String getAgirHastaSayisi() {
        return agirHastaSayisi;
    }


}

