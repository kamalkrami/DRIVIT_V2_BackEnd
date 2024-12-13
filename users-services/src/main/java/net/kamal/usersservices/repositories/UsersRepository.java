package net.kamal.usersservices.repositories;

import net.kamal.usersservices.entities.Users;
import net.kamal.usersservices.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {
    public List<Users> findUsersByStatus(UserType userType);
}
