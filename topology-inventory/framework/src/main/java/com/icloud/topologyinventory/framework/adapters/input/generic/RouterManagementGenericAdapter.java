package com.icloud.topologyinventory.framework.adapters.input.generic;

import com.icloud.topologyinventory.application.ports.input.RouterManagementInputPort;
import com.icloud.topologyinventory.application.usecases.RouterManagementUseCase;
import com.icloud.topologyinventory.domain.entity.CoreRouter;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.vo.*;
import com.icloud.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;

public class RouterManagementGenericAdapter {

    private RouterManagementUseCase routerManagementUseCase;

    public RouterManagementGenericAdapter() {
        setPorts();
    }

    private void setPorts() {
        this.routerManagementUseCase = new RouterManagementInputPort(
                RouterManagementH2Adapter.getInstance()
        );
    }

    /**
     * GET /router/retrieve/{id}
     */
    public Router retrieveRouter(Id id) {
        return routerManagementUseCase.retrieveRouter(id);
    }

    /**
     * GET /router/remove/{id}
     */
    public Router removeRouter(Id id) {
        return routerManagementUseCase.removeRouter(id);
    }

    /**
     * POST /router/add
     */
    public Router createRouter(Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        var router = routerManagementUseCase.createRouter(
                null, vendor, model, ip, location, routerType
        );

        return routerManagementUseCase.persistRouter(router);
    }

    /**
     * POST /router/add
     */
    public Router addRouterToCoreRouter(Id routerId, Id coreRouterId) {
        var router = routerManagementUseCase.retrieveRouter(routerId);
        var coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.addRouterToCoreRouter(router, coreRouter);
    }

    /**
     * POST /router/remove
     */
    public Router removeRouterFromCoreRouter(Id routerId, Id coreRouterId) {
        var router = routerManagementUseCase.retrieveRouter(routerId);
        var coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.removeRouterFromCoreRouter(router, coreRouter);
    }
}
