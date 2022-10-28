package com.icloud.topologyinventory.domain;

import com.icloud.topologyinventory.domain.entity.CoreRouter;
import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;
import com.icloud.topologyinventory.domain.service.NetworkService;
import com.icloud.topologyinventory.domain.service.RouterService;
import com.icloud.topologyinventory.domain.service.SwitchService;
import com.icloud.topologyinventory.domain.vo.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest {

    @Test
    void addNetworkToSwitch() {
        var location = createLocation("US");
        var newNetwork = createTestNetwork("30.0.0.1", 8);
        Switch networkSwitch = createSwitch("30.0.0.0", 8, location);

        assertTrue(networkSwitch.addNetworkToSwitch(newNetwork));
    }

    @Test
    void addNetworkToSwitch_failBecauseSameNetworkAddress() {
        var location = createLocation("US");
        var newNetwork = createTestNetwork("30.0.0.0", 8);
        Switch networkSwitch = createSwitch("30.0.0.0", 8, location);

        assertThrows(GenericSpecificationException.class, () -> networkSwitch.addNetworkToSwitch(newNetwork));
    }

    @Test
    void addSwitchToEdgeRouter() {
        var location = createLocation("US");
        var networkSwitch = createSwitch("30.0.0.0", 8, location);
        var edgeRouter = createEdgeRouter(location, "30.0.0.1");

        edgeRouter.addSwitch(networkSwitch);

        assertEquals(1, edgeRouter.getSwitches().size());
    }

    @Test
    void addSwitchToEdgeRouter_failBecauseEquipmentOfDifferentCountries() {
        var locationUS = createLocation("US");
        var locationJP = createLocation("JP");
        var networkSwitch = createSwitch("30.0.0.0", 8, locationUS);
        var edgeRouter = createEdgeRouter(locationJP, "30.0.0.1");

        assertThrows(GenericSpecificationException.class, () -> edgeRouter.addSwitch(networkSwitch));
    }

    @Test
    void addEdgeToCoreRouter() {
        var location = createLocation("US");
        var edgeRouter = createEdgeRouter(location, "30.0.0.1");
        var coreRouter = createCoreRouter(location, "40.0.0.1");

        coreRouter.addRouter(edgeRouter);

        assertEquals(1, coreRouter.getRouters().size());
    }

    @Test
    void addEdgeToCoreRouter_failBecauseRoutersOfDifferentCountries() {
        var locationUS = createLocation("US");
        var locationJP = createLocation("JP");
        var edgeRouter = createEdgeRouter(locationUS, "30.0.0.1");
        var coreRouter = createCoreRouter(locationJP, "40.0.0.1");

        assertThrows(GenericSpecificationException.class, () -> coreRouter.addRouter(edgeRouter));
    }

    @Test
    void addCoreToCoreRouter() {
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var newCoreRouter = createCoreRouter(location, "40.0.0.1");

        coreRouter.addRouter(newCoreRouter);
        assertEquals(1, coreRouter.getRouters().size());
    }

    @Test
    void addCoreToCoreRouter_failBecauseRoutersOfSameIp() {
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var newCoreRouter = createCoreRouter(location, "30.0.0.1");

        assertThrows(GenericSpecificationException.class, () -> coreRouter.addRouter(newCoreRouter));
    }

    @Test
    void removeRouter() {
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var edgeRouter = createEdgeRouter(location, "40.0.0.1");
        var expectedId = edgeRouter.getId();

        coreRouter.addRouter(edgeRouter);
        var actualId = coreRouter.removeRouter(edgeRouter).getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void removeSwitch() {
        var location = createLocation("US");
        var network = createTestNetwork("30.0.0.0", 8);
        var networkSwitch = createSwitch("30.0.0.0", 8, location);
        var edgeRouter = createEdgeRouter(location, "40.0.0.1");

        edgeRouter.addSwitch(networkSwitch);
        networkSwitch.removeNetworkFromSwitch(network);
        var expectedId = Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
        var actualId = edgeRouter.removeSwitch(networkSwitch).getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void removeNetwork() {
        var location = createLocation("US");
        var network = createTestNetwork("30.0.0.0", 8);
        var networkSwitch = createSwitch("30.0.0.0", 8, location);

        assertEquals(1, networkSwitch.getSwitchNetworks().size());
        assertTrue(networkSwitch.removeNetworkFromSwitch(network));
        assertEquals(0, networkSwitch.getSwitchNetworks().size());
    }

    @Test
    void filterRouterByType() {
        var routers = new ArrayList<Router>();
        var location = createLocation("US");
        var coreRouter = createCoreRouter(location, "30.0.0.1");
        var edgeRouter = createEdgeRouter(location, "40.0.0.1");

        routers.add(coreRouter);
        routers.add(edgeRouter);
        var coreRouters = RouterService.filterAndRetrieveRouter(routers, Router.getRouterTypePredicate(RouterType.CORE));
        var actualCoreType = coreRouters.get(0).getRouterType();
        assertEquals(RouterType.CORE, actualCoreType);

        var edgeRouters = RouterService.filterAndRetrieveRouter(routers, Router.getRouterTypePredicate(RouterType.EDGE));
        var actualEdgeType = edgeRouters.get(0).getRouterType();
        assertEquals(RouterType.EDGE, actualEdgeType);
    }

    @Test
    void filterRouterByVendor() {
        final var routers = new ArrayList<Router>();
        final var location = createLocation("US");
        final var coreRouter = createCoreRouter(location, "30.0.0.1");
        final var edgeRouter = createEdgeRouter(location, "40.0.0.1");

        routers.add(coreRouter);
        routers.add(edgeRouter);

        var actualVendor = RouterService.filterAndRetrieveRouter(routers, Router.getVendorPredicate(Vendor.HP)).get(0).getVendor();
        assertEquals(Vendor.HP, actualVendor);

        actualVendor = RouterService.filterAndRetrieveRouter(routers, Router.getVendorPredicate(Vendor.CISCO)).get(0).getVendor();
        assertEquals(Vendor.CISCO, actualVendor);
    }


    @Test
    void filterRouterByLocation() {
        final var routers = new ArrayList<Router>();
        final var location = createLocation("US");
        final var coreRouter = createCoreRouter(location, "30.0.0.1");

        routers.add(coreRouter);

        var actualCountry = RouterService.filterAndRetrieveRouter(routers, Router.getCountryPredicate(location)).get(0).getLocation().country();

        assertEquals(location.country(), actualCountry);
    }

    @Test
    void filterRouterByModel() {
        final var routers = new ArrayList<Router>();
        final var location = createLocation("US");
        final var coreRouter = createCoreRouter(location, "30.0.0.1");
        final var newCoreRouter = createCoreRouter(location, "40.0.0.1");

        coreRouter.addRouter(newCoreRouter);
        routers.add(coreRouter);

        var actualModel = RouterService.filterAndRetrieveRouter(routers, Router.getModelPredicate(Model.XYZ0001)).get(0).getModel();
        assertEquals(Model.XYZ0001, actualModel);
    }

    @Test
    void filterSwitchByType() {
        var switches = new ArrayList<Switch>();
        final var location = createLocation("US");
        var networkSwitch = createSwitch("30.0.0.0", 8, location);

        switches.add(networkSwitch);

        var actualSwitchType = SwitchService.filterAndRetrieveSwitch(switches, Switch.getSwitchTypePredicate(SwitchType.LAYER3)).get(0).getSwitchType();

        assertEquals(SwitchType.LAYER3, actualSwitchType);
    }

    @Test
    public void filterNetworkByProtocol() {
        var testNetwork = createTestNetwork("30.0.0.0", 8);
        var networks = createNetworks(testNetwork);

        var expectedProtocol = Protocol.IPV4;
        var actualProtocol = NetworkService.filterAndRetrieveNetworks(networks, Switch.getNetworkProtocolPredicate(Protocol.IPV4)).get(0).getNetworkAddress().getProtocol();
        assertEquals(expectedProtocol, actualProtocol);
    }

    @Test
    void findRouterById() {
        final var routersOfCoreRouter = new HashMap<Id, Router>();
        final var location = createLocation("US");
        final var coreRouter = createCoreRouter(location, "30.0.0.1");
        final var newCoreRouter = createCoreRouter(location, "40.0.0.1");

        coreRouter.addRouter(newCoreRouter);
        routersOfCoreRouter.put(newCoreRouter.getId(), newCoreRouter);

        var expectedId = newCoreRouter.getId();
        var actualId = RouterService.findById(routersOfCoreRouter, expectedId).getId();


        assertEquals(actualId, expectedId);
    }

    @Test
    void findSwitchById() {
        var switchesOfEdgeRouter = new HashMap<Id, Switch>();
        var location = createLocation("US");
        var networkSwitch = createSwitch("30.0.0.0", 8, location);

        switchesOfEdgeRouter.put(networkSwitch.getId(), networkSwitch);

        var expectedId = Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
        var actualId = SwitchService.findById(switchesOfEdgeRouter, expectedId).getId();

        assertEquals(expectedId, actualId);
    }

    private CoreRouter createCoreRouter(Location location, String address) {
        Map<Id, Router> routersOfCoreRouter = new HashMap<>();
        return CoreRouter.builder()
                .id(Id.withoutId())
                .vendor(Vendor.HP)
                .model(Model.XYZ0001)
                .ip(IP.fromAddress(address))
                .location(location)
                .routerType(RouterType.CORE)
                .routers(routersOfCoreRouter)
                .build();
    }

    private EdgeRouter createEdgeRouter(Location location, String address) {
        Map<Id, Switch> switchesOfEdgeRouter = new HashMap<>();
        return EdgeRouter.builder()
                .id(Id.withoutId())
                .vendor(Vendor.CISCO)
                .model(Model.XYZ0002)
                .ip(IP.fromAddress(address))
                .location(location)
                .routerType(RouterType.EDGE)
                .switches(switchesOfEdgeRouter)
                .build();
    }

    private Switch createSwitch(String address, int CIDR, Location location) {
        var newNetwork = createTestNetwork(address, CIDR);
        var networks = createNetworks(newNetwork);
        return createNetworkSwitch(location, networks);
    }

    private Switch createNetworkSwitch(Location location, List<Network> networks) {
        return Switch.builder()
                .id(Id.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490"))
                .vendor(Vendor.CISCO)
                .model(Model.XYZ0004)
                .ip(IP.fromAddress("20.0.0.100"))
                .location(location)
                .switchType(SwitchType.LAYER3)
                .switchNetworks(networks).
                build();
    }

    private List<Network> createNetworks(Network network) {
        List<Network> networks = new ArrayList<>();
        networks.add(network);
        return networks;
    }

    private Network createTestNetwork(String address, int CIDR) {
        return Network.builder()
                .networkAddress(IP.fromAddress(address))
                .networkName("NewNetwork")
                .networkCidr(CIDR)
                .build();
    }

    private Location createLocation(String country) {
        return Location.builder()
                .address("Test street")
                .city("Test City")
                .state("Test State")
                .country(country)
                .zipCode(00000)
                .latitude(10F)
                .longitude(-10F)
                .build();
    }
}
