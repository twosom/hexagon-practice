package com.icloud.topologyinventory.application.ports.input;

import com.icloud.topologyinventory.application.ports.output.RouterManagementOutputPort;
import com.icloud.topologyinventory.application.usecases.RouterManagementUseCase;
import com.icloud.topologyinventory.domain.entity.CoreRouter;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.entity.factory.RouterFactory;
import com.icloud.topologyinventory.domain.vo.*;

public class RouterManagementInputPort implements RouterManagementUseCase {

    private final RouterManagementOutputPort routerManagementOutputPort;

    public RouterManagementInputPort(RouterManagementOutputPort routerManagementOutputPort) {
        this.routerManagementOutputPort = routerManagementOutputPort;
    }


    @Override
    public Router createRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        return RouterFactory.getRouter(id, vendor, model, ip, location, routerType);
    }

    @Override
    public Router removeRouter(Id id) {
        return this.routerManagementOutputPort.removeRouter(id);
    }

    @Override
    public Router retrieveRouter(Id id) {
        return routerManagementOutputPort.retrieveRouter(id);
    }

    @Override
    public Router persistRouter(Router router) {
        return routerManagementOutputPort.persistRouter(router);
    }

    @Override
    public CoreRouter addRouterToCoreRouter(Router router, CoreRouter coreRouter) {
        var addedRouter = coreRouter.addRouter(router);
//        persistRouter(addedRouter);
        return addedRouter;
    }

    @Override
    public Router removeRouterFromCoreRouter(Router router, CoreRouter coreRouter) {
        var removedRouter = coreRouter.removeRouter(router);
//        persistRouter(removedRouter);
        return removedRouter;
    }
}
