package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.CaseTagRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用例标签关联 / Case-Tag Association Mapper 接口
 * 用例标签关联 / Case-Tag Association Mapper Interface
 *
 * @author ByDWL
 */
@Mapper
public interface CaseTagRelationMapper extends BaseMapper<CaseTagRelation> {
}
