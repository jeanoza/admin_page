package com.fastcampus.java.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Pagination {

    //프론트엔드에서 페이징처리를 위한 기본 요소
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer currentElements;

}
