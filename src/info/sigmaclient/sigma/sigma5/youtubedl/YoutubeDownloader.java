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
    public void setUPProxy(){
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
        // 检查响应状态
        // 设置不跟随重定向
        connection.setInstanceFollowRedirects(true);

        // 获取响应状态码
        int statusCode = connection.getResponseCode();

        // 检查响应状态码是否为 3XX
        if (statusCode >= 300 && statusCode < 400) {
            // 响应为重定向
            System.out.println("Redirected to: " + connection.getURL());
        } else {
            // 响应不是重定向
            System.out.println("Not redirected");
        }

        // 创建文件
        File file = new File(ConfigManager.musicDir,name);

        // 创建输出流
        FileOutputStream outputStream = new FileOutputStream(file);
        InputStream inputStream = connection.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len=inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);//写出这个数据
        }
        inputStream.close();
        connection.disconnect();//断开连接
        outputStream.close();

        return file.toURI().toString();
    }
    public interface CallBack {
        void run(String downloadPath);
    }
    Process process;
    public void download(String id, CallBack callBack) {
        if (downloading) return;
        setUPProxy();
        // C:\Users\IamFrozenMilk\Desktop\sigmaNG1.19.4\run\.\sigma5ng\musics
        String ytURL = "https://www.youtube.com/watch?v=" + id;
        String Path = "--paths " + ConfigManager.musicDir.getAbsolutePath();
        String exeFile = "youtube-dl.exe";
        File file = new File(ConfigManager.musicDir, exeFile);
        String downloadURL = "https://github.com/yt-dlp/yt-dlp/releases/download/2023.10.13/yt-dlp.exe";
        if (!file.exists()) {
            downloading = true;
            System.out.println("Downloading File " + downloadURL);
            try {
                downLoadToFile(downloadURL, exeFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            downloading = false;
        }

//        visitSite(downloadURL);
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
                process = Runtime.getRuntime().exec(file.getAbsolutePath() + " " + ytURL + " " + Path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                // [download] Destination: C:\Users\1\Desktop\IiI\.minecraft\sigma5\music\downloading.mp4\JELLO RELEASE - Introducing Jello for Sigma 5.0 - Minecraft 1.8 to 1.16.4 Hacked Client [dwTlcgW6dDA].mp4
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    if(Minecraft.getInstance().player != null){
                        ChatUtils.sendMessageWithPrefix(line);
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
//            file.delete();
                throw new RuntimeException(e);
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
