package none.zds.zdsstestapp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class GankDataItem {
    @SerializedName("_id") public String id;
    public Date createdAt;
    public String desc;
    public List<String> images;
    public Date publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
}
