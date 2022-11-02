package com.icloud.topologyinventory.application.usecases;

import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.*;

public interface SwitchManagementUseCase {

    Switch retrieveSwitch(Id id);

    Switch createSwitch(Vendor vendor, Model model, IP ip, Location location, SwitchType switchType);

    EdgeRouter addSwitchToEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);

    EdgeRouter removeSwitchFromEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);

}
