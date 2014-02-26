package com.jeffy.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jeffy.bo.PageResult;
import com.jeffy.dao.CommonDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.persistence.Entity;

public class CommonDaoImpl<T> extends HibernateDaoSupport implements CommonDao<T> {

	public void saveOrUpdate(Class<T> clazz, Object entity) {
		getHibernateTemplate().saveOrUpdate(clazz.getName(), entity);
	}

    public Object save(Object entity) {
        getHibernateTemplate().save(entity);
        return entity;
    }

    public Object update(Object entity) {
        getHibernateTemplate().update(entity);
        return  entity;
    }

	public void delete(Class<T> clazz, Object entity) {
		getHibernateTemplate().delete(clazz.getName(), entity);
	}

	public void deleteById(Class<T> clazz, int id) {
		getHibernateTemplate().delete(clazz.getName(), getById(clazz, id));
	}

	public T getById(Class<T> clazz, int id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public List<T> getListAll(final Class<T> clazz,
			final Map<String, Object> equalCondition,
			final Map<String, String> likeCondition) {
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}

						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								crit.add(Restrictions.like(key, value,
										MatchMode.ANYWHERE));
							}
						}

						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.addOrder(Order.desc("id"));
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

	/**
	 * only to retrive the special condtion that the same key and must be used
	 * as 'or' retrive; e.g. select * from table where id = 1 or id =2
	 * 
	 * @param clazz
	 * @param equalCondition
	 *            List type,e.g.(<<id,1>,<id,2>>)
	 * @param likeCondition
	 * @return List
	 */

	public List<T> getListAll(final Class<T> clazz,
			final List<Map<String, Object>> equalCondition,
			final Map<String, String> likeCondition) {
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							if (1 == equalCondition.size())
								crit.add(Restrictions.allEq(equalCondition
										.get(0)));
							if (2 == equalCondition.size())
								crit.add(Restrictions.or(Restrictions
										.allEq(equalCondition.get(0)),
										Restrictions.allEq(equalCondition
												.get(1))));
						}

						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								crit.add(Restrictions.like(key, value,
										MatchMode.ANYWHERE));
							}
						}

						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.addOrder(Order.desc("id"));
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

	public List<T> getListForPage(final Class<T> clazz, final int offset,
			final int length, final Map<String, String> equalCondition,
			final Map<String, String> likeCondition) {
		return getListForPage(clazz, offset, length, equalCondition,
				likeCondition, null);
	}

	public List<T> getListForPage(final Class<T> clazz, final int offset,
			final int length, final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,
			final Map<String, String> gtCondition) {
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							String startDate = "";
							String endDate = "";
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								if (key.equals("startDate")) {
									startDate = value;
								}
								if (key.equals("endDate")) {
									endDate = value;
								}
								if (!endDate.equals("")
										&& !startDate.equals("")) {
									crit.add(Restrictions.between(
											"createdatetime", startDate,
											endDate));
								}								
								if (!key.equals("startDate")
										&& !key.equals("endDate"))
									crit.add(Restrictions.like(key, value,
											MatchMode.ANYWHERE));
											
							}
						}
						if (gtCondition != null) {
							Set<String> keySet = gtCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								Object value = gtCondition.get(key);
								crit.add(Restrictions.gt(key, value));
							}
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.setFirstResult(offset);
						crit.addOrder(Order.desc("id"));
						crit.setMaxResults(length);
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}
//	public List<T> getListForPage(final Class<T> clazz, final int offset,
//			final int length, final Map<String, String> equalCondition,
//			final Map<String, String> likeCondition,
//			final Map<String, String> gtCondition,
//			final Map<String, String> ltCondition) {
//		// @SuppressWarnings("unchecked")
//		List<T> list = getHibernateTemplate().execute(
//				new HibernateCallback<List<T>>() {
//
//					public List<T> doInHibernate(Session session)
//							throws HibernateException, SQLException {
//						Criteria crit = session.createCriteria(clazz);
//						if (equalCondition != null) {
//							crit.add(Restrictions.allEq(equalCondition));
//						}
//						if (likeCondition != null) {
//							Set<String> keySet = likeCondition.keySet();
//							Iterator<String> it = keySet.iterator();
//							while (it.hasNext()) {
//								String key = it.next();
//								String value = likeCondition.get(key);
//								crit.add(Restrictions.like(key, value,
//										MatchMode.ANYWHERE));
//							}
//						}
//						if (gtCondition != null) {
//							Set<String> keySet = gtCondition.keySet();
//							Iterator<String> it = keySet.iterator();
//							while (it.hasNext()) {
//								String key = it.next();
//								Object value = gtCondition.get(key);
//								crit.add(Restrictions.gt(key, value));
//							}
//						}
//						if (ltCondition != null) {
//							Set<String> keySet = ltCondition.keySet();
//							Iterator<String> it = keySet.iterator();
//							while (it.hasNext()) {
//								String key = it.next();
//								Object value = ltCondition.get(key);
//								crit.add(Restrictions.lt(key, value));
//							}
//						}
//						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//						crit.setFirstResult(offset);
//						crit.addOrder(Order.desc("id"));
//						crit.setMaxResults(length);
//						List<T> page = crit.list();
//						return page;
//					}
//
//				});
//		return list;
//	}
	public PageResult getListForPage(final Class<T> clazz, final int offset,
			final int length, final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,
			final Map<String, String> gtCondition,
			final Map<String, String> ltCondition) {
		// @SuppressWarnings("unchecked")
		PageResult pageResult = getHibernateTemplate().execute(
				new HibernateCallback<PageResult>() {

					public PageResult doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								crit.add(Restrictions.like(key, value,
										MatchMode.ANYWHERE));
							}
						}
						if (gtCondition != null) {
							Set<String> keySet = gtCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								Object value = gtCondition.get(key);
								crit.add(Restrictions.gt(key, value));
							}
						}
						if (ltCondition != null) {
							Set<String> keySet = ltCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								Object value = ltCondition.get(key);
								crit.add(Restrictions.lt(key, value));
							}
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						int countNumber = crit.list().size();
						//System.out.println("countNumber:"+countNumber);
						PageResult pageResult = new PageResult(countNumber,length);
						crit.setFirstResult(offset);
						//Order.desc("id")按照id降序排列
						//crit.addOrder(Order.desc("id"));
						crit.addOrder(Order.desc("dateTime"));
						crit.setMaxResults(length);
						List<T> pageList = crit.list();
						pageResult.setEventList(pageList);
						return pageResult;
					}

				});
		return pageResult;
	}

	/**
	 * only to retrive the special condtion that the same key and must be used
	 * as 'or' retrive; e.g. select * from table where id = 1 or id =2
	 * 
	 * @param clazz
	 * @param offset
	 * @param length
	 * @param equalCondition
	 *            List type,e.g.(<<id,1>,<id,2>>)
	 * @param likeCondition
	 * can use in date(between) ,e.g.(<startDate,2011-11-11 00:00:00> <endDate,2012-11-11 00:00:00>)
	 * @return List
	 */

	public List<T> getListForPage(final Class<T> clazz, final int offset,
			final int length, final List<Map<String, Object>> equalCondition,
			final Map<String, String> likeCondition) {
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							if (1 == equalCondition.size())
								crit.add(Restrictions.allEq(equalCondition
										.get(0)));
							if (2 == equalCondition.size())
								crit.add(Restrictions.or(Restrictions
										.allEq(equalCondition.get(0)),
										Restrictions.allEq(equalCondition
												.get(1))));
						}
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							String startDate = "";
							String endDate = "";
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								if (key.equals("startDate")) {
									startDate = value;
								}
								if (key.equals("endDate")) {
									endDate = value;
								}
								if (!endDate.equals("")
										&& !startDate.equals("")) {
									crit.add(Restrictions.between(
											"createdatetime", startDate,
											endDate));
								}								
								if (!key.equals("startDate")
										&& !key.equals("endDate"))
									crit.add(Restrictions.like(key, value,
											MatchMode.ANYWHERE));
											
							}
							
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.setFirstResult(offset);
						crit.addOrder(Order.desc("id"));
						crit.setMaxResults(length);
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

	public long getListCount(final Class<T> clazz,
			final Map<String, String> equalCondition,
			final Map<String, String> likeCondition) {
		Long count = getHibernateTemplate().execute(
				new HibernateCallback<Long>() {

					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								if (value != null) {
									crit.add(Restrictions.like(key, value,
											MatchMode.ANYWHERE));
								}
							}
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						return new Long(crit.list().size());
					}

				});
		return count == null ? 0 : count.longValue();
	}

	public long getListCount(final Class<T> clazz,
			final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,
			final Map<String, String> neCondition) {
		Long count = getHibernateTemplate().execute(
				new HibernateCallback<Long>() {

					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								if (value != null) {
									crit.add(Restrictions.like(key, value,
											MatchMode.ANYWHERE));
								}
							}
						}
						try {
							if (neCondition != null) {
								Set<String> keySet = neCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									Object value = neCondition.get(key);
									if (value != null) {
										if (clazz.getDeclaredField(key)
												.getType().getName()
												.equals("int")) {
											crit.add(Restrictions.ne(key,
													Integer.parseInt(value
															.toString())));
										} else if (clazz.getDeclaredField(key)
												.getType().getName()
												.equals("double")) {
											crit.add(Restrictions.ne(key,
													Double.parseDouble(value
															.toString())));
										} else if (clazz.getDeclaredField(key)
												.getType().getName()
												.equals("float")) {
											crit.add(Restrictions.ne(key, Float
													.parseFloat(value
															.toString())));
										} else {
											crit.add(Restrictions
													.ne(key, value));
										}
									}
								}
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						return new Long(crit.list().size());
					}

				});
		return count == null ? 0 : count.longValue();
	}

	public List<T> getListForPage(final Class<T> clazz, final int offset,
			final int length, final Map<String, Object> equalCondition,
			final Map<String, String> likeCondition, final String order) {
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						Set<String> keySet = likeCondition.keySet();
						Iterator<String> it = keySet.iterator();
						while (it.hasNext()) {
							String key = it.next();
							String value = likeCondition.get(key);
							crit.add(Restrictions.like(key, value,
									MatchMode.ANYWHERE));
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.setFirstResult(offset);
						crit.addOrder(Order.desc(order));
						crit.setMaxResults(length);
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

	public List<T> getListForPage(final Class<T> clazz, final int offset,
			final int length, final Map<String, Object> equalCondition,
			final Map<String, String> likeCondition,
			final Map<String, Object> neCondition, final String order) {
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);

						try {
							if (equalCondition != null) {
								crit.add(Restrictions.allEq(equalCondition));
							}

							if (likeCondition != null) {
								Set<String> keySet = likeCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									String value = likeCondition.get(key);
									crit.add(Restrictions.like(key, value,
											MatchMode.ANYWHERE));
								}
							}

							if (neCondition != null) {
								Set<String> keySet = neCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									Object value = neCondition.get(key);
									if (value != null) {
										if (clazz.getDeclaredField(key)
												.getType().getName()
												.equals("int")) {
											crit.add(Restrictions.ne(key,
													Integer.parseInt(value
															.toString())));
										} else if (clazz.getDeclaredField(key)
												.getType().getName()
												.equals("double")) {
											crit.add(Restrictions.ne(key,
													Double.parseDouble(value
															.toString())));
										} else if (clazz.getDeclaredField(key)
												.getType().getName()
												.equals("float")) {
											crit.add(Restrictions.ne(key, Float
													.parseFloat(value
															.toString())));
										} else {
											crit.add(Restrictions
													.ne(key, value));
										}
									}
								}
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						}

						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.setFirstResult(offset);
						crit.addOrder(Order.desc(order));
						crit.setMaxResults(length);
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

	public long isExist(final Class<T> clazz,
			final Map<String, String> equalCondition, final String id) {
		Long count = getHibernateTemplate().execute(
				new HibernateCallback<Long>() {

					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(clazz);
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}

						crit.add(Restrictions.ne("id", new Integer(id)));
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						return new Long(crit.list().size());
					}

				});
		return count == null ? 0 : count.longValue();
	}

	public List<T> getListForPage(final Class<T> clazz, final int offset, final int length,
			final List<Map<String, Object>> equalCondition,
			final Map<String, String> likeCondition, final  String order) {
			// @SuppressWarnings("unchecked")
			List<T> list = getHibernateTemplate().execute(
					new HibernateCallback<List<T>>() {

						public List<T> doInHibernate(Session session)
								throws HibernateException, SQLException {
							Criteria crit = session.createCriteria(clazz);
							if (equalCondition != null) {
								if (1 == equalCondition.size())
									crit.add(Restrictions.allEq(equalCondition
											.get(0)));
								if (2 == equalCondition.size())
									crit.add(Restrictions.or(Restrictions
											.allEq(equalCondition.get(0)),
											Restrictions.allEq(equalCondition
													.get(1))));
							}
							if (likeCondition != null) {
								Set<String> keySet = likeCondition.keySet();
								Iterator<String> it = keySet.iterator();
								String startDate = "";
								String endDate = "";
								while (it.hasNext()) {
									String key = it.next();
									String value = likeCondition.get(key);
									if (key.equals("startDate")) {
										startDate = value;
									}
									if (key.equals("endDate")) {
										endDate = value;
									}
									if (!endDate.equals("")
											&& !startDate.equals("")) {
										crit.add(Restrictions.between(
												"dealDate", startDate,
												endDate));
									}
									if (!key.equals("startDate")
											&& !key.equals("endDate"))
										crit.add(Restrictions.like(key, value,
												MatchMode.ANYWHERE));
								}
							}
							crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
							crit.setFirstResult(offset);
							crit.addOrder(Order.desc(order));
							crit.setMaxResults(length);
							List<T> page = crit.list();
							return page;
						}

					});
			return list;
		}
}
