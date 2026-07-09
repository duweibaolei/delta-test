package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.EnvVariable;
import org.apache.ibatis.annotations.Mapper;

/**
 * 环境变量 / Environment Variable Mapper 接口
 * 环境变量 / Environment Variable Mapper Interface
 *
 * @author ByDWL
 */
@Mapper
public interface EnvVariableMapper extends BaseMapper<EnvVariable> {
}
