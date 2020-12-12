package com.fastcampus.java.controller.api;

import com.fastcampus.java.controller.CrudController;
import com.fastcampus.java.model.entity.Partner;
import com.fastcampus.java.model.network.Header;
import com.fastcampus.java.model.network.request.PartnerApiRequest;
import com.fastcampus.java.model.network.response.PartnerApiResponse;
import com.fastcampus.java.service.PartnerApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/partner")
public class PartnerApiController extends CrudController<PartnerApiRequest, PartnerApiResponse> {

    @Autowired
    private PartnerApiLogicService partnerApiLogicService;

    @PostConstruct
    public void init() {
        this.baseService = partnerApiLogicService;
    }


}
