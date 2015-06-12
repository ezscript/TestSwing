package org.ma.win;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class TextWin extends JFrame {

	private JPanel contentPane;
	JTextArea tf;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextWin frame = new TextWin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static TextWin _frame;
	public static TextWin getInstance() {
		if(null == _frame){
			_frame = new TextWin();
		}
		return _frame;
	}
	/**
	 * Create the frame.
	 */
	public TextWin() {
		setTitle("\u6570\u636E");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 423, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		tf = new JTextArea();
		scrollPane.setViewportView(tf);
	}
	public void showIt(String title, String msg) {
		_frame.setVisible(true);
		_frame.setTitle(title);
		_frame.tf.setText(msg);
	}
}
