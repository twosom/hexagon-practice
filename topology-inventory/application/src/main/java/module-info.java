module application {
    exports com.icloud.topologyinventory.application.ports.output;
    exports com.icloud.topologyinventory.application.usecases;
    exports com.icloud.topologyinventory.application.ports.input;
    requires domain;
    requires static lombok;
}