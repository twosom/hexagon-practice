package com.icloud.topologyinventory.domain.service;

import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.Id;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class SwitchService {


    public static List<Switch> filterAndRetrieveSwitch(List<Switch> switches, Predicate<Switch> switchPredicate) {
        return switches.stream()
                .filter(switchPredicate)
                .toList();
    }

    public static Switch findById(Map<Id, Switch> switches, Id id) {
        return switches.get(id);
    }
}
