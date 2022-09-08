package co.webnexs.taxinetrider;

import com.google.gson.annotations.SerializedName;

public class UserTypeDetails {

    @SerializedName("type")
    private String type;

    @SerializedName("id")
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
