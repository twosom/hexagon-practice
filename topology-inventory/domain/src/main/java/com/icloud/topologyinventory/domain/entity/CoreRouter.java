package com.icloud.topologyinventory.domain.entity;


import com.icloud.topologyinventory.domain.specification.EmptyRouterSpec;
import com.icloud.topologyinventory.domain.specification.EmptySwitchSpec;
import com.icloud.topologyinventory.domain.specification.SameCountrySpec;
import com.icloud.topologyinventory.domain.specification.SameIpSpec;
import com.icloud.topologyinventory.domain.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class CoreRouter extends Router {

    private final Map<Id, Router> routers;

    @Builder
    public CoreRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType, Map<Id, Router> routers) {
        super(id, vendor, model, ip, location, routerType);
        this.routers = routers == null ? new HashMap<>() : routers;
    }

    public CoreRouter addRouter(Router anyRouter) {
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        sameCountryRouterSpec.check(anyRouter);
        sameIpSpec.check(anyRouter);
        this.routers.put(anyRouter.id, anyRouter);
        return this;
    }

    public Router removeRouter(Router anyRouter) {
        var emptyRouterSpec = new EmptyRouterSpec();
        var emptySwitchSpec = new EmptySwitchSpec();

        switch (anyRouter.routerType) {
            case CORE -> {
                var coreRouter = (CoreRouter) anyRouter;
                emptyRouterSpec.check(coreRouter);
            }
            case EDGE -> {
                var edgeRouter = (EdgeRouter) anyRouter;
                emptySwitchSpec.check(edgeRouter);
            }
        }
        return this.routers.remove(anyRouter.id);
    }
}
