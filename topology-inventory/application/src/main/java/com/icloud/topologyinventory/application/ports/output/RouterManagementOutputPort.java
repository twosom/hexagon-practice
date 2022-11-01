package com.icloud.topologyinventory.application.ports.output;

import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.vo.Id;

public interface RouterManagementOutputPort {
    Router retrieveRouter(Id id);

    Router persistRouter(Router router);
}
