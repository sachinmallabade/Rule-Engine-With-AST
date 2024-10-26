package com.zeotap.ruleengine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeotap.ruleengine.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
