package com.fastcampus.java.repository;

import com.fastcampus.java.JavaApplicationTests;
import com.fastcampus.java.model.entity.OrderGroup;
import com.fastcampus.java.model.enumclass.E_OrderType;
import com.fastcampus.java.model.enumclass.E_PaymentType;
import com.fastcampus.java.model.enumclass.E_Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderGroupTest extends JavaApplicationTests {

    @Autowired
    OrderGroupRepository orderGroupRepository;

    @Test
    public void create() {

        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setStatus(E_Status.COMPLETE);
        orderGroup.setOrderType(E_OrderType.ALL);
        orderGroup.setRevAddress("Paris 5e");
        orderGroup.setRevName("Kyubong");
        orderGroup.setPaymentType(E_PaymentType.CARD);
        orderGroup.setTotalPrice(BigDecimal.valueOf(900000));
        orderGroup.setTotalQuantity(1);
        orderGroup.setOrderAt(LocalDateTime.now().minusDays(2));
        orderGroup.setArrivalDate(LocalDateTime.now());
        orderGroup.setCreatedAt(LocalDateTime.now());
        orderGroup.setCreatedBy("AdminServer");
        //orderGroup.setUserId(1L); // -> user

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);
        Assertions.assertNotNull(newOrderGroup);
    }
}
