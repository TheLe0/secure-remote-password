package br.com.tosin.profiles;

import br.com.tosin.mocks.MockUserRepository;
import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Set;

public class MockUserRepositoryProfile implements QuarkusTestProfile {
    @Override
    public Set<Class<?>> getEnabledAlternatives() {
        return Set.of(MockUserRepository.class);
    }
}
