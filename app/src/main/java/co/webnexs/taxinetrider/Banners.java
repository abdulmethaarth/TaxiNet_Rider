package co.webnexs.taxinetrider;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.webnexs.taxinetrider.beens.Banner;

public class Banners    {

    @SerializedName("screenList")
    private ArrayList<Banner> banners;

    public Banners(){ }

    public ArrayList<Banner> getBanners(){
        return banners;
    }
}
