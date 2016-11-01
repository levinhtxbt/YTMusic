package net.levinh.ytmusic.utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import net.levinh.ytmusic.Constant;
import net.levinh.ytmusic.R;
import net.levinh.ytmusic.model.VideoItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * Created by virus on 12/10/2016.
 */

public class YoutubeConnector {
    private static final String TAG = YoutubeConnector.class.getSimpleName();
    YouTube youtube;

    public YoutubeConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                //if you wanna build request with token
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();
    }


    public List<VideoItem> search(String keyword) {
        final YouTube.Search.List query;
        try {
            query = youtube.search().list("id,snippet");
            query.setKey(Constant.API_KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
            query.setQ(keyword);
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<>();
            for (SearchResult result : results) {
                final VideoItem item = new VideoItem();
                item.setId(result.getId().getVideoId());
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                new YoutubeExtractorUtils(item.getId()) {
                    @Override
                    public void fetchLinkComplte(String url) {
                        item.setUrl(url);
                    }
                };
                items.add(item);
            }
            return items;

        } catch (IOException e) {
            Log.d(TAG, "YoutubeConnector: ERROR " + e.getMessage());
            return null;
        }
    }

    public List<VideoItem> getMostPopular() {
        final YouTube.Videos.List query;
        try {
            query = youtube.videos().list("id,snippet");
            query.setKey(Constant.API_KEY);
            query.setChart("mostPopular");
            query.setRegionCode("vn");
            query.setFields("items(id,snippet/title,snippet/description,snippet/thumbnails/default/url)");

            VideoListResponse response = query.execute();
            List<Video> results = response.getItems();

            List<VideoItem> items = new ArrayList<>();

            for (Video result : results) {
                final VideoItem item = new VideoItem();
                item.setId(result.getId());
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                new YoutubeExtractorUtils(item.getId()) {
                    @Override
                    public void fetchLinkComplte(String url) {
                        item.setUrl(url);
                    }
                };
                items.add(item);
            }
            return items;

        } catch (IOException e) {
            Log.d(TAG, "YoutubeConnector: ERROR " + e.getMessage());
            return null;
        }

    }

    public void getMyPlaylist() {
//        GoogleCredential.Builder credential = new GoogleCredential.Builder();
        AuthorizationCodeRequestUrl requestUrl = new AuthorizationCodeRequestUrl("", "");
//        requestUrl.setScopes("");
        requestUrl.build();

        GoogleAuthorizationCodeTokenRequest request = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                new JacksonFactory(),
                Constant.CLIENT_ID,
                Constant.CLIENT_SECRET,
                Constant.TOKEN,
                "urn:ietf:wg:oauth:2.0:oob");
        request.setGrantType("authorization_code");

        request.setScopes(Collections.singleton(YouTubeScopes.YOUTUBE));
        try {
            String token = request.execute().getAccessToken();
            Log.d(TAG, "getMyPlaylist: token: " + token);
            final YouTube.Playlists.List query;
            query = youtube.playlists().list("id,snippet");
            query.setKey(Constant.API_KEY);
            query.setMine(true);
            query.setOauthToken(token);
            query.setFields("items(id,snippet/title,snippet/description)");
            PlaylistListResponse response = query.execute();
            List<Playlist> results = response.getItems();
            for (Playlist result : results) {
                Log.d(TAG, "getMyPlaylist: " + result.getId() + " - " + result.getSnippet().getTitle());
            }
        } catch (IOException e) {
            Log.d(TAG, "YoutubeConnector: ERROR " + e.getMessage());
        }

    }
}
