package io.hari.demo.service;


import io.hari.demo.dao.BaseDao;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
public abstract class BaseService<T> {
    BaseDao<T> dao;

    public BaseService(BaseDao<T> dao) {
        this.dao = dao;
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public T save(T entity) {
        return dao.save(entity);
    }

    public T findById(Long id) {
        final Optional<T> optional = dao.findById(id);
        return optional.get();
    }

    public List<? extends T> saveAll(Iterable<? extends T> saveAll) {
        return dao.saveAll(saveAll);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
