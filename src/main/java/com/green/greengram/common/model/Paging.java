package com.green.greengram.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
    private int page;
    private int size;
    @JsonIgnore
    private int startIdx;

    public Paging (Integer page, Integer size) {
        this.page = (page == null || page == 0) ? 1 : page;
        this.size = (size == null || size == 0) ? 10 : size;
        this.startIdx = (this.page - 1) * this.size;
    }
}
