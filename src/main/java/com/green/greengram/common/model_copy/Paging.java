package com.green.greengram.common.model_copy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
public class Paging {
    private int page;
    private int size;
    @JsonIgnore
    private int startIdx;

    @ConstructorProperties({"page", "size"})
    public Paging(Integer page, Integer size) {
        this.page = (page == null || page == 0) ? 1 : page;
        this.size = (size == null || size == 0) ? 10 : size;
        this.startIdx = (this.page - 1) * this.size;
    }


}