package org.ma.util;

import java.awt.Component;
import java.io.File;

import javax.swing.filechooser.FileFilter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FrameUtil {
	public static void showMessage(Component parentComponent,
	        Object message){
		JOptionPane.showMessageDialog(parentComponent,message);
	}
	
	
	public static void showError(Component parentComponent, Exception e){
		FrameUtil.showMessage(parentComponent,e.getClass() +e.getMessage());
	}
	
	/**
	 * 获取文件对话框
	 */
	public static JFileChooser getJFileChooser(FileFilter filter){
		  JFileChooser c = new JFileChooser();
		  if(filter != null)
			  c.setFileFilter(filter);
		  return c;
	}
}
