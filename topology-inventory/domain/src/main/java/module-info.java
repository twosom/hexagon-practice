module domain {
    opens com.icloud.topologyinventory.domain;
    exports com.icloud.topologyinventory.domain.entity;
    exports com.icloud.topologyinventory.domain.vo;
    exports com.icloud.topologyinventory.domain.entity.factory;
    requires static lombok;
}