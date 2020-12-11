package com.fastcampus.java.repository;

import com.fastcampus.java.JavaApplicationTests;
import com.fastcampus.java.model.entity.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderDetailRepositoryTest extends JavaApplicationTests {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void create(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setStatus("FAIL");
        orderDetail.setArrivalDate(LocalDateTime.now().plusDays(2));
        orderDetail.setQuantity(1);
        orderDetail.setTotalPrice(BigDecimal.valueOf(90000));

        //orderDetail.setOrderGroupId(1L);
        //orderDetail.setItemId(1L);

        orderDetail.setCreatedAt(LocalDateTime.now());
        orderDetail.setCreatedBy("AdminServer");

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);
        assertNotNull(newOrderDetail);
    }

    @Test
    public void delete() {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(4L);
        Assertions.assertTrue(orderDetail.isPresent());

        orderDetail.ifPresent(selectOrderDetail -> {
            orderDetailRepository.delete(selectOrderDetail);
        });

        Optional<OrderDetail> deleteOrderDetail = orderDetailRepository.findById(4L);
        Assertions.assertFalse(deleteOrderDetail.isPresent());

    }

}