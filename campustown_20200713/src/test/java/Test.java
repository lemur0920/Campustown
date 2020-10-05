import java.awt.BorderLayout;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.site.service.impl.SiteServiceImpl;
import egovframework.com.asapro.statistics.service.StatisticsService;
import egovframework.com.asapro.statistics.service.impl.StatisticsServiceImpl;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.utl.sim.service.EgovNetworkState;





/**
 * 
 */

/**
 * @author yckim
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		
		//System.out.println("[asapro] Test : " + "guest_175.198.11.121".matches("guest_\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}"));
		/*
		try {
			File file = new File("C:/DEV/EGOV/eGovFrameDev-2.7.1-32bit/eGovFrameDev-2.7.1-32bit/workspace/asapro/src/main/webapp/uploadfile/member/test/test.jpg"); 
			FileUtils.copyURLToFile(new URL("https://lh3.googleusercontent.com/-JTTyVz_MzUk/AAAAAAAAAAI/AAAAAAAAAPA/BZhqETjW2N0/photo.jpg?sz=100"), file);
			System.out.println("[asapro] Test : " + file.length());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		/*
		try {
			
			String input = "/site/main/board/notice";
			Matcher m = Pattern.compile("\\/board\\/([a-zA-Z-_0-9]+)\\/?").matcher(input);
			if( m.find() ){
				System.out.println("[asapro] Test : " + m.group(1));
			}

			String input2 = "/site/main/board/notice/1/1234";
			Matcher m2 = Pattern.compile("\\/board\\/[a-zA-Z-_0-9]+\\/([0-9]+)\\/?").matcher(input2);
			if( m2.find() ){
				System.out.println("[asapro] Test : " + m2.group(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		/*
		String datapath = "G:/EGOV/eGovFrameDev-3.5.1-64bit/workspace/naa/src/main/resources";
	    String testResourcesDataPath = "G:/EGOV/eGovFrameDev-3.5.1-64bit/workspace/naa/src/main/resources/test-data/eurotest.png";
	    File imageFile = new File(testResourcesDataPath);
	    ITesseract instance = new Tesseract();
	    
	    //System.out.println(new File(datapath).getPath());
	    instance.setDatapath(new File(datapath).getPath());
	    instance.setLanguage("eng+kor");

	    try {
	      //ImageIO.scanForPlugins();
	      String result = instance.doOCR(imageFile);
	      System.out.println("result : " + result);
	    } catch (TesseractException e) {
	      System.out.println(e.getMessage());
	    }
	    
		
		
		String text = "1944. 일본 후쿠오카 규슈여고 졸업";
		System.out.println(text);
		text = text.replaceAll("\\. ", "</span>");
		System.out.println(text);
		*/
		
		
		//System.out.println((Math.round(212/1200.0 * 100 * 100)/100.0) + " %");
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
		SimpleDateFormat sdf1 = new SimpleDateFormat("Z", Locale.KOREA);
		System.out.println(sdf.format(new Date()) + sdf1.format(new Date()).substring(0, 3) + ":" + sdf1.format(new Date()).substring(3, 5) );
		*/
		
		/*
		String str1 = "phone@tel";
        String[] words1 = str1.split("@",0);
        String[] words2 = str1.split("@",1);
        String[] words3 = str1.split("@",3);
         
        for (String wo : words1 ){
            System.out.println(wo);
        }
        System.out.println(words1.length);
         
        for (String wo : words2 ){
            System.out.println(wo);
        }
        System.out.println(words2.length);
        for (String wo : words3 ){
        	System.out.println(wo);
        }
        System.out.println(words3.length);
        
        
      //System.out.println("[asapro] Test : " + "guest_175.198.11.121".matches("guest_\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}"));
      
      		*/
      	
		/*
        Image image = null;
        try {
        	final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        		@Override
        		public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
        		}
        		@Override
        		public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
        		}
        		@Override
        		public X509Certificate[] getAcceptedIssuers() {
        		return null;
        		}
        	} };


        	final SSLContext sslContext = SSLContext.getInstance( "SSL" );
        	sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
        	// Create an ssl socket factory with our all-trusting manager
        	final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        	
            URL url = new URL("https://www.textbook.or.kr/txtbkadmn/resources/NamoImage/images/000041/1_1.png");
            URLConnection conn = url.openConnection();
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
            HttpsURLConnection conn1 = (HttpsURLConnection) url.openConnection();
            
            image = ImageIO.read(conn.getInputStream());
            System.out.println(url);
            System.out.println(image);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
        */
		
		/*
		//===========  주간 달력 테스트  =========================
		//현제 일의 일요일 , 토요일 날짜
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentWeekCal = Calendar.getInstance(Locale.KOREA);
		
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
 		int year = Integer.parseInt(sdfYear.format(new Date()) );
 		int weekOfYear = currentWeekCal.get(Calendar.WEEK_OF_YEAR);
 		
 		
 		currentWeekCal.set(Calendar.YEAR, year);
 		//currentWeekCal.set(Calendar.MONTH, 11);
 		currentWeekCal.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
 		
// 		try {
//			currentWeekCal.setTime(sdf.parse("2018-12-01"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
 		//currentWeekCal.set(2018,11,1);
 		currentWeekCal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
 		String date = sdf.format(currentWeekCal.getTime());

 		System.out.println(date);

      	//전주의 일요일 , 토요일	날짜
 		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
 		Calendar lastWeekCal = Calendar.getInstance(Locale.KOREA);
 		lastWeekCal.add(Calendar.DATE, -7);
 		lastWeekCal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
 		String date2 = sdf2.format(lastWeekCal.getTime());

 		System.out.println(date2);
 		
 		//다음주의 일요일 , 토요일	날짜
 		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
 		Calendar nextWeekCal = Calendar.getInstance(Locale.KOREA);
 		nextWeekCal.add(Calendar.DATE, 14);
 		nextWeekCal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
 		String date3 = sdf3.format(nextWeekCal.getTime());

 		System.out.println(date3);
		*/
		
		//======================================================================
		//System.out.println(EgovNetworkState.getMyIPaddress());
		//System.out.println(EgovNetworkState.getMyMACAddress(EgovNetworkState.getMyIPaddress()) );
		
		//=============================================================================
		StatisticsService statisticsService = new StatisticsServiceImpl();
		statisticsService.statisticsDailyBatch();
		
		
	}
	

}
