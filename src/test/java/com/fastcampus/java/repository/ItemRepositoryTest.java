package com.fastcampus.java.repository;

import com.fastcampus.java.JavaApplicationTests;
import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.model.enumclass.E_Status;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ItemRepositoryTest extends JavaApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {

        Item item = new Item();
        item.setStatus(E_Status.UNREGISTERED);
        item.setName("Samsung NoteBook");
        item.setTitle("Samsung NT-100");
        item.setContent("Model 2019");
        item.setPrice(BigDecimal.valueOf(90000));
        item.setBrandName("Samsung");

        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("Partner01");
        //item.setPartnerId(1L);

        Item newItem = itemRepository.save(item);
        assertNotNull(newItem);
    }

    @Test
    public void read() {

        Long id = 1L;
        Optional<Item> item =itemRepository.findById(id);

        Assertions.assertTrue(item.isPresent());

        /** print
        item.ifPresent(i -> {
            System.out.println(i);
        });
         */



    }

}