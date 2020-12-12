package com.fastcampus.java.sample;

import com.fastcampus.java.component.LoginUserAuditorAware;
import com.fastcampus.java.config.JpaConfig;
import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.model.entity.OrderDetail;
import com.fastcampus.java.model.entity.OrderGroup;
import com.fastcampus.java.model.entity.User;
import com.fastcampus.java.model.enumclass.E_OrderType;
import com.fastcampus.java.model.enumclass.E_PaymentType;
import com.fastcampus.java.model.enumclass.E_Status;
import com.fastcampus.java.repository.ItemRepository;
import com.fastcampus.java.repository.OrderDetailRepository;
import com.fastcampus.java.repository.OrderGroupRepository;
import com.fastcampus.java.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DataJpaTest                                                                    // JPA 테스트 관련 컴포넌트만 Import
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 실제 db 사용
@DisplayName("OrderDetailSample 생성")
@Import({JpaConfig.class, LoginUserAuditorAware.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrderDetailSample {

    private Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Test
    public void createOrder(){

        List<User> userList = userRepository.findAll();

        for(int j = 0; j < 1; j++){
            User user = userList.get(j);
            item(user);

        }


        userList.forEach(user -> {

            int orderCount = random.nextInt(10) + 1;
            for (int i = 0; i < orderCount; i++) {
                item(user);
            }
        });


    }


    private void item(User user){
        double totalAmount = 0;

        List<Item> items = new ArrayList<>();
        List<OrderDetail> orderHistoryDetails = new ArrayList<>();


        int itemCount = random.nextInt(10)+1;
        for(int i = 0 ; i < itemCount; i ++){
            // db에 아이템 갯수가 총 500개 ( * 자신의 샘플에 맞추세요 )
            int itemNumber = random.nextInt(405)+1;

            Item item = itemRepository.findById((long)itemNumber).get();
            totalAmount += item.getPrice().doubleValue();
            items.add(item);
        }


        int s = random.nextInt(3)+1;
        E_Status status = E_Status.ORDERING;
        E_PaymentType paymentType = E_PaymentType.BANK_TRANSFER;
        switch (s){
            case 1 :
                status = E_Status.ORDERING;
                paymentType = E_PaymentType.BANK_TRANSFER;
                break;

            case 2 :
                status = E_Status.COMPLETE;
                paymentType = E_PaymentType.CARD;
                break;

            case 3 :
                status = E_Status.CONFIRM;
                paymentType = E_PaymentType.CHECK_CARD;
                break;
        }

        int t = random.nextInt(2)+1;
        E_OrderType type = t==1? E_OrderType.ALL:E_OrderType.EACH;


        OrderGroup orderGroup = OrderGroup.builder()
                .user(user)
                .status(status)
                .orderType(type)
                .revAddress("경기도 분당구 판교역로")
                .revName(user.getEmail())
                .paymentType(paymentType)
                .totalPrice(new BigDecimal(totalAmount))
                .orderAt(getRandomDate())
                .totalQuantity(itemCount)
                .arrivalDate(getRandomDate().plusDays(3))
                .orderDetailList(orderHistoryDetails)
                .build();

        orderGroupRepository.save(orderGroup);



        for(Item item : items){

            E_Status orderDetailStatus = E_Status.ORDERING;
            switch (random.nextInt(3)+1){
                case 1 :
                    orderDetailStatus = E_Status.ORDERING;
                    break;

                case 2 :
                    orderDetailStatus = E_Status.COMPLETE;
                    break;

                case 3 :
                    orderDetailStatus = E_Status.CONFIRM;
                    break;
            }


            OrderDetail orderDetail = OrderDetail.builder()
                    .orderGroup(orderGroup)
                    .item(item)
                    .totalPrice(new BigDecimal(totalAmount))
                    .quantity(itemCount)
                    .arrivalDate(type.equals(E_OrderType.ALL) ? orderGroup.getArrivalDate() : getRandomDate())
                    .status(type.equals(E_OrderType.ALL) ? status :orderDetailStatus)
                    .build();
            orderDetailRepository.save(orderDetail);
            orderHistoryDetails.add(orderDetail);
        }


    }


    private LocalDateTime getRandomDate(){
        return LocalDateTime.of(2019,getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber());
    }

    private int getRandomNumber(){
        return random.nextInt(11)+1;
    }
}
