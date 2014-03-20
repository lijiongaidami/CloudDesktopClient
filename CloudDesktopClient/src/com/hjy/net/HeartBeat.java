package com.hjy.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * ���������������
 * @author hjy
 *
 */
public class HeartBeat extends Thread{
	
	/**
	 * ��������˷�����������ʾ�Լ�����
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private boolean isDead = false;
	public void setisDead(boolean ll){
		isDead = ll;
	}
	
	public void sendHeartbeat() throws UnsupportedEncodingException, IOException{
		URL url = new URL(String.format("http://%s:8080/CloudDesktop/HeartbeatServlet", IpInfo.getIpString()));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		String sessionID = SendForm.getSessionID();
		// ����sessionID
		if (sessionID != null)
			connection.setRequestProperty("Cookie", sessionID);
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), "utf-8");
		out.write("Alive=true");
		out.flush();
		out.close();
		
		String currentLine = null;
		ArrayList<String> result = new ArrayList<String>();
		InputStream urlStream;
		urlStream = connection.getInputStream();
		BufferedReader urlReader = new BufferedReader(new InputStreamReader(
				urlStream));
		while ((currentLine = urlReader.readLine()) != null) {
			result.add(currentLine);
		}
		System.out.println(result.get(0));
		
	}

	/**
	 * ���̶߳��������������������ʹ������֪����������
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(isDead)
				break;
			try {
				this.sendHeartbeat();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
