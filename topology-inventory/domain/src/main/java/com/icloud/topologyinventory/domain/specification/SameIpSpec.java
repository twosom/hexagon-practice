package com.icloud.topologyinventory.domain.specification;

import com.icloud.topologyinventory.domain.entity.Equipment;
import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;
import com.icloud.topologyinventory.domain.specification.shared.AbstractSpecification;

public class SameIpSpec extends AbstractSpecification<Equipment> {

    private final Equipment equipment;

    public SameIpSpec(Equipment equipment) {
        this.equipment = equipment;
    }


    @Override
    public boolean isSatisfiedBy(Equipment anyEquipment) {
        return !equipment.getIp().equals(anyEquipment.getIp());
    }

    @Override
    public void check(Equipment equipment) {
        if (!isSatisfiedBy(equipment)) {
            throw new GenericSpecificationException("It's not possible to attach routers with the same IP");
        }
    }
}
