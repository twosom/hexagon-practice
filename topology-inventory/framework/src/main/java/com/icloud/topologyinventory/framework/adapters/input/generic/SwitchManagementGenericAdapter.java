package com.icloud.topologyinventory.framework.adapters.input.generic;

import com.icloud.topologyinventory.application.usecases.RouterManagementUseCase;
import com.icloud.topologyinventory.application.usecases.SwitchManagementUseCase;
import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.*;

public class SwitchManagementGenericAdapter {

    private final SwitchManagementUseCase switchManagementUseCase;
    private final RouterManagementUseCase routerManagementUseCase;

    public SwitchManagementGenericAdapter(RouterManagementUseCase routerManagementUseCase, SwitchManagementUseCase switchManagementUseCase) {
        this.switchManagementUseCase = switchManagementUseCase;
        this.routerManagementUseCase = routerManagementUseCase;
    }


    /**
     * GET /switch/retrieve/{id}
     */
    public Switch retrieveSwitch(Id switchId) {
        return switchManagementUseCase.retrieveSwitch(switchId);
    }

    /**
     * POST /switch/create
     */
    public EdgeRouter createAndAddSwitchToEdgeRouter(Vendor vendor, Model model, IP ip, Location location, SwitchType switchType, Id routerId) {
        var newSwitch = switchManagementUseCase.createSwitch(vendor, model, ip, location, switchType);
        var edgeRouter = routerManagementUseCase.retrieveRouter(routerId);
        if (!edgeRouter.getRouterType().equals(RouterType.EDGE)) {
            throw new UnsupportedOperationException("Please inform the id of an edge router to add a switch");
        }

        var router = switchManagementUseCase.addSwitchToEdgeRouter(newSwitch, (EdgeRouter) edgeRouter);
        return (EdgeRouter) routerManagementUseCase.persistRouter(router);
    }

    public EdgeRouter removeSwitchFromEdgeRouter(Id switchId, Id edgeRouterId) {
        var edgeRouter = (EdgeRouter) routerManagementUseCase.retrieveRouter(edgeRouterId);
        Switch networkSwitch = edgeRouter.getSwitches().get(switchId);
        var router = switchManagementUseCase.removeSwitchFromEdgeRouter(networkSwitch, edgeRouter);
        return (EdgeRouter) routerManagementUseCase.persistRouter(router);
    }

}
