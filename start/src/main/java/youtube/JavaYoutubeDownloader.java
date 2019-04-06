package youtube;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class JavaYoutubeDownloader {

 public static String newline = System.getProperty("line.separator");
 private static final Logger log = Logger.getLogger(JavaYoutubeDownloader.class.getCanonicalName());
 private static final Level defaultLogLevelSelf = Level.FINER;
 private static final Level defaultLogLevel = Level.WARNING;
 private static final Logger rootlog = Logger.getLogger("");
 private static final String scheme = "http";
 private static final String host = "www.youtube.com";
 private static final Pattern commaPattern = Pattern.compile(",");
 private static final Pattern pipePattern = Pattern.compile("\\|");
 private static final char[] ILLEGAL_FILENAME_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };

 private static void usage(String error) {
  if (error != null) {
   System.err.println("Error: " + error);
  }
  System.err.println("usage: JavaYoutubeDownload VIDEO_ID DESTINATION_DIRECTORY");
  System.exit(-1);
 }

 public static void main(String[] args) {
//  if (args == null || args.length == 0) {
//   usage("Missing video id. Extract from http://www.youtube.com/watch?v=VIDEO_ID");
//  }
  try {
   setupLogging();

   log.fine("Starting");
   String videoId = "7NN4RlKspW0";
   String outdir = "F:\\";
   // TODO Ghetto command line parsing
//   if (args.length == 1) {
//    videoId = args[0];
//   } else if (args.length == 2) {
//    videoId = args[0];
//    outdir = args[1];
//   }

   int format = 18; // http://en.wikipedia.org/wiki/YouTube#Quality_and_codecs
   String encoding = "UTF-8";
   String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13";
   File outputDir = new File(outdir);
   String extension = getExtension(format);

   play(videoId, format, encoding, userAgent, outputDir, extension);

  } catch (Throwable t) {
   t.printStackTrace();
  }
  log.fine("Finished");
 }

 private static String getExtension(int format) {
  // TODO
  return "mp4";
 }

 private static void play(String videoId, int format, String encoding, String userAgent, File outputdir, String extension) throws Throwable {
  log.fine("Retrieving " + videoId);
  List<NameValuePair> qparams = new ArrayList<NameValuePair>();
  qparams.add(new BasicNameValuePair("video_id", videoId));
  qparams.add(new BasicNameValuePair("fmt", "" + format));
  URI uri = getUri("get_video_info", qparams);

  CookieStore cookieStore = new BasicCookieStore();
  HttpContext localContext = new BasicHttpContext();
  localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

  HttpClient httpclient = new DefaultHttpClient();
  HttpGet httpget = new HttpGet(uri);
  httpget.setHeader("User-Agent", userAgent);

  log.finer("Executing " + uri);
//  HttpResponse response = httpclient.execute(httpget, localContext);
//  HttpEntity entity = response.getEntity();
//  if (entity != null && response.getStatusLine().getStatusCode() == 200) {
//   InputStream instream = entity.getContent();
   String videoInfo = "apiary_host_firstparty=&cos=Windows&timestamp=1491407244&plid=AAVMbU8L6R8ZXmxp&cver=2.20170404&url_encoded_fmt_stream_map=quality%3Dhd720%26itag%3D22%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fratebypass%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526requiressl%253Dyes%2526sparams%253Ddur%25252Cei%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Cratebypass%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490669242352313%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.972%2526signature%253DE2A967293C46BC144B5BE2E06B88558F07673C8C.464249ED92F60417ABE10035E76E2BCDBBD82403%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D22%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.64001F%252C%2Bmp4a.40.2%2522%2Cquality%3Dmedium%26itag%3D43%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fratebypass%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D29931744%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Cratebypass%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490468871803306%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D0.000%2526signature%253D39B1B02E57309A5E3311CF3B714546D03C6D29A3.3C3638258D31378EF160CF95F8F57F8CEF428474%2526ms%253Dau%2526mime%253Dvideo%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D43%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp8.0%252C%2Bvorbis%2522%2Cquality%3Dmedium%26itag%3D18%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fratebypass%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D20534842%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Cratebypass%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668915321036%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.972%2526signature%253D06DFAA0A099D4E88B8E28A04C7B3028F815AC567.A15DB05D252256AF0F24E0EAEFBC8141AD256B2D%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D18%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.42001E%252C%2Bmp4a.40.2%2522%2Cquality%3Dsmall%26itag%3D36%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fmt%253D1491407132%2526key%253Dyt6%2526clen%253D7432224%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490467254292433%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D274.041%2526signature%253D28098811341EA56302D76D14B884120A9AED3283.54E16262D165628B13EED3A76B38CC9A4E8ADE28%2526ms%253Dau%2526mime%253Dvideo%25252F3gpp%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D36%26type%3Dvideo%252F3gpp%253B%2Bcodecs%253D%2522mp4v.20.3%252C%2Bmp4a.40.2%2522%2Cquality%3Dsmall%26itag%3D17%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fmt%253D1491407132%2526key%253Dyt6%2526clen%253D2641537%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490467255695364%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D274.041%2526signature%253D082C3BABDF02A9AB134B0B2B84A1BFF555007F11.71891D0A1A4E4D293E3F6A88F45058BFF8C3E349%2526ms%253Dau%2526mime%253Dvideo%25252F3gpp%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D17%26type%3Dvideo%252F3gpp%253B%2Bcodecs%253D%2522mp4v.20.3%252C%2Bmp4a.40.2%2522&loudness=-16.3490009308&player_error_log_fraction=1.0&eventid=jBHlWMPiL6S94ALsir_4DQ&innertube_context_client_version=2.20170404&iv_load_policy=1&root_ve_type=27240&fexp=9422596%2C9428399%2C9431012%2C9433221%2C9434046%2C9434289%2C9446054%2C9446364%2C9449243%2C9451490%2C9453897%2C9456640%2C9457141%2C9458576%2C9460160%2C9463496%2C9463594%2C9463965%2C9465533%2C9465867%2C9466793%2C9466795%2C9468483%2C9469224&iv_allow_in_place_switch=1&video_id=7NN4RlKspW0&atc=a%3D3%26b%3DoXt3aE31RrgUsJnRqzDyUk1AsTo%26c%3D1491407244%26d%3D1%26e%3D7NN4RlKspW0%26c3a%3D29%26c1a%3D1%26c6a%3D1%26hh%3DvpFlqZu66QNiwXP7CkPrNGxsptc&innertube_api_version=v1&cbrver=57.0.2987.133&is_listed=1&idpj=-3&csi_page_type=embed&enablecsi=1&ppv_remarketing_url=https%3A%2F%2Fwww.googleadservices.com%2Fpagead%2Fconversion%2F971134070%2F%3Fbackend%3Dinnertube%26cname%3D1%26cver%3D2_20170404%26data%3Dbackend%253Dinnertube%253Bcname%253D1%253Bcver%253D2_20170404%253Bdactive%253D0%253Bdynx_itemid%253D7NN4RlKspW0%253Bptype%253Dppv%26label%3DiuZUCLmC72YQ9qiJzwM%26ptype%3Dppv&relative_loudness=4.65099906921&ptchn=Rfk-Q4xILetcvG2arvMO9w&vm=CAIQARgE&c=WEB&itct=CAEQu2kiEwjD2Kn41I3TAhWkHlgKHWzFD98o6NQB&cr=SG&innertube_api_key=AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8&cl=152171927&player_response=%7B%22videoDetails%22%3A%7B%22thumbnail%22%3A%7B%22thumbnails%22%3A%5B%7B%22url%22%3A%22https%3A%2F%2Fi.ytimg.com%2Fvi%2F7NN4RlKspW0%2Fhqdefault.jpg%3Fcustom%3Dtrue%5Cu0026w%3D168%5Cu0026h%3D94%5Cu0026stc%3Dtrue%5Cu0026jpg444%3Dtrue%5Cu0026jpgq%3D90%5Cu0026sp%3D67%5Cu0026sigh%3DRkX7Lg42L5IG4HUusjVY3ZSHqhY%22%2C%22width%22%3A168%2C%22height%22%3A94%7D%2C%7B%22url%22%3A%22https%3A%2F%2Fi.ytimg.com%2Fvi%2F7NN4RlKspW0%2Fhqdefault.jpg%3Fcustom%3Dtrue%5Cu0026w%3D196%5Cu0026h%3D110%5Cu0026stc%3Dtrue%5Cu0026jpg444%3Dtrue%5Cu0026jpgq%3D90%5Cu0026sp%3D67%5Cu0026sigh%3Dz8k855VED7Y5uko4Jx98lhRaBLI%22%2C%22width%22%3A196%2C%22height%22%3A110%7D%2C%7B%22url%22%3A%22https%3A%2F%2Fi.ytimg.com%2Fvi%2F7NN4RlKspW0%2Fhqdefault.jpg%3Fcustom%3Dtrue%5Cu0026w%3D246%5Cu0026h%3D138%5Cu0026stc%3Dtrue%5Cu0026jpg444%3Dtrue%5Cu0026jpgq%3D90%5Cu0026sp%3D67%5Cu0026sigh%3DqxKCjRx4dT544Xin_yeik_I-mJ8%22%2C%22width%22%3A246%2C%22height%22%3A138%7D%2C%7B%22url%22%3A%22https%3A%2F%2Fi.ytimg.com%2Fvi%2F7NN4RlKspW0%2Fhqdefault.jpg%3Fcustom%3Dtrue%5Cu0026w%3D336%5Cu0026h%3D188%5Cu0026stc%3Dtrue%5Cu0026jpg444%3Dtrue%5Cu0026jpgq%3D90%5Cu0026sp%3D67%5Cu0026sigh%3DL-PYM_52A7rcYZ7FrRV13MiZRX8%22%2C%22width%22%3A336%2C%22height%22%3A188%7D%5D%7D%7D%2C%22adSafetyReason%22%3A%7B%7D%7D&allow_ratings=1&ptk=Rfk-Q4xILetcvG2arvMO9w&of=RG5VpaQOIBBVpjK9tJP04A&host_language=zh-CN&pltype=content&ps=desktop-polymer&ismb=70900000&apiary_host=&iv3_module=1&author=Boon+Hui+Lu+%E6%96%87%E6%85%A7%E5%A6%82&keywords=%E7%8E%8B%E5%8A%9B%E5%AE%8F%2Cwanglihom%2Cnamewee%2C%E9%A3%84%E5%90%91%E5%8C%97%E6%96%B9&ucid=UCRfk-Q4xILetcvG2arvMO9w&oid=AfQP021ZfcKifdBV1faqnA&vmap=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22UTF-8%22%3F%3E%3Cvmap%3AVMAP+xmlns%3Avmap%3D%22http%3A%2F%2Fwww.iab.net%2Fvideosuite%2Fvmap%22+xmlns%3Ayt%3D%22http%3A%2F%2Fyoutube.com%22+version%3D%221.0%22%3E%3C%2Fvmap%3AVMAP%3E&cbr=Chrome&csn=jBHlWMPiL6S94ALsir_4DQ&length_seconds=274&hl=zh_CN&token=1&title=%E9%BB%83%E6%98%8E%E5%BF%97Namewee+feat.+%E7%8E%8B%E5%8A%9B%E5%AE%8F+Leehom+Wang%E3%80%90%E6%BC%82%E5%90%91%E5%8C%97%E6%96%B9+Stranger+In+The+North+%E3%80%91%E6%96%87%E6%85%A7%E5%A6%82+%E5%A5%B3%E7%94%9F%E5%94%B1RAP%E7%89%88&videostats_playback_base_url=https%3A%2F%2Fs.youtube.com%2Fapi%2Fstats%2Fplayback%3Fei%3DjBHlWMPiL6S94ALsir_4DQ%26ns%3Dyt%26plid%3DAAVMbU8L6R8ZXmxp%26len%3D274%26vm%3DCAIQARgE%26of%3DRG5VpaQOIBBVpjK9tJP04A%26cl%3D152171927%26docid%3D7NN4RlKspW0%26fexp%3D9422596%252C9428399%252C9431012%252C9433221%252C9434046%252C9434289%252C9446054%252C9446364%252C9449243%252C9451490%252C9453897%252C9456640%252C9457141%252C9458576%252C9460160%252C9463496%252C9463594%252C9463965%252C9465533%252C9465867%252C9466793%252C9466795%252C9468483%252C9469224%26el%3Dembedded&watermark=%2Chttps%3A%2F%2Fs.ytimg.com%2Fyts%2Fimg%2Fwatermark%2Fyoutube_watermark-vflHX6b6E.png%2Chttps%3A%2F%2Fs.ytimg.com%2Fyts%2Fimg%2Fwatermark%2Fyoutube_hd_watermark-vflAzLcD6.png&gapi_hint_params=m%3B%2F_%2Fscs%2Fabc-static%2F_%2Fjs%2Fk%3Dgapi.gapi.en.DTPeBB_SvOA.O%2Fm%3D__features__%2Frt%3Dj%2Fd%3D1%2Frs%3DAHpOoo-J3J0yqNDMPVrmQT6j-SBFfGx8oA&external_play_video=1&status=ok&t=1&ldpj=-37&fmt_list=22%2F1280x720%2F9%2F0%2F115%2C43%2F640x360%2F99%2F0%2F0%2C18%2F640x360%2F9%2F0%2F115%2C36%2F320x180%2F99%2F1%2F0%2C17%2F176x144%2F99%2F1%2F0&cosver=10.0&no_get_video_log=1&iv_invideo_url=https%3A%2F%2Fwww.youtube.com%2Fannotations_invideo%3Fcap_hist%3D1%26video_id%3D7NN4RlKspW0%26client%3D1%26ei%3DjBHlWMPiL6S94ALsir_4DQ&tmi=1&adaptive_fmts=projection_type%3D1%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.640028%2522%26lmt%3D1490668824626909%26bitrate%3D3613387%26quality_label%3D1080p%26xtags%3D%26fps%3D25%26size%3D1920x1080%26index%3D716-1395%26clen%3D67403526%26itag%3D137%26init%3D0-715%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D67403526%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668824626909%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.920%2526signature%253D5BC8F51AECA0003480E23947511B7B35980CCA21.BBEC2CDA36B4B942904517F707C937ABC2AD8D62%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D137%2Cprojection_type%3D1%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp9%2522%26lmt%3D1490526058274763%26bitrate%3D3027203%26quality_label%3D1080p%26xtags%3D%26fps%3D25%26size%3D1920x1080%26index%3D243-1192%26clen%3D77650884%26itag%3D248%26init%3D0-242%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D77650884%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490526058274763%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.880%2526signature%253DD004D8AD5937D2082404504576BFA7CA970FD98A.AF172E025E06D333CAE07DF480931A21E691097D%2526ms%253Dau%2526mime%253Dvideo%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D248%2Cprojection_type%3D1%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d401f%2522%26lmt%3D1490668852740454%26bitrate%3D2086103%26quality_label%3D720p%26xtags%3D%26fps%3D25%26size%3D1280x720%26index%3D714-1393%26clen%3D38377420%26itag%3D136%26init%3D0-713%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D38377420%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668852740454%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.920%2526signature%253D86DB6786562A4EC0A8399597F29BCF8060239237.62B6D086E489714D7BC383A1FC918A90520B58D2%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D136%2Cprojection_type%3D1%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp9%2522%26lmt%3D1490525896247450%26bitrate%3D1698260%26quality_label%3D720p%26xtags%3D%26fps%3D25%26size%3D1280x720%26index%3D243-1183%26clen%3D41417996%26itag%3D247%26init%3D0-242%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D41417996%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490525896247450%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.880%2526signature%253D2C238D3D0C77E95E74ECE59E1DEE060D2DD470CB.A4843DA0DBC300F11BB60C0FE2C308EC324C8EEE%2526ms%253Dau%2526mime%253Dvideo%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D247%2Cprojection_type%3D1%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d401e%2522%26lmt%3D1490668852525506%26bitrate%3D1164782%26quality_label%3D480p%26xtags%3D%26fps%3D25%26size%3D854x480%26index%3D715-1394%26clen%3D21523058%26itag%3D135%26init%3D0-714%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D21523058%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668852525506%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.920%2526signature%253DA3A1BBA1D51CDFDEB28E28751A36B15028F53A5D.24C99A8E736DCF89963AB7274397F9312E86EB53%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D135%2Cprojection_type%3D1%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp9%2522%26lmt%3D1490525895372820%26bitrate%3D884301%26quality_label%3D480p%26xtags%3D%26fps%3D25%26size%3D854x480%26index%3D243-1162%26clen%3D21581772%26itag%3D244%26init%3D0-242%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D21581772%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490525895372820%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.880%2526signature%253D0B94A5BBD0A127BFDBBCDF4C5511A3ED5357D833.AED280AAB9741A911F5F666A0F97FBAA5CC0A217%2526ms%253Dau%2526mime%253Dvideo%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D244%2Cprojection_type%3D1%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d401e%2522%26lmt%3D1490668852398820%26bitrate%3D616626%26quality_label%3D360p%26xtags%3D%26fps%3D25%26size%3D640x360%26index%3D715-1394%26clen%3D11435149%26itag%3D134%26init%3D0-714%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D11435149%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668852398820%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.920%2526signature%253D31E9A84A14AF3940DE99B71F2FC9C8407355301E.65BC77FB6E53323407D60DCB5364DC95D59A3BE3%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D134%2Cprojection_type%3D1%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp9%2522%26lmt%3D1490525895183052%26bitrate%3D489263%26quality_label%3D360p%26xtags%3D%26fps%3D25%26size%3D640x360%26index%3D243-1151%26clen%3D13156812%26itag%3D243%26init%3D0-242%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D13156812%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490525895183052%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.880%2526signature%253D6C977D92111FF27C250E30BB9FACA73F911C1214.086274213DF6E08740D1F1B34BA662BF8570BB98%2526ms%253Dau%2526mime%253Dvideo%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D243%2Cprojection_type%3D1%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d4015%2522%26lmt%3D1490668852400057%26bitrate%3D247871%26quality_label%3D240p%26xtags%3D%26fps%3D25%26size%3D426x240%26index%3D714-1393%26clen%3D8400613%26itag%3D133%26init%3D0-713%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D8400613%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668852400057%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.920%2526signature%253D14E82DBA9D29FA46E60324DBF2609D7BAC073738.67B3FC43E3BE872DFAA0E8C7DA5A51CCDF279F09%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D133%2Cprojection_type%3D1%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp9%2522%26lmt%3D1490525895128876%26bitrate%3D264219%26quality_label%3D240p%26xtags%3D%26fps%3D25%26size%3D426x240%26index%3D242-1150%26clen%3D7327206%26itag%3D242%26init%3D0-241%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D7327206%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490525895128876%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.880%2526signature%253DCDB95F5A11E27DB98969CFE614E95912B7BAFF3D.D3CA3AFC47036645C0C79A754B2697D96BED8475%2526ms%253Dau%2526mime%253Dvideo%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D242%2Cprojection_type%3D1%26type%3Dvideo%252Fmp4%253B%2Bcodecs%253D%2522avc1.4d400c%2522%26lmt%3D1490668852301890%26bitrate%3D111884%26quality_label%3D144p%26xtags%3D%26fps%3D25%26size%3D256x144%26index%3D713-1392%26clen%3D2674523%26itag%3D160%26init%3D0-712%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D2674523%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668852301890%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.920%2526signature%253D3880C5B91F5320C311162667BC3BC6F279950F17.90A4643C195FAADE95DF72769C947797A0E35B47%2526ms%253Dau%2526mime%253Dvideo%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D160%2Cprojection_type%3D1%26type%3Dvideo%252Fwebm%253B%2Bcodecs%253D%2522vp9%2522%26lmt%3D1490525894062365%26bitrate%3D114396%26quality_label%3D144p%26xtags%3D%26fps%3D25%26size%3D256x144%26index%3D242-1149%26clen%3D3440361%26itag%3D278%26init%3D0-241%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D3440361%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490525894062365%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.880%2526signature%253D24E52A0083F01EF6EDD106DFC518589E83627BFB.91A4E752AFC4B16C6BB70F84CEA6DC07E97300C2%2526ms%253Dau%2526mime%253Dvideo%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D278%2Cindex%3D592-959%26lmt%3D1490668785873541%26clen%3D4352030%26itag%3D140%26bitrate%3D128318%26projection_type%3D1%26xtags%3D%26init%3D0-591%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D4352030%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490668785873541%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.972%2526signature%253D6FB2639DFC9A7A382984C2A368C094D37C575539.DD87E607930D0E7B0C766073CAFE388F5B42D439%2526ms%253Dau%2526mime%253Daudio%25252Fmp4%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D140%26type%3Daudio%252Fmp4%253B%2Bcodecs%253D%2522mp4a.40.2%2522%2Cindex%3D4452-4924%26lmt%3D1490524960448970%26clen%3D4349420%26itag%3D171%26bitrate%3D136670%26projection_type%3D1%26xtags%3D%26init%3D0-4451%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D4349420%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490524960448970%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.922%2526signature%253D37190E9C03939460DA3ADC498342A662DEC5C56D.39BE64D8D118B472EBDD12A8B45B0046D68144C1%2526ms%253Dau%2526mime%253Daudio%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D171%26type%3Daudio%252Fwebm%253B%2Bcodecs%253D%2522vorbis%2522%2Cindex%3D272-744%26lmt%3D1490524950798652%26clen%3D1811792%26itag%3D249%26bitrate%3D66017%26projection_type%3D1%26xtags%3D%26init%3D0-271%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D1811792%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490524950798652%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.921%2526signature%253DDA4706DC1EA95107067A8B7B9AB0D74F133D00A5.2F66680420AD91B7534AA2E46536868776D901ED%2526ms%253Dau%2526mime%253Daudio%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D249%26type%3Daudio%252Fwebm%253B%2Bcodecs%253D%2522opus%2522%2Cindex%3D272-744%26lmt%3D1490524935713790%26clen%3D2385660%26itag%3D250%26bitrate%3D84481%26projection_type%3D1%26xtags%3D%26init%3D0-271%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D2385660%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490524935713790%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.921%2526signature%253D1ABBED9917B49EE7FFB0A785C146D3F29CADA44B.CEC94C5254D9EDF032AE3F8BAE4C284343BD51BE%2526ms%253Dau%2526mime%253Daudio%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D250%26type%3Daudio%252Fwebm%253B%2Bcodecs%253D%2522opus%2522%2Cindex%3D272-744%26lmt%3D1490524952923472%26clen%3D4685759%26itag%3D251%26bitrate%3D155287%26projection_type%3D1%26xtags%3D%26init%3D0-271%26url%3Dhttps%253A%252F%252Fr2---sn-ogueln7r.googlevideo.com%252Fvideoplayback%253Fkeepalive%253Dyes%2526mt%253D1491407132%2526key%253Dyt6%2526clen%253D4685759%2526requiressl%253Dyes%2526gir%253Dyes%2526sparams%253Dclen%25252Cdur%25252Cei%25252Cgir%25252Cid%25252Cinitcwndbps%25252Cip%25252Cipbits%25252Citag%25252Ckeepalive%25252Clmt%25252Cmime%25252Cmm%25252Cmn%25252Cms%25252Cmv%25252Cpl%25252Crequiressl%25252Csource%25252Cupn%25252Cexpire%2526ipbits%253D0%2526expire%253D1491428844%2526lmt%253D1490524952923472%2526ei%253DjBHlWMPiL6S94ALsir_4DQ%2526upn%253DGuiu6Hx6agE%2526pl%253D26%2526id%253Do-AMXSMOa1Y8B3ZIMBpx9dZqMx6IGM004cGbsgfIS8YviP%2526initcwndbps%253D8862500%2526dur%253D273.921%2526signature%253DAAC9160F0DAF5400584EC1B91AC5E359E1614BAF.D2F366FFC7814E3F8C0E97976CF56974FEC6E096%2526ms%253Dau%2526mime%253Daudio%25252Fwebm%2526mv%253Dm%2526source%253Dyoutube%2526mn%253Dsn-ogueln7r%2526ip%253D45.32.253.224%2526mm%253D31%2526itag%253D251%26type%3Daudio%252Fwebm%253B%2Bcodecs%253D%2522opus%2522&xhr_apiary_host=youtubei.youtube.com&allow_embed=1&fflags=html5_adjust_effective_request_size%3Dtrue%26html5_throttle_rate%3D0.0%26html5_live_4k_more_buffer%3Dtrue%26enable_live_state_auth%3Dtrue%26html5_enable_embedded_player_visibility_signals%3Dtrue%26html5_reseek_on_infinite_buffer%3Dtrue%26legacy_autoplay_flag%3Dtrue%26html5_suspend_manifest_on_pause%3Dtrue%26html5_idle_preload_secs%3D1%26html5_bandwidth_window_size%3D0%26html5_timeupdate_readystate_check%3Dtrue%26html5_burst_less_for_no_bw_data%3Dtrue%26html5_max_vss_watchtime_ratio%3D0.0%26use_push_for_desktop_live_chat%3Dtrue%26yto_feature_hub_channel%3Dfalse%26hls_deterministic_output_version%3D7%26send_html5_api_stats_ads_abandon%3Dtrue%26html5_report_conn%3Dtrue%26use_fast_fade_in_0s%3Dtrue%26kids_enable_post_onboarding_red_flow%3Dtrue%26html5_ad_no_buffer_abort_after_skippable%3Dtrue%26kids_enable_block_servlet%3Dtrue%26html5_local_max_byterate_lookahead%3D15%26midroll_notify_time_seconds%3D5%26spherical_on_android_iframe%3Dtrue%26kids_asset_theme%3Dserver_side_assets%26forced_brand_precap_duration_ms%3D2000%26html5_min_vss_watchtime_to_cut_secs_redux%3D0.0%26vss_dni_delayping%3D0%26html5_variability_no_discount_thresh%3D1.0%26hls_variant_deterministic_output_version%3D7%26interaction_log_delayed_event_batch_size%3D200%26html5_min_byterate_to_time_out%3D0%26html5_msi_error_fallback%3Dtrue%26html5_live_disable_dg_pacing%3Dtrue%26html5_allowable_liveness_drift_chunks%3D2%26html5_min_secs_between_format_selections%3D8.0%26android_enable_thumbnail_overlay_side_panel%3Dfalse%26enable_plus_page_pts%3Dtrue%26fixed_padding_skip_button%3Dtrue%26html5_tight_max_buffer_allowed_bandwidth_stddevs%3D0.0%26html5_disable_audio_slicing%3Dtrue%26html5_request_size_min_secs%3D0.0%26lugash_header_by_service%3Dtrue%26desktop_cleanup_companion_on_instream_begin%3Dtrue%26enable_pla_desktop_shelf%3Dtrue%26html5_live_pin_to_tail%3Dtrue%26disable_search_mpu%3Dtrue%26lugash_header_warmup%3Dtrue%26ios_notifications_disabled_subs_tab_promoted_item_promo%3Dtrue%26html5_strip_emsg%3Dtrue%26show_countdown_on_bumper%3Dtrue%26chrome_promo_enabled%3Dtrue%26kids_enable_privacy_notice%3Dtrue%26king_crimson_player_redux%3Dtrue%26variable_load_timeout_ms%3D0%26html5_trust_platform_bitrate_limits%3Dtrue%26exo_drm_max_keyfetch_delay_ms%3D0%26html5_post_interrupt_readahead%3D0%26enable_playlist_multi_season%3Dtrue%26live_chunk_readahead%3D3%26html5_use_adaptive_live_readahead%3Dtrue%26log_js_exceptions_fraction%3D0.20%26website_actions_throttle_percentage%3D1.0%26html5_playing_event_buffer_underrun%3Dtrue%26html5_max_buffer_health_for_downgrade_proportion%3D0.0%26html5_max_av_sync_drift%3D50%26html5_background_quality_cap%3D360%26html5_variability_full_discount_thresh%3D3.0%26yto_enable_ytr_promo_refresh_assets%3Dtrue%26player_destroy_old_version%3Dtrue%26mweb_adsense_instreams_disabled_for_android_tablets%3Dtrue%26ios_enable_mixin_accessibility_custom_actions%3Dtrue%26html5_max_buffer_duration%3D0%26html5_min_readbehind_secs%3D0%26html5_spherical_bicubic_mode%3D0%26enable_offer_restricts_for_watch_page_offers%3Dtrue%26html5_min_startup_smooth_target%3D10.0%26html5_min_readbehind_cap_secs%3D0%26use_new_style%3Dtrue%26html5_background_cap_idle_secs%3D60%26live_readahead_seconds_multiplier%3D0.8%26html5_disable_non_contiguous%3Dtrue%26html5_new_preloading%3Dtrue%26html5_reduce_startup_rebuffers%3Dtrue%26mweb_blacklist_progressive_chrome_mobile%3Dtrue%26playready_on_borg%3Dtrue%26enable_red_carpet_p13n_shelves%3Dtrue%26sdk_wrapper_levels_allowed%3D0%26mpu_visible_threshold_count%3D2%26dynamic_ad_break_pause_threshold_sec%3D0%26polymer_report_missing_web_navigation_endpoint%3Dfalse%26html5_long_term_bandwidth_window_size%3D0%26html5_repredict_interval_secs%3D0.0%26king_crimson_player%3Dfalse%26html5_elbow_tracking_tweaks%3Dtrue%26html5_max_readahead_bandwidth_cap%3D0%26autoplay_time%3D8000%26enable_local_channel%3Dtrue%26html5_audio_preload_duration%3D2.0%26fix_gpt_pos_params%3Dtrue%26live_fresca_v2%3Dtrue%26html5_min_vss_watchtime_to_cut_secs%3D0.0%26send_api_stats_ads_asr%3Dtrue%26html5_progressive_signature_reload%3Dtrue%26html5_max_buffer_health_for_downgrade%3D15%26html5_serverside_biscotti_id_wait_ms%3D1000%26kids_enable_terms_servlet%3Dtrue%26html5_get_video_info_promiseajax%3Dtrue%26lock_fullscreen%3Dfalse%26yto_enable_watch_offer_module%3Dtrue%26html5_deadzone_multiplier%3D1.1%26html5_min_upgrade_health%3D0%26request_mpu_on_unfilled_ad_break%3Dtrue%26html5_get_video_info_timeout_ms%3D0%26html5_pause_manifest_ended%3Dtrue%26html5_variability_discount%3D0.5%26dynamic_ad_break_seek_threshold_sec%3D0%26ad_duration_threshold_for_showing_endcap_seconds%3D15%26html5_always_reload_on_403%3Dtrue%26html5_connect_timeout_secs%3D7.0%26dash_manifest_version%3D4%26show_thumbnail_on_standard%3Dtrue%26html5_request_sizing_multiplier%3D0.8%26html5_stale_dash_manifest_retry_factor%3D1.0%26kids_enable_server_side_assets%3Dtrue%26doubleclick_gpt_retagging%3Dtrue%26pla_shelf_hovercard%3Dtrue%26disable_desktop_homepage_pyv_backfill%3Dtrue%26mobile_disable_ad_mob_on_home%3Dtrue%26html5_default_quality_cap%3D0%26enable_ccs_buy_flow_for_chirp%3Dtrue%26yto_enable_unlimited_landing_page_yto_features%3Dtrue%26flex_theater_mode%3Dtrue%26sidebar_renderers%3Dtrue%26html5_tight_max_buffer_allowed_impaired_time%3D0.0%26use_new_skip_icon%3Dtrue%26postroll_notify_time_seconds%3D5%26ios_disable_notification_preprompt%3Dtrue%26ad_video_end_renderer_duration_milliseconds%3D7000%26html5_vp9_live_whitelist%3Dfalse%26yt_unlimited_pts_skip_ads_promo_desktop_always%3Dtrue%26html5_min_buffer_to_resume%3D6%26disable_new_pause_state3%3Dtrue%26html5_check_for_reseek%3Dtrue%26html5_max_bandwidth_sample_duration%3D0.0%26html5_nnr_downgrade_count%3D16%26stop_using_ima_sdk_gpt_request_activity%3Dtrue%26mweb_pu_android_chrome_54_above%3Dtrue%26html5_retry_media_element_errors_delay%3D0&account_playback_token=QUFFLUhqbTFmb1pzM082WnpyUG5RRDMwaTI5dlc4Wkd6UXxBQ3Jtc0tuZWZMdUQ4N2luX0VPZ2dqR3kycVpSd196YzhrYnNxLU5CSGExbEpOeVRuU0tGWXhkNmRxNmNUaXZNSlFwdFRvZkVWeHdmVk1Ud3BFTEJYQ3V1dE9VZWRkRGRudmloWU1uelZqV2psYWItdGJOcXQ2cw%3D%3D&view_count=3289019&watch_xlb=https%3A%2F%2Fs.ytimg.com%2Fyts%2Fxlbbin%2Fwatch-strings-zh_CN-vflCpAPwi.xlb&enablecastapi=0&swf_player_response=1&avg_rating=4.9294052124&ssl=1&probe_url=https%3A%2F%2Fr15---sn-vgqs7ne6.googlevideo.com%2Fvideogoodput%3Fid%3Do-ACvYsgFOgWnOBpOuZOpJWNuWYmT0k25Qk1g_hJ1ptq3g%26source%3Dgoodput%26range%3D0-4999%26expire%3D1491410844%26ip%3D45.32.253.224%26ms%3Dpm%26mm%3D35%26pl%3D26%26nh%3DIgpwZjAzLm9yZDM1Kg02NC4xMjQuOTguMjUz%26sparams%3Did%2Csource%2Crange%2Cexpire%2Cip%2Cms%2Cmm%2Cpl%2Cnh%26signature%3D74A040587FCE22E824D11B37ECDA3BB6EFE6219C.48EDC225C42AD03496EB14A80ECDE5D43489EEA0%26key%3Dcms1&storyboard_spec=https%3A%2F%2Fi9.ytimg.com%2Fsb%2F7NN4RlKspW0%2Fstoryboard3_L%24L%2F%24N.jpg%7C48%2327%23100%2310%2310%230%23default%23AF-_AF3awpspx04feQNYYS-GMeY%7C80%2345%23138%2310%2310%232000%23M%24M%23WPrAJ2CPiCeqyeTBBAf2zf3Z6A4%7C160%2390%23138%235%235%232000%23M%24M%233U9qoqD0RbVsF73UqfuPV3QhJcc&thumbnail_url=https%3A%2F%2Fi.ytimg.com%2Fvi%2F7NN4RlKspW0%2Fdefault.jpg";//getStringFromInputStream(encoding, instream);
   if (videoInfo != null && videoInfo.length() > 0) {
    List<NameValuePair> infoMap = new ArrayList<NameValuePair>();
    URLEncodedUtils.parse(infoMap, new Scanner(videoInfo), encoding);
    String token = null;
    String downloadUrl = null;
    String filename = videoId;

    for (NameValuePair pair : infoMap) {
     String key = pair.getName();
     String val = pair.getValue();
     System.out.println(key + "=" + val);;
     log.finest(key + "=" + val);
     if (key.equals("token")) {
      token = val;
     } else if (key.equals("title")) {
      filename = val;
     } else if (key.equals("url_encoded_fmt_stream_map")) {
      String[] formats = commaPattern.split(val);
      for (String fmt : formats) {
       String[] fmtPieces = pipePattern.split(fmt);
       if (fmtPieces.length == 2) {
        // in the end, download somethin!
        downloadUrl = fmtPieces[1];
        int pieceFormat = Integer.parseInt(fmtPieces[0]);
        if (pieceFormat == format) {
         // found what we want
         downloadUrl = fmtPieces[1];
         break;
        }
       }
      }
     }
    }

    filename = cleanFilename(filename);
    if (filename.length() == 0) {
     filename = videoId;
    } else {
     filename += "_" + videoId;
    }
    filename += "." + extension;
    File outputfile = new File(outputdir, filename);
    downloadUrl = "https://r2---sn-ogueln7r.googlevideo.com/videoplayback?ratebypass=yes&mt=14…tube&mn=sn-ogueln7r&ip=45.32.253.224&mm=31&itag=22&type=video/mp4;+codecs=";//fmtPieces[1];
    if (downloadUrl != null) {
     downloadWithHttpClient(userAgent, downloadUrl, outputfile);
    }
   }
//  }
 }

 private static void downloadWithHttpClient(String userAgent, String downloadUrl, File outputfile) throws Throwable {
  HttpGet httpget2 = new HttpGet(downloadUrl);
  httpget2.setHeader("User-Agent", userAgent);

  log.finer("Executing " + httpget2.getURI());
  HttpClient httpclient2 = new DefaultHttpClient();
  HttpResponse response2 = httpclient2.execute(httpget2);
  HttpEntity entity2 = response2.getEntity();
  if (entity2 != null && response2.getStatusLine().getStatusCode() == 200) {
   long length = entity2.getContentLength();
   InputStream instream2 = entity2.getContent();
   log.finer("Writing " + length + " bytes to " + outputfile);
   if (outputfile.exists()) {
    outputfile.delete();
   }
   FileOutputStream outstream = new FileOutputStream(outputfile);
   try {
    byte[] buffer = new byte[2048];
    int count = -1;
    while ((count = instream2.read(buffer)) != -1) {
     outstream.write(buffer, 0, count);
    }
    outstream.flush();
   } finally {
    outstream.close();
   }
  }
 }

 private static String cleanFilename(String filename) {
  for (char c : ILLEGAL_FILENAME_CHARACTERS) {
   filename = filename.replace(c, '_');
  }
  return filename;
 }

 private static URI getUri(String path, List<NameValuePair> qparams) throws URISyntaxException {
  URI uri = URIUtils.createURI(scheme, host, -1, "/" + path, URLEncodedUtils.format(qparams, "UTF-8"), null);
  return uri;
 }

 private static void setupLogging() {
  changeFormatter(new Formatter() {
   @Override
   public String format(LogRecord arg0) {
    return arg0.getMessage() + newline;
   }
  });
  explicitlySetAllLogging(Level.FINER);
 }

 private static void changeFormatter(Formatter formatter) {
  Handler[] handlers = rootlog.getHandlers();
  for (Handler handler : handlers) {
   handler.setFormatter(formatter);
  }
 }

 private static void explicitlySetAllLogging(Level level) {
  rootlog.setLevel(Level.ALL);
  for (Handler handler : rootlog.getHandlers()) {
   handler.setLevel(defaultLogLevelSelf);
  }
  log.setLevel(level);
  rootlog.setLevel(defaultLogLevel);
 }

 private static String getStringFromInputStream(String encoding, InputStream instream) throws UnsupportedEncodingException, IOException {
  Writer writer = new StringWriter();

  char[] buffer = new char[1024];
  try {
   Reader reader = new BufferedReader(new InputStreamReader(instream, encoding));
   int n;
   while ((n = reader.read(buffer)) != -1) {
    writer.write(buffer, 0, n);
   }
  } finally {
   instream.close();
  }
  String result = writer.toString();
  return result;
 }
}

/**
 * <pre>
 * Exploded results from get_video_info:
 * 
 * fexp=90...
 * allow_embed=1
 * fmt_stream_map=35|http://v9.lscache8...
 * fmt_url_map=35|http://v9.lscache8...
 * allow_ratings=1
 * keywords=Stefan Molyneux,Luke Bessey,anarchy,stateless society,giant stone cow,the story of our unenslavement,market anarchy,voluntaryism,anarcho capitalism
 * track_embed=0
 * fmt_list=35/854x480/9/0/115,34/640x360/9/0/115,18/640x360/9/0/115,5/320x240/7/0/0
 * author=lukebessey
 * muted=0
 * length_seconds=390
 * plid=AA...
 * ftoken=null
 * status=ok
 * watermark=http://s.ytimg.com/yt/swf/logo-vfl_bP6ud.swf,http://s.ytimg.com/yt/swf/hdlogo-vfloR6wva.swf
 * timestamp=12...
 * has_cc=False
 * fmt_map=35/854x480/9/0/115,34/640x360/9/0/115,18/640x360/9/0/115,5/320x240/7/0/0
 * leanback_module=http://s.ytimg.com/yt/swfbin/leanback_module-vflJYyeZN.swf
 * hl=en_US
 * endscreen_module=http://s.ytimg.com/yt/swfbin/endscreen-vflk19iTq.swf
 * vq=auto
 * avg_rating=5.0
 * video_id=S6IZP3yRJ9I
 * token=vPpcFNh...
 * thumbnail_url=http://i4.ytimg.com/vi/S6IZP3yRJ9I/default.jpg
 * title=The Story of Our Unenslavement - Animated
 * </pre>
 */