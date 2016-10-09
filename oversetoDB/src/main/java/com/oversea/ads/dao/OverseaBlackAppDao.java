package com.oversea.ads.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.oversea.ads.bean.OverseaBlackApp;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaBlackAppDao extends OverseaBaseDao {
	
	private static final String TBLPREFIX                     = "o_blackapp";
    private static final String CACHE_KEY_ALL                 = OverseaBlackAppDao.class
                                                                      .getName()
                                                                      + "_all";
    public static String table()
    {
        return TBLPREFIX;
    }
	
	
	public void batchInsert(final List<OverseaBlackApp> list){
    	StringBuffer sb = new StringBuffer();
    	 sb.append("INSERT INTO ");
         sb.append(table());
         sb
                 .append(" (`pkg`,`name`,`status`,`createdate`,`updatedate`) VALUES (?, ?, ?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
         final String sql = sb.toString();
    	getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
    		 //为prepared statement设置参数。这个方法将在整个过程中被调用的次数  
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				OverseaBlackApp item = list.get(index);
				int i = 1;
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                } else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getStatus() != null)
                {
                	ps.setInt(i++, item.getStatus());
                } else
                {
                	ps.setNull(i++, 0);
                }
			}
			 //返回更新的结果集条数  
			public int getBatchSize() {
				return list.size();
			}
		});
    	OverseaCacheFactory.delete(CACHE_KEY_ALL);
    }
    
	
	 @SuppressWarnings("unchecked")
	    public List<OverseaBlackApp> findAll()
	    {
	        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
	        if (obj != null && obj instanceof List)
	        {
	            return (List<OverseaBlackApp>) obj;
	        }
	        else
	        {
	        	StringBuffer sb = new StringBuffer("select * from "+table()+" where `status`=0");
	        	logger.info(sb.toString());
	            List<OverseaBlackApp> list = query(sb.toString(), null,
	                    null, new OverseaCommonRowMapper(OverseaBlackApp.class));
	            OverseaCacheFactory.add(CACHE_KEY_ALL ,list,
	                    OverseaCacheFactory.ONE_DAY);
	            return list;
	        }
	    }
}
