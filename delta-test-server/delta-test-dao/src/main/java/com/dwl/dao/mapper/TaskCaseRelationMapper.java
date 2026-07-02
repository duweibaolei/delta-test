package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.TaskCaseRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务-用例关联 / Task-Case Association Mapper 接口
 * 任务-用例关联 / Task-Case Association Mapper Interface
 *
 * @author DeltaTest
 */
@Mapper
public interface TaskCaseRelationMapper extends BaseMapper<TaskCaseRelation> {
}
