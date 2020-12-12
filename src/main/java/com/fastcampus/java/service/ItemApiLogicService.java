package com.fastcampus.java.service;

import com.fastcampus.java.controller.ifs.CrudInterface;
import com.fastcampus.java.model.entity.Item;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.Pagination;
import com.fastcampus.java.model.network.request.ItemApiRequest;
import com.fastcampus.java.model.network.response.ItemApiResponse;
import com.fastcampus.java.repository.ItemRepository;
import com.fastcampus.java.repository.PartnerRepository;
import com.fastcampus.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService implements CrudInterface<ItemApiRequest, ItemApiResponse>{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();

        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .content(body.getContent())
                .title(body.getTitle())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = itemRepository.save(item);

        return Header.OK(response(newItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {

        return itemRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();
        return itemRepository.findById(body.getId())
                .map(entityItem ->
                    entityItem
                            .setStatus(body.getStatus())
                            .setName(body.getName())
                            .setTitle(body.getTitle())
                            .setContent(body.getContent())
                            .setPrice(body.getPrice())
                            .setBrandName(body.getBrandName())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setUnregisteredAt(body.getUnregisteredAt())
                )
                .map(newEntityItem -> itemRepository.save(newEntityItem))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    itemRepository.delete(item);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {

        Page<Item> items = itemRepository.findAll(pageable);

        //1st
        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(this::response)
                .collect(Collectors.toList());

        //2nd
        Pagination pagination = Pagination.builder()
                .currentElements(items.getNumberOfElements())
                .currentPage(items.getNumber())
                .totalElements(items.getTotalElements())
                .totalPages(items.getTotalPages())
                .build();


        return Header.OK(itemApiResponseList, pagination);
    }

    private ItemApiResponse response(Item item) {

        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return body;
    }
}
