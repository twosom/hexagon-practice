package com.icloud.topologyinventory.domain.specification;

import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;
import com.icloud.topologyinventory.domain.specification.shared.AbstractSpecification;

public class EmptyNetworkSpec extends AbstractSpecification<Switch> {

    @Override
    public boolean isSatisfiedBy(Switch switchNetwork) {
        return switchNetwork.getSwitchNetworks() == null ||
               switchNetwork.getSwitchNetworks().isEmpty();
    }

    @Override
    public void check(Switch switchNetwork) throws GenericSpecificationException {
        if (!isSatisfiedBy(switchNetwork)) {
            throw new GenericSpecificationException("It's not possible to remove a switch with networks attached to it");
        }
    }
}
