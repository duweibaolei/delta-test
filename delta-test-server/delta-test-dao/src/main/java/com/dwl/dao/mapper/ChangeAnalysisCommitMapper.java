package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.ChangeAnalysisCommit;
import org.apache.ibatis.annotations.Mapper;

/**
 * 变更分析-提交关联 / Change Analysis-Commit Association Mapper 接口
 * 变更分析-提交关联 / Change Analysis-Commit Association Mapper Interface
 *
 * @author ByDWL
 */
@Mapper
public interface ChangeAnalysisCommitMapper extends BaseMapper<ChangeAnalysisCommit> {
}
