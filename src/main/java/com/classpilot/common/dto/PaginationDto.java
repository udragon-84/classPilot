package com.classpilot.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "페이지 처리 Dto", name = "PaginationDto")
public class PaginationDto {

    @Schema(description = "기본 페이지 1", example = "1")
    protected int page = 1;

    @Schema(description = "화면에 보여질 갯수", example = "20")
    protected int size = 20;

    // 기본 생성자
    public PaginationDto() {}

    // Getters and Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}