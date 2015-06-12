package org.ma;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.ma.entity.DBParam;
import org.ma.face.OptUrl;
import org.ma.util.DBUtil;
import org.ma.util.FrameUtil;
import org.ma.win.SelectFrame;


public class DataFrame extends JFrame  implements OptUrl{

	private JPanel contentPane;
	private static DataFrame frame;
	private DBParam dbParam;
	private Connection conn;
	JTextArea textArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataFrame frame = new DataFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static DataFrame getInstance(){
		if(frame== null){
			frame = new DataFrame();
		}
		return frame;
	}
	/**
	 * Create the frame.
	 */
	public DataFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 871, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(1, 1, 852, 35);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton button = new JButton("\u8F7D\u5165\u6570\u636E\u5E93");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UrlPanel panel = UrlPanel.getUrlPanel("load",frame);
				panel.setVisible(true);
			}
		});
		button.setBounds(14, 5, 110, 23);
		panel_1.add(button);
		
		JButton button_1 = new JButton("\u63D0\u4EA4\u672C\u6B21\u64CD\u4F5C");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					conn.commit();
				}catch(Exception Err){
					dealErr(Err);
				}
			}

		});
		button_1.setBounds(698, 4, 138, 23);
		panel_1.add(button_1);
		
		JButton btnsql = new JButton("\u6267\u884Csql");
		btnsql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					DBUtil.exe(conn,textArea.getText());
					FrameUtil.showMessage(frame,"Ö´ÐÐ³É¹¦");
				}catch(Exception Err){
					dealErr(Err);
				}
			}
		});
		btnsql.setBounds(141, 4, 93, 23);
		panel_1.add(btnsql);
		
		JButton button_2 = new JButton("\u67E5\u770B\u5F53\u524D\u6570\u636E");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectFrame frame = SelectFrame.getInstance(conn,dbParam);
				frame.setVisible(true);
			}
		});
		button_2.setBounds(267, 3, 119, 23);
		panel_1.add(button_2);
		
		JButton button_3 = new JButton("\u56DE\u6EDA");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					conn.rollback();
				}catch(Exception Err){
					dealErr(Err);
				}
			}
		});
		button_3.setBounds(605, 5, 86, 23);
		panel_1.add(button_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(1, 36, 850, 398);
		panel.add(scrollPane);
		
		textArea= new JTextArea();
		scrollPane.setViewportView(textArea);
	}
	
	public void optDBParam(DBParam dbParam, String type) {
		this.dbParam = dbParam;
		loadDB();
	}

	private void loadDB() {
		try{
			this.conn = DBUtil.getConn(dbParam);
			conn.setAutoCommit(false);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,e.getClass() +e.getMessage());
		}
		
	}
	private void dealErr(Exception e) {
		JOptionPane.showMessageDialog(frame,"Ê§°Ü\n" + e.getClass() +e.getMessage());
	}

}
