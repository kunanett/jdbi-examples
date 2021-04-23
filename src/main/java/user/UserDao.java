package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(User.class)
public interface UserDao {

    @SqlUpdate("""
        CREATE TABLE user (
                        id IDENTITY PRIMARY KEY NOT NULL,
                        username VARCHAR NOT NULL ,
                        password VARCHAR NOT NULL ,
                        name VARCHAR NOT NULL ,
                        email VARCHAR NOT NULL ,
                        gender ENUM('female', 'male'),
                        birth_date DATE, 
                        enabled BOOLEAN
                    )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO user (username, password, name, email, gender, birth_date, enabled) VALUES (:username, :password, :name, :email, :gender, :birthDate, :enabled)")
    @GetGeneratedKeys
    long insertUser(@Bind("username") String username,
                       @Bind("password") String password,
                       @Bind("name") String name,
                       @Bind("email") String email,
                       @Bind("gender") User.Gender gender,
                       @Bind("birth_date") LocalDate birthDate,
                       @Bind("enabled") boolean enabled);

    @SqlUpdate("INSERT INTO user (username, password, name, email, gender, birth_date, enabled) VALUES (:username, :password, :name, :email, :gender, :birthDate, :enabled)")
    @GetGeneratedKeys
    long insertUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user")
    List<User> listUsers();

    @SqlQuery("SELECT * FROM user WHERE id = :id")
    Optional<User> getUserById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM user WHERE username = :username")
    Optional<User> getUserByUsername(@Bind("username") String username);

    @SqlUpdate("DELETE FROM user WHERE id = :u.id")
    void deleteUser(@BindBean("u") User user);
}
