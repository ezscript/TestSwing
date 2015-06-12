package org.ma.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileUtil {

	
	/**
	 * 
	 * @param file  查找起始位置
	 * @param iFilter  iFilter.isFilter(file)?"过滤文件":"添加到list"
	 * @param keys   文件后缀
	 * @return
	 */
	public static List<File> findAllFile(File file,IFilter iFilter, String ...keys) {
		List<File> files = new ArrayList<File>();
		findAllFile(files,file,iFilter,keys);
		return files;
	}
	
	
	
	public static void findAllFile(List<File> files,File file,IFilter iFilter, String ...keys) {
		if(file.isDirectory()){
			File[] fileArr = file.listFiles();
			for(File f: fileArr){
				findAllFile(files,f,iFilter,keys);
			}
		}else{
			if(checkFiLe(file,keys)){
				if(iFilter != null && iFilter.isFilter(file)){
					return;
				}
				files.add(file);
			}
		}
		
	}

	private static boolean checkFiLe(File file, String[] keys) {
		String fileName = file.getName();
		for(String key : keys){
			if(fileName.endsWith(key))
				return true;
		}
		return false;
	}

	public static Map<String, File> getFileMap(List<File> files) {
		Map<String, File> map = new HashMap<String,File>();
		for(File f: files){
			map.put(f.getPath(), f);
		}
		return map;
	}

	public static List<File> filter(List<File> files, IFilter iFilter) {
		List<File> result = new ArrayList<File>();
		for(File f : files){
			if(!iFilter.isFilter(f)){
				result.add(f);
			}
		}
		return result;
	}
	
	public interface IFilter{
		boolean isFilter(File f);
	}



}
