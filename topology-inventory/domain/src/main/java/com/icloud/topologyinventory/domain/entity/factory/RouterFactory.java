package com.icloud.topologyinventory.domain.entity.factory;

import com.icloud.topologyinventory.domain.entity.CoreRouter;
import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.vo.*;

public class RouterFactory {
    public static Router getRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        return switch (routerType) {
            case CORE -> CoreRouter.builder()
                    .id(id == null ? Id.withoutId() : id)
                    .vendor(vendor)
                    .model(model)
                    .ip(ip)
                    .location(location)
                    .routerType(routerType)
                    .build();
            case EDGE -> EdgeRouter.builder()
                    .id(id == null ? Id.withoutId() : id)
                    .vendor(vendor)
                    .model(model)
                    .ip(ip)
                    .location(location)
                    .routerType(routerType)
                    .build();

            default -> throw new UnsupportedOperationException("No valid router type informed");
        };
    }
}
