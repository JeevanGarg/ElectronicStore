package com.ElectronicStore.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T>
{
    private List<T> content;
    private Integer pageNumber;

    private Integer pageSize;

    private long totalElements;

    private Integer totalPages;

    private boolean lastPage;
}
