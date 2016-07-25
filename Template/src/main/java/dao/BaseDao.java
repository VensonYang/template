package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao {

	<T> Serializable save(T t);

	Serializable save(String sql, Map<String, Object> params);

	<T> void delete(T t);

	<T> void delete(Class<T> entityClass, Integer id);

	<T> int delete(String hql, Class<T> entityClass, Object param);

	<T> int delete(String hql, Class<T> entityClass, Object[] params);

	<T> int delete(String hql, Class<T> entityClass, Map<String, Object> params);

	int delete(String hql, Map<String, Object> params);

	<T> void update(T t);

	<T> void update(Class<T> entityClass, Integer id);

	<T> int update(String hql, Class<T> entityClass, Object param);

	<T> int update(String hql, Class<T> entityClass, Object[] params);

	<T> int update(String hql, Class<T> entityClass, Map<String, Object> params);

	int update(String hql, Map<String, Object> params);

	int updateSql(String sql, Map<String, Object> params);

	<T> int get(String hql, Class<T> entityClass, Object param);

	<T> int get(String hql, Class<T> entityClass, Object[] params);

	<T> int get(String hql, Class<T> entityClass, Map<String, Object> params);

	int get(String hql, Map<String, Object> params);

	Object get(String hql);

	Object get(String hql, Object params);

	Object get(String hql, Object[] params);

	int sqlGet(String sql, Map<String, Object> params);

	Object sqlGet(String sql, Object[] params);

	Object sqlGet(String sql, Object params);

	Object sqlGet(String sql);

	<T> T get(Class<T> entityClass, Integer id);

	<T> List<T> findAll(String hql, Class<T> entityClass);

	<T> List<T> findAll(String hql, Class<T> entityClass, Object param);

	<T> List<T> findAll(String hql, Class<T> entityClass, Object[] params);

	<T> List<T> findAll(String hql, Class<T> entityClass, Map<String, Object> params);

	List<Map<String, Object>> findAll(String hql, Map<String, Object> params);

	List<Map<String, Object>> findAll(String hql);

	<T> List<T> sqlFindAll(String hql, Class<T> entityClass);

	<T> List<T> sqlFindAll(String hql, Class<T> entityClass, Object param);

	<T> List<T> sqlFindAll(String hql, Class<T> entityClass, Object[] params);

	<T> List<T> sqlFindAll(String hql, Class<T> entityClass, Map<String, Object> params);

	List<Map<String, Object>> sqlFindAll(String hql, Map<String, Object> params);

	List<Map<String, Object>> sqlFindAll(String hql);

	<T> List<T> findByPage(final String hql, final Class<T> entityClass, final int firstResult, final int maxResult);

	<T> List<T> findByPage(final String hql, final Class<T> entityClass, final Object param, final int firstResult,
			final int maxResult);

	<T> List<T> findByPage(final String hql, final Class<T> entityClass, final Object[] params, final int firstResult,
			final int maxResult);

	<T> List<T> findByPage(final String hql, final Class<T> entityClass, Map<String, Object> params,
			final int firstResult, final int maxResult);

	List<Map<String, Object>> findByPage(final String hql, Map<String, Object> params, final int firstResult,
			final int maxResult);

	List<Map<String, Object>> findByPage(final String hql, final int firstResult, final int maxResult);

	<T> List<T> sqlFindByPage(final String sql, final Class<T> entityClass, final int firstResult, final int maxResult);

	<T> List<T> sqlFindByPage(final String sql, final Class<T> entityClass, final Object param, final int firstResult,
			final int maxResult);

	<T> List<T> sqlFindByPage(final String sql, final Class<T> entityClass, final Object[] params,
			final int firstResult, final int maxResult);

	<T> List<T> sqlFindByPage(final String sql, final Class<T> entityClass, Map<String, Object> params,
			final int firstResult, final int maxResult);

	List<Map<String, Object>> sqlFindByPage(final String sql, Map<String, Object> params, final int firstResult,
			final int maxResult);

	List<Map<String, Object>> sqlFindByPage(final String sql, final int firstResult, final int maxResult);

	int excuteHQL(String hql);

	int excuteSQL(String sql);
}
