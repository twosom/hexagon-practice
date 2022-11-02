package com.icloud.topologyinventory.application.usecases;

import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.IP;
import com.icloud.topologyinventory.domain.vo.Network;

public interface NetworkManagementUseCase {

    Network createNetwork(
            IP networkAddress,
            String networkName,
            int networkCidr);

    Switch addNetworkToSwitch(Network network, Switch networkSwitch);

    Switch removeNetworkFromSwitch(String name, Switch networkSwitch);
}
