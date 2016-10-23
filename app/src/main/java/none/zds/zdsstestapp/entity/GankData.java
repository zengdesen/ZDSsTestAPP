package none.zds.zdsstestapp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankData extends BaseGankData {
    public Result results;
    public List<String> category;

    public class Result {
        @SerializedName("Android") public List<GankDataItem> androidList;
        @SerializedName("休息视频") public List<GankDataItem> vedioList;
        @SerializedName("iOS") public List<GankDataItem> iOSList;
        @SerializedName("福利") public List<GankDataItem> fuliList;
        @SerializedName("拓展资源") public List<GankDataItem> exList;
        @SerializedName("瞎推荐") public List<GankDataItem> recomList;
        @SerializedName("App") public List<GankDataItem> appList;
    }
}
