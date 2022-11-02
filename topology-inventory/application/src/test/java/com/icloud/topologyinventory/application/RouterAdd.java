package com.icloud.topologyinventory.application;


import com.icloud.topologyinventory.domain.entity.CoreRouter;
import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.vo.IP;
import com.icloud.topologyinventory.domain.vo.Id;
import com.icloud.topologyinventory.domain.vo.Model;
import com.icloud.topologyinventory.domain.vo.Vendor;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static com.icloud.topologyinventory.domain.vo.RouterType.CORE;
import static com.icloud.topologyinventory.domain.vo.RouterType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RouterAdd extends ApplicationTestData {

    CoreRouter anotherCoreRouter;

    public RouterAdd() {
        this.loadData();
    }

    @Given("에지 라우터가 있다")
    public void assert_edge_router_exists() {
        edgeRouter = (EdgeRouter) this.routerManagementUseCase
                .createRouter(Id.withoutId(), Vendor.HP, Model.XYZ0004, IP.fromAddress("20.0.0.1"), locationA, EDGE);
        assertNotNull(edgeRouter);
    }

    @And("코어 라우터가 있다")
    public void assert_core_router_exists() {
        coreRouter = (CoreRouter) this.routerManagementUseCase
                .createRouter(null, Vendor.CISCO, Model.XYZ0001, IP.fromAddress("30.0.0.1"), locationA, CORE);
        assertNotNull(coreRouter);
    }

    @Then("코어 라우터에 에지 라우터를 추가한다")
    public void add_edge_router_to_core_router() {
        var actualEdgeId = edgeRouter.getId();
        var routerWithEdge = this.routerManagementUseCase
                .addRouterToCoreRouter(edgeRouter, coreRouter);
        var expectedEdgeId = routerWithEdge.getRouters().get(actualEdgeId).getId();
        assertEquals(actualEdgeId, expectedEdgeId);
    }

    @Given("이 코어 라우터가 있다")
    public void assert_this_core_router_exists() {
        coreRouter = (CoreRouter) this.routerManagementUseCase
                .createRouter(
                        null,
                        Vendor.CISCO,
                        Model.XYZ0001,
                        IP.fromAddress("30.0.0.1"),
                        locationA,
                        CORE
                );
        assertNotNull(coreRouter);
    }

    @And("또 다른 코어 라우터가 있다")
    public void assert_another_core_router_exists() {
        anotherCoreRouter = (CoreRouter) this.routerManagementUseCase.createRouter(
                Id.withoutId(),
                Vendor.CISCO,
                Model.XYZ0001,
                IP.fromAddress("40.0.0.1"),
                locationA,
                CORE
        );
        assertNotNull(anotherCoreRouter);
    }

    @Then("코어 라우터에 이 코어 라우터를 추가한다")
    public void add_core_to_core_router() {
        var coreRouterId = coreRouter.getId();
        var routerWithCore = this.routerManagementUseCase.addRouterToCoreRouter(coreRouter, anotherCoreRouter);
        var expectedId = routerWithCore.getRouters().get(coreRouterId).getId();
        assertEquals(coreRouterId, expectedId);
    }

}
