package com.jekyloco.base;

public interface BaseMapper<D, E> {
    D toDto(E entity);
}
