package kosiorek.michal.restcinemaapi.infrastructure.repository.impl;

import kosiorek.michal.restcinemaapi.domain.security.User;
import kosiorek.michal.restcinemaapi.domain.security.UserRepository;
import kosiorek.michal.restcinemaapi.infrastructure.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> addOrUpdate(User item) {
        return Optional.of(jpaUserRepository.save(item));
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Optional<User> deleteById(Long id) {

        var deleteOp = jpaUserRepository.findById(id);
        jpaUserRepository.deleteById(id);
        return deleteOp;

    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll();
    }

    @Override
    public List<User> findAllById(Iterable<Long> idsCollection) {
        return jpaUserRepository.findAllById(idsCollection);
    }

    @Override
    public List<User> addAll(List<User> items) {
        return jpaUserRepository.saveAll(items);
    }

    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username);
    }

}
