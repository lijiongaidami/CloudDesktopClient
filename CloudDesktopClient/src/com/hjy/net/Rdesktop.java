package com.hjy.net;

import java.io.IOException;

public class Rdesktop {

	public static void runRdesktop(String name, String password, String ip) {
		String shellPath = System.getProperty("usr.dir")
				+ "/shellResource/rdp.sh";
		String cmd = String.format("bash %s %s %s %s", shellPath, ip, name,
				password);
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
