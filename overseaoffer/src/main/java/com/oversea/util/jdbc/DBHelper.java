package com.oversea.util.jdbc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBHelper {
	private Logger logger = Logger.getLogger(DBHelper.class);
	private Properties prop;
	private String configname =null; 
	public  Connection conn = null;
	 /**定义一个int记录更新的记录数量*/  
    int count=0;  
    /**定义一个结果集 用于放回查询结果*/  
    private ResultSet resultSet=null;  
    private PreparedStatement pstmt=null;  
    public DBHelper(){
    	
//        conn = connectionDB();  
    } 
    public void loadConfig(){
    	try {
    		configname = this.getClass().getResource("/").getPath()+"mysql.properties";
			prop = new Properties();
			//加载配置文件									
//			prop.load(ClassLoader.getSystemResourceAsStream(CONFIGNAME));
			InputStream in = new BufferedInputStream(new FileInputStream(configname));   
			prop.load(in);  
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
//    public static DBHelper getDBHelper(){
//    	if(db==null){
//    		db = new DBHelper();
//    		try {
//    			prop = new Properties();
//    			//加载配置文件									
////    			prop.load(ClassLoader.getSystemResourceAsStream(CONFIGNAME));
//    			InputStream in = new BufferedInputStream(new FileInputStream(CONFIGNAME));   
//    			prop.load(in);  
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
////            conn = connectionDB();  
//    	}
//    	return db;
//    }
    /** 
     * 建立数据的连接 
     * @exception SQLException, ClassNotFoundException 
     */  
    @SuppressWarnings("finally")  
    public  Connection connectionDB(){  
        try{  
        	Class.forName(prop.getProperty("mysql.driver"));//指定连接类型
			conn = DriverManager.getConnection(prop.getProperty("mysql.adsurl"), prop.getProperty("mysql.username"), prop.getProperty("mysql.password"));//获取连接
            logger.info("success connection db!");  
        }catch(Exception e){  
            e.printStackTrace();  
            logger.info("fild connection db！");  
        }finally{  
            return conn;  
        }  
    }  
    /** 
     * 查询方法 
     * @param sql查询sql语句 
     * @return resultSet 
     */  
    @SuppressWarnings("finally")  
    public ResultSet query(String sql){  
        try {  
            pstmt = conn.prepareStatement(sql);  
            /**查询*/  
            resultSet = pstmt.executeQuery();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }finally{  
            return resultSet;  
        }  
    }  
    /** 
     * 更新数据 
     * @param sql 更新sql语句 
     * @return 
     */  
    public int update(String sql){  
        try {  
            pstmt = conn.prepareStatement(sql);  
            count=pstmt.executeUpdate();  
        } catch (SQLException e) {  
            e.printStackTrace();  
            logger.info("执行更新出错了");  
        }  
          
        return count;  
          
    }  
    /**关闭连接*/  
    public boolean coles(){  
        boolean isColes = false;  
        if(resultSet!=null){  
            try {  
                resultSet.close();  
                resultSet=null;  
                isColes=true;  
            } catch (SQLException e) {  
                isColes=false;  
                e.printStackTrace();  
                logger.info("关闭结果集发生错误");  
            }  
        }  
        if(pstmt!=null){  
            try {  
                pstmt.close();  
                pstmt=null;  
                isColes=true;  
            } catch (SQLException e) {  
                isColes=false;  
                e.printStackTrace();  
                logger.info("关闭pstmt发生异常");  
            }  
        }  
        if(conn!=null){  
            try{  
                conn.close();  
                conn=null;  
                isColes=true;  
            }catch (Exception e) {  
                isColes=false;  
                e.printStackTrace();  
                logger.info("关闭conn发生异常");  
            }  
        }  
        return isColes;  
    }  
    /*//使用PreparedStatement + 批处理     
    public void batchInsert(String sql){     
        
      try {     
        
       conn.setAutoCommit(false);     
        
       Long beginTime = System.currentTimeMillis();     
        
       pstmt = conn.prepareStatement(sql);
//       String[] strs = sql.split("?");
//       int size = strs.length;
       for(int i=0;i<=list.size();i++){         
    	   T t = list.get(i);
    	   pstmt.setInt(i+1, t.getClass().);     
        
    	   pstmt.addBatch();     
        
        if(i%1000==0){//可以设置不同的大小；如50，100，500，1000等等     
        
        	pstmt.executeBatch();     
        
        	conn.commit();     
        
        	pstmt.clearBatch();     
        
        }     
	     // 最后插入不足1w条的数据
	        pstmt.executeBatch();
	        
	        conn.commit();

       }     
        
       Long endTime = System.currentTimeMillis();     
        
       logger.info("pst+batch："+(endTime-beginTime)/1000+"秒");     
        
//       pst.close();     
        
//       conn.close();     
        
      } catch (SQLException e) {     
       e.printStackTrace();     
        
      }     
        
     }     */
    
	public static void main(String[] args) {
		DBHelper db = new DBHelper();
		System.out.println(db.connectionDB());
//		System.out.println(prop.get("mysql.driver"));
		db.coles();
		String path2 = Class.class.getClass().getResource("/").getPath();  
//		System.out.println(path2);
//		String path=path2.getRealPath("/data/count.dat");  //获得应用程序的根目录 
	}
}
