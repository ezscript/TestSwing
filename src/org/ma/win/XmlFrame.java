package org.ma.win;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import org.ma.util.FrameUtil;
import org.ma.util.ReflectUtil;
import org.ma.util.XmlUtil;

public class XmlFrame extends JFrame {

	private JPanel contentPane;
	private JTextArea textMain;
	private static XmlFrame _xf = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XmlFrame frame = XmlFrame.getInstance();
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
	public XmlFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 349);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u6587\u4EF6");
		menuBar.add(menu);

		JMenuItem itemOpen = new JMenuItem("\u6253\u5F00");
		itemOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jc = FrameUtil.getJFileChooser(new FileFilter() {
					public boolean accept(File f) {
						return true;
					}
					public String getDescription() {
						return "选择xml相关文件";
					}
				});
				int rVal = jc.showOpenDialog(_xf);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					try { // try ,catch 抛出，捕获 异常
						BufferedReader br = ReflectUtil.getBufferReader(jc.getSelectedFile());
						StringWriter buffer = new StringWriter();
						String temp = null;
						while((temp = br.readLine()) != null)
							buffer.append(temp+"\r\n");
						textMain.setText(buffer.toString());
					} catch (IOException e1) {
						e1.printStackTrace(); // 抛出异常时执行的代码
						FrameUtil.showMessage(_xf, "打开文件异常");
					}
				}
			}
		});
		menu.add(itemOpen);

		JMenuItem menuItem = new JMenuItem("\u4FDD\u5B58");
		menu.add(menuItem);

		JMenu menu_1 = new JMenu("\u64CD\u4F5C");
		menuBar.add(menu_1);

		JMenuItem itemFormat = new JMenuItem("\u683C\u5F0F\u5316");
		itemFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//formatByCode
				textMain.setText(XmlUtil.formatByCode(textMain.getText()));
			}
		});
		menu_1.add(itemFormat);

		JMenuItem menuItem_2 = new JMenuItem("New menu item");
		menu_1.add(menuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textMain = new JTextArea();
		scrollPane.setViewportView(textMain);
	}

	public static XmlFrame getInstance() {
		if (_xf != null)
			return _xf;
		_xf = new XmlFrame();
		return _xf;
	}

}
