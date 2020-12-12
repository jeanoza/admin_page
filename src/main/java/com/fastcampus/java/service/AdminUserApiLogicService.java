package com.fastcampus.java.service;

import com.fastcampus.java.controller.ifs.CrudInterface;
import com.fastcampus.java.model.entity.AdminUser;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.Pagination;
import com.fastcampus.java.model.network.request.AdminUserApiRequest;
import com.fastcampus.java.model.network.response.AdminUserApiResponse;
import com.fastcampus.java.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserApiLogicService implements CrudInterface<AdminUserApiRequest, AdminUserApiResponse> {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest body = request.getData();
        AdminUser adminUser = AdminUser.builder()
                .account(body.getAccount())
                .password(body.getPassword())
                .status(body.getStatus())
                .role(body.getRole())
                .lastLoginAt(body.getLastLoginAt())
                .passwordUpdatedAt(body.getPasswordUpdatedAt())
                .loginFailCount(body.getLoginFailCount())
                .registeredAt(LocalDateTime.now())
                .unregisteredAt(body.getUnregisteredAt())
                .build();
        AdminUser newAdminUser = adminUserRepository.save(adminUser);

        return Header.OK(response(newAdminUser));
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        return adminUserRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest body = request.getData();
        return adminUserRepository.findById(body.getId())
                .map(adminUser -> adminUser
                        .setId(body.getId())
                        .setAccount(body.getAccount())
                        .setPassword(body.getPassword())
                        .setStatus(body.getStatus())
                        .setRole(body.getRole())
                        .setLastLoginAt(body.getLastLoginAt())
                        .setPasswordUpdatedAt(body.getPasswordUpdatedAt())
                        .setLoginFailCount(body.getLoginFailCount())
                        .setRegisteredAt(body.getRegisteredAt())
                        .setUnregisteredAt(body.getUnregisteredAt())
                )
                .map(updatedAdminUser -> adminUserRepository.save(updatedAdminUser))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return adminUserRepository.findById(id)
                .map(adminUser-> {
                    adminUserRepository.delete(adminUser);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<AdminUserApiResponse>> search(Pageable pageable) {
        Page<AdminUser> adminUsers = adminUserRepository.findAll(pageable);

        //1st
        List<AdminUserApiResponse> adminUserApiResponseList = adminUsers.stream()
                .map(this::response)
                .collect(Collectors.toList());

        //2nd
        Pagination pagination = Pagination.builder()
                .currentElements(adminUsers.getNumberOfElements())
                .currentPage(adminUsers.getNumber())
                .totalElements(adminUsers.getTotalElements())
                .totalPages(adminUsers.getTotalPages())
                .build();

        return Header.OK(adminUserApiResponseList, pagination);
    }

    private AdminUserApiResponse response(AdminUser adminUser) {
        AdminUserApiResponse body = AdminUserApiResponse.builder()
                .id(adminUser.getId())
                .account(adminUser.getAccount())
                .password(adminUser.getPassword())
                .status(adminUser.getStatus())
                .role(adminUser.getRole())
                .lastLoginAt(adminUser.getLastLoginAt())
                .passwordUpdatedAt(adminUser.getPasswordUpdatedAt())
                .loginFailCount(adminUser.getLoginFailCount())
                .registeredAt(adminUser.getRegisteredAt())
                .unregisteredAt(adminUser.getUnregisteredAt())
                .build();
        return body;
    }

}


