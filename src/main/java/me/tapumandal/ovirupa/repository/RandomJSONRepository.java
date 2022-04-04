package me.tapumandal.ovirupa.repository;

import me.tapumandal.ovirupa.entity.RandomJSON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by TapuMandal on 11/10/2021.
 * For any query ask online.tapu@gmail.com
 */

@Component
@Repository
public interface RandomJSONRepository extends JpaRepository<RandomJSON, Integer> {
}
