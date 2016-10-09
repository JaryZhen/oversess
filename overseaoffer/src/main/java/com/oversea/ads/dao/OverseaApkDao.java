package com.oversea.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mysql.jdbc.Statement;
import com.oversea.ads.bean.OverseaApk;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaApkDao extends OverseaBaseDao
{
    private static final String TBLPREFIX     = "o_apk";
    private static final String CACHE_KEY_ALL = OverseaApkDao.class.getName()
                                                      + "_all";
    private static final String CACHE_KEY_ID  = OverseaApkDao.class.getName()
                                                      + "_id";
    private static final String CACHE_KEY_PKG = OverseaApkDao.class.getName()
                                                      + "_pkg";

    public static String table()
    {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final OverseaApk item)
    {
        if (item == null)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`name`,`pkg`,`apk`, `icon`, `version`,`versioncode`,"
                        + "`androidversion`, `category`, `subcategory`, `size`,`md5`,`status`, `createdate`, `updatedate`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
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
                if (item.getIcon() != null)
                {
                    ps.setString(i++, item.getIcon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVersion() != null)
                {
                    ps.setString(i++, item.getVersion());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVersioncode() != null)
                {
                    ps.setInt(i++, item.getVersioncode());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getAndroidversion() != null)
                {
                    ps.setString(i++, item.getAndroidversion());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCategory() != null)
                {
                    ps.setString(i++, item.getCategory());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getSubcategory() != null)
                {
                    ps.setLong(i++, item.getSubcategory());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getSize() != null)
                {
                    ps.setLong(i++, item.getSize());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMd5() != null)
                {
                    ps.setString(i++, item.getMd5());
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
            OverseaCacheFactory.delete(CACHE_KEY_PKG + item.getPkg());
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final OverseaApk item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `name`=?,`pkg`=?, `apk`=?, `icon`=?, `version`=?,`versioncode`=?,"
                        + "`androidversion` = ?, `category`=?, `subcategory`=?, `size`=?, `md5`=?,`status`=?,`updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
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
                if (item.getIcon() != null)
                {
                    ps.setString(i++, item.getIcon());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVersion() != null)
                {
                    ps.setString(i++, item.getVersion());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVersioncode() != null)
                {
                    ps.setInt(i++, item.getVersioncode());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getAndroidversion() != null)
                {
                    ps.setString(i++, item.getAndroidversion());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCategory() != null)
                {
                    ps.setString(i++, item.getCategory());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getSubcategory() != null)
                {
                    ps.setLong(i++, item.getSubcategory());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getSize() != null)
                {
                    ps.setLong(i++, item.getSize());
                }
                else
                {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMd5() != null)
                {
                    ps.setString(i++, item.getMd5());
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
            OverseaCacheFactory.delete(CACHE_KEY_PKG + item.getPkg());
        }
    }

    public void deleteAll()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb.append(" SET `status`=?, `updatedate`=CURRENT_TIMESTAMP where 1=1 ");
        PreparedStatementSetter psc = new PreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps) throws SQLException
            {
                int i = 1;
                ps.setInt(i++, -1);
            }
        };
        int id = getJdbcTemplate().update(sb.toString(), psc);
        if (id > 0)
        {
            OverseaCacheFactory.delete(CACHE_KEY_ALL);
            OverseaCacheFactory.flushAll();
        }
    }

    @SuppressWarnings("unchecked")
    public OverseaApk findSingle(long id)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof OverseaApk)
        {
            return (OverseaApk) obj;
        }
        else
        {
            List<OverseaApk> list = query("select * from " + table()
                    + " where id=? and status = 0 LIMIT 1",
                    new Object[] { id }, new int[] { Types.BIGINT },
                    new OverseaCommonRowMapper(OverseaApk.class));
            if (list != null && list.size() > 0)
            {
                OverseaApk item = list.get(0);
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
    public OverseaApk findByPkg(String pkg)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_PKG + pkg);
        if (obj != null && obj instanceof OverseaApk)
        {
            OverseaApk item = (OverseaApk) obj;
            if (item != null && item.getPkg() != null)
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
            List<OverseaApk> list;
            OverseaApk item, ret;

            sb.append("select * from " + table()
                    + " where pkg = ? and status = 0 order by id desc limit 1");

            list = query(sb.toString(), new Object[] { pkg },
                    new int[] { Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaApk.class));

            if (list != null && list.size() > 0)
            {
                item = list.get(0);
                ret = item;
            }
            else
            {
                item = new OverseaApk();
                item.setPkg(null);
                ret = null;
            }

            OverseaCacheFactory.add(CACHE_KEY_PKG + pkg, item,
                    OverseaCacheFactory.ONE_MONTH);
            return ret;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaApk> findAll()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaApk>) obj;
        }
        else
        {
            List<OverseaApk> list = query("select * from " + table()
                    + " where status = 0", null, null,
                    new OverseaCommonRowMapper(OverseaApk.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL, list,
                        OverseaCacheFactory.ONE_DAY);
            }
            return list;
        }
    }
}
