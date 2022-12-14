package com.icloud.topologyinventory.application.ports.input;

import com.icloud.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import com.icloud.topologyinventory.application.usecases.SwitchManagementUseCase;
import com.icloud.topologyinventory.domain.entity.EdgeRouter;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SwitchManagementInputPort implements SwitchManagementUseCase {

    SwitchManagementOutputPort switchManagementOutputPort;

    @Override
    public void setOutputPort(SwitchManagementOutputPort switchManagementOutputPort) {
        this.switchManagementOutputPort = switchManagementOutputPort;
    }


    @Override
    public Switch retrieveSwitch(Id id) {
        return switchManagementOutputPort.retrieveSwitch(id);
    }

    @Override
    public Switch createSwitch(Vendor vendor, Model model, IP ip, Location location, SwitchType switchType) {
        return Switch.builder()
                .switchId(Id.withoutId())
                .vendor(vendor)
                .model(model)
                .ip(ip)
                .location(location)
                .switchType(switchType)
                .build();
    }

    @Override
    public EdgeRouter addSwitchToEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter) {
        edgeRouter.addSwitch(networkSwitch);
        return edgeRouter;
    }

    @Override
    public EdgeRouter removeSwitchFromEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter) {
        edgeRouter.removeSwitch(networkSwitch);
        return edgeRouter;
    }
}
