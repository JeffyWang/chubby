package com.jeffy.dao;

import com.jeffy.bo.PageResult;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface CommonDao<T> {

	void saveOrUpdate(Class<T> clazz, Object entity);

    Object save(Object entity);

    Object update(Object entity);

	void delete(Class<T> clazz, Object entity);

	void deleteById(Class<T> clazz, int id);

	T getById(Class<T> clazz, int id);

	List<T> getListAll(Class<T> clazz,
                       final Map<String, Object> equalCondition,
                       final Map<String, String> likeCondition);

	public List<T> getListAll(final Class<T> clazz,
                              final List<Map<String, Object>> equalCondition,
                              final Map<String, String> likeCondition);

	List<T> getListForPage(Class<T> clazz, final int offset, final int length,
                           final Map<String, String> equalCondition,
                           final Map<String, String> likeCondition);

	List<T> getListForPage(Class<T> clazz, final int offset, final int length,
                           final Map<String, String> equalCondition,
                           final Map<String, String> likeCondition,
                           final Map<String, String> gtCondition);
	PageResult getListForPage(Class<T> clazz, final int offset, final int length,
                              final Map<String, String> equalCondition,
                              final Map<String, String> likeCondition,
                              final Map<String, String> gtCondition,
                              final Map<String, String> ltCondition);

	List<T> getListForPage(Class<T> clazz, final int offset, final int length,
                           final Map<String, Object> equalCondition,
                           final Map<String, String> likeCondition, final String order);

	List<T> getListForPage(Class<T> clazz, final int offset,
                           final int length, final List<Map<String, Object>> equalCondition,
                           final Map<String, String> likeCondition,
                           final String order);
	
	public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length, final List<Map<String, Object>> equalCondition,
                                  final Map<String, String> likeCondition);

	long getListCount(Class<T> clazz, final Map<String, String> equalCondition,
                      final Map<String, String> likeCondition);

	long isExist(Class<T> clazz, final Map<String, String> equalCondition,
                 final String id);
}
