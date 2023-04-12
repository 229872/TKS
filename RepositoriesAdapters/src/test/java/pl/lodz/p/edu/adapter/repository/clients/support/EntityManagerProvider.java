package pl.lodz.p.edu.adapter.repository.clients.support;

import de.hilling.junit.cdi.annotations.GlobalTestImplementation;
import de.hilling.junit.cdi.jee.EntityManagerResourcesProvider;
import de.hilling.junit.cdi.scope.TestScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@TestScoped
public class EntityManagerProvider {
    @Inject
    private EntityManagerResourcesProvider provider;

    @Produces
    @RequestScoped
    @GlobalTestImplementation
    public EntityManager getEntityManager() {
        return provider.resolveEntityManager("test");
    }
}
