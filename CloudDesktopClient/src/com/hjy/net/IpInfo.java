package com.hjy.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * �ڸ����в��ҷ������������ط�������IP
 * 
 * @author hjy
 * 
 */
public class IpInfo extends Thread {

	// ��������IP
	private static String IpString = null;

	// ������IP
	private static String localIp = null;
	public static boolean isContinue = true;

	/**
	 * ���ط�������IP
	 * 
	 * @return
	 */
	public static String getIpString() {
		return IpString;
	}

	public static void setIpString(String ip) {
		IpString = ip;
	}

	/**
	 * ����UDP���ݱ�
	 * 
	 * @param content
	 *            ��������
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public static void UdpSend(String content) throws SocketException,
			UnknownHostException {
		DatagramSocket ds = new DatagramSocket();
		DatagramPacket dp = new DatagramPacket(content.getBytes(),
				content.getBytes().length,
				InetAddress.getByName("255.255.255.255"), 8081);
		try {
			ds.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ds.close();
		}
	}

	/**
	 * ��ʼ����UDP���ݱ�
	 */
	public void run() {
		TCPListen tcpListen = new TCPListen();
		tcpListen.start();
		while (true) {
			if (!isContinue) {
				isContinue = true;
				break;
			}
			try {
				// �㲥����
				UdpSend("CloudDesktop");
			} catch (SocketException | UnknownHostException e) {
				// TODO Auto-generated catch + localIpblock
				e.printStackTrace();
			}
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ȡ����IP��ַ,�����bug,����˫�����Լ�VM����������
	 * 
	 * @throws SocketException
	 */
	public static String getLocalIp() {
		if (localIp != null)
			return localIp;
		Enumeration<NetworkInterface> e = null;
		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ip = null;
		while (e.hasMoreElements()) {
			Enumeration<InetAddress> ee = e.nextElement().getInetAddresses();
			while (ee.hasMoreElements()) {
				ip = ee.nextElement().getHostAddress();
				if (ip.contains(":") || ip.equals("127.0.0.1"))
					continue;
				localIp = ip;
				return ip;
			}
		}
		return null;

	}

}
