package com.green.greengram.feed.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PicsDto {
    private long feedId;
    @Builder.Default
    private List<String> pics = new ArrayList<>();
}
