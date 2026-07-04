package com.dwl.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dwl.model.entity.AgentConversation;
import org.apache.ibatis.annotations.Mapper;

/**
 * Agent对话记录 / Agent Conversation Mapper 接口
 * Agent对话记录 / Agent Conversation Mapper Interface
 *
 * @author DeltaTest
 */
@Mapper
public interface AgentConversationMapper extends BaseMapper<AgentConversation> {
}
