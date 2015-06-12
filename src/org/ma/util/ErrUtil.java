package org.ma.util;

public class ErrUtil {
	public static String getErrMsg(Exception err){
		StackTraceElement[] eles = err.getStackTrace();
		StringBuffer sb = new StringBuffer();
		for(StackTraceElement er : eles){
			sb.append(String.valueOf(er));
		}
		return sb.toString();
	}
}
