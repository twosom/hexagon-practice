package com.icloud.topologyinventory.application.ports.input;

import com.icloud.topologyinventory.application.ports.output.RouterManagementOutputPort;
import com.icloud.topologyinventory.application.usecases.NetworkManagementUseCase;
import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.service.NetworkService;
import com.icloud.topologyinventory.domain.vo.IP;
import com.icloud.topologyinventory.domain.vo.Id;
import com.icloud.topologyinventory.domain.vo.Network;

import java.util.function.Predicate;

public class NetworkManagementInputPort implements NetworkManagementUseCase {

    private final RouterManagementOutputPort routerManagementOutputPort;

    public NetworkManagementInputPort(RouterManagementOutputPort routerManagementOutputPort) {
        this.routerManagementOutputPort = routerManagementOutputPort;
    }

    @Override
    public Network createNetwork(
            IP networkAddress, String networkName, int networkCidr) {
        return Network
                .builder()
                .networkAddress(networkAddress)
                .networkName(networkName)
                .networkCidr(networkCidr)
                .build();
    }

    @Override
    public Switch addNetworkToSwitch(Network network, Switch networkSwitch) {
        Id routerId = networkSwitch.getRouterId();
        Id switchId = networkSwitch.getId();
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
                .retrieveRouter(routerId);
        Switch switchToAddNetwork = edgeRouter
                .getSwitches()
                .get(switchId);
        switchToAddNetwork.addNetworkToSwitch(network);
        routerManagementOutputPort.persistRouter(edgeRouter);
        return switchToAddNetwork;
    }

    @Override
    public Switch removeNetworkFromSwitch(String networkName, Switch networkSwitch) {
        Id routerId = networkSwitch.getRouterId();
        Id switchId = networkSwitch.getId();
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
                .retrieveRouter(routerId);
        Switch switchToRemoveNetwork = edgeRouter
                .getSwitches()
                .get(switchId);
        Predicate<Network> networkPredicate = Network.getNetworkNamePredicate(networkName);
        var network = NetworkService.findNetwork(switchToRemoveNetwork.getSwitchNetworks(), networkPredicate);
        switchToRemoveNetwork.removeNetworkFromSwitch(network);
        routerManagementOutputPort.persistRouter(edgeRouter);
        return switchToRemoveNetwork.removeNetworkFromSwitch(network)
                ? switchToRemoveNetwork
                : null;
    }
}
