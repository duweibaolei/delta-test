package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.TaskExecution;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务执行记录 / Task Execution Record Mapper 接口
 * 任务执行记录 / Task Execution Record Mapper Interface
 *
 * @author DeltaTest
 */
@Mapper
public interface TaskExecutionMapper extends BaseMapper<TaskExecution> {
}
