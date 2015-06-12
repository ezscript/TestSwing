package org.ma.win;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.ma.entity.DBParam;
import org.ma.util.DBUtil;
import org.ma.util.FrameUtil;

public class SelectFrame extends JFrame {

	private JPanel contentPane;
	JTextArea sqlArea;
	JTextArea resultArea;
	private static SelectFrame frame;
	private Connection conn;
	private DBParam dbParam;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectFrame frame = new SelectFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static SelectFrame getInstance(Connection conn, DBParam dbParam) {
		frame = getInstance();
		frame.conn = conn;
		frame.dbParam = dbParam;
		return frame;
	}
	public static SelectFrame getInstance(){
		if(frame== null){
			frame = new SelectFrame();
		}
		return frame;
	}
	/**
	 * Create the frame.
	 */
	public SelectFrame() {
		setTitle("\u67E5\u8BE2");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 839, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("\u67E5\u8BE2");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = sqlArea.getText().trim();
				try{
					ResultSet rs = DBUtil.getResultSet(conn, sql);
					ResultSetMetaData rsm = rs.getMetaData();
					int	colNum = rsm.getColumnCount();
					resultArea.setText("");
		
					for (int i = 1; i <= colNum; i++) {
						String lName  = rsm.getColumnLabel(i);
						resultArea.append(lName+"\t");
					}
					resultArea.append("\n--------------------------------------------\n");
					while(rs.next()){
						
						for (int i = 1; i <= colNum; i++) {
							Object o = rs.getObject(i);
						//	String cName = rsm.getColumnName(i);
							if(o == null){
								o = " ";
							}
							resultArea.append(o.toString()+"\t");
						}
						resultArea.append("\n");			
					}
				}catch(Exception ex){
					ex.printStackTrace();
					FrameUtil.showError(frame, ex);
				}
			}
		});
		button.setBounds(714, 29, 107, 23);
		contentPane.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 10, 664, 92);
		contentPane.add(scrollPane);
		
		sqlArea = new JTextArea();
		scrollPane.setViewportView(sqlArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 130, 811, 298);
		contentPane.add(scrollPane_1);
		
		resultArea= new JTextArea();
		scrollPane_1.setViewportView(resultArea);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 112, 811, 8);
		contentPane.add(panel);
		
		JButton btnexcel = new JButton("\u5BFC\u51FAexcel");
		btnexcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = sqlArea.getText().trim();
				try{
					ResultSet rs = DBUtil.getResultSet(conn, sql);
					String msg = DBUtil.excelOut(rs);
					FrameUtil.showMessage(frame,msg);
				}catch(Exception ex){
					FrameUtil.showError(frame, ex);
					ex.printStackTrace();
				}
				
			}
		});
		btnexcel.setBounds(714, 62, 107, 23);
		contentPane.add(btnexcel);
	}
}
