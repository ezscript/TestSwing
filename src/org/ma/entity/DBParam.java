package org.ma.entity;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBParam {
	
	/**
	 * 基本参数
	 */
	public String url;
	public String name;
	public String password;
	public String driverClass;
	public String jar;
	
	public ISmartFind smartFind = new SmartFind();
	public List<String> keys = null;
	public Map<String,List<String>> keyMap = new HashMap<String,List<String>>();
	
	private String[] selfkeys = {"SELECT","FROM","WHERE","ORDER BY","GROUP BY","HAVING","COUNT"};
	
	public List<Table> tables =new ArrayList<Table>();
	
	public Map<String,Table> tableMap =new HashMap<String,Table>();
	
	public DBParam(){
		
	}
	
	public void fillSelf(Connection conn)throws Exception{
		tables.clear();
		tableMap.clear();
		keyMap.clear();
		keys = Arrays.asList(selfkeys);
		
		DatabaseMetaData   dbMetaData   =   conn.getMetaData();  
		String[]   types   =   {"TABLE"};  
		ResultSet   tabs   =   dbMetaData.getTables(null,   null,   null,types/*只要表就好了*/);
		while(tabs.next()){  
			//只要表名这一列
			Table t = new Table(tabs.getObject("TABLE_NAME").toString());
			tables.add(t);
			tableMap.put(t.tableName, t);
			System.out.println(tabs.getObject("TABLE_NAME").toString());
			ResultSet cs = dbMetaData.getColumns( null, "%" , t.tableName , "%");
			ResultSet index = dbMetaData.getIndexInfo(null, null, t.tableName, false, true);
			keys.add(t.tableName);
			while(cs.next())
			{
				Types d;
			    String fieldName = cs.getString(4);
			    String fieldType = cs.getString(5);
			    String typeName = cs.getString(6);
			    String fieldSize = cs.getString(7);
			    Integer dd = cs.getInt(9); //DECIMAL_DIGITS
			    String columnDef = cs.getString(13); //COLUMN_DEF
			 //   String compareStr = fieldName+ "--" + fieldType +"--" + fieldSize;
			    String compareStr = fieldName+ "--" + typeName +"--" + fieldSize + "--" +dd +"--"+columnDef ;
				t.columns.add(fieldName.toUpperCase());
				t.columnMap.put(fieldName, compareStr);
			}
			Collections.sort(t.columns);
			while(index.next()){
				t.index.add(index.getString(9));
		//		System.out.println(index.getString(6) + ":"+ index.getString(9));
			}
		}
		
		//处理key
		Collections.sort(keys);
		fillKeyMap(keyMap,keys);
	//	System.out.println(tables.size());  
	}

	private void fillKeyMap(Map<String,List<String>> keyMap,List<String> keys) {
		for(String key : keys){
			if(key.length() < 2)
				continue;
			String keyKey = key.substring(0,2);
			if(keyMap.containsKey(keyKey)){
				keyMap.get(keyKey).add(key);
			}else{
				List<String> arr = new ArrayList<String>();
				arr.add(key);
				keyMap.put(keyKey, arr);
			}
		}
	}
}
/**
字段在表里就是一个Column,关于Column在JDBC里面有20多个参数来描述它，称为元数据,Metadata。包括：
1.                TABLE_CAT String => table catalog (may be null)
2.                TABLE_SCHEM String => table schema (may be null)
3.                TABLE_NAME String => table name
4.                COLUMN_NAME String => column name
5.                DATA_TYPE int => SQL type from java.sql.Types
6.                TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
7.                COLUMN_SIZE int => column size.
8.                BUFFER_LENGTH is not used.
9.                DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
10.            NUM_PREC_RADIX int => Radix (typically either 10 or 2)
11.            NULLABLE int => is NULL allowed.
o                                            columnNoNulls - might not allow NULL values
o                                            columnNullable - definitely allows NULL values
o                                            columnNullableUnknown - nullability unknown
12.            REMARKS String => comment describing column (may be null)
13.            COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
14.*/

/**
Each index column description has the following columns: 

1 TABLE_CAT String => table catalog (may be null) 
2 TABLE_SCHEM String => table schema (may be null) 
3 TABLE_NAME String => table name 
4 NON_UNIQUE boolean => Can index values be non-unique. false when TYPE is tableIndexStatistic 
5 INDEX_QUALIFIER String => index catalog (may be null); null when TYPE is tableIndexStatistic 
6 INDEX_NAME String => index name; null when TYPE is tableIndexStatistic 
7 TYPE short => index type: 
		tableIndexStatistic - this identifies table statistics that are returned in conjuction with a table's index descriptions 
		tableIndexClustered - this is a clustered index 
		tableIndexHashed - this is a hashed index 
		tableIndexOther - this is some other style of index 
8 ORDINAL_POSITION short => column sequence number within index; zero when TYPE is tableIndexStatistic 
9 COLUMN_NAME String => column name; null when TYPE is tableIndexStatistic 
10 ASC_OR_DESC String => column sort sequence, "A" => ascending, "D" => descending, may be null if sort sequence is not supported; null when TYPE is tableIndexStatistic 
11 CARDINALITY int => When TYPE is tableIndexStatistic, then this is the number of rows in the table; otherwise, it is the number of unique values in the index. 
12 PAGES int => When TYPE is tableIndexStatisic then this is the number of pages used for the table, otherwise it is the number of pages used for the current index. 
13 FILTER_CONDITION String => Filter condition, if any. (may be null) 
*/