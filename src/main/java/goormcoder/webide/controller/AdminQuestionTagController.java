package goormcoder.webide.controller;

import goormcoder.webide.dto.request.QuestionTagCreateDto;
import goormcoder.webide.dto.request.QuestionTagIdsDto;
import goormcoder.webide.dto.request.QuestionTagUpdateDto;
import goormcoder.webide.dto.response.QuestionTagSummaryDto;
import goormcoder.webide.service.QuestionService;
import goormcoder.webide.service.QuestionTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin/Tag", description = "태그 관련 API (어드민 전용)")
public class AdminQuestionTagController {

    private final QuestionService questionService;
    private final QuestionTagService questionTagService;
    
    @PostMapping("/tags")
    @Operation(summary = "태그 생성")
    public ResponseEntity<QuestionTagSummaryDto> createTag(@RequestBody @Valid QuestionTagCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(questionTagService.create(createDto));
    }

    @PatchMapping("/tags/{tagId}")
    @Operation(summary = "태그 수정")
    public ResponseEntity<?> deleteTag(@PathVariable Long tagId, @RequestBody@Valid QuestionTagUpdateDto updateDto) {
        return ResponseEntity.ok(
                questionTagService.update(tagId, updateDto)
        );
    }

    @DeleteMapping("/tags/{tagId}")
    @Operation(summary = "태그 제거")
    public ResponseEntity<?> deleteTag(@PathVariable Long tagId) {
        questionTagService.delete(tagId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "문제에 태그 추가")
    @PostMapping("/questions/{questionId}/tags/{tagId}")
    public ResponseEntity<List<QuestionTagSummaryDto>> addTagToQuestion(@PathVariable Long questionId, @PathVariable Long tagId) {
        return ResponseEntity.ok(
                questionTagService.addTagToQuestion(questionId, tagId)
        );
    }

    @Operation(summary = "문제에서 태그 삭제")
    @DeleteMapping("/questions/{questionId}/tags/{tagId}")
    public ResponseEntity<List<QuestionTagSummaryDto>> removeTagFromQuestion(@PathVariable Long questionId, @PathVariable Long tagId) {
        return ResponseEntity.ok(
                questionTagService.removeTagFromQuestion(questionId, tagId)
        );
    }

    @Operation(summary = "문제의 태그 일괄 수정")
    @PatchMapping("/questions/{questionId}/tags")
    public ResponseEntity<List<QuestionTagSummaryDto>> modifyQuestionTags(
            @PathVariable Long questionId,
            @RequestBody QuestionTagIdsDto tagIdsDto
    ) {
        return ResponseEntity.ok(
                questionTagService.modifyQuestionTags(questionId, tagIdsDto)
        );
    }

}
