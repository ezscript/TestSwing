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
			return "没找到文件";
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
				System.out.println("找到:" + f.getPath());
				result.append( f.getPath()).append("\n");
				count++;
			}
			
		}
		System.out.println(count +"---------------------------------------------------");
		return result.toString();
	}
	
	
	// 读取doc文件
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



	 // 读取docx文件
	public static String readWORD2007(String file)  {
	    try {
			return new XWPFWordExtractor(POIXMLDocument.openPackage(file)).getText();
		}  catch (Exception e) {
			SimpleLog.INSTANCE.appendLine(ErrUtil.getErrMsg(e));
		}   
	     return "";
	}
	
	public static void main(String [] args){
		String str =  readWORD("E:\\部标\\网安业务标准总览 - 20150512\\5.支撑系列\\GAWA 6002 网安认证授权平台业务规范-20150511.doc");
		int index = str.indexOf("序号");
		str.indexOf("元素",index);
	/*	String ss = "序号	元素编码	数据项中文名称	数据项英文描述	是否做为查询条件	必填	说明";
		
		
		System.out.println(ss.startsWith("序号\t元素编码"));*/
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
				System.out.println("找到:" + en.getKey());
				result.append(en.getKey()).append("\n");
			}
			i++;
			System.out.println(en.getKey());
		}
		System.out.println(i);
		return result.toString();
	}
	
	
	
}
