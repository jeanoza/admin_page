package com.fastcampus.java.repository;

import com.fastcampus.java.JavaApplicationTests;
import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.model.entity.User;
import com.fastcampus.java.model.enumclass.E_Status;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;



public class UserRepositoryTest extends JavaApplicationTests {

    /**
     * Dependency Injection (DI)줌
     * 원래는 사용자가 UserRepository(Interface)의 구현체(UserRepositoryImpl)를 만들어 객체생성을 해야되나
     *
     * @Autowired를 Repository(Interface) 변수 위에 적어주면 스프링부트가 객체를 대신 생성해줌
     * 해당 Interface의 메서드까지 구현할 수 있음
     */
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        String account = "choikb916";
        String password = "07449945";
        E_Status status = E_Status.REGISTERED;
        String email = "choikb916@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);

        User u = User.builder()
                .account(account)
                .password(password)
                .phoneNumber(phoneNumber)
                .status(status)
                .email(email)
                .registeredAt(registeredAt)
                .build();

        User newUser = userRepository.save(u);

        Assertions.assertNotNull(newUser);

    }

    @Test
    @Transactional
    public void read() {

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010111122222");
        user.getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("=============ma commande==========");
            System.out.println("Recepteur : " + orderGroup.getRevName());
            System.out.println("Address : " + orderGroup.getRevAddress());
            System.out.println("Total Price : " + orderGroup.getTotalPrice());
            System.out.println("Total Quantity : " + orderGroup.getTotalQuantity());
            System.out.println("================detail===========");
            orderGroup.getOrderDetailList().forEach(orderDetail -> {
                System.out.println("Name of partner : "+ orderDetail.getItem().getPartner().getName());
                System.out.println("Category of partner : "+ orderDetail.getItem().getPartner().getCategory().getTitle());
                System.out.println("Goods :"+ orderDetail.getItem().getName());
                System.out.println("Call Center Numero : "+orderDetail.getItem().getPartner().getCallCenter());
                System.out.println("status :" + orderDetail.getStatus());
                System.out.println("Date prevue :" + orderDetail.getArrivalDate());

                orderDetail.getItem().getName();
            });
        });
        Assertions.assertNotNull(user);

    }

    @Test
    public void update() {
        User user = userRepository.findById(2L).get();
        user.setAccount("CHANGED")
            .setPassword("1234");
        userRepository.save(user);
    }

    @Test
    @Transactional
    public void delete() {
        Optional<User> user = userRepository.findById(6L);

        Assertions.assertTrue(user.isPresent());

        user.ifPresent(selectUser-> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(6L);
        Assertions.assertFalse(deleteUser.isPresent());
    }


}

