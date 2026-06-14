-- ============================================================================
-- 机房设备巡检数字化管控系统 — 完整数据库 DDL
-- Database: patrol_db
-- Engine:   MySQL 8.0+ / InnoDB / utf8mb4
-- Author:   patrol-team
-- Date:     2026-06-14
-- ============================================================================

CREATE DATABASE IF NOT EXISTS patrol_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE patrol_db;

-- ============================================================================
-- 第一部分：系统管理 (6 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. sys_user  用户表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    dept_id     BIGINT       DEFAULT NULL            COMMENT '所属部门ID',
    username    VARCHAR(64)  NOT NULL                COMMENT '用户名（登录账号）',
    password    VARCHAR(256) NOT NULL                COMMENT '密码（BCrypt 加密）',
    mobile      VARCHAR(20)  DEFAULT NULL            COMMENT '手机号',
    real_name   VARCHAR(32)  DEFAULT NULL            COMMENT '真实姓名',
    nickname    VARCHAR(64)  DEFAULT NULL            COMMENT '昵称',
    email       VARCHAR(128) DEFAULT NULL            COMMENT '邮箱',
    avatar      VARCHAR(256) DEFAULT NULL            COMMENT '头像URL',
    role_id     BIGINT       DEFAULT NULL            COMMENT '默认角色ID',
    status      TINYINT      DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_by   VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0               COMMENT '逻辑删除：0=正常，1=已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_mobile (mobile),
    KEY idx_dept_id (dept_id),
    KEY idx_role_id (role_id),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';


-- ----------------------------------------------------------------------------
-- 2. sys_role  角色表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    role_name   VARCHAR(64) NOT NULL                COMMENT '角色名称',
    role_code   VARCHAR(64) NOT NULL                COMMENT '角色编码（ADMIN/INSPECTOR/AUDITOR）',
    sort        INT         DEFAULT 0               COMMENT '排序',
    status      TINYINT     DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_by   VARCHAR(64) DEFAULT NULL            COMMENT '创建人',
    create_time DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64) DEFAULT NULL            COMMENT '更新人',
    update_time DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT     DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code (role_code),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';


-- ----------------------------------------------------------------------------
-- 3. sys_menu  菜单权限表（目录/菜单/按钮三级）
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    parent_id   BIGINT       DEFAULT 0               COMMENT '父菜单ID（0=顶级）',
    name        VARCHAR(64)  NOT NULL                COMMENT '菜单名称',
    path        VARCHAR(256) DEFAULT NULL            COMMENT '前端路由路径',
    component   VARCHAR(256) DEFAULT NULL            COMMENT '前端组件路径',
    perms       VARCHAR(256) DEFAULT NULL            COMMENT '按钮权限标识（如 system:user:create）',
    type        TINYINT      DEFAULT 1               COMMENT '类型：0=目录，1=菜单，2=按钮',
    icon        VARCHAR(64)  DEFAULT NULL            COMMENT '图标',
    sort        INT          DEFAULT 0               COMMENT '排序',
    status      TINYINT      DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_by   VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id),
    KEY idx_type (type),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单/权限表';


-- ----------------------------------------------------------------------------
-- 4. sys_user_role  用户-角色关联表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    KEY idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户-角色关联表';


-- ----------------------------------------------------------------------------
-- 5. sys_role_menu  角色-菜单关联表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id),
    KEY idx_menu_id (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色-菜单关联表';


-- ============================================================================
-- 第二部分：系统字典 (2 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 6. sys_dict_type  字典类型表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    dict_name   VARCHAR(100) NOT NULL                COMMENT '字典名称',
    dict_code   VARCHAR(100) NOT NULL                COMMENT '字典编码',
    status      TINYINT      DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    remark      VARCHAR(500) DEFAULT NULL            COMMENT '备注',
    create_by   VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_dict_code (dict_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';


-- ----------------------------------------------------------------------------
-- 7. sys_dict_data  字典数据表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    dict_code   VARCHAR(100) NOT NULL                COMMENT '关联字典编码',
    label       VARCHAR(100) NOT NULL                COMMENT '字典标签（显示值）',
    value       VARCHAR(100) NOT NULL                COMMENT '字典值（存储值）',
    sort        INT          DEFAULT 0               COMMENT '排序',
    css_class   VARCHAR(100) DEFAULT NULL            COMMENT '样式类名',
    list_class  VARCHAR(100) DEFAULT NULL            COMMENT '表格回显样式',
    is_default  TINYINT      DEFAULT 0               COMMENT '是否默认：0=否，1=是',
    status      TINYINT      DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    remark      VARCHAR(500) DEFAULT NULL            COMMENT '备注',
    create_by   VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_dict_code_value (dict_code, value),
    KEY idx_dict_code (dict_code),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';


-- ============================================================================
-- 第三部分：系统日志 & 文件 (2 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 8. sys_oper_log  操作日志表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log (
    id           BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    request_uri  VARCHAR(256)  DEFAULT NULL            COMMENT '请求URI',
    request_method VARCHAR(10) DEFAULT NULL            COMMENT '请求方法（GET/POST/PUT/DELETE）',
    ip           VARCHAR(64)   DEFAULT NULL            COMMENT '操作IP',
    operator     VARCHAR(64)   DEFAULT NULL            COMMENT '操作人用户名',
    request_args TEXT          DEFAULT NULL            COMMENT '请求参数',
    response_body LONGTEXT     DEFAULT NULL            COMMENT '返回结果',
    cost_time    BIGINT        DEFAULT NULL            COMMENT '耗时（毫秒）',
    oper_status  TINYINT       DEFAULT NULL            COMMENT '操作状态：0=失败，1=成功',
    error_msg    TEXT          DEFAULT NULL            COMMENT '错误信息',
    create_time  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_operator (operator),
    KEY idx_request_uri (request_uri),
    KEY idx_create_time (create_time),
    KEY idx_oper_status (oper_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';


-- ----------------------------------------------------------------------------
-- 9. sys_file  文件表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_file;
CREATE TABLE sys_file (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    original_name   VARCHAR(256) DEFAULT NULL            COMMENT '原始文件名',
    storage_path    VARCHAR(512) NOT NULL                COMMENT '存储路径（MinIO objectName）',
    file_type       VARCHAR(64)  DEFAULT NULL            COMMENT '文件类型（MIME）',
    file_size       BIGINT       DEFAULT 0               COMMENT '文件大小（字节）',
    biz_id          BIGINT       DEFAULT NULL            COMMENT '业务ID',
    biz_type        VARCHAR(64)  DEFAULT NULL            COMMENT '业务类型（如 danger/task/rectify）',
    create_by       VARCHAR(64)  DEFAULT NULL            COMMENT '上传人',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    PRIMARY KEY (id),
    KEY idx_biz (biz_type, biz_id),
    KEY idx_create_by (create_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';


-- ============================================================================
-- 第四部分：巡检计划 & 任务 (2 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 10. patrol_plan  巡检计划表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS patrol_plan;
CREATE TABLE patrol_plan (
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    plan_name    VARCHAR(128) NOT NULL                COMMENT '计划名称',
    description  VARCHAR(512) DEFAULT NULL            COMMENT '计划描述',
    cron_expr    VARCHAR(64)  DEFAULT NULL            COMMENT 'Cron 执行周期',
    start_date   DATE         DEFAULT NULL            COMMENT '计划开始日期',
    end_date     DATE         DEFAULT NULL            COMMENT '计划结束日期',
    dept_id      BIGINT       DEFAULT NULL            COMMENT '负责部门ID',
    status       TINYINT      DEFAULT 1               COMMENT '状态：0=停用，1=启用',
    create_by    VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by    VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted      TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_status (status),
    KEY idx_start_date (start_date),
    KEY idx_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检计划表';


-- ----------------------------------------------------------------------------
-- 11. patrol_task  巡检任务表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS patrol_task;
CREATE TABLE patrol_task (
    id              BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    plan_id         BIGINT   DEFAULT NULL            COMMENT '关联巡检计划ID',
    point_id        BIGINT   DEFAULT NULL            COMMENT '关联巡检点位ID',
    task_name       VARCHAR(128) DEFAULT NULL        COMMENT '任务名称',
    task_date       DATE     DEFAULT NULL            COMMENT '巡检日期',
    status          TINYINT  DEFAULT 0               COMMENT '任务状态：0=待派发，1=待接单，2=巡检中，3=已完成，4=超期',
    plan_start_time DATETIME DEFAULT NULL            COMMENT '计划开始时间',
    plan_end_time   DATETIME DEFAULT NULL            COMMENT '计划结束时间',
    actual_start_time DATETIME DEFAULT NULL          COMMENT '实际开始时间',
    actual_end_time DATETIME  DEFAULT NULL           COMMENT '实际结束时间',
    inspector_id    BIGINT   DEFAULT NULL            COMMENT '巡检员ID（关联sys_user）',
    create_by       VARCHAR(64) DEFAULT NULL         COMMENT '创建人',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by       VARCHAR(64) DEFAULT NULL         COMMENT '更新人',
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT  DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_plan_id (plan_id),
    KEY idx_point_id (point_id),
    KEY idx_task_date (task_date),
    KEY idx_status (status),
    KEY idx_inspector_id (inspector_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检任务表';


-- ============================================================================
-- 第五部分：巡检点位 & 二维码 (2 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 12. patrol_point  巡检点位表（升级自 patrol_location）
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS patrol_point;
CREATE TABLE patrol_point (
    id               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    area_name        VARCHAR(64)  DEFAULT NULL            COMMENT '所属区域',
    device_name      VARCHAR(128) DEFAULT NULL            COMMENT '设备名称',
    device_type      VARCHAR(64)  DEFAULT NULL            COMMENT '设备类型（如空调/UPS/配电柜/服务器）',
    point_code       VARCHAR(64)  NOT NULL                COMMENT '点位编码（唯一，如 P001）',
    location_detail  VARCHAR(256) DEFAULT NULL            COMMENT '详细位置描述',
    qrcode_content   VARCHAR(512) DEFAULT NULL            COMMENT '二维码内容（URL/JSON）',
    qrcode_image_url VARCHAR(512) DEFAULT NULL            COMMENT '二维码图片URL',
    status           TINYINT      DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_by        VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time      DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by        VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time      DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted          TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_point_code (point_code),
    KEY idx_device_type (device_type),
    KEY idx_area_name (area_name),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检点位表';


-- ----------------------------------------------------------------------------
-- 13. patrol_qrcode  二维码表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS patrol_qrcode;
CREATE TABLE patrol_qrcode (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    point_id    BIGINT       NOT NULL                COMMENT '关联巡检点位ID',
    code_value  VARCHAR(128) NOT NULL                COMMENT '二维码编码值（全局唯一）',
    gen_time    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code_value (code_value),
    KEY idx_point_id (point_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检点位二维码表';


-- ============================================================================
-- 第六部分：隐患 & 整改 (2 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 14. danger_record  隐患记录表（升级自 rectify_record）
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS danger_record;
CREATE TABLE danger_record (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    task_id         BIGINT       DEFAULT NULL            COMMENT '关联巡检任务ID',
    point_id        BIGINT       DEFAULT NULL            COMMENT '关联巡检点位ID',
    danger_level    TINYINT      DEFAULT 0               COMMENT '隐患等级：0=一般，1=严重，2=紧急',
    description     TEXT         DEFAULT NULL            COMMENT '隐患描述',
    finder_id       BIGINT       DEFAULT NULL            COMMENT '发现人ID（关联sys_user）',
    photo_urls      TEXT         DEFAULT NULL            COMMENT '照片URL列表（JSON数组）',
    status          TINYINT      DEFAULT 0               COMMENT '状态：0=待审核，1=待派单，2=已派单，3=待整改，4=待验收，5=关闭',
    auditor_id      BIGINT       DEFAULT NULL            COMMENT '审核人ID',
    audit_opinion   VARCHAR(512) DEFAULT NULL            COMMENT '审核意见',
    audit_time      DATETIME     DEFAULT NULL            COMMENT '审核时间',
    rectify_order_id BIGINT      DEFAULT NULL            COMMENT '生成的整改工单ID',
    create_by       VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by       VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_task_id (task_id),
    KEY idx_point_id (point_id),
    KEY idx_danger_level (danger_level),
    KEY idx_status (status),
    KEY idx_finder_id (finder_id),
    KEY idx_rectify_order_id (rectify_order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='隐患记录表';


-- ----------------------------------------------------------------------------
-- 15. rectify_order  整改工单表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS rectify_order;
CREATE TABLE rectify_order (
    id               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    danger_id        BIGINT       NOT NULL                COMMENT '关联隐患记录ID',
    rectifier_id     BIGINT       DEFAULT NULL            COMMENT '整改人ID（关联sys_user）',
    deadline         DATETIME     DEFAULT NULL            COMMENT '整改期限',
    actual_finish_time DATETIME   DEFAULT NULL            COMMENT '实际完成时间',
    rectify_measure  TEXT         DEFAULT NULL            COMMENT '整改措施',
    rectify_photos   TEXT         DEFAULT NULL            COMMENT '整改照片URL列表（JSON数组）',
    status           TINYINT      DEFAULT 0               COMMENT '状态：0=待整改，1=整改中，2=待验收，3=验收通过，4=验收驳回',
    create_by        VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time      DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by        VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time      DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted          TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_danger_id (danger_id),
    KEY idx_rectifier_id (rectifier_id),
    KEY idx_status (status),
    KEY idx_deadline (deadline)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='整改工单表';


-- ============================================================================
-- 第七部分：预警 (2 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 16. warn_rule  预警规则表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS warn_rule;
CREATE TABLE warn_rule (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    rule_name       VARCHAR(128) NOT NULL                COMMENT '规则名称',
    device_type     VARCHAR(64)  DEFAULT NULL            COMMENT '适用设备类型',
    rule_expression TEXT         DEFAULT NULL            COMMENT '规则表达式（JSON，如阈值、条件）',
    alarm_level     TINYINT      DEFAULT 1               COMMENT '告警级别：0=信息，1=警告，2=严重',
    is_enabled      TINYINT      DEFAULT 1               COMMENT '是否启用：0=否，1=是',
    create_by       VARCHAR(64)  DEFAULT NULL            COMMENT '创建人',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by       VARCHAR(64)  DEFAULT NULL            COMMENT '更新人',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT      DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_device_type (device_type),
    KEY idx_is_enabled (is_enabled),
    KEY idx_alarm_level (alarm_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预警规则表';


-- ----------------------------------------------------------------------------
-- 17. warn_record  预警记录表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS warn_record;
CREATE TABLE warn_record (
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    rule_id      BIGINT       DEFAULT NULL            COMMENT '关联预警规则ID',
    task_id      BIGINT       DEFAULT NULL            COMMENT '关联巡检任务ID',
    point_id     BIGINT       DEFAULT NULL            COMMENT '关联巡检点位ID',
    warn_content TEXT         DEFAULT NULL            COMMENT '预警内容',
    status       TINYINT      DEFAULT 0               COMMENT '状态：0=待处理，1=已忽略，2=已派单',
    danger_id    BIGINT       DEFAULT NULL            COMMENT '关联隐患记录ID（派单后回填）',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '预警时间',
    PRIMARY KEY (id),
    KEY idx_rule_id (rule_id),
    KEY idx_task_id (task_id),
    KEY idx_point_id (point_id),
    KEY idx_status (status),
    KEY idx_danger_id (danger_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预警记录表';


-- ============================================================================
-- 第八部分：AI 巡检结果 (1 张表)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 18. ai_inspection_result  AI巡检结果表（升级自 ai_record）
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS ai_inspection_result;
CREATE TABLE ai_inspection_result (
    id               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    task_id          BIGINT       DEFAULT NULL            COMMENT '关联巡检任务ID',
    point_id         BIGINT       DEFAULT NULL            COMMENT '关联巡检点位ID',
    original_image_url VARCHAR(512) DEFAULT NULL          COMMENT '原始图片URL',
    recognition_json LONGTEXT     DEFAULT NULL            COMMENT '识别结论（JSON：设备状态/异常类型/置信度等）',
    is_abnormal      TINYINT      DEFAULT 0               COMMENT '是否异常：0=正常，1=异常',
    confidence       DOUBLE       DEFAULT NULL            COMMENT '置信度（0~1）',
    model_name       VARCHAR(64)  DEFAULT NULL            COMMENT '使用的AI模型名称',
    status           TINYINT      DEFAULT 1               COMMENT '状态：0=处理中，1=已完成，2=失败',
    create_time      DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_task_id (task_id),
    KEY idx_point_id (point_id),
    KEY idx_is_abnormal (is_abnormal),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI巡检结果表';


-- ============================================================================
-- 第九部分：种子数据
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 管理员账号：admin / admin123
-- BCrypt hash: $2a$10$ewkCwe/oF78BLeHgjlYsHOBEL7KtWoc4KfEgMdnYheAtSbZcVRqES
-- ----------------------------------------------------------------------------
INSERT INTO sys_user (id, username, password, real_name, nickname, mobile, status, create_by, create_time) VALUES
(1, 'admin', '$2a$10$ewkCwe/oF78BLeHgjlYsHOBEL7KtWoc4KfEgMdnYheAtSbZcVRqES',
 '系统管理员', '管理员', '13800000000', 1, 'SYSTEM', NOW());

-- ----------------------------------------------------------------------------
-- 角色
-- ----------------------------------------------------------------------------
INSERT INTO sys_role (id, role_name, role_code, sort, status, create_by, create_time) VALUES
(1, '管理员',   'ADMIN',     1, 1, 'SYSTEM', NOW()),
(2, '巡检员',   'INSPECTOR', 2, 1, 'SYSTEM', NOW()),
(3, '审核员',   'AUDITOR',   3, 1, 'SYSTEM', NOW());

-- ----------------------------------------------------------------------------
-- 菜单权限（三层结构：目录/菜单/按钮）
-- ----------------------------------------------------------------------------
-- 一级目录
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1,  0, '系统管理', '/system',   NULL,             NULL,               0, 'Setting',    1, 1, 'SYSTEM', NOW()),
(2,  0, '巡检管理', '/patrol',   NULL,             NULL,               0, 'Monitor',    2, 1, 'SYSTEM', NOW()),
(3,  0, '预警管理', '/warn',     NULL,             NULL,               0, 'Bell',       3, 1, 'SYSTEM', NOW()),
(4,  0, 'AI 分析',  '/ai',       NULL,             NULL,               0, 'Cpu',        4, 1, 'SYSTEM', NOW());

-- 系统管理 > 二级菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(10, 1, '用户管理', '/system/user',  'system/user/index',  'system:user:list',   1, 'User',   1, 1, 'SYSTEM', NOW()),
(11, 1, '角色管理', '/system/role',  'system/role/index',  'system:role:list',   1, 'Avatar', 2, 1, 'SYSTEM', NOW()),
(12, 1, '菜单管理', '/system/menu',  'system/menu/index',  'system:menu:list',   1, 'Menu',   3, 1, 'SYSTEM', NOW()),
(13, 1, '字典管理', '/system/dict',  'system/dict/index',  'system:dict:list',   1, 'Notebook',4, 1, 'SYSTEM', NOW()),
(14, 1, '操作日志', '/system/log',   'system/log/index',   'system:log:list',    1, 'Document',5, 1, 'SYSTEM', NOW());

-- 巡检管理 > 二级菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(20, 2, '巡检计划',  '/patrol/plan',  'patrol/plan/index',  'patrol:plan:list',   1, 'Calendar', 1, 1, 'SYSTEM', NOW()),
(21, 2, '巡检任务',  '/patrol/task',  'patrol/task/index',  'patrol:task:list',   1, 'Clock',   2, 1, 'SYSTEM', NOW()),
(22, 2, '巡检点位',  '/patrol/point', 'patrol/point/index', 'patrol:point:list',  1, 'Location',3, 1, 'SYSTEM', NOW()),
(23, 2, '隐患记录',  '/patrol/danger','patrol/danger/index','patrol:danger:list', 1, 'Warning', 4, 1, 'SYSTEM', NOW()),
(24, 2, '整改工单',  '/patrol/rectify','patrol/rectify/index','patrol:rectify:list',1, 'Edit',   5, 1, 'SYSTEM', NOW());

-- 预警管理 > 二级菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(30, 3, '预警规则', '/warn/rule',   'warn/rule/index',   'warn:rule:list',   1, 'SetUp',  1, 1, 'SYSTEM', NOW()),
(31, 3, '预警记录', '/warn/record', 'warn/record/index', 'warn:record:list', 1, 'Tickets',2, 1, 'SYSTEM', NOW());

-- AI 分析 > 二级菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(40, 4, 'AI 巡检结果', '/ai/result', 'ai/result/index', 'ai:result:list', 1, 'DataLine', 1, 1, 'SYSTEM', NOW());

-- 按钮权限（示例：用户管理）
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(100, 10, '新增用户', NULL, NULL, 'system:user:create', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(101, 10, '修改用户', NULL, NULL, 'system:user:update', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(102, 10, '删除用户', NULL, NULL, 'system:user:delete', 2, NULL, 3, 1, 'SYSTEM', NOW()),
(103, 10, '导出用户', NULL, NULL, 'system:user:export', 2, NULL, 4, 1, 'SYSTEM', NOW()),
(104, 10, '重置密码', NULL, NULL, 'system:user:resetPwd',2,NULL, 5, 1, 'SYSTEM', NOW());

-- 按钮权限（巡检计划）
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(200, 20, '新增计划', NULL, NULL, 'patrol:plan:create', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(201, 20, '修改计划', NULL, NULL, 'patrol:plan:update', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(202, 20, '删除计划', NULL, NULL, 'patrol:plan:delete', 2, NULL, 3, 1, 'SYSTEM', NOW()),
(203, 20, '生成任务', NULL, NULL, 'patrol:plan:genTask',2, NULL, 4, 1, 'SYSTEM', NOW());

-- 按钮权限（巡检任务）
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(210, 21, '新增任务', NULL, NULL, 'patrol:task:create', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(211, 21, '派发任务', NULL, NULL, 'patrol:task:dispatch',2,NULL, 2, 1, 'SYSTEM', NOW()),
(212, 21, '接单',     NULL, NULL, 'patrol:task:accept',  2, NULL, 3, 1, 'SYSTEM', NOW()),
(213, 21, '开始巡检', NULL, NULL, 'patrol:task:start',   2, NULL, 4, 1, 'SYSTEM', NOW()),
(214, 21, '完成巡检', NULL, NULL, 'patrol:task:complete', 2, NULL, 5, 1, 'SYSTEM', NOW());

-- 按钮权限（隐患记录）
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(220, 23, '审核隐患', NULL, NULL, 'patrol:danger:audit',  2, NULL, 1, 1, 'SYSTEM', NOW()),
(221, 23, '生成工单', NULL, NULL, 'patrol:danger:genOrder',2, NULL, 2, 1, 'SYSTEM', NOW()),
(222, 23, '关闭隐患', NULL, NULL, 'patrol:danger:close',  2, NULL, 3, 1, 'SYSTEM', NOW());

-- 按钮权限（整改工单）
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(230, 24, '开始整改', NULL, NULL, 'patrol:rectify:start',   2, NULL, 1, 1, 'SYSTEM', NOW()),
(231, 24, '提交整改', NULL, NULL, 'patrol:rectify:submit',  2, NULL, 2, 1, 'SYSTEM', NOW()),
(232, 24, '验收通过', NULL, NULL, 'patrol:rectify:accept',  2, NULL, 3, 1, 'SYSTEM', NOW()),
(233, 24, '验收驳回', NULL, NULL, 'patrol:rectify:reject',  2, NULL, 4, 1, 'SYSTEM', NOW());

-- ----------------------------------------------------------------------------
-- 用户-角色关联（admin → 管理员）
-- ----------------------------------------------------------------------------
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- ----------------------------------------------------------------------------
-- 角色-菜单关联
-- ----------------------------------------------------------------------------
-- 管理员拥有全部权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu WHERE status = 1;

-- 巡检员权限：巡检计划（查看）、巡检任务（查看/接单/开始/完成）、巡检点位（查看）、隐患记录（查看/创建）、整改工单（查看/开始/提交）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 2, id FROM sys_menu WHERE perms IN (
    'patrol:plan:list',
    'patrol:task:list', 'patrol:task:accept', 'patrol:task:start', 'patrol:task:complete',
    'patrol:point:list',
    'patrol:danger:list', 'patrol:danger:create',
    'patrol:rectify:list', 'patrol:rectify:start', 'patrol:rectify:submit'
);

-- 审核员权限：巡检计划（查看）、巡检任务（查看）、巡检点位（查看）、隐患记录（查看/审核/生成工单/关闭）、整改工单（查看/验收通过/验收驳回）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 3, id FROM sys_menu WHERE perms IN (
    'patrol:plan:list',
    'patrol:task:list',
    'patrol:point:list',
    'patrol:danger:list', 'patrol:danger:audit', 'patrol:danger:genOrder', 'patrol:danger:close',
    'patrol:rectify:list', 'patrol:rectify:accept', 'patrol:rectify:reject'
);

-- ============================================================================
-- 字典种子数据
-- ============================================================================
INSERT INTO sys_dict_type (id, dict_name, dict_code, status, create_by, create_time) VALUES
(1, '设备类型',   'device_type',   1, 'SYSTEM', NOW()),
(2, '隐患等级',   'danger_level',  1, 'SYSTEM', NOW()),
(3, '任务状态',   'task_status',   1, 'SYSTEM', NOW()),
(4, '隐患状态',   'danger_status', 1, 'SYSTEM', NOW()),
(5, '工单状态',   'order_status',  1, 'SYSTEM', NOW()),
(6, '预警级别',   'alarm_level',   1, 'SYSTEM', NOW());

INSERT INTO sys_dict_data (id, dict_code, label, value, sort, status, create_by, create_time) VALUES
-- 设备类型
(1,  'device_type', '空调',    'AC',       1, 1, 'SYSTEM', NOW()),
(2,  'device_type', 'UPS',     'UPS',      2, 1, 'SYSTEM', NOW()),
(3,  'device_type', '配电柜',  'PDC',      3, 1, 'SYSTEM', NOW()),
(4,  'device_type', '服务器',  'SERVER',   4, 1, 'SYSTEM', NOW()),
(5,  'device_type', '网络设备','NETWORK',  5, 1, 'SYSTEM', NOW()),
-- 隐患等级
(10, 'danger_level', '一般',   '0', 1, 1, 'SYSTEM', NOW()),
(11, 'danger_level', '严重',   '1', 2, 1, 'SYSTEM', NOW()),
(12, 'danger_level', '紧急',   '2', 3, 1, 'SYSTEM', NOW()),
-- 任务状态
(20, 'task_status', '待派发',  '0', 1, 1, 'SYSTEM', NOW()),
(21, 'task_status', '待接单',  '1', 2, 1, 'SYSTEM', NOW()),
(22, 'task_status', '巡检中',  '2', 3, 1, 'SYSTEM', NOW()),
(23, 'task_status', '已完成',  '3', 4, 1, 'SYSTEM', NOW()),
(24, 'task_status', '超期',    '4', 5, 1, 'SYSTEM', NOW()),
-- 预警级别
(30, 'alarm_level',  '信息',   '0', 1, 1, 'SYSTEM', NOW()),
(31, 'alarm_level',  '警告',   '1', 2, 1, 'SYSTEM', NOW()),
(32, 'alarm_level',  '严重',   '2', 3, 1, 'SYSTEM', NOW());

-- ============================================================================
-- 完成
-- 管理员登录: admin / admin123
-- 口令如需变更，使用 BCryptPasswordEncoder 重新生成后替换
-- ============================================================================
