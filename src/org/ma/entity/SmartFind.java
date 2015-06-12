package org.ma.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * 1 ≈≈–Ú¥Ê¥¢
 * 2 2∑÷≤È’“
 * @author MaXin
 *
 */
public class SmartFind implements ISmartFind {
	
	
	public List<String> findData(List<String> list,String prefix){
		List<String> arr = new ArrayList<String>();
		int start = -1;
		boolean has= false;
		int end = -1;
		int i = 0;
		for( ; i< list.size(); i++){
			if(list.get(i).startsWith(prefix)){
				start = i;
				end = i;
				break;
			}
		}
		for(;i<list.size();i++){
			if(list.get(i).startsWith(prefix)){
				end = i;
			}else
				break;
		}
		if(start != -1){
			return list.subList(start, end +1);
		}else
			return null;
	}
	
	
}
