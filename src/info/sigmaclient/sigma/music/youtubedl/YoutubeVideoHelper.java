package info.sigmaclient.sigma.music.youtubedl;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.sigma5.youtubedl.YoutubeDownloader;
import info.sigmaclient.sigma.config.ConfigManager;
import info.sigmaclient.sigma.modules.gui.hide.ClickGUI;
import info.sigmaclient.sigma.gui.clickgui.musicplayer.JelloMusicPlayer;
import info.sigmaclient.sigma.utils.ChatUtils;
import info.sigmaclient.sigma.utils.music.Music;
import it.sauronsoftware.jave.Encoder;

import java.io.*;
import java.net.*;
import java.util.*;

public class YoutubeVideoHelper {
    static String apiKey = "AIzaSyCGP7w_jD6Xh1B1BHp21bNpRsICsYYcRok";
    static HttpTransport httpTransport = new NetHttpTransport();
    static JsonFactory jsonFactory = new JacksonFactory();
    static YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, request -> {}).setApplicationName("youtube").build();

    public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36 Edg/129.0.0.0";

    public static String fetchContentWithRedirect(String urlString) throws IOException {
        if (!ConfigManager.musicDir.exists()) {
            ConfigManager.musicDir.mkdir();
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        setRequestHeaders(connection);
        connection.setInstanceFollowRedirects(false);

        int statusCode = connection.getResponseCode();
        if (statusCode >= 300 && statusCode < 400) {
            System.out.println("Redirected to: " + connection.getURL());
        } else {
            System.out.println("Not redirected");
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private static void setRequestHeaders(HttpURLConnection connection) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", userAgent);
        headers.forEach(connection::setRequestProperty);
    }

    public static String downloadToFile(String urlString) throws IOException {
        if (!ConfigManager.musicDir.exists()) {
            ConfigManager.musicDir.mkdir();
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        setRequestHeaders(connection);
        connection.setInstanceFollowRedirects(false);

        int statusCode = connection.getResponseCode();
        if (statusCode >= 300 && statusCode < 400) {
            System.out.println("Redirected to: " + connection.getURL());
        } else {
            System.out.println("Not redirected");
        }

        File file = new File(ConfigManager.musicDir, urlString.hashCode() + ".mp3");
        try (FileOutputStream outputStream = new FileOutputStream(file);
             InputStream inputStream = connection.getInputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        }
        connection.disconnect();
        return file.toURI().toString();
    }
    static Thread thread = null;

    public static void playMusicFromLink(Music music) {
        if (thread != null && thread.isAlive()) {
            thread.stop();
        }
        thread = new Thread(() -> {
            try {
                new YoutubeDownloader().download(music.ytid, (file) -> {
                    ClickGUI.clickGui.musicPlayer.mp3.SetPlayAudioPathYT(file, music);
                    JelloMusicPlayer.currentSongLength = (int) getMp4Duration(file);
                    System.out.println("Successfully got virtual storage location " + JelloMusicPlayer.currentSongLength + "\n");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static long getMp4Duration(String videoPath) {
        File source = new File(videoPath);
        Encoder encoder = new Encoder();
        long duration = 0;
        try {
            it.sauronsoftware.jave.MultimediaInfo info = encoder.getInfo(source);
            duration = info.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public static List<Music> search(String channelId) throws IOException {
        SigmaNG.initProxy();
        if (channelId.equals("NCS")) {
            channelId = "NCS - Copyright Free Music";
        }

        System.out.println("Start!");
        List<Music> musicList = new ArrayList<>();
        List<SearchResult> results = get(channelId);
        for (SearchResult searchResult : results) {
            Music music = new Music();
            music.copyRight = false;
            String title = searchResult.getSnippet().getTitle();
            music.name = title.replace("&#39;", "'");
            if (music.name.contains(" - ")) {
                music.name = music.name.split(" - ")[0];
            }
            music.aliasName = searchResult.getSnippet().getChannelTitle();
            music.ytid = searchResult.getId().getVideoId();
            music.picUrl = searchResult.getSnippet().getThumbnails().getDefault().getUrl();
            music.download();
            musicList.add(music);
        }
        return musicList;
    }

    public static List<SearchResult> get(String channelId) throws IOException {
        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(apiKey);
        search.setType("video");
        search.setFields("items(id/videoId,snippet/title,snippet/channelTitle,snippet/thumbnails/default/url),pageInfo");
        search.setMaxResults(20L);
        search.setQ(channelId);

        SearchListResponse searchResponse;
        System.out.println("Response!");
        try {
            searchResponse = search.execute();
            System.out.println("Done search response");
        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            ChatUtils.sendMessageWithPrefix("API key is over, please try tomorrow");
            return null;
        }

        List<SearchResult> searchResultList = searchResponse.getItems();
        searchResultList.forEach(System.out::println);
        System.out.println("Okay!");
        return searchResultList;
    }
}