package com.sts.csgits.configuration;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jedis.JedisLockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.ScheduledLockConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * 定时任务调度器配置
 * @author gb
 */
@Configuration
public class SchedulerConfiguration {
	
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Bean
	public ScheduledLockConfiguration taskScheduler(LockProvider lockProvider) {
		return ScheduledLockConfigurationBuilder
				.withLockProvider(lockProvider)
				.withPoolSize(10)
				.withDefaultLockAtMostFor(Duration.ofMinutes(10)).build();
	}

	@Bean
	public LockProvider lockProvider(JedisPool jedisPool) {
		return new JedisLockProvider(jedisPool, "trm");
	}
	
	/**
	 * 单独注册一个JedisPool提供给Shedlock的LockProvider
	 */
	@Bean
	public JedisPool shedlockJedisPool() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(1);
		poolConfig.setMinIdle(1);
		
		return new JedisPool(poolConfig, host, port, timeout, "".equals(password) ? null : password, database);
	}
}
