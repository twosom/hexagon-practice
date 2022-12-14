package com.icloud.topologyinventory.domain.specification.shared;

import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;

public abstract class AbstractSpecification<T> implements Specification<T> {

    public abstract void check(T t) throws GenericSpecificationException;

    @Override
    public Specification<T> and(Specification<T> specification) {
        return new AndSpecification<>(this, specification);
    }
}
