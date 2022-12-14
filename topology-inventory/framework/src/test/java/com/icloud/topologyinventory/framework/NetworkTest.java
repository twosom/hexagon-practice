package com.icloud.topologyinventory.framework;

import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.service.NetworkService;
import com.icloud.topologyinventory.domain.vo.Id;
import com.icloud.topologyinventory.domain.vo.Network;
import com.icloud.topologyinventory.framework.adapters.input.generic.NetworkManagementGenericAdapter;
import com.icloud.topologyinventory.framework.adapters.input.generic.SwitchManagementGenericAdapter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NetworkTest extends FrameworkTestData {

    NetworkManagementGenericAdapter networkManagementGenericAdapter;
    SwitchManagementGenericAdapter switchManagementGenericAdapter;

    public NetworkTest() {
        loadData();
    }

    @Test
    @Order(1)
    public void addNetworkToSwitch() {
        Id switchId = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");
        Switch networkSwitch = networkManagementGenericAdapter.addNetworkToSwitch(network, switchId);
        Predicate<Network> predicate = Network.getNetworkNamePredicate("TestNetwork");
        Network actualNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
        assertEquals(network, actualNetwork);
    }

    @Test
    @Order(2)
    public void removeNetworkFromSwitch() {
        Id switchId = Id.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");
        var networkName = "HR";
        Predicate<Network> predicate = Network.getNetworkNamePredicate(networkName);
        Switch networkSwitch = switchManagementGenericAdapter.retrieveSwitch(switchId);
        Network existentNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
        assertNotNull(existentNetwork);
        networkSwitch = networkManagementGenericAdapter.removeNetworkFromSwitch(networkName, switchId);
        assertNull(networkSwitch);
    }
}
