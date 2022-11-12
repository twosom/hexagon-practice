package com.icloud.topologyinventory.framework.adapters.input.generic;

import com.icloud.topologyinventory.application.usecases.NetworkManagementUseCase;
import com.icloud.topologyinventory.application.usecases.SwitchManagementUseCase;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.Id;
import com.icloud.topologyinventory.domain.vo.Network;

public class NetworkManagementGenericAdapter {

    private final SwitchManagementUseCase switchManagementUseCase;
    private final NetworkManagementUseCase networkManagementUseCase;

    public NetworkManagementGenericAdapter(SwitchManagementUseCase switchManagementUseCase,
                                           NetworkManagementUseCase networkManagementUseCase) {
        this.switchManagementUseCase = switchManagementUseCase;
        this.networkManagementUseCase = networkManagementUseCase;
    }

    /**
     * POST /network/add
     */
    public Switch addNetworkToSwitch(Network network, Id switchId) {
        var networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.addNetworkToSwitch(network, networkSwitch);
    }

    public Switch removeNetworkFromSwitch(String networkName, Id switchId) {
        var networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.removeNetworkFromSwitch(networkName, networkSwitch);
    }


}
