package org.ma.util;

public enum SimpleLog {
	INSTANCE;
	private StringBuffer msg = new StringBuffer();
	public void clear(){
		msg =  new StringBuffer();
	}
	public void appendLine(String str){
		msg.append(str).append("\n\r");
	}
	
	
	public String getLog(){
		return msg.toString();
	}
}
