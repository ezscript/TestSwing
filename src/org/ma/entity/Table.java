package org.ma.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	public String tableName;
	
	public List<String> columns =new ArrayList<String>();
	public Map<String,String> columnMap = new HashMap<String,String> ();
	
	
	public List<String> index =new ArrayList<String>();
	public Map<String,String> indexMap = new HashMap<String,String> ();
	
	
	public Table(String  tableName) {
		this.tableName = tableName.toUpperCase();
	}

}
