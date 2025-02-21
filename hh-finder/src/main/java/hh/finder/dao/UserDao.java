package hh.finder.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hh.finder.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

}
