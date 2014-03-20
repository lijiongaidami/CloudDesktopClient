package com.hjy.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPListen extends Thread{
	private ServerSocket server = null;
	private Socket socket = null;
	private BufferedReader in = null;
	
	private void startListen() throws IOException{
		server = new ServerSocket(8081);
		String result = null;
		while (true){
			socket = server.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = in.readLine();
			System.out.println(result);
			if (result.equals("welcome")){
				System.out.println(socket.getInetAddress().getHostAddress());
				IpInfo.setIpString(socket.getInetAddress().getHostAddress());
				break;
			}
		}
		IpInfo.isContinue = false;
		socket.close();
		server.close();
	}
	
	public void run(){
		try {
			this.startListen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
