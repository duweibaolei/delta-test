package com.dwl.web.controller;

import com.dwl.common.result.R;
import com.dwl.model.dto.EnvironmentCreateDTO;
import com.dwl.model.vo.EnvironmentVO;
import com.dwl.service.SysEnvironmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 环境管理 控制器
 * Environment Management Controller
 *
 * @author DeltaTest
 */
@RestController
@RequestMapping("/api/system/environments")
@RequiredArgsConstructor
@Tag(name = "环境管理", description = "环境管理 / Environment Management")
public class SysEnvironmentController {

    /** 环境服务 / Environment service */
    private final SysEnvironmentService environmentService;

    @GetMapping
    @Operation(summary = "查询环境列表", description = "查询环境列表 / List environments")
    public R<List<EnvironmentVO>> listEnvironments() {
        return R.ok(environmentService.listEnvironments());
    }

    @PostMapping
    @Operation(summary = "创建环境", description = "创建环境 / Create environment")
    public R<Long> createEnvironment(@Valid @RequestBody EnvironmentCreateDTO dto) {
        return R.ok(environmentService.createEnvironment(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新环境", description = "更新环境 / Update environment")
    public R<Void> updateEnvironment(
            @Parameter(description = "环境ID / Environment ID", required = true) @PathVariable Long id,
            @Valid @RequestBody EnvironmentCreateDTO dto) {
        environmentService.updateEnvironment(id, dto);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除环境", description = "删除环境 / Delete environment")
    public R<Void> deleteEnvironment(
            @Parameter(description = "环境ID / Environment ID", required = true) @PathVariable Long id) {
        environmentService.deleteEnvironment(id);
        return R.ok();
    }
}
