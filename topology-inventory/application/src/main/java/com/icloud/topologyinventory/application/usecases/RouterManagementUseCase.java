package com.icloud.topologyinventory.application.usecases;

import com.icloud.topologyinventory.application.ports.output.RouterManagementOutputPort;
import com.icloud.topologyinventory.domain.entity.CoreRouter;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.vo.*;

public interface RouterManagementUseCase {

    void setOutputPort(RouterManagementOutputPort routerManagementOutputPort);

    Router createRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType);

    Router removeRouter(Id id);

    Router retrieveRouter(Id id);

    CoreRouter addRouterToCoreRouter(Router router, CoreRouter coreRouter);

    Router removeRouterFromCoreRouter(Router router, CoreRouter coreRouter);

    Router persistRouter(Router router);
}
