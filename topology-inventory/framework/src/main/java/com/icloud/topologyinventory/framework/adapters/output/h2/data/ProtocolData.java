package com.icloud.topologyinventory.framework.adapters.output.h2.data;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ProtocolData {
    IPV4,
    IPV6;
}
