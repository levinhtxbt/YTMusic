package net.levinh.ytmusic.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import net.levinh.ytmusic.YTMusicApplication;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * Created by virus on 26/10/2016.
 */

public abstract class YoutubeExtractorUtils {

    private String audioURL;

    public YoutubeExtractorUtils(String videoId) {

        String buildURL = "https://www.youtube.com/watch?v=" + videoId;
        YouTubeUriExtractor extractor = new YouTubeUriExtractor(YTMusicApplication.mContext) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles == null) {
                    return;
                }
                for (int i = 0, itag = 0; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    // ytFile represents one file with its url and meta data
                    YtFile ytFile = ytFiles.get(itag);
                    // Just add videos in a decent format => height -1 = audio
                    if (ytFile.getMeta().getHeight() == -1) {
                        fetchLinkComplte(ytFile.getUrl());
                    }
                }
            }
        };
        // Ignore the webm container format
        extractor.setIncludeWebM(false);
        extractor.setParseDashManifest(true);
        // Lets execute the request
        extractor.execute(buildURL);
    }

    public abstract void fetchLinkComplte(String url);
}
