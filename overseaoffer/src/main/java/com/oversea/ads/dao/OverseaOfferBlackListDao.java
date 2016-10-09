package com.oversea.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mysql.jdbc.Statement;
import com.oversea.ads.bean.OverseaOfferBlackList;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaOfferBlackListDao extends OverseaBaseDao
{
    private static final String TBLPREFIX                     = "o_offerblacklist";
    private static final String CACHE_KEY_ALL                 = OverseaOfferBlackListDao.class
                                                                      .getName()
                                                                      + "_all";
    private static final String CACHE_KEY_ID                  = OverseaOfferBlackListDao.class
                                                                      .getName()
                                                                      + "_id";
    private static final String CACHE_KEY_PROVIDER                  = OverseaOfferBlackListDao.class
    .getName()
    + "_provider";
    private static final String CACHE_KEY_COUNTRY                  = OverseaOfferBlackListDao.class
    .getName()
    + "_country";

    private static final String CACHE_KEY_STATUS              = OverseaOfferBlackListDao.class
                                                                      .getName()
                                                                      + "_status_1";

    public static String table()
    {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final OverseaOfferBlackList item)
    {
        if (item == null)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`pkg`,`provider`,`country`,`createdate`,`updatedate`) VALUES (?,?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
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
                	ps.setNull(i++, 0);
                }
                
                if (item.getCountry() != null)
                {
                	ps.setString(i++, item.getCountry());
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
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final OverseaOfferBlackList item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `pkg`=?,`provider`=?,`country`=?,`status`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;

                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setInt(i++, Types.NULL);
                }
                if (item.getProvider() != null)
                {
                	ps.setLong(i++, item.getProvider());
                }
                else
                {
                	ps.setNull(i++, 0);
                }
                
                if (item.getCountry() != null)
                {
                	ps.setString(i++, item.getCountry());
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
                	ps.setNull(i++, Types.NULL);
                }
                ps.setLong(i++, item.getId());
            }
        };
        int id = getJdbcTemplate().update(sb.toString(), psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_ID+id);
            OverseaCacheFactory.delete(CACHE_KEY_PROVIDER+item.getProvider());
            OverseaCacheFactory.delete(CACHE_KEY_COUNTRY+item.getCountry());
        }
    }
    

    @SuppressWarnings("unchecked")
    public List<OverseaOfferBlackList> findAll()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaOfferBlackList>) obj;
        }
        else
        {
        	StringBuffer sb = new StringBuffer("select * from "+table());
        	sb.append(" where `status`=0");
        	logger.info(sb.toString());
            List<OverseaOfferBlackList> list = query(sb.toString(), null,
                    null, new OverseaCommonRowMapper(OverseaOfferBlackList.class));
            OverseaCacheFactory.add(CACHE_KEY_ALL ,list,
                    OverseaCacheFactory.ONE_HOUR*2);
            return list;
        }
    }
    
    public OverseaOfferBlackList findItemByPkg_Provider_Country(String pkg,Long provider,String country){
    		OverseaOfferBlackList item = null;
    		String proSelection = "";
    		String cSelection = "";
    		if(!OverseaStringUtil.isBlank(provider)){
    			proSelection=" and provider="+provider;
    		}
    		if(!OverseaStringUtil.isBlank(country)){
    			cSelection = " and country='"+country+"'";
    		}
        	StringBuffer sb = new StringBuffer("select * from "+table());
        	sb.append(" where  pkg='"+pkg+"'")
        	.append(proSelection)
        	.append(cSelection)
        	.append(" limit 1");
        	logger.info(sb.toString());
            List<OverseaOfferBlackList> list = query(sb.toString(), null,
                    null, new OverseaCommonRowMapper(OverseaOfferBlackList.class));
            if (list!=null&&list.size()>0) {
				item = list.get(0);
			}
            return item;
    }

}
