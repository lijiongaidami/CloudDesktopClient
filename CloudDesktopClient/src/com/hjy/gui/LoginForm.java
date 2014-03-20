package com.hjy.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hjy.net.IpInfo;
import com.hjy.net.SendForm;

public class LoginForm extends JFrame {

	private static final long serialVersionUID = 1L;
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	int screenHeight = screenSize.height;
	int screenWidth = screenSize.width;
	private String FrameName = "��ӭʹ�������棬���¼��";

	public LoginForm() {
		setTitle(this.FrameName);
		Image img = kit.getImage(System.getProperty("user.dir")
				+ "/imageResource/FrameTitle.jpg");
		setIconImage(img);
		toFront();
		setBack();
		setPanel(this);
		setResizable(false);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * �������
	 * 
	 * @param jframe
	 */
	public void setPanel(JFrame jframe) {

		// �û���panel
		JPanel usernamePanel = new JPanel();
		JLabel usernamelabel = new JLabel("�û�����", SwingConstants.RIGHT);
		final JTextField usernameText = new JTextField(20);
		usernamePanel.add(usernamelabel);
		usernamePanel.add(usernameText);
		usernamePanel.setBounds(screenWidth / 2 - 300, screenHeight / 2 - 200,
				500, 60);
		usernamePanel.setOpaque(false);

		// ����panel
		JPanel pwdPanel = new JPanel();
		JLabel pwdlabel = new JLabel("��  �룺", SwingConstants.RIGHT);
		final JPasswordField pwdText = new JPasswordField(20);
		pwdPanel.add(pwdlabel);
		pwdPanel.add(pwdText);
		pwdPanel.setBounds(screenWidth / 2 - 300, screenHeight / 2 - 200 + 60,
				500, 60);
		pwdPanel.setOpaque(false);

		// ��ťpanel
		JPanel buttonPanel = new JPanel();
		JButton loginButton = new JButton("��¼");
		JButton exitButton = new JButton("�˳�");
		buttonPanel.add(loginButton);
		buttonPanel.add(exitButton);
		buttonPanel.setBounds(screenWidth / 2 - 300, screenHeight / 2 - 200
				+ 60 + 60, 500, 60);
		buttonPanel.setOpaque(false);

		// ��ʾ��Ϣ
		final JLabel notice = new JLabel("��δ�ҵ�������");
		JPanel noticePanel = new JPanel();
		noticePanel.add(notice);
		notice.setVisible(false);
		notice.setForeground(Color.RED);
		notice.setBounds(screenWidth / 2 - 300, screenHeight / 2 - 200 + 60
				+ 60 + 60, 500, 60);
		notice.setOpaque(false);

		// ��Ӱ�ť�¼�
		loginButton.addActionListener(new ActionListener() {
			// ��¼��ť�¼�
			@Override
			public void actionPerformed(ActionEvent e) {
				if (IpInfo.getIpString() == null) {
					JOptionPane.showMessageDialog(null, "��ʱ�޷����ӷ������������Ƿ�����δ������");
					return;
				}
				// TODO Auto-generated method stub
				try {
					String password = new String(pwdText.getPassword());
					SendForm.sendToServer(usernameText.getText(), password);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				notice.setVisible(false);
			}
		});

		exitButton.addActionListener(new ActionListener() {
			// �˳���ť�¼�
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					SendForm.Logout();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		jframe.setLayout(null);
		jframe.add(usernamePanel);
		jframe.add(pwdPanel);
		jframe.add(buttonPanel);
		jframe.add(noticePanel);
	}

	/**
	 * ���ñ���ͼƬ
	 */
	public void setBack() {
		((JPanel) this.getContentPane()).setOpaque(false);
		ImageIcon img = new ImageIcon(System.getProperty("user.dir")
				+ "/imageResource/FrameBackgroud.jpg");
		JLabel background = new JLabel(img);
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		this.setSize(img.getIconWidth(), img.getIconHeight());
		this.setUndecorated(true);
//		this.setSize(screenWidth, screenHeight);
	}

}
