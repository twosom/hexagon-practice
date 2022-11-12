module framework {
    requires domain;
    requires lombok;
    requires application;
    requires jakarta.persistence;
    requires org.eclipse.persistence.core;

    exports com.icloud.topologyinventory.framework.adapters.output.h2.data;
    opens com.icloud.topologyinventory.framework.adapters.output.h2.data;

    provides com.icloud.topologyinventory.application.ports.output.RouterManagementOutputPort
            with com.icloud.topologyinventory.framework.adapters.output.h2.RouterManagementH2Adapter;

    provides com.icloud.topologyinventory.application.ports.output.SwitchManagementOutputPort
            with com.icloud.topologyinventory.framework.adapters.output.h2.SwitchManagementH2Adapter;

    // application 헥사곤 모듈에서 제공하는 서비스
    uses com.icloud.topologyinventory.application.usecases.RouterManagementUseCase;
    uses com.icloud.topologyinventory.application.usecases.SwitchManagementUseCase;
    uses com.icloud.topologyinventory.application.usecases.NetworkManagementUseCase;

    // framework 헥사곤 모듈에서 노출하는 서비스
    uses com.icloud.topologyinventory.application.ports.output.RouterManagementOutputPort;
    uses com.icloud.topologyinventory.application.ports.output.SwitchManagementOutputPort;

}