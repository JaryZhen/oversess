package com.oversea.ads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.mysql.jdbc.Statement;
import com.oversea.ads.bean.OverseaClassApk;
import com.oversea.factory.OverseaCacheFactory;

public class OverseaClassApkDao extends OverseaBaseDao
{
    private static final String TBLPREFIX     = "o_classapk";
    private static final String CACHE_KEY_ALL = OverseaClassApkDao.class
                                                      .getName()
                                                      + "_all";
    private static final String CACHE_KEY_ID  = OverseaClassApkDao.class
                                                      .getName()
                                                      + "_id";
    private static final String CACHE_KEY_PKG = OverseaClassApkDao.class
                                                      .getName()
                                                      + "_pkg";

    public static String table()
    {
        return TBLPREFIX;
    }

    /**
     * 插入
     */

    public int insert(final OverseaClassApk item)
    {
        if (item == null)
        {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb.append(" (`name`,`pkg`, `icon`, `size`,`cat`,"
                + "`apkclass`) VALUES (?, ?, ?, ?, ?, ?)");
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

                if (item.getIcon() != null)
                {
                    ps.setString(i++, item.getIcon());
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

                if (item.getCat() != null)
                {
                    ps.setString(i++, item.getCat());
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
    public void update(final OverseaClassApk item)
    {
        if (item == null)
        {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `name`=?,`pkg`=?, `icon`=?, `size`=?, `cat`=?,`apkclass`=? "
                        + " WHERE `id`=?");
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

                if (item.getIcon() != null)
                {
                    ps.setString(i++, item.getIcon());
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
                if (item.getCat() != null)
                {
                    ps.setString(i++, item.getCat());
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

    @SuppressWarnings("unchecked")
    public OverseaClassApk findSingle(long id)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof OverseaClassApk)
        {
            return (OverseaClassApk) obj;
        }
        else
        {
            List<OverseaClassApk> list = query("select * from " + table()
                    + " where id=? LIMIT 1", new Object[] { id },
                    new int[] { Types.BIGINT }, new OverseaCommonRowMapper(
                            OverseaClassApk.class));
            if (list != null && list.size() > 0)
            {
                OverseaClassApk item = list.get(0);
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
    public OverseaClassApk findByPkg(String pkg)
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_PKG + pkg);
        if (obj != null && obj instanceof OverseaClassApk)
        {
            OverseaClassApk item = (OverseaClassApk) obj;
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
            List<OverseaClassApk> list;
            OverseaClassApk item, ret;

            sb.append("select * from " + table()
                    + " where pkg = ? order by id desc limit 1");

            list = query(sb.toString(), new Object[] { pkg },
                    new int[] { Types.VARCHAR }, new OverseaCommonRowMapper(
                            OverseaClassApk.class));

            if (list != null && list.size() > 0)
            {
                item = list.get(0);
                ret = item;
            }
            else
            {
                item = new OverseaClassApk();
                item.setPkg(null);
                ret = null;
            }

            OverseaCacheFactory.add(CACHE_KEY_PKG + pkg, item,
                    OverseaCacheFactory.ONE_MONTH);
            return ret;
        }
    }

    @SuppressWarnings("unchecked")
    public List<OverseaClassApk> findAll()
    {
        Object obj = OverseaCacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List)
        {
            return (List<OverseaClassApk>) obj;
        }
        else
        {
            List<OverseaClassApk> list = query("select * from " + table()
                    + " where status = 0", null, null,
                    new OverseaCommonRowMapper(OverseaClassApk.class));
            if (list != null && list.size() > 0)
            {
                OverseaCacheFactory.add(CACHE_KEY_ALL, list,
                        OverseaCacheFactory.ONE_MONTH);
            }
            return list;
        }
    }
}
