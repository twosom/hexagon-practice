package com.icloud.topologyinventory.domain.entity;

import com.icloud.topologyinventory.domain.vo.*;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
public abstract class Router extends Equipment {

    protected final RouterType routerType;


    public Router(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        super(id, vendor, model, ip, location);
        this.routerType = routerType;
    }

    public static Predicate<Equipment> getRouterTypePredicate(RouterType routerType) {
        return equipment -> equipment instanceof Router router && router.getRouterType().equals(routerType);
    }

    public static Predicate<Equipment> getModelPredicate(Model model) {
        return equipment -> equipment.getModel().equals(model);
    }

    public static Predicate<Equipment> getCountryPredicate(Location location) {
        return equipment -> equipment.getLocation().country().equals(location.country());
    }


}
