package none.zds.zdsstestapp.ui.base;

public interface SwipeRefreshLayer {

    void requestDataRefresh();

    void setRefresh(boolean refresh);

    void setProgressViewOffset(boolean scale, int start, int end);
}
