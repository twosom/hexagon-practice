package com.icloud.topologyinventory.domain.service;

import com.icloud.topologyinventory.domain.entity.Equipment;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.vo.Id;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class RouterService {

    public static List<Router> filterAndRetrieveRouter(List<Router> routers, Predicate<Equipment> routerPredicate) {
        return routers.stream()
                .filter(routerPredicate)
                .toList();
    }

    public static Router findById(Map<Id, Router> routers, Id id) {
        return routers.get(id);
    }
}
