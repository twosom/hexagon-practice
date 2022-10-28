package com.icloud.topologyinventory.domain.specification;

import com.icloud.topologyinventory.domain.entity.Equipment;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.exception.GenericSpecificationException;
import com.icloud.topologyinventory.domain.specification.shared.AbstractSpecification;
import com.icloud.topologyinventory.domain.vo.IP;
import com.icloud.topologyinventory.domain.vo.Network;

public class NetworkAvailabilitySpec extends AbstractSpecification<Equipment> {

    private final IP address;
    private final String name;
    private final int cidr;

    public NetworkAvailabilitySpec(Network network) {
        this.address = network.getNetworkAddress();
        this.name = network.getNetworkName();
        this.cidr = network.getNetworkCidr();
    }

    @Override
    public boolean isSatisfiedBy(Equipment switchNetworks) {
        return switchNetworks != null && isNetworkAvailable(switchNetworks);
    }

    @Override
    public void check(Equipment switchNetworks) throws GenericSpecificationException {
        if (!isSatisfiedBy(switchNetworks)) {
            throw new GenericSpecificationException("This network already exists");
        }
    }

    private boolean isNetworkAvailable(Equipment switchNetworks) {
        return ((Switch) switchNetworks).getSwitchNetworks().stream()
                .filter(network -> network.getNetworkName().equals(this.name) &&
                                   network.getNetworkCidr() == cidr &&
                                   network.getNetworkAddress().equals(this.address)
                )
                .findAny()
                .isEmpty();
    }
}
