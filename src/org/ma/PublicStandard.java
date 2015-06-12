package org.ma;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.ma.util.ErrUtil;
import org.ma.util.FrameUtil;
import org.ma.util.OfficeWordUtil;
import org.ma.util.SimpleLog;
import org.ma.win.TextWin;
import javax.swing.JScrollPane;


public class PublicStandard extends JFrame {

	private JPanel contentPane;
	private JTextField keyWordField;
	private JTextArea resultArea;
	
	private static PublicStandard _publicStandard = null;
	public static PublicStandard getInstance(){
		if(_publicStandard == null)
			_publicStandard = new PublicStandard();
		return _publicStandard;
		
	}
	
	private String fileStartPath = "E:/部标";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublicStandard frame = getInstance();
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
	public PublicStandard() {
		setTitle("\u90E8\u6807\u67E5\u627E");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 649, 434);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u83DC\u5355");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("\u8D77\u59CB\u4F4D\u7F6E");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  String path = null;  
				  JFileChooser fileChooser = new JFileChooser();  
				  FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句  
				  System.out.println(fsv.getHomeDirectory());                //得到桌面路径  
				  fileChooser.setCurrentDirectory(fsv.getHomeDirectory());  
				  fileChooser.setDialogTitle("请选择要上传的文件...");  
				  fileChooser.setApproveButtonText("确定");  
				 // fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
				  fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
				  int result = fileChooser.showOpenDialog(_publicStandard);  
				  if (JFileChooser.APPROVE_OPTION == result) {  
				      path=fileChooser.getSelectedFile().getPath();  
				      System.out.println("path: "+path);  
				      fileStartPath = path;
				  }  
			}
		});
		menu.add(menuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		splitPane.setLeftComponent(panel);
		
		keyWordField = new JTextField();
		panel.add(keyWordField);
		keyWordField.setColumns(30);
		
		JButton btnFind = new JButton("\u67E5\u627E");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					findByKey(keyWordField.getText());
				}catch(Exception err){
				
				}
			}
		});
		btnFind.setFont(new Font("华文中宋", Font.PLAIN, 12));
		panel.add(btnFind);
		
		JButton button = new JButton("\u65E5\u5FD7");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextWin.getInstance().showIt("日志",SimpleLog.INSTANCE.getLog());
			}
		});
		button.setFont(new Font("华文中宋", Font.PLAIN, 12));
		panel.add(button);
		
		JButton button_1 = new JButton("\u6253\u5F00\u9009\u4E2D\u7684\u6587\u4EF6");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		button_1.setFont(new Font("华文中宋", Font.PLAIN, 12));
		panel.add(button_1);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		resultArea = new JTextArea();
		scrollPane.setViewportView(resultArea);
	}
	
	Map<String,String> fileMap = null;
	TempData td= null;
	private void findByKey(String text) {
		SimpleLog.INSTANCE.clear();
		SimpleLog.INSTANCE.appendLine("start .....");
		if(null == text)
			return;
		text = text.trim();
		if( "".equals(text))
			return;
		if(fileStartPath == null){
			FrameUtil.showMessage(_publicStandard, "请先选择路径: 菜单-起始位置");
			return;
		}
		System.out.println(fileStartPath + ":" + text);
		if(td == null || fileMap == null ){
			fileMap = OfficeWordUtil.getStringMap(fileStartPath);
			System.out.println("td == null");
			td = new TempData(fileStartPath);
		}else{
			TempData tdNew = new TempData(fileStartPath);
			if(!td.isSame(tdNew)){
				fileMap = OfficeWordUtil.getStringMap(fileStartPath);
				System.out.println("!td.isSame(tdNew)");
				td = tdNew;
			}
		}
		resultArea.setText(OfficeWordUtil.findWordByMap(fileMap,text));
		SimpleLog.INSTANCE.appendLine("end ...:" +fileStartPath + ":" + text );
		
		
	}
	
	private void openFile(){
		String fileName = resultArea.getSelectedText().trim();
		Desktop dt = Desktop.getDesktop();
		try {
			dt.open(new File(fileName));
		} catch (IOException e) {
			SimpleLog.INSTANCE.appendLine(ErrUtil.getErrMsg(e));
		}
	}
	
	private class TempData{
		private String fileStartPath = null;
		public TempData(String fileStartPath){
			this.fileStartPath = fileStartPath;
		}
		public boolean isSame(TempData td){
			return this.fileStartPath.equals(td.fileStartPath);
		}
	}
}
