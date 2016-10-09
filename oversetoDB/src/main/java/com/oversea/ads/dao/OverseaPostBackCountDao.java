package com.oversea.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mysql.jdbc.Statement;
import com.oversea.ads.bean.OverseaPostBackCount;
import com.oversea.ads.bean.OverseaRanking;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaPostBackCountDao extends OverseaBaseDao
{
    private static final String TBLPREFIX         = "o_postbackcount";
    private static final String CACHE_KEY_ALL     = OverseaPostBackCountDao.class
                                                          .getName()
                                                          + ";all";
    private static final String CACHE_KEY_ID      = OverseaPostBackCountDao.class
                                                          .getName()
                                                          + ";id;";
    private static final String CACHE_KEY_DATE = OverseaPostBackCountDao.class.getName()+ ";date;";
    private static final String CACHE_KEY_PROVIDER = OverseaPostBackCountDao.class
    .getName()
    + ";provider;";
    private static final String CACHE_KEY_ADID = OverseaPostBackCountDao.class
    .getName()
    + ";adid;";
    private static final String CACHE_KEY_CONVERSION = OverseaPostBackCountDao.class.getName()+ ";conversion;";

    public static String table()
    {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final OverseaPostBackCount item)
    {
        if (item == null)
        {
            return 0;
        }else{
        	if(item.getAdid()==null) return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`date`,`provider`,`adid`,`installnum`,`createdate`, `updatedate`) VALUES (?, ?, ?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getDate() != null)
                {
                    ps.setString(i++, item.getDate());
                }
                else
                {
                     ps.setNull(i++, Types.NULL);
                }
                if (item.getProvider() != null)
                {
                	ps.setLong(i++, item.getProvider());
                }
                else
                {
                	ps.setNull(i++, Types.NULL);
                }
                if (item.getAdid() != null)
                {
                    ps.setLong(i++, item.getAdid());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getInstallnum() != null)
                {
                	ps.setInt(i++, item.getInstallnum());
                }
                else
                {
                	ps.setNull(i++, 0);
                }
                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_DATE + item.getDate());
            OverseaCacheFactory.delete(CACHE_KEY_PROVIDER + item.getProvider());
            OverseaCacheFactory.delete(CACHE_KEY_ADID + item.getAdid());
        }
        return id;
    }

    @SuppressWarnings("unchecked")
    public List<OverseaPostBackCount> findAll()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaPostBackCount>) obj;
        }
        else
        {
            List<OverseaPostBackCount> list = query("select * from " + table()
            		, null, null,
                    new OverseaCommonRowMapper(OverseaPostBackCount.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL, list,
                        OverseaCacheFactory.ONE_MONTH);
            }
            return list;
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public List<OverseaRanking> findPostBacktByDate(Integer date)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_DATE + date);
        if (obj != null && obj instanceof OverseaRanking)
        {
            return (List<OverseaRanking>) obj;
        }
        else
        {
        	List<OverseaRanking> list = query("select adid, sum(installnum) num from " + table()+ " where date =? GROUP BY adid ORDER BY num desc",new Object[] { date }, new int[] {Types.INTEGER },new OverseaCommonRowMapper(OverseaRanking.class));
        	if (list != null && list.size() > 0)
        	{
        		OverseaCacheFactory.add(CACHE_KEY_DATE + date , list, OverseaCacheFactory.ONE_DAY*2);
        		return list;
        	}
        	return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public OverseaPostBackCount findPostBacktByDateAdid(String date,Long adid)
    {
    	Object obj = OverseaCacheFactory.get(CACHE_KEY_DATE + date+adid);
    	if (obj != null && obj instanceof OverseaPostBackCount)
    	{
    		return (OverseaPostBackCount) obj;
    	}
    	else
    	{
    		List<OverseaPostBackCount> list = 
    			query("select adid, sum(installnum) installnum from " + table()+ " where date =? and adid = ?",
    				new Object[] { date,adid }, new int[] {Types.VARCHAR,Types.INTEGER },
    				new OverseaCommonRowMapper(OverseaPostBackCount.class));
    		if (list != null && list.size() > 0)
    		{
    			OverseaCacheFactory.add(CACHE_KEY_DATE + date+adid , list.get(0), OverseaCacheFactory.ONE_DAY);
    			return list.get(0);
    		}
    		return null;
    	}
    }
    
    public List<OverseaRanking> findConversionRateByDate(Integer date)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_CONVERSION + date);
        if (obj != null && obj instanceof OverseaRanking)
        {
            return (List<OverseaRanking>) obj;
        }
        else
        {							 
        	List<OverseaRanking> list = query("select a.adid, a.installnum/b.`install` price from " + table() + " a, o_adstatis b where a.date= b.date and a.adid=b.adid and a.date =? ORDER BY price desc",new Object[] { date }, new int[] {Types.INTEGER },new OverseaCommonRowMapper(OverseaRanking.class));
        	if (list != null && list.size() > 0)
        	{
        		OverseaCacheFactory.add(CACHE_KEY_CONVERSION + date , list, OverseaCacheFactory.ONE_DAY*2);
        		return list;
        	}
        	return null;
        }
    }
    
}
