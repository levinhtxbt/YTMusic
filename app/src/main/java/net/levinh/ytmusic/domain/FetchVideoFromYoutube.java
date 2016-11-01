package net.levinh.ytmusic.domain;

import android.content.Context;
import android.text.TextUtils;

import net.levinh.ytmusic.Constant;
import net.levinh.ytmusic.utils.YoutubeConnector;
import net.levinh.ytmusic.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virus on 12/10/2016.
 */

public class FetchVideoFromYoutube extends BaseAsyncTask<String, Void, FetchVideoFromYoutube.YoutubeData> {
    YoutubeConnector youtubeConnector;

    public FetchVideoFromYoutube(Context context, int type, IAsyncCallBack callback) {
        super(context, type, callback);
        youtubeConnector = new YoutubeConnector(context);
    }

    @Override
    protected YoutubeData doInBackground(String... params) {
        YoutubeData data = new YoutubeData();
        if (mType == Constant.TypeFetch.MOST_POPULAR_VIDEO) {
            data.mItems = youtubeConnector.getMostPopular();
        } else if (mType == Constant.TypeFetch.RELATED_VIDEO) {


        } else if (mType == Constant.TypeFetch.SEARCH_VIDEO && !TextUtils.isEmpty(params[0])) {
            data.mItems = youtubeConnector.search(params[0]);
        }
        return data;
    }

    public static class YoutubeData {

        protected List<VideoItem> mItems;

        public List<VideoItem> getItems() {
            return mItems;
        }

        public YoutubeData() {
            mItems = new ArrayList<>();
        }

        public YoutubeData(List<VideoItem> items) {
            this.mItems = items;
        }


    }
}
