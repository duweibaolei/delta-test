package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.GitCommit;
import org.apache.ibatis.annotations.Mapper;

/**
 * Git提交记录 / Git Commit Record Mapper 接口
 * Git提交记录 / Git Commit Record Mapper Interface
 *
 * @author ByDWL
 */
@Mapper
public interface GitCommitMapper extends BaseMapper<GitCommit> {
}
