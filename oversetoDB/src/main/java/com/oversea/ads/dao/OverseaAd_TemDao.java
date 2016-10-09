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
import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaOptimizing;
import com.oversea.ads.bean.OverseaRanking;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaAd_TemDao extends OverseaBaseDao
{
    private static final String TBLPREFIX                     = "o_ad";
    //临时表，用于存放最新offer
    private static final String TBLPREFIX_TEM                     = "o_ad_tem";
    private static final String CACHE_KEY_ALL                 = OverseaAd_TemDao.class
                                                                      .getName()
                                                                      + "_all";
    private static final String CACHE_KEY_ID                  = OverseaAd_TemDao.class
                                                                      .getName()
                                                                      + "_id";
    private static final String CACHE_KEY_TYPE                = OverseaAd_TemDao.class
                                                                      .getName()
                                                                      + "_type";
    private static final String CACHE_KEY_TYPE_ADMIN          = OverseaAd_TemDao.class
                                                                      .getName()
                                                                      + "_type_admin";

    private static final String CACHE_KEY_TYPE_PROVIDER_ADMIN = OverseaAd_TemDao.class
                                                                      .getName()
                                                                      + "_type_provider_admin";
    private static final String CACHE_KEY_PROVIDER = OverseaAd_TemDao.class
    .getName()
    + "_provider";

    private static final String CACHE_KEY_TAPJOYID            = OverseaAd_TemDao.class
                                                                      .getName()
                                                                      + "_tapjoyid";

    private static final String CACHE_KEY_STATUS              = OverseaAd_TemDao.class
                                                                      .getName()
                                                                      + "_status_1";
    private static final String CACHE_KEY_PKG              = OverseaAd_TemDao.class
    .getName()
    + "_pkg_";
    
    private static final String CACHE_KEY_PRICE              = OverseaAd_TemDao.class
    .getName()
    + "_price_";
    private static final String	CACHE_KEY_PREWEIHGT				= OverseaAd_TemDao.class
    .getName()
    + "_preweight_";

    private static final String CACHE_KEY_TYPE_STATUS_ADMIN          = OverseaAd_TemDao.class
    .getName()
    + "_type_status_admin";
    
    private static final String CACHE_KEY_COUNTRY             = OverseaAd_TemDao.class
    .getName()
    + "country";
    
    private static final String CACHE_KEY_AFFLICATE                = OverseaAd_TemDao.class
    .getName()
    + "_AFFLICATE";
    
    public static String table()
    {
        return TBLPREFIX;
    }
    public static String table_tem()
    {
    	return TBLPREFIX_TEM;
    }

    /**
     * 插入
     */
    public int insert_tem(final OverseaAd_Tem item)
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
                        + "`mainicon`, `visit`, `click`, `cap`, `object_cap`, `price`, `preweight`,`weight`,`pullratio`,`sinstall`,`status`, `offerid`,`is_onlypush`,`is_exchange`,`createdate`,`updatedate`) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?,?, ?,?, ?,?,?,  ?, ?, ?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
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
                if (item.getWeight() != null)
                {
                	ps.setInt(i++, item.getWeight());
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

    public void delAdTem(int provider){
    	StringBuffer sql = new StringBuffer();
    	sql.append("DELETE FROM ").append(table_tem())
    	.append(" WHERE provider="+provider);
    	//logger.info(sql.toString());
    	getJdbcTemplate().update(sql.toString());
    }
    
    @SuppressWarnings("unchecked")
    public List<OverseaAd_Tem> findAllFromTerm()
    {
        List<OverseaAd_Tem> list = query("select * from " + table_tem(), null, null,
                new OverseaCommonRowMapper(OverseaAd_Tem.class));
        return list;
    }
    
//    @SuppressWarnings("unchecked")
//    public List<NgsteamAd> findProvidersFromTerm()
//    {
//        List<NgsteamAd> list = query("select DISTINCT provider from" + table_tem(), null, null,
//                new NgsteamCommonRowMapper(NgsteamAd.class));
//        return list;
//    }
    
	public List<OverseaAd_Tem> findProviders() {
		// TODO Auto-generated method stub
		List<OverseaAd_Tem> list = query("select provider from " + table_tem()+ " where provider in (SELECT id FROM o_provider WHERE `status`=0) group by provider", null, null,
                new OverseaCommonRowMapper(OverseaAd_Tem.class));
        return list;
	}
	
	   @SuppressWarnings("unchecked")
	    public List<OverseaAd_Tem> findAffliateByProvider(String provider)
	    {
	    	//String in= "select provider from " + table_tem()+ " group by provider";
	            List<OverseaAd_Tem> list = query("select * from " + table_tem()
	                    + " where type = 3 and provider in ("+ provider + ") ",
	                    null, null, new OverseaCommonRowMapper(
	                            OverseaAd_Tem.class));
	            return list;
	    }
	
//---------------------------------------------以下为移植AD表的---------------------------------------------------
	
	
    /**
     * 插入v
     */
    public int insert(final OverseaAd item)
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
                        + "`mainicon`, `visit`, `click`, `cap`,`object_cap`, `price`, `preweight`,`pullratio`,`sinstall`,`status`, `offerid`,`is_onlypush`,`is_exchange`,`createdate`,`updatedate`) VALUES (?,?,  ?, ?, ?, ?, ?, ?,?, ?, ?,?,?,?, ?, ?, ?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
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
                else{
                	ps.setInt(i++, 0);
                }
                if (item.getIs_exchange() != null)
                {
                	ps.setInt(i++, item.getIs_exchange());
                }
                else{
                	ps.setInt(i++, 0);
                }
                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.delete(CACHE_KEY_TYPE + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_ADMIN + item.getType());
            OverseaCacheFactory.delete(CACHE_KEY_TYPE_PROVIDER_ADMIN
                    + item.getType() + "_" + item.getProvider());
        }
        return id;
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
                	logger.info("insert ing.object_cap........"+item.getObject_cap());
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
                }else{
                	ps.setInt(i++, 0);
                }
                if (item.getIs_exchange() != null)
                {
                	ps.setInt(i++, item.getIs_exchange());
                }else{
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
    

    public void updateWeight (final List<OverseaOptimizing> list){

    	StringBuffer sb = new StringBuffer();
    	sb.append("UPDATE ");
    	sb.append(table());
		sb.append(" SET `weight`=? WHERE `id`=?");
    	final String sql = sb.toString();
    	getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				OverseaOptimizing item = list.get(index);
				int i = 1;
                if (item.getScore()!= null)
                {
                    ps.setFloat(i++, item.getScore());
                }else{
                	ps.setNull(i++, Types.NULL);
                }
                	ps.setLong(i++, item.getAdid());
               
			}
			 //返回更新的结果集条数  
			public int getBatchSize() {
				return list.size();
			}
		});
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
    public OverseaAd findSingle(long id)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof OverseaAd)
        {
            return (OverseaAd) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where id=? LIMIT 1", new Object[] { id },
                    new int[] { Types.BIGINT }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaAd item = list.get(0);
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
    public List<OverseaAd> findAdFromCountyAndPkg(String county, String pkg)
    {
    	String key  = CACHE_KEY_ID + county + "_" + pkg;
    	Object obj =null;
		try {
			obj = OverseaCacheFactory.get(key);
		} catch (Exception e1) {
			List<OverseaAd> list = query("select * from " + table()
                    + " where country=? and pkg=? and status = 0",
                    new Object[] { county, pkg }, new int[] { Types.VARCHAR,
                            Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                try {
					OverseaCacheFactory.add(key,
					        list, OverseaCacheFactory.ONE_MONTH);
				} catch (Exception e) {//处理key过长，报错
//					e.printStackTrace();
					logger.info("key 值过长已捕获,key="+key);
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
                try {
					OverseaCacheFactory.add(key,
					        list, OverseaCacheFactory.ONE_MONTH);
				} catch (Exception e) {//处理key过长，报错
//					e.printStackTrace();
					logger.info("key 值过长已捕获,key="+key);
					return list;
				}
                return list;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findByTypeStatuAdmin(int providerValue,int type,int status,String offerid,String packagename)
    {
    	
    	String key = CACHE_KEY_TYPE_STATUS_ADMIN +providerValue+ type+status+offerid+packagename;
//    	logger.info("key:"+key);
//    	Object obj = NgsteamCacheFactory.get(key);
//		
//    	if (obj != null && obj instanceof List)
//    	{
//    		return (List<NgsteamAd>) obj;
//    	}else 
//    	{
        	String offeroradidSelection = "";
    		String packgSelection="";
    		String proString="";
    		
    		if (providerValue!=0) {
    			proString = " and provider='"+providerValue+"'";
    		}
    		
    		if(!OverseaStringUtil.isBlank(offerid)&&!"0".equals(offerid)){
    			offeroradidSelection = " and (offerid='"+offerid+"'"+" or id='"+offerid+"')";
    			
    		}else if(!OverseaStringUtil.isBlank(packagename)) {
    			packgSelection = " and pkg='"+packagename+"'";
    		}
    		if (status==8) {
    			//所有状态的
    			
    			StringBuffer sb = new StringBuffer("select * from " +table());
    			sb.append(" where type ="+type)
    			.append(proString)
    			.append(packgSelection)
    			.append(offeroradidSelection);
    			logger.info(sb.toString());
    			logger.info("status==8:"+sb.toString());
    			List<OverseaAd> list = query(sb.toString(),null,null,
    					new OverseaCommonRowMapper(OverseaAd.class));
    			if (list != null && list.size() > 0)
    			{
    				OverseaCacheFactory.add(key, list,
    						OverseaCacheFactory.ONE_MINUTE*5);
    			}
    			return list;
    		}else {
    			//0或1状态的
    			String sta_sele = " and status";
    			if(status==-1){
    				sta_sele+="!=0";
    			}else{
    				sta_sele+="=0";
    			}
    			StringBuffer sb = new StringBuffer("select * from " +table());
    			sb.append(" where type ="+type)
    			.append(proString)
//    			.append(" and status ="+status)
    			.append(sta_sele)
    			.append(packgSelection)
    			.append(offeroradidSelection);
    			logger.info("status!=8"+sb.toString());
    			List<OverseaAd> list = query(sb.toString(),null,null,
    					new OverseaCommonRowMapper(OverseaAd.class));
    			if (list != null && list.size() > 0)
    			{
					OverseaCacheFactory.add(key, list,
    						OverseaCacheFactory.ONE_MINUTE*5);
    			}
    			return list;
    		}
//    	}
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
    public List<OverseaAd> findAffliateByProviderInsertToday(int type, int provider,long datetime)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_TYPE + type + provider + datetime);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
//            List<NgsteamAd> list = query("select * from " + table()
//                    + " where type = ? and provider = ? and status = 0 and createdate>= ?",
//                    new Object[] { type, provider,datetime }, new int[] { Types.INTEGER,
//                            Types.INTEGER ,Types.LONGVARCHAR}, new NgsteamCommonRowMapper(
//                            NgsteamAd.class));
            List<OverseaAd> list = query("select * from " + table()
                    + " where type = ? and provider = ? and status = 0",
                    new Object[] { type, provider }, new int[] { Types.INTEGER,
                            Types.INTEGER }, new OverseaCommonRowMapper(
                            OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_TYPE + type+provider+datetime, list,
                        OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
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
//        obj = null;
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
        	String sele_provider = " and provider = "+provider;
        	if(provider==0){
        		sele_provider = "";
        	}
        	StringBuffer sb = new StringBuffer();
        	sb.append("select id,cap,name,price,provider from " )
        	.append(table())
        	.append(" where type = "+type).append(sele_provider).append(" and `status`=0");
        	logger.info(sb.toString());
            List<OverseaAd> list = query(sb.toString(),null,null,
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
    public List<OverseaAd> findByTypeProviderPackAdmin(int type, int provider,String packgename)
    {
    	String key = CACHE_KEY_TYPE_PROVIDER_ADMIN+ type + "_" + provider;
    	if(!OverseaStringUtil.isBlank(packgename)){
    		key+="_"+packgename;
    	}
        Object obj = OverseaCacheFactory.get(key);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
        	String packgSelection = "";
        	if(!OverseaStringUtil.isBlank(packgename)){
        		packgSelection = " and pkg='"+packgename+"'";
        	}
        	StringBuffer sb = new StringBuffer("select * from " +table());
        	sb.append(" where type ="+type)
        	.append(" and provider="+provider)
        	.append(packgSelection);
        	logger.info(sb.toString());
            List<OverseaAd> list = query(sb.toString(),null,null,
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
            List<OverseaAd> list = query("select * from " + table()
                    + " where status = 0 ", null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL, list,
                        OverseaCacheFactory.ONE_HOUR*2);
            }
            return list;
        }
    }
    
   
    

    @SuppressWarnings("unchecked")
    public List<OverseaAd> findAllByCountry(String country)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL + country);
        // obj = null;
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            StringBuffer sb = new StringBuffer("select * from " + table()
                    + " where status = 0 ");
            sb.append(" and find_in_set('" + country + "', country)").append(
                    " and type=7");
            logger.info(sb.toString());
            List<OverseaAd> list = query(sb.toString(), null, null,
                    new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL + country, list,
                        OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }

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
    	String key = CACHE_KEY_COUNTRY+country;
        Object obj = OverseaCacheFactory.get(key);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaAd>) obj;
        }
        else
        {
            List<OverseaAd> list = query("select * from " + table()
                    + " where status = 0 and (country like '%"+country+"%' or country = 'ALL') ", null,
                    null, new OverseaCommonRowMapper(OverseaAd.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(key, list,
                        OverseaCacheFactory.ONE_HOUR*2);
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
    		StringBuffer sb = new StringBuffer("select * from "+table());
    		sb.append(" where pkg ='"+pkg+"'")
    		.append(" and `status`=0");
    		logger.info(sb.toString());
    		List<OverseaAd> list = query(sb.toString(), null,
			null, new OverseaCommonRowMapper(OverseaAd.class));
			OverseaCacheFactory.add(CACHE_KEY_PKG + pkg, list,
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
        	List<OverseaRanking> list = query("select id adid, price from " + table()
                    + " where status = 0 order by price desc", null, null,
                    new OverseaCommonRowMapper(OverseaRanking.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_PRICE + date , list, OverseaCacheFactory.ONE_DAY *2);
                return list;
            }
            return null;
        }
    }

	public List<OverseaRanking> findPreWeightlistByDate(String date) {
		// TODO Auto-generated method stub
		 Object obj = OverseaCacheFactory.get(CACHE_KEY_PREWEIHGT + date);
	        if (obj != null && obj instanceof OverseaRanking)
	        {
	            return (List<OverseaRanking>) obj;
	        }
	        else
	        {
	        	List<OverseaRanking> list = query("select id adid, preweight num from " + table()
	                    + " where  status = 0 ORDER BY num desc", null, null,
	                    new OverseaCommonRowMapper(OverseaRanking.class));
	            if (list != null && list.size() > 0) 
	            {
	                OverseaCacheFactory.add(CACHE_KEY_PREWEIHGT + date , list, OverseaCacheFactory.ONE_DAY *2);
	                return list;
	            }
	            return null;
	        }
	}
    
	public List<OverseaAd> findAdByProvider(String provider) {
		Object obj = OverseaCacheFactory.get(CACHE_KEY_PROVIDER + provider);
		if (obj != null && obj instanceof OverseaRanking)
		{
			return (List<OverseaAd>) obj;
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("select `id`,`name` from ")
			.append(table()).append(" where provider = "+provider+"")
			.append(" and is_exchange = 1");
			String sql = sb.toString();
			logger.info(sql);
			List<OverseaAd> list = query(sql, null, null,
					new OverseaCommonRowMapper(OverseaAd.class));
			if (list != null && list.size() > 0) 
			{
				OverseaCacheFactory.add(CACHE_KEY_PROVIDER + provider , list, OverseaCacheFactory.ONE_DAY *2);
				return list;
			}
			return null;
		}
	}

	

}
