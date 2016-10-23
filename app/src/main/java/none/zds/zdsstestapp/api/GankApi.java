package none.zds.zdsstestapp.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankApi {
    private static GankService gankService = null;

    public static GankService getGankApi() {
        if (gankService != null) {
            return gankService;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        gankService = retrofit.create(GankService.class);
        return gankService;
    }
}
