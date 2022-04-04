package me.tapumandal.ovirupa.repository;

import me.tapumandal.ovirupa.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    public User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.isActive = 1")
    public User findByIdIfActive(int id);

    List<User> findAllByRole(String admin);
}
