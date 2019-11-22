package com.kongque.component;

public interface IRedisClient {
	public String set(String key, String value);

	public String get(String key);

	public Long hset(String key, String item, String value);

	public String hget(String key, String item);

	public Long incr(String key);

	public Long decr(String key);

	public Long expire(String key, int second);

	public Long ttl(String key);

	public Long hdel(String key, String item);
	
	/**
	 * 删除redis中保存的信息
	 * @param key redis中要删除的信息对应的token（键名）
	 * @return Long 执行结果状态码——1：表示执行成功
	 */
	public Long remove(String key);
}
