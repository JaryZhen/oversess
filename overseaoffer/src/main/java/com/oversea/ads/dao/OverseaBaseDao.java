package com.oversea.ads.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;

public class OverseaBaseDao extends JdbcDaoSupport
{
    public OverseaBaseDao()
    {
    }

    @SuppressWarnings("unchecked")
    public List query(String sql, Object[] args, int[] argTypes,
            RowMapper mapper)
    {
        return getJdbcTemplate().query(sql, args, argTypes, mapper);
    }

    @SuppressWarnings("unchecked")
    public List query(String sql, RowMapper mapper)
    {
        return getJdbcTemplate().query(sql, mapper);
    }

    public List<Map<String, Object>> query(String sql, Object[] args,
            int[] argTypes)
    {
        return getJdbcTemplate().queryForList(sql, args, argTypes);
    }

    public int queryForInt(String sql, Object[] args, int[] argTypes)
    {
        return getJdbcTemplate().queryForInt(sql, args, argTypes);
    }

    public long queryForLong(String sql, Object[] args, int[] argTypes)
    {
        return getJdbcTemplate().queryForLong(sql, args, argTypes);
    }
}
