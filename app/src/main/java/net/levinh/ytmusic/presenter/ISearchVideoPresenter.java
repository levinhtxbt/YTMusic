package net.levinh.ytmusic.presenter;

/**
 * Created by virus on 13/10/2016.
 */

public interface ISearchVideoPresenter {

    void searchVideo(String keyword);

    void getMostPopularVideo();

    void onItemClick(int position);
}
