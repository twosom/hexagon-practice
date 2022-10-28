package com.icloud.topologyinventory.domain.specification;

import com.icloud.topologyinventory.domain.entity.Equipment;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;
import com.icloud.topologyinventory.domain.specification.shared.AbstractSpecification;

public class NetworkAmountSpec extends AbstractSpecification<Equipment> {

    final static public int MAXIMUM_ALLOWED_NETWORKS = 6;

    @Override
    public boolean isSatisfiedBy(Equipment equipment) {
        return ((Switch) equipment).getSwitchNetworks().size() <= MAXIMUM_ALLOWED_NETWORKS;
    }

    @Override
    public void check(Equipment equipment) throws GenericSpecificationException {
        if (!isSatisfiedBy(equipment))
            throw new GenericSpecificationException("The max number of networks is " + NetworkAmountSpec.MAXIMUM_ALLOWED_NETWORKS);
    }
}
