package org.ma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.ma.entity.DBParam;
import org.ma.face.OptUrl;

public class UrlPanel extends JFrame {

	private JPanel contentPane;
	private JTextField t_url;
	private JTextField t_name;
	private JTextField t_password;
	private JTextField t_driverClass;
	OptUrl optUrl;
	private static UrlPanel urlPanel =null;
	private String type = "load";
	
	/**
	 * Create the frame.
	 */
	public static UrlPanel getUrlPanel(String t,OptUrl main) {
		if(urlPanel == null){
			urlPanel = new UrlPanel();
		}
		urlPanel.type = t;
		urlPanel.optUrl = main;
		
		if("load".equals(t)){
			urlPanel.setTitle("‘ÿ»Î◊Û±ﬂ");
		}else if("compare".equals(t)){
			urlPanel.setTitle("‘ÿ»Î”“±ﬂ");
		}
		return urlPanel;
	}

	/**
	 * Create the frame.
	 */
	public UrlPanel() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 503, 242);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		urlPanel = this;
		JButton button = new JButton("\u786E\u8BA4");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBParam dbParam = new DBParam();
				dbParam.url = t_url.getText();
				dbParam.name = t_name.getText();
				dbParam.password = t_password.getText();
				dbParam.driverClass = t_driverClass.getText();
				optUrl.optDBParam(dbParam, type);
				urlPanel.setVisible(false);
				
			}
		});
		button.setBounds(341, 179, 73, 23);
		contentPane.add(button);
		
		t_url = new JTextField();
		t_url.setText("jdbc:mysql://localhost:3306/netmeter_demo");
		t_url.setBounds(96, 26, 373, 21);
		contentPane.add(t_url);
		t_url.setColumns(10);
		
		JLabel lblDburl = new JLabel("URL\uFF1A");
		lblDburl.setBounds(50, 26, 43, 19);
		contentPane.add(lblDburl);
		
		t_name = new JTextField();
		t_name.setText("root");
		t_name.setColumns(10);
		t_name.setBounds(96, 68, 373, 21);
		contentPane.add(t_name);
		
		JLabel lblName = new JLabel("name\uFF1A");
		lblName.setBounds(44, 70, 49, 15);
		contentPane.add(lblName);
		
		t_password = new JTextField();
		t_password.setText("root");
		t_password.setColumns(10);
		t_password.setBounds(96, 108, 373, 21);
		contentPane.add(t_password);
		
		JLabel lblPassword = new JLabel("password\uFF1A");
		lblPassword.setBounds(20, 110, 73, 15);
		contentPane.add(lblPassword);
		
		JLabel lblDriverclass = new JLabel("driverClass\uFF1A");
		lblDriverclass.setBounds(10, 139, 93, 15);
		contentPane.add(lblDriverclass);
		
		t_driverClass = new JTextField();
		t_driverClass.setText("com.mysql.jdbc.Driver");
		t_driverClass.setColumns(10);
		t_driverClass.setBounds(96, 139, 373, 21);
		contentPane.add(t_driverClass);
		
		urlPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
