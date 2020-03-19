package com.sts.csgits.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * PG Jsonb 类型定义
 * @author wangzb
 */
public class PgJsonb extends HashMap<String, String> {
	
	private static final long serialVersionUID = 3120930148682418867L;
	
	public PgJsonb() {}
	
	public PgJsonb(Map<String, String> map) {
		super.putAll(map);
	}
}
