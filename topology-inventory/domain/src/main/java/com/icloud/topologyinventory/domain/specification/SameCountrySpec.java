package com.icloud.topologyinventory.domain.specification;

import com.icloud.topologyinventory.domain.entity.CoreRouter;
import com.icloud.topologyinventory.domain.entity.Equipment;
import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;
import com.icloud.topologyinventory.domain.specification.shared.AbstractSpecification;

public class SameCountrySpec extends AbstractSpecification<Equipment> {

    private final Equipment equipment;


    public SameCountrySpec(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public boolean isSatisfiedBy(Equipment anyEquipment) {
        if (anyEquipment instanceof CoreRouter) return true;

        if (anyEquipment != null && this.equipment != null) {
            return this.equipment
                    .getLocation()
                    .country().equals(anyEquipment.getLocation().country());
        } else {
            return false;
        }
    }

    @Override
    public void check(Equipment equipment) {
        if (!isSatisfiedBy(equipment)) {
            throw new GenericSpecificationException("The equipments should be in the same country");
        }
    }
}
