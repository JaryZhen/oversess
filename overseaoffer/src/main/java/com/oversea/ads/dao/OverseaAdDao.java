package com.oversea.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mysql.jdbc.Statement;
import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaRanking;
import com.oversea.ads.util.OverseaDateUtil;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaCacheFactory;
import com.oversea.factory.OverseaRedisDb;

public class OverseaAdDao extends OverseaBaseDao
{
    private static final String TBLPREFIX                     = "o_ad";
    // 临时表，用于存放最新offer
    private static final String TBLPREFIX_TEM                 = "o_ad_tem";
    private static final String CACHE_KEY_ALL                 = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_all";
    private static final String CACHE_KEY_ID                  = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_id";
    private static final String CACHE_KEY_TYPE                = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_type";
    private static final String CACHE_KEY_TYPE_ADMIN          = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_type_admin";

    private static final String CACHE_KEY_TYPE_PROVIDER_ADMIN = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_type_provider_admin";
    private static final String CACHE_KEY_PROVIDER            = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_provider";

    private static final String CACHE_KEY_TAPJOYID            = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_tapjoyid";

    private static final String CACHE_KEY_STATUS              = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_status_1";
    private static final String CACHE_KEY_PKG                 = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_pkg_";
    private static final String CACHE_KEY_KEYS                = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_keys_";

    private static final String CACHE_KEY_PRICE               = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_price_";
    private static final String CACHE_KEY_PREWEIHGT           = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_preweight_";

    private static final String CACHE_KEY_TYPE_STATUS_ADMIN   = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_type_status_admin";

    private static final String CACHE_KEY_COUNTRY             = OverseaAdDao.class
                                                                      .getName()
                                                                      + "country";

    private static final String CACHE_KEY_AFFLICATE           = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_AFFLICATE";

    private static final String CACHE_KEY_REDIS_DB            = OverseaAdDao.class
                                                                      .getName()
                                                                      + "_redis_db";
    public static String redis_Db(){
    	return CACHE_KEY_REDIS_DB;
    }

    public static String table()
    {
        return TBLPREFIX;
    }

    public static String table_tem()
    {
        return TBLPREFIX_TEM;
    }

    public int insertTem(final OverseaAd_Tem item)
    {

        if (item == null)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`type`,`provider`,`name`, `pkg`, `apk`, `country`,`ecountry`,"
                        + "`mainicon`, `visit`, `click`, `cap`,`object_cap`, `price`, `weight`,`preweight`,`pullratio`,`sinstall`,`status`, `offerid`,`is_onlypush`,`is_exchange`,`createdate`,`updatedate`) VALUES (?,?,  ?,?, ?, ?, ?, ?, ?,?, ?, ?,?,?,?, ?, ?, ?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
              
                if (item.getCap() != null)
                {
                    ps.setInt(i++, item.getCap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getObject_cap() != null)
                {
                    ps.setInt(i++, item.getObject_cap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
                }
                if (item.getWeight() != null)
                {
                    ps.setInt(i++, item.getWeight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                
                if (item.getPreweight() != null)
                {
                    ps.setInt(i++, item.getPreweight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPullratio() != null)
                {
                    ps.setInt(i++, item.getPullratio());
                }
                else
                {
                    ps.setInt(i++, 100);
                }

                if (item.getSinstall() != null)
                {
                    ps.setInt(i++, item.getSinstall());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getOfferid() != null)
                {
                    ps.setString(i++, item.getOfferid());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getIs_onlypush() != null)
                {
                    ps.setInt(i++, item.getIs_onlypush());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getIs_exchange() != null)
                {
                    ps.setInt(i++, item.getIs_exchange());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc);
//        if (id > 0)
//        {
//            NgsteamCacheFactory.delete(CACHE_KEY_ALL);
//            NgsteamCacheFactory.delete(CACHE_KEY_TYPE + item.getType());
//            NgsteamCacheFactory.delete(CACHE_KEY_TYPE_ADMIN + item.getType());
//            NgsteamCacheFactory.delete(CACHE_KEY_TYPE_PROVIDER_ADMIN
//                    + item.getType() + "_" + item.getProvider());
//        }
        return id;

    }

    /**
     * 插入
     */
    public long insert(final OverseaAd item)
    {
        if (item == null)
        {
            return 0;
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`type`,`provider`,`name`, `pkg`, `apk`, `country`,`ecountry`,"
                        + "`mainicon`, `visit`, `click`,`cap`,`object_cap`, `price`, `preweight`,`pullratio`,`sinstall`,`status`, `offerid`,`is_onlypush`,`is_exchange`,`createdate`,`updatedate`) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?, ?, ?, ?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                
                if (item.getCap() != null)
                {
                    ps.setInt(i++, item.getCap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getObject_cap() != null)
                {
                    ps.setInt(i++, item.getObject_cap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
                }
                if (item.getPreweight() != null)
                {
                    ps.setInt(i++, item.getPreweight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPullratio() != null)
                {
                    ps.setInt(i++, item.getPullratio());
                }
                else
                {
                    ps.setInt(i++, 100);
                }

                if (item.getSinstall() != null)
                {
                    ps.setInt(i++, item.getSinstall());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getOfferid() != null)
                {
                    ps.setString(i++, item.getOfferid());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getIs_onlypush() != null)
                {
                    ps.setInt(i++, item.getIs_onlypush());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getIs_exchange() != null)
                {
                    ps.setInt(i++, item.getIs_exchange());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc, keyHolder);
        long ret = keyHolder.getKey().longValue();
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_COUNTRY+item.getCountry());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_ADMIN + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_PROVIDER_ADMIN
                    + item.getType() + "_" + item.getProvider());
            item.setId(ret);
            OverseaRedisDb.getInstance().insert(CACHE_KEY_REDIS_DB, item);
        }

        return ret;
    }

    public void updateStatus_Tem(final List<OverseaAd_Tem> item1)
    {
        for (final OverseaAd_Tem item : item1)
        {

            if (item == null)
            {
                return;
            }

            StringBuffer sb = new StringBuffer();
            sb.append("UPDATE ");
            sb.append(table());
            sb
                    .append(" SET  `status`=0 ,`updatedate`=CURRENT_TIMESTAMP  WHERE `provider`=? and `pkg`=? and `country`=? and `offerid`=? and is_manual=1");
            PreparedStatementSetter psc = new PreparedStatementSetter()
            {
                public void setValues(PreparedStatement ps) throws SQLException
                {
                    int i = 1;
                    if (item.getProvider() != null)
                    {
                        ps.setLong(i++, item.getProvider());
                    }
                    else
                    {
                        ps.setNull(i++, Types.NULL);
                    }
                    if (item.getPkg() != null)
                    {
                        ps.setString(i++, item.getPkg());
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
                    if (item.getOfferid() != null) 
                    {
                        ps.setString(i++, item.getOfferid());
                    }
                    else
                    {
                        ps.setNull(i++, Types.NULL);
                    }
                }
            };
            int id = getJdbcTemplate().update(sb.toString(), psc);
        }
    }

    public void updateStatus_AD(final OverseaAd item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET  `status`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
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
        //更新offer时，不再清理缓存。最后统一处理
//        if(id>0){
//        	NgsteamCacheFactory.delete(CACHE_KEY_COUNTRY+item.getCountry());
//        }
    }

    /**
     * 更新
     */
    public void update(final OverseaAd item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `type`=?,`provider`=?, `name`=?, `pkg`=?, `apk`=?,"
                        + "`country`=?,`ecountry`=?,"
                        + "`mainicon` = ?, `visit`=?, `click`=?, `cap`=?,`object_cap`=?, "
                        + "`price`=?,`preweight`=?, `status`=?,`sinstall`=?, `pullratio`=?,`is_onlypush`=?,`is_exchange`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
               
                if (item.getCap() != null)
                {
                    ps.setInt(i++, item.getCap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getObject_cap() != null)
                {
                    ps.setInt(i++, item.getObject_cap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
                }
                if (item.getPreweight() != null)
                {
                    ps.setInt(i++, item.getPreweight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getSinstall() != null)
                {
                    ps.setInt(i++, item.getSinstall());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getPullratio() != null)
                {
                    ps.setInt(i++, item.getPullratio());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getIs_onlypush() != null)
                {
                    ps.setInt(i++, item.getIs_onlypush());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getIs_exchange() != null)
                {
                    ps.setInt(i++, item.getIs_exchange());
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
//        	if(item.getStatus()==0){//实时数据只是修改cap时才清该键，目的是即使下架也要显示部分时间
        		Calendar calendar = Calendar.getInstance();
        		calendar.add(Calendar.DATE, -14);
        		Date date2 = calendar.getTime();
        		
        		String updatedate = OverseaDateUtil.NgsteamToString(date2, "yyyyMMdd");
        		String createdate =updatedate;
        		OverseaCacheFactory.delete(CACHE_KEY_ID + "4reatime_"+item.getId()+createdate+updatedate);
//        	}
        	
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_COUNTRY+item.getCountry());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_ADMIN + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_PROVIDER_ADMIN
                    + item.getType() + "_" + item.getProvider());
            OverseaRedisDb.getInstance().update(CACHE_KEY_REDIS_DB, item);
        }
    }

    /**
     * 插入
     */
    public int insert_tem(final OverseaAd item)
    {
        if (item == null)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table_tem());
        sb
                .append(" (`type`,`provider`,`name`, `pkg`, `apk`, `country`,`ecountry`,"
                        + "`mainicon`, `visit`, `click`, `cap`, `price`, `preweight`,`pullratio`,`sinstall`,`status`, `offerid`,`is_onlypush`,`is_exchange`,`createdate`,`updatedate`) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
               
                if (item.getCap() != null)
                {
                    ps.setInt(i++, item.getCap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
                }
                if (item.getPreweight() != null)
                {
                    ps.setInt(i++, item.getPreweight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPullratio() != null)
                {
                    ps.setInt(i++, item.getPullratio());
                }
                else
                {
                    ps.setInt(i++, 100);
                }

                if (item.getSinstall() != null)
                {
                    ps.setInt(i++, item.getSinstall());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getOfferid() != null)
                {
                    ps.setString(i++, item.getOfferid());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getIs_onlypush() != null)
                {
                    ps.setInt(i++, item.getIs_onlypush());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getIs_exchange() != null)
                {
                    ps.setInt(i++, item.getIs_exchange());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc);
        return id;
    }

    public void delAdTem(int provider)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM ").append(table_tem()).append(
                " WHERE provider=" + provider);
        logger.info(sql.toString());
        getJdbcTemplate().update(sql.toString());
    }

    //jary 2015-09-14
    public void Trancate_Bak()
    {
        StringBuffer sql = new StringBuffer();
        sql.append("truncate Table o_ad_bak");
        getJdbcTemplate().update(sql.toString());
    }
    //jary 2015-09-14
    public void insertTo_Bak()
    {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO o_ad_bak (SELECT * FROM o_ad WHERE (`status` in (0,-2,1)) or createdate >= '2015-08')");
        getJdbcTemplate().update(sql.toString());
    }
    
   

    //jary 2015-09-07
    public void update3(final OverseaAd_Tem item)
    {

        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `type`=?,`provider`=?, `name`=?, `pkg`=?, `apk`=?,"
                        + "`country`=?,`ecountry`=?,"
                        + "`mainicon` = ?, `visit`=?, `click`=?, `cap`=?,`object_cap`=?, "
                        + "`price`=?,`preweight`=?,`weight`=?, `status`=?,`sinstall`=?, `pullratio`=?,`offerid`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
               
                if (item.getCap() != null)
                {
                    ps.setInt(i++, item.getCap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getObject_cap() != null)
                {
                    ps.setInt(i++, item.getObject_cap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
                }
                if (item.getPreweight() != null)
                {
                    ps.setInt(i++, item.getPreweight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getWeight() != null)
                {
                    ps.setInt(i++, item.getWeight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getSinstall() != null)
                {
                    ps.setInt(i++, item.getSinstall());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getPullratio() != null)
                {
                    ps.setInt(i++, item.getPullratio());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getOfferid() != null)
                {
                    ps.setString(i++, item.getOfferid());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }

                ps.setLong(i++, item.getId());
            }
        };
        int id = getJdbcTemplate().update(sb.toString(), psc);
    }
    //jary 2015-9-10
    public void update2track(final OverseaAd item){

        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `type`=?,`provider`=?, `name`=?, `pkg`=?, `apk`=?,"
                        + "`country`=?,`ecountry`=?,"
                        + "`mainicon` = ?, `visit`=?, `click`=?, `cap`=?,`object_cap`=?, "
                        + "`price`=?,`preweight`=?,`weight`=?, `status`=?,`sinstall`=?, `pullratio`=?,`offerid`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                
                if (item.getCap() != null)
                {
                    ps.setInt(i++, item.getCap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getObject_cap() != null)
                {
                    ps.setInt(i++, item.getObject_cap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
                }
                if (item.getPreweight() != null)
                {
                    ps.setInt(i++, item.getPreweight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getWeight() != null)
                {
                    ps.setInt(i++, item.getWeight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getSinstall() != null)
                {
                    ps.setInt(i++, item.getSinstall());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getPullratio() != null)
                {
                    ps.setInt(i++, item.getPullratio());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getOfferid() != null)
                {
                    ps.setString(i++, item.getOfferid());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }

                ps.setLong(i++, item.getId());
            }
        };
        int id = getJdbcTemplate().update(sb.toString(), psc);
    }
    
    public void update2(final OverseaAd item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `type`=?,`provider`=?, `name`=?, `pkg`=?, `apk`=?,"
                        + "`country`=?,`ecountry`=?,"
                        + "`mainicon` = ?, `visit`=?, `click`=?,`cap`=?,`object_cap`=?, "
                        + "`price`=?,`preweight`=?,`weight`=?, `status`=?,`sinstall`=?, `pullratio`=?,`offerid`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCap() != null)
                {
                    ps.setInt(i++, item.getCap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getObject_cap() != null)
                {
                    ps.setInt(i++, item.getObject_cap());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
                }
                if (item.getPreweight() != null)
                {
                    ps.setInt(i++, item.getPreweight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getWeight() != null)
                {
                    ps.setInt(i++, item.getWeight());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getStatus() != null)
                {
                    ps.setInt(i++, item.getStatus());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getSinstall() != null)
                {
                    ps.setInt(i++, item.getSinstall());
                }
                else
                {
                    ps.setInt(i++, 0);
                }

                if (item.getPullratio() != null)
                {
                    ps.setInt(i++, item.getPullratio());
                }
                else
                {
                    ps.setInt(i++, 0);
                }
                if (item.getOfferid() != null)
                {
                    ps.setString(i++, item.getOfferid());
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
            OverseaCacheFactory.delete(CACHE_KEY_TYPE + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_ADMIN + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_PROVIDER_ADMIN
                    + item.getType() + "_" + item.getProvider());
            OverseaRedisDb.getInstance().update(CACHE_KEY_REDIS_DB, item);
        }
    }

    @SuppressWarnings("unchecked")
    public OverseaAd findSingle(long id)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof OverseaAd)
        {
            return (OverseaAd) obj;
        }
        else
        {
            OverseaAd item = OverseaRedisDb.getInstance().get(CACHE_KEY_REDIS_DB, id);
            if (item == null)
            {
                List<OverseaAd> list = query("select * from " + table()
                        + " where id=? LIMIT 1", new Object[] { id },
                        new int[] { Types.BIGINT }, new OverseaCommonRowMapper(
                                OverseaAd.class));
                if (list != null && list.size() > 0)
                {
                    item = list.get(0);
                    OverseaRedisDb.getInstance().insert(CACHE_KEY_REDIS_DB, item);
                }
            }
            if (item != null)
            {
                OverseaCacheFactory.add(CACHE_KEY_ID + id, item,
                        OverseaCacheFactory.ONE_MONTH);
            }
            return item;
        }
    }

    @SuppressWarnings("unchecked")
    public OverseaAd findSingle4Reatime(long id, String createdate,
            String updatedate)
    {
        String key = CACHE_KEY_ID + "4reatime_" + id + createdate + updatedate;
        Object obj = OverseaCacheFactory.get(key);
        if (obj != null && obj instanceof OverseaAd)
        {
            return (OverseaAd) obj;
        }
        else
        {
            List<OverseaAd> list = query(
                    "select * from "
                            + table()
                            + " where id=? and (`status`=0 or createdate>='"
                            + createdate + "' or updatedate >='" + updatedate
                            + "')", new Object[] { id },
                    new int[] { Types.BIGINT }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaAd item = list.get(0);
                if (item != null)
                {
                    OverseaCacheFactory.add(key, item,
                            OverseaCacheFactory.ONE_DAY);
                }
                return item;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAdFromCountyAndPkg(String county, String pkg)
    {
        String key = CACHE_KEY_ID + county + "_" + pkg;
        Object obj = null;
        try
        {
            obj = OverseaCacheFactory.get(key);
        }
        catch (Exception e1)
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where country=? and pkg=? and status = 0",
                    new Object[] { county, pkg }, new int[] { Types.VARCHAR,
                            Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                try
                {
                    OverseaCacheFactory.add(key, list,
                            OverseaCacheFactory.ONE_MONTH);
                }
                catch (Exception e)
                {// 处理key过长，报错
                    // e.printStackTrace();
                    logger.info("key 值过长已捕获,key=" + key);
                    return list;
                }
                return list;
            }
        }
        if (obj != null && obj instanceof OverseaAd)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where country=? and pkg=? and status = 0",
                    new Object[] { county, pkg }, new int[] { Types.VARCHAR,
                            Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                try
                {
                    OverseaCacheFactory.add(key, list,
                            OverseaCacheFactory.ONE_MONTH);
                }
                catch (Exception e)
                {// 处理key过长，报错
                    // e.printStackTrace();
                    logger.info("key 值过长已捕获,key=" + key);
                    return list;
                }
                return list;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByTypeStatuAdmin(int providerValue, int type,
            int status, String offerid, String packagename)
    {

        String key = CACHE_KEY_TYPE_STATUS_ADMIN + providerValue + type
                + status + offerid + packagename;
        // logger.info("key:"+key);
        // Object obj = NgsteamCacheFactory.get(key);
        //		
        // if (obj != null && obj instanceof List)
        // {
        // return (List<NgsteamAd>) obj;
        // }else
        // {
        String offeroradidSelection = "";
        String packgSelection = "";
        String proString = "";

        if (providerValue != 0)
        {
            proString = " and provider='" + providerValue + "'";
        }

        if (!OverseaStringUtil.isBlank(offerid) && !"0".equals(offerid))
        {
            offeroradidSelection = " and (offerid='" + offerid + "'"
                    + " or id='" + offerid + "')";

        }
        else if (!OverseaStringUtil.isBlank(packagename))
        {
            packgSelection = " and pkg='" + packagename + "'";
        }
        if (status == 8)
        {
            // 所有状态的

            StringBuffer sb = new StringBuffer("select * from " + table());
            sb.append(" where type =" + type).append(proString).append(
                    packgSelection).append(offeroradidSelection);
            logger.info(sb.toString());
            logger.info("status==8:" + sb.toString());
            List<OverseaAd> list = query(sb.toString(), null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(key, list,
                        OverseaCacheFactory.ONE_MINUTE * 5);
            }
            return list;
        }
        else
        {
            // 0或1状态的
            String sta_sele = " and status";
            if (status == -1)
            {
                sta_sele += "!=0";
            }
            else
            {
                sta_sele += "=0";
            }
            StringBuffer sb = new StringBuffer("select * from " + table());
            sb.append(" where type =" + type).append(proString)
            // .append(" and status ="+status)
                    .append(sta_sele).append(packgSelection).append(
                            offeroradidSelection);
            logger.info("status!=8" + sb.toString());
            List<OverseaAd> list = query(sb.toString(), null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(key, list,
                        OverseaCacheFactory.ONE_MINUTE * 5);
            }
            return list;
        }
        // }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAdFromPkg(String pkg)
    {
        Object obj = null;
        if (obj != null && obj instanceof OverseaAd)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where pkg=?  ORDER BY createdate DESC LIMIT 1",
                    new Object[] { pkg }, new int[] { Types.VARCHAR },
                    new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ID + "_" + pkg, list,
                        OverseaCacheFactory.ONE_MONTH);
                return list;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByType(int type)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_TYPE + type);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where type = ? and status = 0 ", new Object[] { type },
                    new int[] { Types.INTEGER }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_TYPE + type, list,
                        OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAffliateByProvider(int type, int provider)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_TYPE + type + provider);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where type = ? and provider = ? and status = 0 ",
                    new Object[] { type, provider }, new int[] { Types.INTEGER,
                            Types.INTEGER }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_TYPE + type + provider, list,
                        OverseaCacheFactory.ONE_HOUR * 3);
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAffliateByProvider(String provider)
    {
//        Object obj = OverseaCacheFactory.get(CACHE_KEY_AFFLICATE + provider);
//    	
//        if (obj != null && obj instanceof List)
//        {
//            return (List<OverseaAd>) obj;
//        }
//        else
//        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where type = 3 and provider in (" + provider
                    + ") and status = 0 ", null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
//            if (list != null && list.size() > 0)
//            {
//               OverseaCacheFactory.add(CACHE_KEY_AFFLICATE + provider, list,OverseaCacheFactory.ONE_HOUR);
//            }
            return list;
   
    
    //}
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAffliateByProviderInsertToday(int type,
            int provider, long datetime)
    {
    	
        Object obj = OverseaCacheFactory.get(CACHE_KEY_TYPE + type + provider
                + datetime);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
             List<OverseaAd> list = query("select * from " + table()
             + " where type = ? and provider = ? and status = 0 and createdate>= ?",
             new Object[] { type, provider,datetime }, new int[] {
             Types.INTEGER,
             Types.INTEGER ,Types.LONGVARCHAR}, new OverseaCommonRowMapper(
             OverseaAd.class));
//            List<NgsteamAd> list = query("select * from " + table()
//                    + " where type = ? and provider = ? and status = 0",
//                    new Object[] { type, provider }, new int[] { Types.INTEGER,
//                            Types.INTEGER }, new NgsteamCommonRowMapper(
//                            NgsteamAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_TYPE + type + provider
                        + datetime, list, OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

    //jary  2015-9-10
    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAffliateByProviderInsertToday2(int type,
            int provider, long datetime)
            {
    	List<OverseaAd> list = query("select * from " + table()
    			+ " where type = ? and provider = ? and status = 0 ",
    			new Object[] { type, provider }, new int[] {
    		Types.INTEGER,Types.INTEGER }, new OverseaCommonRowMapper(OverseaAd.class));
    	return list;

            }
    
    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByTypeAdmin(int type)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_TYPE_ADMIN + type);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where type = ?", new Object[] { type },
                    new int[] { Types.INTEGER }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_TYPE_ADMIN + type, list,
                        OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByTypeProviderAdmin(int type, int provider)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_TYPE_PROVIDER_ADMIN
                + type + "_" + provider);
        // obj = null;
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            String sele_provider = " and provider = " + provider;
            if (provider == 0)
            {
                sele_provider = "";
            }
            StringBuffer sb = new StringBuffer();
            sb.append("select id,cap,name,price,provider from ")
                    .append(table()).append(" where type = " + type).append(
                            sele_provider).append(" and `status`=0");
            logger.info(sb.toString());
            List<OverseaAd> list = query(sb.toString(), null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_TYPE_PROVIDER_ADMIN + type
                        + "_" + provider, list, OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByTypeProviderPackAdmin(int type, int provider,
            String packgename)
    {
        String key = CACHE_KEY_TYPE_PROVIDER_ADMIN + type + "_" + provider;
        if (!OverseaStringUtil.isBlank(packgename))
        {
            key += "_" + packgename;
        }
        Object obj = OverseaCacheFactory.get(key);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            String packgSelection = "";
            if (!OverseaStringUtil.isBlank(packgename))
            {
                packgSelection = " and pkg='" + packgename + "'";
            }
            StringBuffer sb = new StringBuffer("select * from " + table());
            sb.append(" where type =" + type).append(
                    " and provider=" + provider).append(packgSelection);
            logger.info(sb.toString());
            List<OverseaAd> list = query(sb.toString(), null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(key, list, OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAll()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = OverseaRedisDb.getInstance().getAll(
                    CACHE_KEY_REDIS_DB);
            if (list == null || list.size() <= 0)
            {
                list = query("select * from " + table() + " where status = 0 ",
                        null, null, new OverseaCommonRowMapper(OverseaAd.class));
                OverseaRedisDb.getInstance().replaceAll(CACHE_KEY_REDIS_DB,
                        list);
            }
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL, list,
                        OverseaCacheFactory.ONE_HOUR);
            }
            return list;
        }
    }


    @SuppressWarnings("unchecked")
    public List<OverseaAd> loadAllFromDbToRedis()
    {
        List<OverseaAd> list = query("select * from " + table() + " where status = 0 ",
                null, null, new OverseaCommonRowMapper(OverseaAd.class));
        OverseaRedisDb.getInstance().replaceAll(CACHE_KEY_REDIS_DB, list);
        return list;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAllFromTerm()
    {
        List<OverseaAd> list = query("select * from " + table_tem(), null,
                null, new OverseaCommonRowMapper(OverseaAd.class));
        return list;
    }

    // @SuppressWarnings("unchecked")
    // public List<NgsteamAd> findProvidersFromTerm()
    // {
    // List<NgsteamAd> list = query("select DISTINCT provider from" +
    // table_tem(), null, null,
    // new NgsteamCommonRowMapper(NgsteamAd.class));
    // return list;
    // }

//    @SuppressWarnings("unchecked")
//    public List<NgsteamAd> findAllByCountry(String country)
//    {
//        Object obj = NgsteamCacheFactory.get(CACHE_KEY_ALL + country);
//        // obj = null;
//        if (obj != null && obj instanceof List)
//        {
//            return (List<NgsteamAd>) obj;
//        }
//        else
//        {
//            StringBuffer sb = new StringBuffer("select * from " + table()
//                    + " where status = 0 ");
//            sb.append(" and find_in_set('" + country + "', country)").append(
//                    " and type=7");
//            logger.info(sb.toString());
//            List<NgsteamAd> list = query(sb.toString(), null, null,
//                    new NgsteamCommonRowMapper(NgsteamAd.class));
//            if (list != null && list.size() > 0)
//            {
//                NgsteamCacheFactory.add(CACHE_KEY_ALL + country, list,
//                        NgsteamCacheFactory.ONE_DAY);
//            }
//            return list;
//        }
//    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByStatus()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_STATUS);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where status = 2 and (type = 2 or type = 3) ", null,
                    null, new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_STATUS, list,
                        OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByCountry(String country)
    {
        String key = CACHE_KEY_COUNTRY + country;
        Object obj = OverseaCacheFactory.get(key);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> allList = findAll();
            List<OverseaAd> list = null;
            if (allList.size() > 0)
            {
                list = new ArrayList<OverseaAd>();
                for (OverseaAd item : allList)
                {
                    if ((item.getCountry() != null)
                            && (item.getCountry().contains(country) || item
                                    .getCountry().equalsIgnoreCase("ALL")))
                    {
                        list.add(item);
                    }
                }
            }
            if (list == null)
            {
                list = query("select * from " + table()
                        + " where status = 0 and (country like '%" + country
                        + "%' or country = 'ALL') ", null, null,
                        new OverseaCommonRowMapper(OverseaAd.class));
            }
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(key, list, OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

    /**
     * 更新
     */
    public void updateStatusByPkg(final String pkg)
    {
        if (pkg == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `status`= 0, `updatedate`=CURRENT_TIMESTAMP WHERE `pkg`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                if (pkg != null)
                {
                    ps.setString(1, pkg);
                }
                else
                {
                    ps.setNull(1, Types.NULL);
                }
            }
        };
        getJdbcTemplate().update(sb.toString(), psc);
    }

    /**
     * 更新广告状态为不可用
     */
    public void updateDisableByPkg(final String pkg)
    {
        if (pkg == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `status`= 2, `updatedate`=CURRENT_TIMESTAMP WHERE `pkg`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                if (pkg != null)
                {
                    ps.setString(1, pkg);
                }
                else
                {
                    ps.setNull(1, Types.NULL);
                }
            }
        };
        getJdbcTemplate().update(sb.toString(), psc);
    }

    public int updateCom2(final List<OverseaAd_Tem> list)
            throws DataAccessException
    {

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `type`=?,`provider`=?, `name`=?, `pkg`=?, `apk`=?,"
                        + "`country`=?,`ecountry`=?,"
                        + "`mainicon` = ?, `visit`=?, `click`=?, "
                        + "`price`=?, `object_cap`=?,`status`=? ,`updatedate`=CURRENT_TIMESTAMP WHERE `provider`=? and `pkg`=? and `country`=? and `offerid`=? and `status`=0");

        String sql = sb.toString();
        try
        {
            int[] ii = this.getJdbcTemplate().batchUpdate(sql,
                    new MyupdateCom2(list));
            return ii.length;
        }
        catch (org.springframework.dao.DataAccessException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    private class MyupdateCom2 implements BatchPreparedStatementSetter
    {
        final List temList;

        public MyupdateCom2(List list)
        {
            temList = list;
        }

        public int getBatchSize()
        {
            return temList.size();
        }

        public void setValues(PreparedStatement ps, int t) throws SQLException
        {

            if (t % 5 == 0)
                logger.info("has already updated...=" + t);
            if (t == 1035)
                logger.info("has already updated...=" + t);
            OverseaAd_Tem item = (OverseaAd_Tem) temList.get(t);
            int i = 1;
            if (item.getType() != null)
            {
                ps.setInt(i++, item.getType());
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
            if (item.getName() != null)
            {
                ps.setString(i++, item.getName());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }
            if (item.getPkg() != null)
            {
                ps.setString(i++, item.getPkg());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }
            if (item.getApk() != null)
            {
                ps.setString(i++, item.getApk());
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
            if (item.getEcountry() != null)
            {
                ps.setString(i++, item.getEcountry());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }
            if (item.getMainicon() != null)
            {
                ps.setString(i++, item.getMainicon());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }
            if (item.getVisit() != null)
            {
                ps.setString(i++, item.getVisit());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }
            if (item.getClick() != null)
            {
                ps.setString(i++, item.getClick());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }

            if (item.getPrice() != null)
            {
                ps.setFloat(i++, item.getPrice());
            }
            else
            {
                ps.setFloat(i++, 0f);
            }
            if (item.getObject_cap() != null)
            {
                ps.setInt(i++, item.getObject_cap());
            }
            else
            {
                ps.setInt(i++, 0);
            }
            if (item.getStatus() != null)
            {
                ps.setInt(i++, item.getStatus());
            }
            else
            {
                ps.setInt(i++, 0);
            }

            if (item.getProvider() != null)
            {
                ps.setLong(i++, item.getProvider());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }
            if (item.getPkg() != null)
            {
                ps.setString(i++, item.getPkg());
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
            if (item.getOfferid() != null)
            {
                ps.setString(i++, item.getOfferid());
            }
            else
            {
                ps.setNull(i++, Types.NULL);
            }
        }
    }

    // jary 都有的
    public void updateCom(final List<OverseaAd_Tem> item1)
    {

        int t = 0;
        for (final OverseaAd_Tem item : item1)
        {

            if (item == null)
            {
                return;
            }

    		StringBuffer sb = new StringBuffer();
    		sb.append("UPDATE ");
    		sb.append(table());
    		sb.append(" SET `price`=?, `object_cap`=? ,`updatedate`=CURRENT_TIMESTAMP " +
    						"WHERE `provider`=? and `pkg`=? and `country`=? and `offerid`=? and `status`=0");
//    		.append(" SET `type`=?,`provider`=?, `name`=?, `pkg`=?, `apk`=?,"
//    				+ "`country`=?,`ecountry`=?,"
//    				+ "`mainicon` = ?, `visit`=?, `click`=?,  "
//    				+ "`price`=?, `object_cap`=?,`status`=? ,`updatedate`=CURRENT_TIMESTAMP " +
//    						"WHERE `provider`=? and `pkg`=? and `country`=? and `offerid`=? and `status`=0");
    		PreparedStatementSetter psc = new PreparedStatementSetter()
    		{
    			public void setValues(PreparedStatement ps) throws SQLException
    			{
    				int i = 1;
    		
                    if (item.getPrice() != null)
                    {
                        ps.setFloat(i++, item.getPrice());
                    }
                    else
                    {
                        ps.setFloat(i++, 0f);
                    }
                    if (item.getObject_cap() != null)
                    {
                        ps.setInt(i++, item.getObject_cap());
                    }
                    else
                    {
                        ps.setInt(i++, 0);
                    }
                    if (item.getProvider() != null)
                    {
                        ps.setLong(i++, item.getProvider());
                    }
                    else
                    {
                        ps.setNull(i++, Types.NULL);
                    }
                    if (item.getPkg() != null)
                    {
                        ps.setString(i++, item.getPkg());
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
                    if (item.getOfferid() != null)
                    {
                        ps.setString(i++, item.getOfferid());
                    }
                    else
                    {
                        ps.setNull(i++, Types.NULL);
                    }

                }
            };
            if (t % 100 == 0)
                logger.info("updateCom 100 per t=" + t);
            int id = getJdbcTemplate().update(sb.toString(), psc);
            t++;
        }
    }

    /**
     * 更新部分offer信息
     * 
     * @author chenbq
     */
    public void updateAds(final OverseaAd item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `type`=?,`provider`=?, `name`=?, `pkg`=?, `apk`=?,"
                        + "`country`=?,`ecountry`=?,"
                        + "`mainicon` = ?, `visit`=?, `click`=?, "
                        + "`price`=?, `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                if (item.getType() != null)
                {
                    ps.setInt(i++, item.getType());
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
                if (item.getName() != null)
                {
                    ps.setString(i++, item.getName());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null)
                {
                    ps.setString(i++, item.getPkg());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getApk() != null)
                {
                    ps.setString(i++, item.getApk());
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
                if (item.getEcountry() != null)
                {
                    ps.setString(i++, item.getEcountry());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMainicon() != null)
                {
                    ps.setString(i++, item.getMainicon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVisit() != null)
                {
                    ps.setString(i++, item.getVisit());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getClick() != null)
                {
                    ps.setString(i++, item.getClick());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                

                if (item.getPrice() != null)
                {
                    ps.setFloat(i++, item.getPrice());
                }
                else
                {
                    ps.setFloat(i++, 0f);
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
            OverseaCacheFactory.delete(CACHE_KEY_TYPE + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_ADMIN + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_PROVIDER_ADMIN
                    + item.getType() + "_" + item.getProvider());
        }
    }

    @SuppressWarnings("unchecked")
    public OverseaAd findSingleByStoreid(String stroeid)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_TAPJOYID + stroeid);
        if (obj != null && obj instanceof OverseaAd)
        {
            return (OverseaAd) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where mainicon like '" + stroeid + "%' LIMIT 1", null,
                    null, new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaAd item = list.get(0);
                if (item != null)
                {
                    OverseaCacheFactory.add(CACHE_KEY_ID + stroeid, item,
                            OverseaCacheFactory.ONE_MONTH);
                }
                return item;
            }
            return null;
        }
    }

    public List<OverseaAd> findByPkg(String pkg)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_PKG + pkg);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            StringBuffer sb = new StringBuffer("select * from " + table());
            sb.append(" where pkg ='" + pkg + "'").append(" and `status`=0");
            logger.info(sb.toString());
            List<OverseaAd> list = query(sb.toString(), null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
            OverseaCacheFactory.add(CACHE_KEY_PKG + pkg, list,
                    OverseaCacheFactory.ONE_DAY);
            return list;
        }
    }

    // jary 2015-8-24 String key =p+":"+k+":"+c+":"+offerid;
    public List<OverseaAd> findByKey(String k0, String k1, String k2, String k3)
    {
    	Object obj = OverseaCacheFactory.get(CACHE_KEY_KEYS + k0+k1+k2+k3);
    	if (obj != null && obj instanceof List)
    	{
    		return (List<OverseaAd>) obj;
    	}
    	else
    	{
    		StringBuffer sb = new StringBuffer("select * from "+table());
    		sb.append(" where `provider`="+k0+" and `pkg`='"+k1+"' and `country`='"+k2+"' and `offerid`='"+k3+"' and `status`=-2 and `is_manual`=0 ORDER BY updatedate DESC");
    		//logger.info(sb.toString());
    		List<OverseaAd> list = query(sb.toString(), null,
			null, new OverseaCommonRowMapper(OverseaAd.class));
			OverseaCacheFactory.add(CACHE_KEY_KEYS + k0+k1+k2+k3, list,
					OverseaCacheFactory.ONE_DAY);
    		return list;
    	}
    }

    @SuppressWarnings("unchecked")
    public List<OverseaRanking> findAdPricelistByDate(String date)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_PRICE + date);
        if (obj != null && obj instanceof OverseaRanking)
        {
            return (List<OverseaRanking>) obj;
        }
        else
        {
            List<OverseaRanking> list = query("select id adid, price from "
                    + table() + " where status = 0 order by price desc", null,
                    null, new OverseaCommonRowMapper(OverseaRanking.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_PRICE + date, list,
                        OverseaCacheFactory.ONE_DAY * 2);
                return list;
            }
            return null;
        }
    }

   


    public List<OverseaAd> findManual(String pros)
    {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();
        sb.append("select * from  " + table()).append(
                " where provider in (" + pros + ")").append(
                " and is_manual = 1").append(" and status = -2");
        String sql = sb.toString();
        logger.info(sql);
        List<OverseaAd> list = query(sql, null, null,
                new OverseaCommonRowMapper(OverseaAd.class));

        return list;
    }
    
   
    /**
     * 需要从数据库获取所有status为0的offer，放入redis。注：不需要加缓存
     * 
     * @author chenbq
     * 
     * @return
     */
    public List<OverseaAd> updateRedisFromDb()
    {
    	//String k="com.mango.ads.dao.AdDao_redis_db";
    	List<OverseaAd> list = query("select * from " + table() + " where status = 0 ",
                null, null, new OverseaCommonRowMapper(OverseaAd.class));
         OverseaRedisDb.getInstance().updateRedisFromDb(list);
        
        return list;
        
    }
   

    public static void main(String[] args) {
		System.out.println(redis_Db());
	}
}
