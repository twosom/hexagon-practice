module application {
    /**
     * @author hope
     * @apiNote {@link com.icloud.topologyinventory.domain domain} 헥사곤 모듈에 액세스하기 위한 <strong>requires</strong> 지정자
     */
    requires domain;
    requires static lombok;

    exports com.icloud.topologyinventory.application.ports.input;
    exports com.icloud.topologyinventory.application.ports.output;
    exports com.icloud.topologyinventory.application.usecases;

    // RouterManagementUseCase 를 RouterManagementInputPort 형식으로 제공하겠다는 의미.
    provides com.icloud.topologyinventory.application.usecases.RouterManagementUseCase
            with com.icloud.topologyinventory.application.ports.input.RouterManagementInputPort;

    // SwitchManagementUseCase 를 SwitchManagementInputPort 형식으로 제공하겠다는 의미.
    provides com.icloud.topologyinventory.application.usecases.SwitchManagementUseCase
            with com.icloud.topologyinventory.application.ports.input.SwitchManagementInputPort;

    // NetworkManagementUseCase 를 NetworkManagementInputPort 형식으로 제공하겠다는 의미.
    provides com.icloud.topologyinventory.application.usecases.NetworkManagementUseCase
            with com.icloud.topologyinventory.application.ports.input.NetworkManagementInputPort;
}