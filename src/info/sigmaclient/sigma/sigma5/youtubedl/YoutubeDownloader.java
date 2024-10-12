package info.sigmaclient.sigma.sigma5.youtubedl;

import info.sigmaclient.sigma.SigmaNG;
import info.sigmaclient.sigma.config.ConfigManager;
import info.sigmaclient.sigma.utils.ChatUtils;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class YoutubeDownloader {
    boolean downloading = false;
    String lastDownload = "";

    public void setUPProxy() {
        SigmaNG.initProxy();
    }

    public String downLoadToFile(String s, String name) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(s).openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "*/*");
        map.put("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4");
        map.put("Connection", "keep-alive");
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("Referer", "github.com");
        map.put("Host", "github.com");
        map.put("Cookie", "appver=2.7.1.198277; os=pc; " + "");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        map.forEach(connection::setRequestProperty);
        connection.setInstanceFollowRedirects(true);

        int statusCode = connection.getResponseCode();
        if (statusCode >= 300 && statusCode < 400) {
            System.out.println("Redirected to: " + connection.getURL());
        } else {
            System.out.println("Not redirected");
        }

        File file = new File(ConfigManager.musicDir, name);
        try (FileOutputStream outputStream = new FileOutputStream(file);
             InputStream inputStream = connection.getInputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.out.println("Error downloading file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            connection.disconnect();
        }

        return file.toURI().toString();
    }

    public interface CallBack {
        void run(String downloadPath);
    }

    Process process;

    public void download(String id, CallBack callBack) {
        if (downloading) return;
        setUPProxy();
        String ytURL = new StringBuilder().append("https://www.youtube.com/watch?v=").append(id).toString();
        String path = "--paths " + ConfigManager.musicDir.getAbsolutePath();
        String exeFile = "yt-dlp.exe";
        File file = new File(ConfigManager.musicDir, exeFile);
        String downloadURL = "https://github.com/yt-dlp/yt-dlp/releases/download/2024.10.07/yt-dlp.exe";
        if (!file.exists()) {
            downloading = true;
            System.out.println("Downloading File " + downloadURL);
            try {
                downLoadToFile(downloadURL, exeFile);
            } catch (IOException e) {
                System.out.println("Failed to download yt-dlp: " + e.getMessage());
                e.printStackTrace();
                downloading = false;
                return;
            }
            downloading = false;
        }

        String downloadDoneFile = "";
        boolean hasFile = false;
        for (File f : Objects.requireNonNull(ConfigManager.musicDir.listFiles())) {
            if (f.getName().endsWith("[" + id + "].mp4")) {
                hasFile = true;
                downloadDoneFile = f.getAbsolutePath();
            }
        }
        if (!hasFile) {
            try {
                downloading = true;
                process = Runtime.getRuntime().exec(file.getAbsolutePath() + " " + ytURL + " " + path);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        if (Minecraft.getInstance().player != null) {
                            ChatUtils.sendMessageWithPrefix(line);
                        }
                    }
                }
                process.waitFor();
                downloading = false;
                Thread.sleep(500);
                for (File f : Objects.requireNonNull(ConfigManager.musicDir.listFiles())) {
                    if (f.getName().endsWith("[" + id + "].mp4")) {
                        downloadDoneFile = f.getAbsolutePath();
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Error during download process: " + e.getMessage());
                e.printStackTrace();
                downloading = false;
                return;
            }
        }
        if (!downloadDoneFile.equals("")) {
            lastDownload = downloadDoneFile;
            callBack.run(downloadDoneFile);
        } else {
            System.out.println("Video too long");
        }
    }
}