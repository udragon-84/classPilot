package com.classpilot.service.lecture.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.classpilot.common.dto.PaginationDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "수강정보 조회 및 정렬 Domain", name = "LectureSearchCriteriaDto")
public class LectureSearchCriteriaDto extends PaginationDto {

    @Schema(description = "수강정보", example = "너나위의 내집마련 기초반")
    private String lectureName;

    @Schema(description = "정렬 기준 DESC", example = "{createdAt Or currentStudentCnt Or enrollmentRate}")
    private String sortBy;

    @Schema(description = "정렬 속성", example = "Map")
    private static final Map<String, Sort> SORT_OPTIONS = new HashMap<>();
    static {
        SORT_OPTIONS.put("createdAt", Sort.by(Sort.Direction.DESC, "createdAt")); // 최근 등록 순
        SORT_OPTIONS.put("currentStudentCnt", Sort.by(Sort.Direction.DESC, "currentStudentCnt")); // 신청자 많은 순
        SORT_OPTIONS.put("enrollmentRate", Sort.by(Sort.Direction.DESC, "enrollmentRate")); // 신청률 높은 순
    }

    public Sort getSort() {
        return Optional.ofNullable(SORT_OPTIONS.get(this.sortBy))
                .orElse(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Pageable getPageable() {
        return PageRequest.of(this.getPage() - 1, this.getSize(), this.getSort());
    }
}
