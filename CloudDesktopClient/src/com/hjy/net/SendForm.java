package com.hjy.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * ģ��HTML���ύ����
 * 
 * @author JustYoung
 * 
 */
public class SendForm {

	private static String sessionValue = null;
	/**
	 * ����������͵�¼����
	 * 
	 * @throws IOException
	 */
	public static void sendToServer(String username, String password)
			throws IOException {
		URL url = new URL(String.format("http://%s:8080/CloudDesktop/LoginServlet", IpInfo.getIpString()));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), "utf-8");
		out.write(String.format("userName=%s&password=%s", username, password));
		out.flush();
		// ��¼sessionID,�����´�ʹ��
		sessionValue = connection.getHeaderField("Set-Cookie").split(";")[0].trim();
		System.out.println(sessionValue);
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
		if (result.get(0).trim().equals("SuccessedLogin")) {
			String clientInfo = result.get(1);
			String clientname = clientInfo
					.substring(0, clientInfo.indexOf(',')).trim();
			String clientpassword = clientInfo.substring(
					clientInfo.indexOf(',') + 1).trim();
			Rdesktop.runRdesktop(clientname, clientpassword, IpInfo.getIpString());
			HeartBeat heart = new HeartBeat();
			heart.start();
		} else {
			JOptionPane.showMessageDialog(null, "�û���������������������룡");
		}
	}
	
	/**
	 * ������������˳�����
	 * @throws IOException
	 */
	public static void Logout() throws IOException{
		URL url = new URL(String.format("http://%s:8080/CloudDesktop/DeleteSession", IpInfo.getIpString()));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		// ����sessionID
		connection.setRequestProperty("Cookie", sessionValue);
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), "utf-8");
		out.write("logout=true");
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
	 * �����ڷ������˵�SessionID����CookieID
	 * @return
	 */
	public static String getSessionID(){
		return sessionValue;
	}
}
