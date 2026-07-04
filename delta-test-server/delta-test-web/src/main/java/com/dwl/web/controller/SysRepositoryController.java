package com.dwl.web.controller;

import com.dwl.common.result.PageResult;
import com.dwl.common.result.R;
import com.dwl.model.dto.RepositoryCreateDTO;
import com.dwl.model.vo.RepositoryVO;
import com.dwl.service.SysRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 仓库管理 控制器
 * Repository Management Controller
 *
 * @author DeltaTest
 */
@RestController
@RequestMapping("/api/system/repositories")
@RequiredArgsConstructor
@Tag(name = "仓库管理", description = "仓库管理 / Repository Management")
public class SysRepositoryController {

    /** 仓库服务 / Repository service */
    private final SysRepositoryService repositoryService;

    @GetMapping("/page")
    @Operation(summary = "分页查询仓库", description = "分页查询仓库 / Page query repositories")
    public R<PageResult<RepositoryVO>> pageRepositories(
            @Parameter(description = "仓库名称 / Repository name") @RequestParam(required = false) String repoName,
            @Parameter(description = "页码 / Page number") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页大小 / Page size") @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(repositoryService.pageRepositories(repoName, pageNum, pageSize));
    }

    @PostMapping
    @Operation(summary = "创建仓库", description = "创建仓库 / Create repository")
    public R<Long> createRepository(@Valid @RequestBody RepositoryCreateDTO dto) {
        return R.ok(repositoryService.createRepository(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新仓库", description = "更新仓库 / Update repository")
    public R<Void> updateRepository(
            @Parameter(description = "仓库ID / Repository ID", required = true) @PathVariable Long id,
            @Valid @RequestBody RepositoryCreateDTO dto) {
        repositoryService.updateRepository(id, dto);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除仓库", description = "删除仓库 / Delete repository")
    public R<Void> deleteRepository(
            @Parameter(description = "仓库ID / Repository ID", required = true) @PathVariable Long id) {
        repositoryService.deleteRepository(id);
        return R.ok();
    }

    @PostMapping("/{id}/webhook-url")
    @Operation(summary = "生成Webhook URL", description = "生成Webhook URL / Generate webhook URL")
    public R<String> generateWebhookUrl(
            @Parameter(description = "仓库ID / Repository ID", required = true) @PathVariable Long id) {
        return R.ok(repositoryService.generateWebhookUrl(id));
    }
}
