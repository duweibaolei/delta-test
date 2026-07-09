package com.dwl.web.controller;

import com.dwl.common.result.PageResult;
import com.dwl.common.result.R;
import com.dwl.model.entity.SysDictData;
import com.dwl.model.entity.SysDictType;
import com.dwl.model.vo.DictDataVO;
import com.dwl.model.vo.DictTypeVO;
import com.dwl.service.SysDictDataService;
import com.dwl.service.SysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理 控制器
 * Dictionary Management Controller
 *
 * @author ByDWL
 */
@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
@Tag(name = "字典管理", description = "字典管理 / Dictionary Management")
public class SysDictController {

    /** 字典类型服务 / Dictionary type service */
    private final SysDictTypeService dictTypeService;

    /** 字典数据服务 / Dictionary data service */
    private final SysDictDataService dictDataService;

    // ==================== 字典类型 / Dictionary Type ====================

    @GetMapping("/dict-types/page")
    @Operation(summary = "分页查询字典类型", description = "分页查询字典类型 / Page query dictionary types")
    public R<PageResult<DictTypeVO>> pageDictTypes(
            @Parameter(description = "字典类型编码 / Dictionary type code") @RequestParam(required = false) String dictType,
            @Parameter(description = "字典类型名称 / Dictionary type name") @RequestParam(required = false) String dictName,
            @Parameter(description = "页码 / Page number") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页大小 / Page size") @RequestParam(defaultValue = "10") int pageSize) {
        return R.ok(dictTypeService.pageDictTypes(dictType, dictName, pageNum, pageSize));
    }

    @GetMapping("/dict-types/{id}")
    @Operation(summary = "查询字典类型详情", description = "查询字典类型详情 / Get dictionary type detail")
    public R<DictTypeVO> getDictType(
            @Parameter(description = "字典类型ID / Dictionary type ID", required = true) @PathVariable Long id) {
        return R.ok(dictTypeService.getDictType(id));
    }

    @PostMapping("/dict-types")
    @Operation(summary = "创建字典类型", description = "创建字典类型 / Create dictionary type")
    public R<Long> createDictType(@RequestBody SysDictType entity) {
        return R.ok(dictTypeService.createDictType(entity));
    }

    @PutMapping("/dict-types")
    @Operation(summary = "更新字典类型", description = "更新字典类型 / Update dictionary type")
    public R<Void> updateDictType(@RequestBody SysDictType entity) {
        dictTypeService.updateDictType(entity);
        return R.ok();
    }

    @DeleteMapping("/dict-types/{id}")
    @Operation(summary = "删除字典类型", description = "删除字典类型 / Delete dictionary type")
    public R<Void> deleteDictType(
            @Parameter(description = "字典类型ID / Dictionary type ID", required = true) @PathVariable Long id) {
        dictTypeService.deleteDictType(id);
        return R.ok();
    }

    // ==================== 字典数据 / Dictionary Data ====================

    @GetMapping("/dict-data/{dictType}")
    @Operation(summary = "根据类型查询字典数据", description = "根据类型查询字典数据 / List dictionary data by type")
    public R<List<DictDataVO>> listDictDataByType(
            @Parameter(description = "字典类型编码 / Dictionary type code", required = true) @PathVariable String dictType) {
        return R.ok(dictDataService.listDictDataByType(dictType));
    }

    @PostMapping("/dict-data")
    @Operation(summary = "创建字典数据", description = "创建字典数据 / Create dictionary data")
    public R<Long> createDictData(@RequestBody SysDictData entity) {
        return R.ok(dictDataService.createDictData(entity));
    }

    @PutMapping("/dict-data")
    @Operation(summary = "更新字典数据", description = "更新字典数据 / Update dictionary data")
    public R<Void> updateDictData(@RequestBody SysDictData entity) {
        dictDataService.updateDictData(entity);
        return R.ok();
    }

    @DeleteMapping("/dict-data/{id}")
    @Operation(summary = "删除字典数据", description = "删除字典数据 / Delete dictionary data")
    public R<Void> deleteDictData(
            @Parameter(description = "字典数据ID / Dictionary data ID", required = true) @PathVariable Long id) {
        dictDataService.deleteDictData(id);
        return R.ok();
    }
}
