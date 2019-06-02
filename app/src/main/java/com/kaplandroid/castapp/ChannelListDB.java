package com.kaplandroid.castapp;

import android.util.Log;
import com.kaplandroid.castapp.model.Channel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ChannelListDB {

    public static String token = "";
    public static String tms = "";

    static Channel[] channelList = {
            new Channel("Kafa Radyo", "kafaradyo", "http://46.20.3.245/stream/510/"),
            new Channel("Trt 1", "trt1"),
            new Channel("Kanal D", "kanald", "https://2122248082.dogannet.tv/S2/HLS_LIVE/kanaldnp/track_4_1250/playlist.m3u8"),
            new Channel("Kanal D 2", "kanald2", "https://blutv-beta.akamaized.net/kanaldhd/kanaldhd.smil/chunklist_b2628000_slturk.m3u8"),
            new Channel("Show Tv", "showtv"),
            new Channel("TV 8", "kanal8"),
            new Channel("DMAX Türkiye", "ntvspor"),
            new Channel("Fox Tv", "foxtv", "https://blutv-beta.akamaized.net/foxtvhd/foxtvhd.smil/chunklist_b1096000_sltur.m3u8"),
            new Channel("Star Tv", "startv"),
            new Channel("ATV", "atv", "https://trkvz-live.ercdn.net/atvhd/atvhd_720p.m3u8?st=AgZp2eAt_kv4el1VVeUaUA&e=1558717472&SessionID=vw0g3l4m01t1bchlcnfn3r2k&StreamGroup=canli-yayin&Site=atv&DeviceGroup=web"),
            new Channel("teve 2", "tv2", "https://teve2.dogannet.tv/S2/HLS_LIVE/teve2np/track_4_1000/playlist.m3u8"),
            new Channel("CNN Türk", "cnnturk"), //new Channel("CNN Türk2", "cnnturk2","https://live.dogannet.tv/S1/HLS_LIVE/cnn_turk/1000/prog_index.m3u8"),
            new Channel("NTV", "ntv"),
            new Channel("TLC TV", "tlctv"),
            new Channel("360 TV", "skyturk"),
            new Channel("TV 8,5", "kanal8bucuk", "https://blutv-beta.akamaized.net/tv8.5hd/tv8.5hd.smil/chunklist_b2628000_sltur.m3u8"),
            new Channel("TRT World", "trtworld", "https://trtcanlitv-lh.akamaihd.net/i/TRTWORLD_1@321783/index_360p_av-p.m3u8?sd=10&rebase=on"),
            new Channel("Akıllı TV", "akillitv"),
            new Channel("Halk Tv", "halktv", "https://stream-03.ix7.dailymotion.com/sec(EXnGEKb7CsE2pyQU86rzg2USjzBbLkrC5lFuj1-P5NU)/dm/3/x6bokr3/s/live-2.m3u8"),
            new Channel("Bloomberg HT", "bloomberght"),
            new Channel("Cartoon Network Türkiye", "cartoonnetwork"),
            new Channel("Haber Global", "haberglobal"),
            new Channel("TV 8 Int", "kanal8int"),
            new Channel("Bein Sports Haber", "bein-sports-haber"),
            new Channel("TRT Haber", "trthaber"),
            new Channel("FB TV", "fbtv"),
            new Channel("Sports TV", "sportstv"),
            new Channel("TRT Belgesel", "trtbelgesel"),
            new Channel("Habertürk", "haberturk"),
            new Channel("Kanal 24", "kanal24"),
            new Channel("Kanal 7", "kanal7"),
            new Channel("Ulusal Kanal", "ulusalkanal"),
            new Channel("A Para", "a-para"),
            new Channel("Ülke TV", "ulketv"),
            new Channel("TGRT Haber", "tgrthaber"),
            new Channel("Tivibu Spor", "tivibuspor"),
            new Channel("TRT Spor", "trt3spor"),
            new Channel("A Spor", "aspor"),
            new Channel("Show Max", "show-max"),
            new Channel("Show Türk", "show-turk"),
            new Channel("TJK TV", "tjktv"),
            new Channel("Tay TV", "taytv"),
            new Channel("A Haber", "ahaber"),
            new Channel("A2 Tv", "a2tv"),
            new Channel("ATV Avrupa", "atv-avrupa"),
            new Channel("A News Türkiye", "anews"),
            new Channel("TV Net", "tvnet"),
            new Channel("Beyaz TV", "beyaztv"),
            new Channel("Euro Star Türkçe", "euro-star"),
            new Channel("TGRT EU", "tgrteu")
    };

    static void updateTokens() {

        String url1 = "http://web.canlitvlive.io/tvizle.php?css=eyJ6ZW1pbiI6IiMxQTFBMUEiLCJub3JlbmsiOiIjNEE4Q0I4Iiwib25pemxlIjoiMSIsImFkcmVuayI6IiNCMEQ1RTciLCJrYXRlZ29yaSI6InVuZGVmaW5lZCIsImFyYW1hdmFyIjoiMSIsImFkaG92ZXIiOiIjZmZmZmZmIiwiYWRyZW5rc2VjIjoiI0ZGRkZGRiJ9&t=2&pos=r&tv=ntv-spor-izle";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url1)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();

            token = res.split("tkn=")[1].split("&tms=")[0];
            tms = res.split("&tms=")[1].substring(0, 10);

            Log.e("res", "token:" + token + " - tms:" + tms);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
