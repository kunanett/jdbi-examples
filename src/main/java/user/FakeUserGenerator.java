package user;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class FakeUserGenerator {

    private final Faker faker;

    public FakeUserGenerator(Locale locale){
        this.faker = new Faker(locale);
    }

    public User getFakeUser(){

        Name fakeName = faker.name();
        String name = fakeName.fullName();
        String username = fakeName.username();
        String password = faker.internet().password(8, 16, true, true, true);
        String email = faker.internet().emailAddress();
        User.Gender gender = faker.options().option(User.Gender.FEMALE, User.Gender.MALE);
        LocalDate birthDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean enabled = faker.bool().bool();

        return User.builder()
                .name(name)
                .username(username)
                .email(email)
                .enabled(enabled)
                .password(DigestUtils.md5Hex(password))
                .gender(gender)
                .birthDate(birthDate)
                .build();
    }
}
