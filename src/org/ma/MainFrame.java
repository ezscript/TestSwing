package org.ma;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import org.ma.win.XmlFrame;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("\u4E3B\u754C\u9762");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 266, 290);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u7A0B\u5E8F");
		menuBar.add(menu);
		
		JMenuItem menuItem_1 = new JMenuItem("\u6570\u636E\u5E93\u6BD4\u8F83");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompareFrame frame  =CompareFrame.getInstance();
				frame.setVisible(true);
				
			}
		});
		menu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u6570\u636E\u5E93\u64CD\u4F5C");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 DataFrame instance =	DataFrame.getInstance();
				 instance.setVisible(true);
			}
		});
		menu.add(menuItem_2);
		
		JMenuItem menuItem = new JMenuItem("doc\u67E5\u627E");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PublicStandard.getInstance().setVisible(true);
			}
		});
		menu.add(menuItem);
		
		JMenu menu_1 = new JMenu("\u5176\u4ED6");
		menu.add(menu_1);
		
		JMenuItem mntmXml = new JMenuItem("\u6587\u672C\u7F16\u8F91");
		mntmXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				XmlFrame xf = XmlFrame.getInstance();
				xf.setVisible(true);
			}
		});
		menu_1.add(mntmXml);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
