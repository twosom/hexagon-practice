package com.icloud.topologyinventory.framework.adapters.output.h2;

import com.icloud.topologyinventory.application.ports.output.SwitchManagementOutputPort;
import com.icloud.topologyinventory.domain.entity.Switch;
import com.icloud.topologyinventory.domain.vo.Id;
import com.icloud.topologyinventory.framework.adapters.output.h2.data.SwitchData;
import com.icloud.topologyinventory.framework.adapters.output.h2.mappers.RouterH2Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class SwitchManagementH2Adapter implements SwitchManagementOutputPort {

    private static SwitchManagementH2Adapter INSTANCE;

    @PersistenceContext
    private EntityManager em;

    private SwitchManagementH2Adapter() {
        setUpH2Database();
    }

    public static SwitchManagementH2Adapter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SwitchManagementH2Adapter();
        }
        return INSTANCE;
    }

    private void setUpH2Database() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("inventory");
        this.em = entityManagerFactory.createEntityManager();
    }

    @Override
    public Switch retrieveSwitch(Id id) {
        var switchData = em.getReference(SwitchData.class, id.getUuid());
        return RouterH2Mapper.switchDataToDomain(switchData);
    }
}
