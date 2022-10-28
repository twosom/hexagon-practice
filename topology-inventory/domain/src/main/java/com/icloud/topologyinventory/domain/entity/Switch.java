package com.icloud.topologyinventory.domain.entity;

import com.icloud.topologyinventory.domain.specification.CIDRSpecification;
import com.icloud.topologyinventory.domain.specification.NetworkAmountSpec;
import com.icloud.topologyinventory.domain.specification.NetworkAvailabilitySpec;
import com.icloud.topologyinventory.domain.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.function.Predicate;

@Getter
@ToString
public class Switch extends Equipment {

    private final SwitchType switchType;
    private final List<Network> switchNetworks;

    @Builder
    public Switch(Id id, Vendor vendor, Model model, IP ip, Location location, SwitchType switchType, List<Network> switchNetworks) {
        super(id, vendor, model, ip, location);
        this.switchType = switchType;
        this.switchNetworks = switchNetworks;
    }

    public static Predicate<Switch> getSwitchTypePredicate(SwitchType switchType) {
        return aSwitch -> aSwitch.switchType.equals(switchType);
    }

    public static Predicate<Network> getNetworkProtocolPredicate(Protocol protocol) {
        return aSwitch -> aSwitch.getNetworkAddress().getProtocol().equals(protocol);
    }

    public boolean addNetworkToSwitch(Network network) {
        var availabilitySpec = new NetworkAvailabilitySpec(network);
        var cidrSpec = new CIDRSpecification();
        var amountSpec = new NetworkAmountSpec();

        cidrSpec.check(network.getNetworkCidr());
        availabilitySpec.check(this);
        amountSpec.check(this);

        return this.switchNetworks.add(network);
    }

    public boolean removeNetworkFromSwitch(Network network) {
        return this.switchNetworks.remove(network);
    }
}
