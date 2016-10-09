package com.oversea.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mysql.jdbc.Statement;
import com.oversea.ads.bean.OverseaCountry;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaCountryDao extends OverseaBaseDao
{
    private static final String TBLPREFIX         = "o_country";
    private static final String CACHE_KEY_ALL     = OverseaCountryDao.class
                                                          .getName()
                                                          + "_all";
    private static final String CACHE_KEY_ID      = OverseaCountryDao.class
                                                          .getName()
                                                          + "_id";
    private static final String CACHE_KEY_COUNTRY = OverseaCountryDao.class
                                                          .getName()
                                                          + "_country";

    private static final String CACHE_KEY_MCC     = OverseaCountryDao.class
                                                          .getName()
                                                          + "_mcc";

    public static String table()
    {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final OverseaCountry item)
    {
        if (item == null)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`mcc`,`mnc`,`country`, `unit`, `unitname`,`unitename`,"
                        + "`timezone`, `ccode`, `language`, `name`,`ename`,`localname`, `remark`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getMcc() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMnc() != null)
                {
                    ps.setString(i++, item.getMnc());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCountry() != null)
                {
                    ps.setString(i++, item.getCountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUnit() != null)
                {
                    ps.setString(i++, item.getUnit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUnitname() != null)
                {
                    ps.setString(i++, item.getUnitname());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUnitename() != null)
                {
                    ps.setString(i++, item.getUnitename());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getTimezone() != null)
                {
                    ps.setString(i++, item.getTimezone());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCcode() != null)
                {
                    ps.setString(i++, item.getCcode());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getLanguage() != null)
                {
                    ps.setString(i++, item.getLanguage());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEname() != null)
                {
                    ps.setString(i++, item.getEname());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getLocalname() != null)
                {
                    ps.setString(i++, item.getLocalname());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getRemark() != null)
                {
                    ps.setString(i++, item.getRemark());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_COUNTRY + item.getCountry());
            OverseaCacheFactory.delete(CACHE_KEY_MCC + item.getMcc());
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final OverseaCountry item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `mcc`=?,`mnc`=?, `country`=?, `unit`=?, `unitname`=?,`unitename`=?,"
                        + "`timezone` = ?, `ccode`=?, `language`=?, `name`=?, `ename`=?,`localname`=?, `remark`=? WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                if (item.getMcc() != null)
                {
                    ps.setString(i++, item.getMcc());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMnc() != null)
                {
                    ps.setString(i++, item.getMnc());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCountry() != null)
                {
                    ps.setString(i++, item.getCountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUnit() != null)
                {
                    ps.setString(i++, item.getUnit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUnitname() != null)
                {
                    ps.setString(i++, item.getUnitname());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUnitename() != null)
                {
                    ps.setString(i++, item.getUnitename());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getTimezone() != null)
                {
                    ps.setString(i++, item.getTimezone());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCcode() != null)
                {
                    ps.setString(i++, item.getCcode());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getLanguage() != null)
                {
                    ps.setString(i++, item.getLanguage());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEname() != null)
                {
                    ps.setString(i++, item.getEname());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getLocalname() != null)
                {
                    ps.setString(i++, item.getLocalname());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getRemark() != null)
                {
                    ps.setString(i++, item.getRemark());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                ps.setLong(i++, item.getId());
            }
        };
        int id = getJdbcTemplate().update(sb.toString(), psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_COUNTRY + item.getCountry());
            OverseaCacheFactory.delete(CACHE_KEY_MCC + item.getMcc());
        }
    }
    
    public void updateByCountry(final List<OverseaCountry> list){
    	StringBuffer sb = new StringBuffer("update ");
    	sb.append(table()).append(" set `country3ch`=?")
    	.append(" where `country`=?");
    	String sql = sb.toString();
    	getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
   		 //为prepared statement设置参数。这个方法将在整个过程中被调用的次数  
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				OverseaCountry item = list.get(index);
				int i = 1;
               if (item.getCountry3ch()!= null)
               {
               	logger.info("getCountry:"+item.getCountry3ch());
                   ps.setString(i++, item.getCountry3ch());
               }
               else
               {
                   ps.setNull(i++, Types.NULL);
               }
               ps.setString(i++, item.getCountry());
			}
			 //返回更新的结果集条数  
			public int getBatchSize() {
				return list.size();
			}
		});
    }
    
    @SuppressWarnings("unchecked")
    public OverseaCountry findSingle(long id)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof OverseaCountry)
        {
            return (OverseaCountry) obj;
        }
        else
        {
            List<OverseaCountry> list = query("select * from " + table()
                    + " where id=? LIMIT 1", new Object[] { id },
                    new int[] { Types.BIGINT }, new OverseaCommonRowMapper(
                            OverseaCountry.class));
            if (list != null && list.size() > 0)
            {
                OverseaCountry item = list.get(0);
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
    public OverseaCountry findByCountry(String country)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_COUNTRY + country);
        if (obj != null && obj instanceof OverseaCountry)
        {
            OverseaCountry item = (OverseaCountry) obj;
            if (item != null && item.getCountry() != null)
            {
                return item;
            }
            else
            {
                return null;
            }
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            List<OverseaCountry> list;
            OverseaCountry item, ret;

            sb
                    .append("select * from " + table()
                            + " where country = ?");

            list = query(sb.toString(), new Object[] { country },
                    new int[] { Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaCountry.class));

            if (list != null && list.size() > 0)
            {
                item = list.get(0);
                ret = item;
            }
            else
            {
                item = new OverseaCountry();
                item.setCountry(null);
                ret = null;
            }

            OverseaCacheFactory.add(CACHE_KEY_COUNTRY + country, item,
                    OverseaCacheFactory.ONE_MONTH);
            return ret;
        }
    }
    
    @SuppressWarnings("unchecked")
    public OverseaCountry findByCountry3ch(String country)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_COUNTRY + country);
        if (obj != null && obj instanceof OverseaCountry)
        {
            OverseaCountry item = (OverseaCountry) obj;
            if (item != null && item.getCountry() != null)
            {
                return item;
            }
            else
            {
                return null;
            }
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            List<OverseaCountry> list;
            OverseaCountry item, ret;

            sb
                    .append("select * from " + table()
                            + " where country3ch = ? limit 1");

            list = query(sb.toString(), new Object[] { country },
                    new int[] { Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaCountry.class));

            if (list != null && list.size() > 0)
            {
                item = list.get(0);
                ret = item;
            }
            else
            {
                item = new OverseaCountry();
                item.setCountry(null);
                ret = null;
            }

            OverseaCacheFactory.add(CACHE_KEY_COUNTRY + country, item,
                    OverseaCacheFactory.ONE_MONTH);
            return ret;
        }
    }

    @SuppressWarnings("unchecked")
    public OverseaCountry findByMcc(String mcc)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_MCC + mcc);
        if (obj != null && obj instanceof OverseaCountry)
        {
            OverseaCountry item = (OverseaCountry) obj;
            if (item != null && item.getMcc() != null)
            {
                return item;
            }
            else
            {
                return null;
            }
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            List<OverseaCountry> list;
            OverseaCountry item, ret;
            sb.append("select * from " + table() + " where mcc = ? limit 1");
            list = query(sb.toString(), new Object[] { mcc },
                    new int[] { Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaCountry.class));

            if (list != null && list.size() > 0)
            {
                item = list.get(0);
                ret = item;
            }
            else
            {
                item = new OverseaCountry();
                item.setMcc(null);
                ret = null;
            }
            OverseaCacheFactory.add(CACHE_KEY_MCC + mcc, item,
                    OverseaCacheFactory.ONE_MONTH);
            return ret;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaCountry> findAll()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaCountry>) obj;
        }
        else
        {
            List<OverseaCountry> list = query("select * from " + table() + " ",
                    null, null,
                    new OverseaCommonRowMapper(OverseaCountry.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL, list,
                        OverseaCacheFactory.ONE_MONTH);
            }
            return list;
        }
    }
}
