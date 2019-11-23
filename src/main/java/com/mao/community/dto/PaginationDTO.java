package com.mao.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private Integer page;
    private boolean showFirstPage;
    private boolean showPrevious;
    private boolean showEndPage;
    private boolean showNext;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalpage;

    public void setPagination(Integer totalcount, Integer page, Integer size) {
        this.page = page;

        if (totalcount % size == 0) {
            totalpage = totalcount / size;
        } else {
            totalpage = totalcount / size + 1;
        }

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalpage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否展示下一页
        if (page == totalpage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalpage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
