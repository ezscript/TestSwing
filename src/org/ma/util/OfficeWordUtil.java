package org.ma.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class OfficeWordUtil {
	public static Map<String,String> getStringMap(String fileStartPath) {
		SimpleLog.INSTANCE.appendLine("start init fileMap:" + fileStartPath);
		
		Map<String,String> fileMap = new HashMap<String, String>();
		List<File> files = FileUtil.findAllFile(new File(fileStartPath), new FileUtil.IFilter() {
				public boolean isFilter(File f) {
					return f.getName().startsWith("~");
				}
			},"doc","docx");
		SimpleLog.INSTANCE.appendLine("doc/docx files size:" + files.size());
		for(File f : files ){
			String fileName = f.getName();
			String strTemp = null;
			if(fileName.endsWith("doc")){
				strTemp = readWORD(f.getPath());
			}else if(fileName.endsWith("docx")){
				strTemp =readWORD2007(f.getPath());
			}
			SimpleLog.INSTANCE.appendLine("init one :"+ f.getPath());
			fileMap.put(f.getPath(), strTemp);
		}
		return fileMap;
		
	}
	
	
	public static String findWord(String fileStartPath, String keyWord) {
		List<File> files = FileUtil.findAllFile(new File(fileStartPath),null,"doc","docx");
		files = FileUtil.filter(files, new FileUtil.IFilter() {
			public boolean isFilter(File f) {
				return f.getName().startsWith("~");
			}
		});
		if(files.size() == 0)
			return "û�ҵ��ļ�";
/*		for(File f : files ){
			System.out.println(f.getPath());
		}
		System.out.println(files.size() +"---------------------------------------------------");*/
 		
		int count = 0;
		StringBuffer result = new StringBuffer();
		for(File f : files ){
			String fileName = f.getName();
			String strTemp = null;
			if(fileName.endsWith("doc")){
				strTemp = readWORD(f.getPath());
			}else if(fileName.endsWith("docx")){
				strTemp =readWORD2007(f.getPath());
			}
			if(strTemp.indexOf(keyWord) != -1){
				System.out.println("�ҵ�:" + f.getPath());
				result.append( f.getPath()).append("\n");
				count++;
			}
			
		}
		System.out.println(count +"---------------------------------------------------");
		return result.toString();
	}
	
	
	// ��ȡdoc�ļ�
	public static String readWORD(String file) {
		String returnStr = "";
		try {
			WordExtractor wordExtractor = new WordExtractor(new FileInputStream(new File(file)));
			returnStr = wordExtractor.getText();
		}  catch (IOException e) {
			SimpleLog.INSTANCE.appendLine(ErrUtil.getErrMsg(e));
		}
		return returnStr;
	}



	 // ��ȡdocx�ļ�
	public static String readWORD2007(String file)  {
	    try {
			return new XWPFWordExtractor(POIXMLDocument.openPackage(file)).getText();
		}  catch (Exception e) {
			SimpleLog.INSTANCE.appendLine(ErrUtil.getErrMsg(e));
		}   
	     return "";
	}
	
	public static void main(String [] args){
		String str =  readWORD("E:\\����\\����ҵ���׼���� - 20150512\\5.֧��ϵ��\\GAWA 6002 ������֤��Ȩƽ̨ҵ��淶-20150511.doc");
		int index = str.indexOf("���");
		str.indexOf("Ԫ��",index);
	/*	String ss = "���	Ԫ�ر���	��������������	������Ӣ������	�Ƿ���Ϊ��ѯ����	����	˵��";
		
		
		System.out.println(ss.startsWith("���\tԪ�ر���"));*/
	}


	public static String findWordByMap(Map<String, String> fileMap, String keyWord) {
		if(fileMap.size() == 0)
			return "";
		Iterator<Entry<String,String>> it = fileMap.entrySet().iterator();
		StringBuffer result = new StringBuffer();
		int i = 0;
		for(;it.hasNext();){
			Entry<String,String> en = it.next();
			if(en.getValue().indexOf(keyWord) != -1){
				System.out.println("�ҵ�:" + en.getKey());
				result.append(en.getKey()).append("\n");
			}
			i++;
			System.out.println(en.getKey());
		}
		System.out.println(i);
		return result.toString();
	}
	
	
	
}
