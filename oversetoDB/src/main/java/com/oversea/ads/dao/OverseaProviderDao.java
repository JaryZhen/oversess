package com.oversea.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mysql.jdbc.Statement;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.bean.OverseaRanking;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaProviderDao extends OverseaBaseDao
{
    private static final String TBLPREFIX     = "o_provider";
    private static final String CACHE_KEY_ALL = OverseaProviderDao.class
                                                      .getName()
                                                      + "_all";
    private static final String CACHE_KEY_ID  = OverseaProviderDao.class
                                                      .getName()
                                                      + "_id";
    private static final String CACHE_KEY_DATE = OverseaProviderDao.class.getName()+ "_date_";
    private static final String CACHE_KEY_CAPSIN = OverseaProviderDao.class.getName()+ "_capsin";
    
    /**
     * provider name 前缀
     */
    private static final String CACHE_KEY_NAME_PREFIX = OverseaProviderDao.class.getName()
    													+"_NAME_PREFIX";

    public static String table()
    {
        return TBLPREFIX;
    }

//   jary
    @SuppressWarnings("unchecked")
	public List<OverseaProvider> findProvider_Cap_Sin() {
    	 Object obj = OverseaCacheFactory.get(CACHE_KEY_CAPSIN );
         if (obj != null && obj instanceof OverseaProvider)
         {
             return (List<OverseaProvider>) obj;
         }
         else
         {
         	List<OverseaProvider> list = query("select * from " + table()+" WHERE `cap` IS NOT NULL",
         			null,null,new OverseaCommonRowMapper(OverseaProvider.class));
         	if (list != null && list.size() > 0)
         	{
         		OverseaCacheFactory.add(CACHE_KEY_CAPSIN , list, OverseaCacheFactory.ONE_HOUR*5);
         		return list;
         	}
         	return null;
         }
    }
    @SuppressWarnings("unchecked")
	public List<OverseaRanking> findProviderByDate(int date) {
		// TODO Auto-generated method stub
    	  Object obj = OverseaCacheFactory.get(CACHE_KEY_DATE + date);
          if (obj != null && obj instanceof OverseaRanking)
          {
              return (List<OverseaRanking>) obj;
          }
          else
          {
          	List<OverseaRanking> list = query("select a.id adid, b.weight num from " + table()+" b,"+ "o_ad"+" a"+ 
          			" where a.status=0 and b.id=a.provider GROUP BY a.id ORDER BY b.weight desc",
          			null,null,new OverseaCommonRowMapper(OverseaRanking.class));
          	if (list != null && list.size() > 0)
          	{
          		OverseaCacheFactory.add(CACHE_KEY_DATE + date , list, OverseaCacheFactory.ONE_DAY *2);
          		return list;
          	}
          	return null;
          }
	}
    
    /**
     * 根据provider name 前缀获取provider列表
     * @param providerNamePrefix
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<OverseaProvider> findProviderByNamePrefix(String providerNamePrefix){
    	if(providerNamePrefix==null||providerNamePrefix.isEmpty()) return null;
    	 String key = CACHE_KEY_NAME_PREFIX + "_" + providerNamePrefix;
         Object obj = OverseaCacheFactory.get(key);
         if (obj != null && obj instanceof List) 
         	return (List<OverseaProvider>) obj;
     	String sql = "select * from "+table()+" where name like '"+providerNamePrefix+"%'";
     	List<OverseaProvider> list = query(sql, null, null,
     			new OverseaCommonRowMapper(OverseaProvider.class));
         if (list != null && list.size() > 0)
             OverseaCacheFactory.add(key, list, OverseaCacheFactory.ONE_DAY);
         return list;
    }
    
    /**
     * 插入
     */
    public int insert(final OverseaProvider item)
    {
        if (item == null)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`name`,`url`,`username`, `password`, `status`,"
                        + "`createdate`, `updatedate`) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUrl() != null)
                {
                    ps.setString(i++, item.getUrl());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUsername() != null)
                {
                    ps.setString(i++, item.getUsername());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPassword() != null)
                {
                    ps.setString(i++, item.getPassword());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final OverseaProvider item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `name`=?,`url`=?, `username`=?, `password`=?, `status`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUrl() != null)
                {
                    ps.setString(i++, item.getUrl());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUsername() != null)
                {
                    ps.setString(i++, item.getUsername());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPassword() != null)
                {
                    ps.setString(i++, item.getPassword());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                ps.setLong(i++, item.getId());
            }
        };
        int id = getJdbcTemplate().update(sb.toString(), psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_ID + item.getId());
        }
    }

    @SuppressWarnings("unchecked")
    public OverseaProvider findSingle(long id)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof OverseaProvider)
        {
            return (OverseaProvider) obj;
        }
        else
        {
            List<OverseaProvider> list = query("select * from " + table()
                    + " where id=? LIMIT 1", new Object[] { id },
                    new int[] { Types.BIGINT }, new OverseaCommonRowMapper(
                            OverseaProvider.class));
            if (list != null && list.size() > 0)
            {
                OverseaProvider item = list.get(0);
                if (item != null)
                {
                    OverseaCacheFactory.add(CACHE_KEY_ID + id, item,
                            OverseaCacheFactory.ONE_MONTH);
                }
                return item;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaProvider> findAll()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaProvider>) obj;
        }
        else
        {
            List<OverseaProvider> list = query(
                    "select * from " + table() + " where `status`=0 ORDER BY `name` ", null, null,
                    new OverseaCommonRowMapper(OverseaProvider.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL, list,
                        OverseaCacheFactory.ONE_DAY*2);
            }
            return list;
        }
    }
}
