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
        
		Socket socket = null; // ������ �Է½�Ʈ���� ��´�. 
		
		String pinNo = "0";
		String pinVal = "0";
		
		if(request.getParameter("pinNo") != null )
			pinNo = request.getParameter("pinNo");
		
		if(request.getParameter("pinVal") != null )
			pinVal = request.getParameter("pinVal");
		
		String reqData = "$" + pinNo + ":" + pinVal + "\n";
		
		System.out.println(reqData);
        
		try{
			String serverIP = "192.168.123.101"; // 127.0.0.1 & localhost ���� 
			System.out.println("������ �������Դϴ�. ���� IP : " + serverIP); // ������ �����Ͽ� ������ ��û�Ѵ�. 
			socket = new Socket(serverIP, 23); // ������ �Է½�Ʈ���� ��´�. 

			//socket.setSoTimeout(500); 

            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
	        
            bw.write(reqData);            // Ŭ���̾�Ʈ�� ������ ����
            bw.flush();

            

            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            
            String data=null;
            data=br.readLine();
            System.out.println("Ŭ���̾�Ʈ�� ���� ���� ������:" + data);
            System.out.println("****** ���� �Ϸ� ****");
            logger.debug("log****** ���� �Ϸ� ****");
            
            
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
                System.out.println("-------���� �����------");
                socket = server.accept();         // Ŭ���̾�Ʈ�� �����ϸ� ����� �� �ִ� ���� ��ȯ
                System.out.println(socket.getInetAddress() + "�� ���� �����û�� ����");
                
                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                // Ŭ���̾�Ʈ�κ��� �����͸� �ޱ� ���� InputStream ����
                
                String data=null;
                data=br.readLine();
                System.out.println("Ŭ���̾�Ʈ�� ���� ���� ������:" + data);
                
                receiveData(data, socket);         // ���� �����͸� �״�� �ٽ� ������
                System.out.println("****** ���� �Ϸ� ****");
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
            // Ŭ���̾�Ʈ�κ��� �����͸� ������ ���� OutputStream ����
            
            bw.write(data);            // Ŭ���̾�Ʈ�� ������ ����
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
