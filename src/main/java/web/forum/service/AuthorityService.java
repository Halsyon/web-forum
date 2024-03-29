package web.forum.service;

import org.springframework.stereotype.Service;
import web.forum.model.Authority;
import web.forum.repository.AuthorityRepository;

/**
 * также при необходимости доступы методы
 *  <S extends T> S save(S var1);
 *     <S extends T> Iterable<S> saveAll(Iterable<S> var1);
 *     Optional<T> findById(ID var1);
 *     boolean existsById(ID var1);
 *     Iterable<T> findAll();
 *     Iterable<T> findAllById(Iterable<ID> var1);
 *     long count();
 *     void deleteById(ID var1);
 *     void delete(T var1);
 *     void deleteAll(Iterable<? extends T> var1);
 *     void deleteAll();
 */
@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority findByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }
}
