package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.ManualFailureMark;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人工失败标记 / Manual Failure Mark Mapper 接口
 * 人工失败标记 / Manual Failure Mark Mapper Interface
 *
 * @author ByDWL
 */
@Mapper
public interface ManualFailureMarkMapper extends BaseMapper<ManualFailureMark> {
}
