package net.levinh.ytmusic.presenter;

import android.util.Log;
import android.util.SparseArray;

import net.levinh.ytmusic.Constant;
import net.levinh.ytmusic.domain.FetchVideoFromYoutube;
import net.levinh.ytmusic.domain.IAsyncCallBack;
import net.levinh.ytmusic.model.VideoItem;
import net.levinh.ytmusic.view.adapter.RecyclerViewAdapter;
import net.levinh.ytmusic.view.fragment.SearchView;

import java.lang.ref.WeakReference;
import java.util.List;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * Created by virus on 13/10/2016.
 */

public class SearchVideoPresenter implements ISearchVideoPresenter, IAsyncCallBack, IBasePresenter {

    private static final String TAG = SettingPresenter.class.getSimpleName();
    WeakReference<SearchView> mSearchView;

    public SearchVideoPresenter(SearchView searchView) {
        mSearchView = new WeakReference<>(searchView);
    }

    @Override
    public void searchVideo(String keyword) {
        if (mSearchView.get() != null) {
            new FetchVideoFromYoutube(mSearchView.get().getContext(),
                    Constant.TypeFetch.SEARCH_VIDEO, this)
                    .execute(keyword);
        }
    }

    @Override
    public void getMostPopularVideo() {
        if (mSearchView.get() != null) {
            new FetchVideoFromYoutube(mSearchView.get().getContext(),
                    Constant.TypeFetch.MOST_POPULAR_VIDEO, this)
                    .execute("");
        }
    }

    @Override
    public void onItemClick(int position) {
        if (mSearchView.get() != null) {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) mSearchView.get().getAdapter();
            VideoItem video = adapter.getItem(position);
            Log.d(TAG, "onItemClick: urlAudio "+video.getUrl());




        }
    }

    @Override
    public void onFinish(int type, Object result) {
        if (result != null && mSearchView.get() != null) {
            List<VideoItem> listVideo = ((FetchVideoFromYoutube.YoutubeData) result).getItems();
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) mSearchView.get().getAdapter();
            adapter.setListVideo(listVideo);
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mSearchView.clear();
    }
}
