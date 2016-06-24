package com.example.jason.jason_workshop_3.Model.FeatureModel;

/**
 * Created by jason on 24/06/2016.
 */
public class Food {
    public String FID;
    public String FName;
    public String FImageURL;
    public String FCalo;

    public Food(String FID, String FName, String FImageURL, String FCalo) {
        this.FID = FID;
        this.FName = FName;
        this.FImageURL = FImageURL;
        this.FCalo = FCalo;
    }

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFImageURL() {
        return FImageURL;
    }

    public void setFImageURL(String FImageURL) {
        this.FImageURL = FImageURL;
    }

    public String getFCalo() {
        return FCalo;
    }

    public void setFCalo(String FCalo) {
        this.FCalo = FCalo;
    }
}
