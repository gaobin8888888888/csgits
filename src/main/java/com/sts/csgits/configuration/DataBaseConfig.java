package com.sts.csgits.configuration;

import com.alibaba.druid.pool.DruidDataSource;

public class DataBaseConfig {

	private String driverClass;
	private String dbIp;
	private String dbPort;
	private String dbName;
	private String dbUser;
	private String dbPwd;
	private int initialSize;
	private int maxActive;
	private int maxIdle;
	private int minIdle;
	private long maxWait;
	
	public DataBaseConfig() {
		this.initialSize=1;
		this.maxActive=2;
		this.maxIdle=2;
		this.minIdle=1;
		this.maxWait=10000;
		this.driverClass="org.postgresql.Driver";
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	
	public DruidDataSource getDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		String url = "jdbc:postgresql://"+this.dbIp+":"+this.dbPort+"/"+this.dbName;
		dataSource.setUrl(url);
		dataSource.setUsername(this.dbUser);
		dataSource.setPassword(this.dbPwd);
		dataSource.setDriverClassName(this.driverClass);
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
	     return dataSource;
	}
	
}
