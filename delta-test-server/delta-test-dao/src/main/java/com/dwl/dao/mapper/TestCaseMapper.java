package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.TestCase;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试用例 / Test Case Mapper 接口
 * 测试用例 / Test Case Mapper Interface
 *
 * @author DeltaTest
 */
@Mapper
public interface TestCaseMapper extends BaseMapper<TestCase> {
}
