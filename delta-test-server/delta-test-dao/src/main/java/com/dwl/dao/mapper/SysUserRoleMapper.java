package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联 / User-Role Association Mapper 接口
 * 用户角色关联 / User-Role Association Mapper Interface
 *
 * @author DeltaTest
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
