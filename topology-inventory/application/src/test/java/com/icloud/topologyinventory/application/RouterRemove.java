package com.icloud.topologyinventory.application;

import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.vo.RouterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterRemove extends ApplicationTestData {
    public RouterRemove() {
        this.loadData();
    }

    @Given("코어 라우터는 적어도 하나의 에지 라우터와 연결되어 있다")
    public void 코어_라우터는_적어도_하나의_에지_라우터와_연결되어_있다() {
        var predicate = Router.getRouterTypePredicate(RouterType.EDGE);
        edgeRouter = (EdgeRouter) this.coreRouter.getRouters().values().stream()
                .filter(predicate)
                .findFirst()
                .get();
        assertEquals(RouterType.EDGE, edgeRouter.getRouterType());
    }

    @Given("스위치는 연결된 네트워크를 갖고 있지 않다")
    public void 스위치는_연결된_네트워크를_갖고_있지_않다() {
        var networksSize = networkSwitch.getSwitchNetworks().size();
        assertEquals(1, networksSize);
        networkSwitch.removeNetworkFromSwitch(network);
        networksSize = networkSwitch.getSwitchNetworks().size();
        assertEquals(0, networksSize);
    }

    @Given("에지 라우터는 연결된 네트워크를 갖고 있지 않다")
    public void 에지_라우터는_연결된_네트워크를_갖고_있지_않다() {
        var switchesSize = edgeRouter.getSwitches().size();
        assertEquals(1, switchesSize);
        edgeRouter.removeSwitch(networkSwitch);
        switchesSize = edgeRouter.getSwitches().size();
        assertEquals(0, switchesSize);
    }

    @Then("코어 라우터에서 에지 라우터를 제거한다")
    public void 코어_라우터에서_에지_라우터를_제거한다() {
        var actualID = edgeRouter.getId();
        var expectedID = this.routerManagementUseCase.removeRouterFromCoreRouter(edgeRouter, coreRouter).getId();
        assertEquals(actualID, expectedID);
    }

    @Given("코어 라우터는 적어도 하나의 코어 라우터와 연결되어 있다")
    public void 코어_라우터는_적어도_하나의_코어_라우터와_연결되어_있다() {

    }

    @Given("코어 라우터는 연결된 또 다른 또 다른 라우터를 갖고 있지 않다")
    public void 코어_라우터는_연결된_또_다른_또_다른_라우터를_갖고_있지_않다() {
    }

    @Then("코어 라우터를 또 다른 코어 라우터에서 제거한다")
    public void 코어_라우터를_또_다른_코어_라우터에서_제거한다() {
    }

}
