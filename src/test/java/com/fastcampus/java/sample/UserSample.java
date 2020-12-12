package com.fastcampus.java.sample;

import com.fastcampus.java.component.LoginUserAuditorAware;
import com.fastcampus.java.config.JpaConfig;
import com.fastcampus.java.model.entity.User;
import com.fastcampus.java.model.enumclass.E_Status;
import com.fastcampus.java.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@DataJpaTest                                                                    // JPA 테스트 관련 컴포넌트만 Import
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 실제 db 사용
@DisplayName("UserSample 생성")
@Import({JpaConfig.class, LoginUserAuditorAware.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserSample {

    private Random random;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void sampleCreate(){

        random = new Random();

        for(int i = 1 ; i < 1001; i++){
            // 가입 상태 랜덤
            int div = (random.nextInt(10)+1) % 2;
            E_Status status = (div == 0 ? E_Status.REGISTERED : E_Status.UNREGISTERED);

            User user = User.builder()
                    .account("TestUser"+i)
                    .password("password"+i)
                    .status(status)
                    .email("TestUser"+i+"@gmail.com")
                    .phoneNumber("010-1111-"+String.format("%04d", i))
                    .registeredAt(getRandomDate())
                    .unregisteredAt(status.equals(E_Status.UNREGISTERED) ? getRandomDate() : null )
                    .build();

            System.out.println(user);
            userRepository.save(user);
        }


    }


    private LocalDateTime getRandomDate(){
        return LocalDateTime.of(2019,getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber());
    }

    private int getRandomNumber(){
        return random.nextInt(11)+1;
    }
}
