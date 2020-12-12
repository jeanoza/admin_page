package com.fastcampus.java.controller;

import com.fastcampus.java.controller.ifs.CrudInterface;
import com.fastcampus.java.model.network.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Component
public abstract class CrudController<Req, Res> implements CrudInterface<Req, Res> {

    protected CrudInterface<Req, Res> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return baseService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return baseService.update(request);
    }

    @Override
    @PutMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return baseService.delete(id);
    }

    @Override
    @GetMapping("")
    public Header<List<Res>> search(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 15) Pageable pageable) {
        log.info("{}", pageable);
        return baseService.search(pageable);
    }
}
