package co.webnexs.taxinetrider.beens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.webnexs.taxinetrider.BaseResponse;

public class CarType extends BaseResponse {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("active_icon")
    @Expose
    private String active_icon;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getActive_icon() {
        return active_icon;
    }

    public void setActive_icon(String active_icon) {
        this.active_icon = active_icon;
    }
}
