package com.icloud.topologyinventory.framework.adapters.input.generic;

import com.icloud.topologyinventory.application.ports.input.NetworkManagementInputPort;
import com.icloud.topologyinventory.application.ports.input.SwitchManagementInputPort;
import com.icloud.topologyinventory.application.usecases.NetworkManagementUseCase;
import com.icloud.topologyinventory.application.usecases.SwitchManagementUseCase;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.Id;
import com.icloud.topologyinventory.domain.vo.Network;
import com.icloud.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;
import com.icloud.topologyinventory.framework.adapters.output.h2.SwitchManagementH2Adapter;

public class NetworkManagementGenericAdapter {

    private SwitchManagementUseCase switchManagementUseCase;
    private NetworkManagementUseCase networkManagementUseCase;

    public NetworkManagementGenericAdapter() {
        setPorts();
    }

    private void setPorts() {
        this.switchManagementUseCase = new SwitchManagementInputPort(
                SwitchManagementH2Adapter.getInstance()
        );
        this.networkManagementUseCase = new NetworkManagementInputPort(
                RouterManagementH2Adapter.getInstance()
        );
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
