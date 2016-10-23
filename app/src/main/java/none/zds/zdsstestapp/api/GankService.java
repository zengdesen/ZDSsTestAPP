package none.zds.zdsstestapp.api;

import none.zds.zdsstestapp.entity.GankData;
import none.zds.zdsstestapp.entity.GankFuLi;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankService {
    int pageSize = 10;

    @GET("data/福利/" + pageSize + "/{page}")
    Observable<GankFuLi> getFuLiData(
            @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

//    @GET("data/休息视频/" + pageSize + "/{page}")
//    Observable<休息视频Data> get休息视频Data(
//            @Path("page") int page);

}
