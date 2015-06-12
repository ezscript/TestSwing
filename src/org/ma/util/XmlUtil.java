package org.ma.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class XmlUtil {
	/**
	 * 通过输入流获取Document;
	 * @param is
	 * @return
	 */
	public static Document getDocumentByIS(InputStream is) {
		SAXReader reader = new SAXReader();
		
		Document doc = null;
		try {
			 doc = reader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return doc;
	}
	
	public static Document  readDocumentByFileName(String fileName) {
		try {
			Document doc =getDocumentByIS(new FileInputStream(fileName));
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public static void printDoc(Document doc,OutputStream out) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		// OutputFormat.createCompactFormat(); //紧凑格式
		// OutputFormat.createPrettyPrint();
		//指定输出流为 PrintWriter
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(out, format);
			writer.write(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static void printDoc(Document doc,Writer out) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		// OutputFormat.createCompactFormat(); //紧凑格式
		// OutputFormat.createPrettyPrint();
		//指定输出流为 PrintWriter
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(out, format);
			writer.write(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static String formatByCode(String text) {
		int spaceTab = 0;
		int index = 0;
		int tempIndex  =0;
		StringBuffer sb = new StringBuffer();

		while((tempIndex = text.indexOf("<",index)) != -1){
			appendText(sb,text,index,tempIndex);
			if(text.charAt(tempIndex + 1) == '/'){
				spaceTab--;
			}else if(tempIndex > 2 && "/>".equals(text.substring(tempIndex -2,tempIndex))){
				;
				
			}else
				spaceTab++;
			
			sb.append("\n");
			appendSpace(sb,spaceTab);
			
			index = tempIndex +1;
		}
		if(index -1 < text.length())
			sb.append(text.substring(index - 1));
		return sb.toString();
	}

	private static boolean testDec(String text, int tempIndex) {
		if(text.charAt(tempIndex + 1) == '/')
			return true;
		if(tempIndex > 2 && "/>".equals(text.substring(tempIndex -2,tempIndex)))
			return true;
		return false;
	}

	private static void appendText(StringBuffer sb,String text, int index, int tempIndex) {
		if(tempIndex == 0)
			return;
		if(tempIndex > index - 1)
			sb.append(text.substring(index - 1,tempIndex));
		
	}

	private static void appendSpace(StringBuffer sb, int spaceTab) {
		for(int i = 0 ; i< spaceTab ;i ++){
			sb.append("\t");
		}
	}
}
