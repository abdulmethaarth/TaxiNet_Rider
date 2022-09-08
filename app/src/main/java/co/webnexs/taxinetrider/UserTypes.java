package co.webnexs.taxinetrider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.webnexs.taxinetrider.beens.Banner;
import co.webnexs.taxinetrider.beens.CarType;

public class UserTypes {

    @SerializedName("user_type")
    private ArrayList<UserTypeDetails> userTypeDetails;

    public ArrayList<UserTypeDetails> getUserTypeDetails() {
        return userTypeDetails;
    }

    public void setUserTypeDetails(ArrayList<UserTypeDetails> userTypeDetails) {
        this.userTypeDetails = userTypeDetails;
    }
}
