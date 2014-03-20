package com.hjy.main;

import com.hjy.gui.LoginForm;
import com.hjy.net.IpInfo;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IpInfo ipInfo = new IpInfo();
		ipInfo.start();
		@SuppressWarnings("unused")
		LoginForm form = new LoginForm();
	}

}
