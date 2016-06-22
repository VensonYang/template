package dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import dao.BaseDao;

@SuppressWarnings("unchecked")
public class BaseDaoImpl implements BaseDao {

	private SessionFactory sessionFactory;

	public Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		if (null == sessionFactory.getCurrentSession()) {
			session = sessionFactory.openSession();
			return session;
		}
		return session;
	}

	@Override
	public <T> Serializable save(T t) {
		return getSession().save(t);
	}

	@Override
	public <T> void delete(T t) {
		getSession().delete(t);
	}

	@Override
	public <T> void delete(Class<T> entityClass, Integer id) {
		getSession().delete(get(entityClass, id));
	}

	@Override
	public <T> void update(T t) {
		getSession().update(t);
	}

	@Override
	public <T> int get(String hql, Class<T> entityClass, Object param) {
		return get(hql, entityClass, new Object[] { param });
	}

	@Override
	public <T> int get(String hql, Class<T> entityClass, Object[] params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}

		List<T> list = query.list();
		if (null != list && list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	@Override
	public <T> int get(String hql, Class<T> entityClass, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		List<T> list = query.list();

		if (null != list && list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	@Override
	public <T> T get(Class<T> entityClass, Integer id) {
		return (T) getSession().get(entityClass, id);
	}

	@Override
	public <T> List<T> findAll(String hql, Class<T> entityClass) {
		return findAll(hql, entityClass, new Object[] {});
	}

	@Override
	public <T> List<T> findAll(String hql, Class<T> entityClass, Object param) {
		return findAll(hql, entityClass, new Object[] { param });
	}

	@Override
	public <T> List<T> findAll(String hql, Class<T> entityClass, Object[] params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return (List<T>) query.list();
	}

	@Override
	public <T> List<T> findAll(String hql, Class<T> entityClass, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return (List<T>) query.list();
	}

	@Override
	public <T> List<T> findByPage(String hql, Class<T> entityClass, int firstResult, int maxResult) {
		return findByPage(hql, entityClass, new Object[] {}, firstResult, maxResult);
	}

	@Override
	public <T> List<T> findByPage(String hql, Class<T> entityClass, Object param, int firstResult, int maxResult) {
		return findByPage(hql, entityClass, new Object[] { param }, firstResult, maxResult);
	}

	@Override
	public <T> List<T> findByPage(String hql, Class<T> entityClass, Object[] params, int firstResult, int maxResult) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return (List<T>) query.list();
	}

	@Override
	public <T> List<T> findByPage(String hql, Class<T> entityClass, Map<String, Object> params, int firstResult,
			int maxResult) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		query.setFirstResult((firstResult - 1) * maxResult);
		query.setMaxResults(maxResult);
		return (List<T>) query.list();
	}

	@Override
	public <T> int delete(String hql, Class<T> entityClass, Object param) {
		return delete(hql, entityClass, new Object[] { param });

	}

	@Override
	public <T> int delete(String hql, Class<T> entityClass, Object[] params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();

	}

	@Override
	public <T> int delete(String hql, Class<T> entityClass, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return query.executeUpdate();

	}

	@Override
	public <T> int update(String hql, Class<T> entityClass, Object param) {
		return update(hql, entityClass, new Object[] { param });
	}

	@Override
	public <T> int update(String hql, Class<T> entityClass, Object[] params) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();

	}

	@Override
	public <T> int update(String hql, Class<T> entityClass, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return query.executeUpdate();

	}

	@Override
	public int updateSql(String sql, Map<String, Object> params) {
		SQLQuery query = getSession().createSQLQuery(sql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return query.executeUpdate();
	}

	@Override
	public List<Map<String, Object>> findAll(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public List<Map<String, Object>> findByPage(String hql, Map<String, Object> params, int firstResult,
			int maxResult) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public List<Map<String, Object>> findAll(String hql) {
		Query query = getSession().createQuery(hql);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public List<Map<String, Object>> findByPage(String hql, int firstResult, int maxResult) {
		Query query = getSession().createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public int get(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		List<Object> list = query.list();
		if (null != list && list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	@Override
	public int update(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return query.executeUpdate();
	}

	@Override
	public <T> List<T> sqlFindByPage(String sql, Class<T> entityClass, int firstResult, int maxResult) {
		return sqlFindByPage(sql, entityClass, new Object[] {}, firstResult, maxResult);
	}

	@Override
	public <T> List<T> sqlFindByPage(String sql, Class<T> entityClass, Object param, int firstResult, int maxResult) {
		return sqlFindByPage(sql, entityClass, new Object[] { param }, firstResult, maxResult);
	}

	@Override
	public <T> List<T> sqlFindByPage(String sql, Class<T> entityClass, Object[] params, int firstResult,
			int maxResult) {
		Query query = getSession().createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<T>) query.list();
	}

	@Override
	public <T> List<T> sqlFindByPage(String sql, Class<T> entityClass, Map<String, Object> params, int firstResult,
			int maxResult) {
		Query query = getSession().createSQLQuery(sql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<T>) query.list();
	}

	@Override
	public List<Map<String, Object>> sqlFindByPage(String sql, Map<String, Object> params, int firstResult,
			int maxResult) {
		Query query = getSession().createSQLQuery(sql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public List<Map<String, Object>> sqlFindByPage(String sql, int firstResult, int maxResult) {
		Query query = getSession().createSQLQuery(sql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public int delete(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return query.executeUpdate();
	}

	@Override
	public <T> void update(Class<T> entityClass, Integer id) {
		getSession().update(get(entityClass, id));
	}

	@Override
	public <T> List<T> sqlFindAll(String hql, Class<T> entityClass) {
		return sqlFindAll(hql, entityClass, new Object[] {});
	}

	@Override
	public <T> List<T> sqlFindAll(String hql, Class<T> entityClass, Object param) {
		return sqlFindAll(hql, entityClass, new Object[] { param });
	}

	@Override
	public <T> List<T> sqlFindAll(String hql, Class<T> entityClass, Object[] params) {
		Query query = getSession().createSQLQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return (List<T>) query.list();
	}

	@Override
	public <T> List<T> sqlFindAll(String hql, Class<T> entityClass, Map<String, Object> params) {
		Query query = getSession().createSQLQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return (List<T>) query.list();
	}

	@Override
	public List<Map<String, Object>> sqlFindAll(String hql, Map<String, Object> params) {
		Query query = getSession().createSQLQuery(hql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public List<Map<String, Object>> sqlFindAll(String hql) {
		Query query = getSession().createSQLQuery(hql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String, Object>>) query.list();
	}

	@Override
	public Serializable save(String sql, Map<String, Object> params) {
		Query query = getSession().createSQLQuery(sql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		return query.executeUpdate();
	}

	@Override
	public int sqlGet(String sql, Map<String, Object> params) {
		Query query = getSession().createSQLQuery(sql);
		Set<Entry<String, Object>> paramsSet = params.entrySet();
		for (Entry<String, Object> item : paramsSet) {
			query.setParameter(item.getKey(), item.getValue());
		}
		List<Object> list = query.list();
		if (null != list && list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	@Override
	public Object get(String hql) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		return query.uniqueResult();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Object get(String hql, Object params) {
		// TODO Auto-generated method stub
		return get(hql, new Object[] { params });
	}

	@Override
	public Object get(String hql, Object[] params) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.uniqueResult();
	}

	@Override
	public int excuteHQL(String hql) {
		Query query = getSession().createQuery(hql);
		return query.executeUpdate();

	}

	@Override
	public int excuteSQL(String sql) {
		SQLQuery query = getSession().createSQLQuery(sql);
		return query.executeUpdate();

	}

}
