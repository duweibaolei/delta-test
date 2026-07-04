package com.dwl.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 自动填充处理器
 * MyBatis-Plus Auto-Fill Meta Object Handler
 * <p>
 * 自动填充数据库实体的审计字段（createdAt、updatedAt）。
 * Automatically fills audit fields (createdAt, updatedAt) of database entities.
 * </p>
 *
 * @author DeltaTest
 */
@Slf4j
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时自动填充
     * Auto-fill on insert
     * <p>
     * 当执行插入操作时，自动设置createdAt和updatedAt字段为当前时间。
     * When performing an insert operation, automatically sets the createdAt
     * and updatedAt fields to the current time.
     * </p>
     *
     * @param metaObject 元对象 / Meta object
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("MyBatis-Plus 自动填充（插入）/ MyBatis-Plus auto-fill (insert)");

        // 填充创建时间 / Fill creation time
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());

        // 填充更新时间 / Fill update time
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 更新时自动填充
     * Auto-fill on update
     * <p>
     * 当执行更新操作时，自动设置updatedAt字段为当前时间。
     * When performing an update operation, automatically sets the updatedAt
     * field to the current time.
     * </p>
     *
     * @param metaObject 元对象 / Meta object
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("MyBatis-Plus 自动填充（更新）/ MyBatis-Plus auto-fill (update)");

        // 填充更新时间 / Fill update time
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
    }
}
