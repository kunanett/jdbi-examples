package user;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.setSqlLogger(new Slf4JSqlLogger());
        jdbi.installPlugin(new SqlObjectPlugin());
        List<User> users = jdbi.withExtension(UserDao.class, dao -> {
            dao.createTable();
            FakeUserGenerator userGenerator = new FakeUserGenerator(new Locale("hu"));
            for (int i = 0; i < 1000; i++ ){
                User user = userGenerator.getFakeUser();
                dao.insertUser(user);
            }

            User user56 = dao.getUserById(56).get();
            System.out.println("56-os azonsítójú felhasználó:");
            System.out.println(user56);

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

            User test = dao.getUserByUsername(user56.getUsername()).get();
            System.out.println("User keresése felhasználónév segítségével:");
            System.out.println(test);

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");

            dao.deleteUser(test);

            return dao.listUsers();
        });
        System.out.println("A táblában levő felhasználók száma: " + users.size());

    }
}

