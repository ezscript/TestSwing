package org.ma;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.ma.entity.DBParam;
import org.ma.face.OptUrl;
import org.ma.face.ShowResult;
import org.ma.util.DBUtil;

public class CompareFrame extends JFrame implements ShowResult,OptUrl{

	private JPanel contentPane;
	public DBParam param_target = null;
	protected DBParam param_src =null;
	private static CompareFrame frame;
	
	
	
	public JTextArea t_result = new JTextArea();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompareFrame frame = getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static CompareFrame getInstance(){
		if(frame== null){
			frame = new CompareFrame();
		}
		return frame;
	}

	/**
	 * Create the frame.
	 */
	public CompareFrame() {
		setTitle("\u6570\u636E\u5E93\u6BD4\u8F83");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 741, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("\u8F7D\u5165\u5DE6\u8FB9\u6570\u636E\u5E93");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UrlPanel panel = UrlPanel.getUrlPanel("load",frame);
				panel.setVisible(true);
			}
		});
		button.setBounds(36, 44, 144, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u8F7D\u5165\u5F85\u53F3\u8FB9\u6570\u636E\u5E93");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UrlPanel panel = UrlPanel.getUrlPanel("compare",frame);
				panel.setVisible(true);
			}
		});
		button_1.setBounds(201, 44, 144, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("\u6BD4\u8F83");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compareDB();
			}
		});
		button_2.setBounds(570, 44, 93, 23);
		contentPane.add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 77, 631, 313);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(t_result);
		
	}

	public void loadDB() {
		try{
			Connection conn = DBUtil.getConn(param_src);
		//	PreparedStatement preparedstatement = conn.prepareStatement("");//"alter sequence " + s + " increment by 1 nocache"
         //   preparedstatement.executeUpdate();
            
//			PreparedStatement preparedstatement = conn.prepareStatement("");	     //"select " + s + ".nextval from dual"      
//            ResultSet resultset = preparedstatement.executeQuery();
			param_src.fillSelf(conn);
            
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,e.getClass() +  e.getMessage());
		}
	}

	public void loadCompareDB() {
		try{
			Connection conn = DBUtil.getConn(param_target);
			param_target.fillSelf(conn);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,e.getClass() +e.getMessage());
		}
	}
	
	public void compareDB() {
		try{
			DBUtil.compare(param_src,param_target,this);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,e.getClass() +e.getMessage());
		}
	}

	public void appendResultN(String str) {
		t_result.append(str+ "\n");
	}

	
	public void appendDiffMap(Map<String, List<String>> map) {
		for(String key : map.keySet())
		{
			appendResultN("±í£º"+ key);
			for(String str :map.get(key)){
				appendResultN(str);	
			}
		} 
		
	}

	public void optDBParam(DBParam dbParam, String type) {
		if("load".equals(type)){
			param_src = dbParam;	
			loadDB();
		}else if("compare".equals(type)){
			param_target = dbParam;
			loadCompareDB();
		}
	}
}
