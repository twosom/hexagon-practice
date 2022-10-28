package com.icloud.topologyinventory.domain.entity;

import com.icloud.topologyinventory.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public abstract class Equipment {

    protected Id id;
    protected Vendor vendor;
    protected Model model;
    protected IP ip;
    protected Location location;

    public static Predicate<Equipment> getVendorPredicate(Vendor vendor) {
        return equipment -> equipment.getVendor().equals(vendor);
    }

}
