package com.icloud.topologyinventory.domain.specification.shared;

import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;

public class AndSpecification<T> extends AbstractSpecification<T> {

    private final Specification<T> specification1;
    private final Specification<T> specification2;

    public AndSpecification(Specification<T> specification1,
                            Specification<T> specification2) {
        this.specification1 = specification1;
        this.specification2 = specification2;
    }


    @Override
    public boolean isSatisfiedBy(T t) {
        return specification1.isSatisfiedBy(t) &&
               specification2.isSatisfiedBy(t);
    }

    @Override
    public void check(T t) throws GenericSpecificationException {

    }
}
