package org.ma.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ma.entity.DBParam;
import org.ma.entity.Table;
import org.ma.face.ShowResult;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DBUtil {
	public static Connection getConn(DBParam dbParam)throws Exception{
		Class.forName(dbParam.driverClass).newInstance();
		String url=dbParam.url; //orcl为数据库的SID
		String user=dbParam.name;
		String password=dbParam.password;
		Connection conn= DriverManager.getConnection(url,user,password);
		return conn;
	}

	public static void compare(DBParam src, DBParam target, ShowResult show) {
		if(src.tables.size() != target.tables.size()){
			show.appendResultN("表的数量不一样,左边:" + src.tables.size() +"; 右边:"+ target.tables.size() );
		}else
			show.appendResultN("表的数量一样:"+ target.tables.size());
		
		Map<String,List<String>> diff = new HashMap<String,List<String>>();
		
		Map<String,List<String>> diffIndex = new HashMap<String,List<String>>();
		for(Table t : target.tables){
			Table srcT = src.tableMap.get(t.tableName);
			if(null == srcT){
				show.appendResultN( "找不到表：" + t.tableName);
				continue;
			}
			for(String c :t.columns){
				if(!t.columnMap.get(c).equals(srcT.columnMap.get(c))){
					if(diff.containsKey(t.tableName)){
						List<String> difflist = diff.get(t.tableName);
						difflist.add(srcT.columnMap.get(c) + " <> " +t.columnMap.get(c));
					}else{
						List<String> difflist = new ArrayList<String>();
						difflist.add(srcT.columnMap.get(c) + " <> " +t.columnMap.get(c));
						diff.put(t.tableName, difflist);
					}
				}//end if 
			}
			
		}
		show.appendDiffMap(diff);
		show.appendResultN("比较结束");
	}
	
	
	public static ResultSet getResultSet(Connection conn,String sql) throws Exception{
		Statement statement =conn.createStatement();
		return statement.executeQuery(sql);
	}

	public static void exe(Connection conn,String sqls)throws Exception {
		
		Statement statement =conn.createStatement();
		sqls = sqls.trim();
		String [] ss = sqls.split(";");
		for(int i = 0 ; i< ss.length; i++){
			statement.execute(ss[i]);
		}
		
	}

	public static String excelOut(ResultSet rs) {
		OutputStream out = null;
		WritableWorkbook wwb = null;
		try {
			out = new FileOutputStream("e:/temp/dbUtil.xls");
			
			wwb = Workbook.createWorkbook(out);
			WritableSheet ws = wwb.createSheet("title", 0); // 设置sheet页
			
			ResultSetMetaData rsm;
			rsm = rs.getMetaData();
			int	colNum = rsm.getColumnCount();
			
			Label l =null;
			
			//第一行
			for (int i = 1; i <= colNum; i++) {
				String lName  = rsm.getColumnLabel(i);
				l = new Label(i-1, 0, lName);
				ws.addCell(l);	
			}
			int col = 1;
			while(rs.next()){
				for (int i = 1; i <= colNum; i++) {
					Object o = rs.getObject(i);
				//	String cName = rsm.getColumnName(i);
					if(o == null){
						o = " ";
					}
					l = new Label(i-1, col, o.toString());
					ws.addCell(l);	
				}
				if(col%100 == 0){
					out.flush();
				}
				col++;			
			}
			
			return "导出文件：e:/temp/dbUtil.xls";
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			try {
				wwb.write();
				wwb.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
