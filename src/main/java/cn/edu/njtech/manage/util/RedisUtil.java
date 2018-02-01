package cn.edu.njtech.manage.util;

import cn.edu.njtech.manage.constant.NumberConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author jackie chen
 * @create 2017/10/27
 * @description RedisUtil redis 工具类
 */
@Component
public class RedisUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 分布式锁过期时间(秒)
	 */
	private static final int DISTRIBUTEDLOCK_TIMEOUT = 3;

	/**
	 * 获取 RedisSerializer
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 将数据保存至缓存区，且设定生存周期(byte)
	 *
	 * @param key      key
	 * @param value    value
	 * @param liveTime liveTime 秒
	 * @return boolean
	 */
	public boolean set(final String key, final String value, final long liveTime) {
		BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
		boundValueOperations.set(value);
		if (liveTime > 0) {
			boundValueOperations.expire(liveTime, TimeUnit.SECONDS);
		} else {
			boundValueOperations.expire(-1, TimeUnit.SECONDS);

		}
		return true;
	}

	/**
	 * 将数据保存至缓存区，且设定生存周期(byte)
	 *
	 * @param key   key
	 * @param value value
	 * @return boolean
	 */
	public boolean set(final String key, final String value) {
		BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
		boundValueOperations.set(value);
		return true;
	}

	/**
	 * Redis setnx
	 *
	 * @param key   key
	 * @param value value
	 * @return boolean
	 */
	private boolean setnx(final String key, final String value) {
		BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
		return boundValueOperations.setIfAbsent(value);
	}

	/**
	 * @param key   key
	 * @param value value
	 * @return Long
	 */
	public Long incrby(final String key, final Long value) {
		BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
		return boundValueOperations.increment(value);
	}

	/**
	 * 返回 key 所关联的字符串值。
	 *
	 * @param key key
	 * @return String
	 */
	public String get(final String key) {
		BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
		return boundValueOperations.get();
	}

	/**
	 * 删除缓存区中的数据
	 *
	 * @param keys keys
	 * @return boolean
	 */
	public boolean del(final String... keys) {
		for (int i = 0; i < keys.length; i++) {
			redisTemplate.delete(keys[i]);
		}
		return true;
	}

	/**
	 * 保存Hash数据到缓存区
	 *
	 * @param key   key
	 * @param field field
	 * @param value value
	 */
	public void hSet(String key, String field, String value) {
		BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
		boundHashOperations.put(field, value);
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 *
	 * @param key   key
	 * @param value value
	 * @return Long
	 */
	public Long lPush(String key, String value) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.leftPush(value);
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 *
	 * @param key   key
	 * @param value value
	 * @return Long
	 */
	public Long lPushAll(String key, String... value) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.leftPushAll(value);
	}

	/**
	 * 移除并返回列表 key 的头元素。
	 *
	 * @param key key
	 * @return String
	 */
	public String lPop(String key) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.leftPop();
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头
	 *
	 * @param key   key
	 * @param value value
	 * @return Long
	 */
	public Long rPushAll(String key, String... value) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.rightPushAll(value);
	}

	/**
	 * 将一个value 插入到列表 key 的表头
	 *
	 * @param key   key
	 * @param value value
	 * @return Long
	 */
	public Long rPush(String key, String value) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.rightPush(value);
	}


	/**
	 * 移除并返回列表 key 的头元素。
	 *
	 * @param key key
	 * @return String
	 */
	public String rPop(String key) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.rightPop();
	}

	/**
	 * 获得下标为index的数据
	 *
	 * @param index index
	 * @return String
	 */
	public String lIndex(String key, long index) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.index(index);
	}

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
	 *
	 * @param start start
	 * @param end   end
	 * @return List
	 */
	public List<String> lRang(String key, long start, long end) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.range(start, end);
	}

	/**
	 * 获得List元素的长度
	 *
	 * @param key key
	 * @return long
	 */
	public Long lLen(final String key) {
		BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(key);
		return boundListOps.size();
	}

	/**
	 * 获取所有hash的filed
	 *
	 * @param key
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Set<Object> hKeys(String key) {
		BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations.keys();
	}

	/**
	 * 批量保存Hash数据到缓存区
	 *
	 * @param key    key
	 * @param values values
	 */
	public void hMSet(String key, Map<String, String> values) {
		BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
		boundHashOperations.putAll(values);
	}

	/**
	 * 获取hashKey对应的所有键值
	 *
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<Object, Object> hMGet(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 获得指定key的Hash数据
	 *
	 * @param key   key
	 * @param filed filed
	 * @return String
	 */
	public String hGet(String key, String filed) {
		BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
		return (String) boundHashOperations.get(filed);
	}

	/**
	 * 获得所有Hash数据
	 *
	 * @param key key
	 * @return Map
	 */
	public Map<Object, Object> hGetAll(String key) {
		BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations.entries();
	}

	/**
	 * 删除key下所有fields
	 *
	 * @param key    key
	 * @param fields fields
	 * @return Long
	 */
	public Long hDel(String key, Object... fields) {
		BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations.delete(fields);
	}

	/**
	 * 保存数据到Set数据集缓存区
	 *
	 * @param key    key
	 * @param values values
	 * @return long
	 */
	public Long sAdd(final String key, final String... values) {

		BoundSetOperations<String, String> boundSetOps = redisTemplate.boundSetOps(key);
		return boundSetOps.add(values);
	}

	/**
	 * 保存数据到Set数据集缓存区并设置时间段
	 *
	 * @param key    key
	 * @param values values
	 * @return Long
	 */
	public Long sAdd(final String key, final String values, long liveTime) {

		BoundSetOperations<String, String> boundSetOps = redisTemplate.boundSetOps(key);
		if (liveTime > 0) {
			boundSetOps.expire(liveTime, TimeUnit.SECONDS);
		} else {
			boundSetOps.expire(-1, TimeUnit.SECONDS);
		}
		return boundSetOps.add(values);
	}

	/**
	 * 保存数据到Set数据集缓存区并设置时间段
	 *
	 * @param key    key
	 * @param values values
	 * @return boolean
	 */
	public boolean sAddAndTime(final String key, final String values, long liveTime) {

		BoundSetOperations<String, String> boundSetOps = redisTemplate.boundSetOps(key);

		if (boundSetOps.add(values) > 0) {
			if (liveTime > 0) {

				return boundSetOps.expire(liveTime, TimeUnit.SECONDS);
			}
			return boundSetOps.expire(-1, TimeUnit.SECONDS);
		}

		return false;
	}

	/**
	 * 获得Set集合的数据集缓存区
	 *
	 * @param key key
	 * @return Set
	 */
	public Set<String> sMembers(String key) {
		BoundSetOperations<String, String> boundSetOps = redisTemplate.boundSetOps(key);
		return boundSetOps.members();
	}

	/**
	 * 将一个元素及其 score 值加入到有序集 key 当中。
	 *
	 * @param key key
	 * @return boolean
	 */
	public Boolean zAdd(String key, String value, double score) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.add(value, score);
	}

	/**
	 * 查找区间内的有序集合,按照value升序排序
	 *
	 * @param key key
	 * @return Set
	 */
	public Set<String> zRange(String key, long begin, long end) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.range(begin, end);
	}

	/**
	 * 查找区间内的有序集合（含score值）,按照value升序排序
	 *
	 * @param key   redis key
	 * @param start 开始位置 0为第一个
	 * @param end   结束位置 可为负数 -1为倒数第一个
	 * @return Set<Tuple>
	 */
	public Set<RedisZSetCommands.Tuple> zRangeWithScores(String key, int start, int end) {
		return redisTemplate.execute(new RedisCallback<Set<RedisZSetCommands.Tuple>>() {
			@Override
			public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keys = serializer.serialize(key);
				return connection.zRangeWithScores(keys, start, end);
			}
		});
	}

	/**
	 * 查找区间内的有序集合,按照value降序排序
	 *
	 * @param key key
	 * @return Set
	 */
	public Set<String> zRevRange(String key, long begin, long end) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.reverseRange(begin, end);
	}

	/**
	 * 查找区间内的有序集合（含score值）,按照value降序排序
	 *
	 * @param key   redis key
	 * @param start 开始位置 0为第一个
	 * @param end   结束位置 可为负数 -1为倒数第一个
	 * @return Set<Tuple>
	 */
	public Set<RedisZSetCommands.Tuple> zRevRangeWithScores(String key, int start, int end) {
		return redisTemplate.execute(new RedisCallback<Set<RedisZSetCommands.Tuple>>() {
			@Override
			public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] keys = serializer.serialize(key);
				return connection.zRevRangeWithScores(keys, start, end);
			}
		});
	}

	/**
	 * 返回计算集合中元素的数量。
	 *
	 * @param key key
	 * @return Long
	 */
	public Long zCard(String key) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.zCard();
	}

	/**
	 * 删除集合中某个成员
	 *
	 * @param key key
	 * @return Long
	 */
	public Long zDelete(String key, String value) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.remove(value);
	}

	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
	 *
	 * @param key key
	 * @return Long
	 */
	public Long zRank(String key, String value) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.rank(value);
	}

	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
	 *
	 * @param key key
	 * @return Long
	 */
	public Long zReverseRank(String key, String value) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.reverseRank(value);
	}


	/**
	 * 设置指定键的TTL
	 *
	 * @param key     key
	 * @param seconds seconds
	 * @return boolean
	 */
	public boolean expire(final String key, final long seconds) {
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 判断数据是否在缓存区中存在
	 *
	 * @param key key
	 * @return boolean
	 */
	public boolean exists(final String key) {

		return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
			// 判断是否键值对是否已经存在
			return connection.exists(redisTemplate.getStringSerializer().serialize(key));
		});
	}

	/**
	 * 尝试加锁
	 *
	 * @param key key
	 * @return boolean
	 */
	public boolean tryLock(String key) {
		long nano = System.nanoTime();
		long timeoutSecond = DISTRIBUTEDLOCK_TIMEOUT;
		try {
			do {
				boolean isnx = setnx(key, key);
				// 1.获得锁
				if (isnx) {
					expire(key, DISTRIBUTEDLOCK_TIMEOUT);

					return Boolean.TRUE;
					// 2.锁被占用
				} else {
					if (redisTemplate.getExpire(key) == -1) {
						del(key);
					}
				}
				//睡眠100毫秒
				Thread.sleep(NumberConstant.INT_ONE_HUNDRED);
			}
			while ((System.nanoTime() - nano) < timeoutSecond * NumberConstant.INT_THREE * NumberConstant.INT_ONE_BILLION);
		} catch (InterruptedException e) {
			//修改so
			Thread.currentThread().interrupt();
			return Boolean.FALSE;
		}

		logger.info("超时>>>>：",
				(System.nanoTime() - nano) + " ，刚进入的时间：" + nano + ";返回false的时间：" + System.nanoTime());
		return Boolean.FALSE;
	}

	/**
	 * 解锁
	 *
	 * @param key key
	 */
	public void unlock(String key) {
		del(key);
	}

	/**
	 * 多线程同步加次数
	 *
	 * @param key   key
	 * @param value value
	 * @return Long
	 */
	public Long incrsy(final String key, final Long value) {
		BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(key);
		return boundValueOperations.increment(value);
	}

	/**
	 * 获取redis中自增长的值
	 *
	 * @param key
	 * @param liveTime
	 * @return
	 */
	public Long incr(String key, long liveTime) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		Long increment = entityIdCounter.incrementAndGet();
		if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
			entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
		}
		return increment;
	}

	public void zincrby(String key, float value, String member) {
		String values = this.hGet(key, member);
		this.hSet(key, member, String.valueOf(value + Float.parseFloat(values)));
	}

	/**
	 * 获取有序set，member对应的score
	 *
	 * @param key    key
	 * @param member member
	 * @return
	 */
	public Double zscore(String key, String member) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.score(member);
	}

	public Double zincrby(String key, String value, double delta) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		return boundZSetOps.incrementScore(value, delta);
	}

	/**
	 * 清除有序集，长度介于 {@code start} 和 {@code end} 之间
	 *
	 * @param start 开始位置
	 * @param end   结束位置
	 */
	public void removeRange(String key, long start, long end) {
		BoundZSetOperations<String, String> boundZSetOps = redisTemplate.boundZSetOps(key);
		boundZSetOps.removeRange(start, end);
	}
}
