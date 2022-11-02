package com.icloud.topologyinventory.domain.service;

import com.icloud.topologyinventory.domain.vo.Network;

import java.util.List;
import java.util.function.Predicate;

public class NetworkService {

    public static List<Network> filterAndRetrieveNetworks(List<Network> networks, Predicate<Network> networkPredicate) {
        return networks.stream()
                .filter(networkPredicate)
                .toList();
    }

    public static Network findNetwork(List<Network> networks, Predicate<Network> networkPredicate) {
        return networks
                .stream()
                .filter(networkPredicate)
                .findFirst().orElse(null);
    }
}
