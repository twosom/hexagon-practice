package com.icloud.topologyinventory.framework.adapters.output.h2;

import com.icloud.topologyinventory.application.ports.output.RouterManagementOutputPort;
import com.icloud.topologyinventory.domain.entity.Router;
import com.icloud.topologyinventory.domain.vo.Id;
import com.icloud.topologyinventory.framework.adapters.output.h2.data.RouterData;
import com.icloud.topologyinventory.framework.adapters.output.h2.mappers.RouterH2Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class RouterManagementH2Adapter implements RouterManagementOutputPort {

    private static RouterManagementH2Adapter INSTANCE;

    @PersistenceContext
    private EntityManager em;

    public RouterManagementH2Adapter() {
        setUpH2Database();
    }

    public static RouterManagementH2Adapter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RouterManagementH2Adapter();
        }
        return INSTANCE;
    }

    private void setUpH2Database() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("inventory");
        this.em = entityManagerFactory.createEntityManager();
    }

    @Override
    public Router retrieveRouter(Id id) {
        var routerData = em.getReference(RouterData.class, id.getUuid());
        return RouterH2Mapper.routerDataToDomain(routerData);
    }

    @Override
    public Router removeRouter(Id id) {
        var routerData = em.getReference(RouterData.class, id.getUuid());
        em.remove(routerData);
        return null;
    }

    @Override
    public Router persistRouter(Router router) {
        var routerData = RouterH2Mapper.routerDomainToData(router);
        em.persist(routerData);
        return router;
    }
}
