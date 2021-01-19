package com.nhn.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

        //DTO리스트
        private List<DTO> dtoList;

        private int totalPage;

        private int page;

        private int size;

        private int start, end; //시작 페이지 번호, 끝 페이지 번호

        private boolean prv, next;

        private List<Integer> pageList; //페이지번호 목록

        public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {   //????
            dtoList = result.stream().map(fn).collect(Collectors.toList());
            totalPage = result.getTotalPages();
            makePageList(result.getPageable());
        }

        private void makePageList(Pageable pageable) {  //????

            this.page = pageable.getPageNumber()+1; //0부터 시작하니까~
            this.size = pageable.getPageSize();

            int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
            start = tempEnd - 9;
            prv = start > 1;
            end = totalPage > tempEnd ? tempEnd : totalPage;
            next = totalPage > tempEnd;

            pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        }
}
