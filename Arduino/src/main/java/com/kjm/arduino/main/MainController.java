package com.kjm.arduino.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass() );
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView main(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/main");
		
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
		Socket socket = null; // 소켓의 입력스트림을 얻는다. 
		
		String pinNo = "0";
		String pinVal = "0";
		
		if(request.getParameter("pinNo") != null )
			pinNo = request.getParameter("pinNo");
		
		if(request.getParameter("pinVal") != null )
			pinVal = request.getParameter("pinVal");
		
		String reqData = "$" + pinNo + ":" + pinVal + "\n";
		
		System.out.println(reqData);
        
		try{
			String serverIP = "192.168.123.101"; // 127.0.0.1 & localhost 본인 
			System.out.println("서버에 연결중입니다. 서버 IP : " + serverIP); // 소켓을 생성하여 연결을 요청한다. 
			socket = new Socket(serverIP, 23); // 소켓의 입력스트림을 얻는다. 

			//socket.setSoTimeout(500); 

            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
	        
            bw.write(reqData);            // 클라이언트로 데이터 전송
            bw.flush();

            

            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            
            String data=null;
            data=br.readLine();
            System.out.println("클라이언트로 부터 받은 데이터:" + data);
            System.out.println("****** 전송 완료 ****");
            logger.debug("log****** 전송 완료 ****");
            
            
            HashMap<String,String> map = new Gson().fromJson(data, new TypeToken<HashMap<String, String>>(){}.getType());

            br.close();
            isr.close();
            is.close();
            
            
			socket.close();
			
			mav.addObject("resp", map);
			
		}catch(Exception e){
			System.out.println(e.toString());
			logger.debug(e.toString());
		}finally {
            try{
                bw.close();
                osw.close();
                os.close();
                
                br.close();
                isr.close();
                is.close();
                socket.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
		
		return mav;
	}
	
	
	public void ServerRun() throws IOException{
        
        ServerSocket server = null;
        int port = 4200;
        Socket socket = null;
        
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
        
        
        try{
            server = new ServerSocket(port);
            while(true){
                System.out.println("-------접속 대기중------");
                socket = server.accept();         // 클라이언트가 접속하면 통신할 수 있는 소켓 반환
                System.out.println(socket.getInetAddress() + "로 부터 연결요청이 들어옴");
                
                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                // 클라이언트로부터 데이터를 받기 위한 InputStream 선언
                
                String data=null;
                data=br.readLine();
                System.out.println("클라이언트로 부터 받은 데이터:" + data);
                
                receiveData(data, socket);         // 받은 데이터를 그대로 다시 보내기
                System.out.println("****** 전송 완료 ****");
            }
        }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try{
                    br.close();
                    isr.close();
                    is.close();
                    server.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    
    
    public void receiveData(String data, Socket socket){
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        
        try{
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            // 클라이언트로부터 데이터를 보내기 위해 OutputStream 선언
            
            bw.write(data);            // 클라이언트로 데이터 전송
            bw.flush();
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            try{
                bw.close();
                osw.close();
                os.close();
                socket.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
