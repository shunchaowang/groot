package me.smartstart.core.service

import org.springframework.data.domain.Page

import java.awt.print.Pageable

interface GenericService<T, ID> {

    T create(T t)

    T get(ID id)

    T update(T t)

    T delete(ID id)

    Long countAll()

    List<T> getAll()

    Page<T> findAll(Pageable pageable)
}