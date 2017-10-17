package me.smartstart.core.service

interface GenericService<T, ID> {

    T create(T t)

    T get(ID id)

    T update(T t)

    T delete(ID id)

    Long countAll()

    List<T> getAll()
}