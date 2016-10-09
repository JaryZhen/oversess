package com.oversea.factory;

import redis.clients.jedis.JedisPoolConfig;

public class RedisConfig {
	private JedisPoolConfig jedisPoolConfig;
	private String host;
	private int port;
	
	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}
	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
