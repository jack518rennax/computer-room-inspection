-- ============================================================================
-- 机房巡检系统 (patrol-backend) 数据库初始化脚本
-- Database: patrol_db
-- MySQL 8.0+
-- ============================================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS patrol_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE patrol_db;

-- ============================================================================
-- 1. 系统管理表
-- ============================================================================

-- 1.1 系统用户表
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_role_menu;
DROP TABLE IF EXISTS sys_user;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_menu;

CREATE TABLE sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    username    VARCHAR(64)  NOT NULL                COMMENT '用户名',
    password    VARCHAR(256) NOT NULL                COMMENT '密码（BCrypt）',
    nickname    VARCHAR(64)  DEFAULT NULL            COMMENT '昵称',
    email       VARCHAR(128) DEFAULT NULL            COMMENT '邮箱',
    phone       VARCHAR(32)  DEFAULT NULL            COMMENT '手机号',
    avatar      VARCHAR(256) DEFAULT NULL            COMMENT '头像',
    status      INT          DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT          DEFAULT 0               COMMENT '逻辑删除：0=正常，1=已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 1.2 系统角色表
CREATE TABLE sys_role (
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(64) NOT NULL                COMMENT '角色名称',
    code        VARCHAR(64) NOT NULL                COMMENT '角色编码',
    sort        INT         DEFAULT 0               COMMENT '排序',
    status      INT         DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_time DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT         DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

-- 1.3 系统菜单/权限表
CREATE TABLE sys_menu (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    parent_id   BIGINT       DEFAULT 0               COMMENT '父菜单ID',
    name        VARCHAR(64)  NOT NULL                COMMENT '菜单名称',
    path        VARCHAR(256) DEFAULT NULL            COMMENT '路由路径',
    component   VARCHAR(256) DEFAULT NULL            COMMENT '前端组件',
    perms       VARCHAR(256) DEFAULT NULL            COMMENT '权限标识（如 system:user:list）',
    type        INT          DEFAULT 1               COMMENT '类型：0=目录，1=菜单，2=按钮',
    icon        VARCHAR(64)  DEFAULT NULL            COMMENT '图标',
    sort        INT          DEFAULT 0               COMMENT '排序',
    status      INT          DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT          DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单/权限表';

-- 1.4 用户-角色关联表
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    KEY idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户-角色关联表';

-- 1.5 角色-菜单关联表
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id),
    KEY idx_menu_id (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色-菜单关联表';

-- ============================================================================
-- 2. 巡检任务表
-- ============================================================================
CREATE TABLE patrol_task (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name            VARCHAR(128) NOT NULL                COMMENT '任务名称',
    description     VARCHAR(512) DEFAULT NULL            COMMENT '任务描述',
    location_id     BIGINT       DEFAULT NULL            COMMENT '关联点位ID',
    cron_expression VARCHAR(64)  DEFAULT NULL            COMMENT 'Cron表达式',
    status          INT          DEFAULT 0               COMMENT '任务状态：0=停止，1=运行',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         INT          DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_status (status),
    KEY idx_location_id (location_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检任务表';

-- ============================================================================
-- 3. 巡检点位表
-- ============================================================================
CREATE TABLE patrol_location (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(128) NOT NULL                COMMENT '点位名称',
    code        VARCHAR(64)  DEFAULT NULL            COMMENT '点位编码',
    address     VARCHAR(256) DEFAULT NULL            COMMENT '详细地址',
    area        VARCHAR(64)  DEFAULT NULL            COMMENT '所属区域',
    type        VARCHAR(32)  DEFAULT NULL            COMMENT '点位类型',
    description VARCHAR(512) DEFAULT NULL            COMMENT '描述',
    status      INT          DEFAULT 1               COMMENT '状态：0=禁用，1=启用',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT          DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_status (status),
    KEY idx_area (area)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='巡检点位表';

-- ============================================================================
-- 4. 隐患整改记录表
-- ============================================================================
CREATE TABLE rectify_record (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    task_id     BIGINT        DEFAULT NULL            COMMENT '关联任务ID',
    location_id BIGINT        DEFAULT NULL            COMMENT '关联点位ID',
    title       VARCHAR(256)  NOT NULL                COMMENT '问题标题',
    description TEXT          DEFAULT NULL            COMMENT '问题描述',
    severity    VARCHAR(16)   DEFAULT NULL            COMMENT '严重程度',
    status      INT           DEFAULT 0               COMMENT '状态：0=待整改，1=整改中，2=已完成',
    handler     VARCHAR(64)   DEFAULT NULL            COMMENT '处理人',
    result      TEXT          DEFAULT NULL            COMMENT '整改结果',
    create_time DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     INT           DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_task_id (task_id),
    KEY idx_location_id (location_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='隐患整改记录表';

-- ============================================================================
-- 5. AI识别记录表
-- ============================================================================
CREATE TABLE ai_record (
    id              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    task_id         BIGINT        DEFAULT NULL            COMMENT '关联任务ID',
    location_id     BIGINT        DEFAULT NULL            COMMENT '关联点位ID',
    image_url       VARCHAR(512)  DEFAULT NULL            COMMENT '图片URL',
    analysis_result TEXT          DEFAULT NULL            COMMENT '分析结果（JSON）',
    confidence      DOUBLE        DEFAULT NULL            COMMENT '置信度',
    model_name      VARCHAR(64)   DEFAULT NULL            COMMENT '使用的模型名称',
    status          INT           DEFAULT 0               COMMENT '状态：0=处理中，1=已完成，2=失败',
    create_time     DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted         INT           DEFAULT 0               COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_task_id (task_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI识别/分析记录表';

-- ============================================================================
-- 6. 初始化数据
-- ============================================================================

-- 6.1 默认管理员账号
-- 明文密码：admin123
-- BCrypt 哈希：$2a$10$ewkCwe/oF78BLeHgjlYsHOBEL7KtWoc4KfEgMdnYheAtSbZcVRqES
INSERT INTO sys_user (id, username, password, nickname, status, create_time) VALUES
(1, 'admin', '$2a$10$ewkCwe/oF78BLeHgjlYsHOBEL7KtWoc4KfEgMdnYheAtSbZcVRqES', '系统管理员', 1, NOW());

-- 6.2 基础角色
INSERT INTO sys_role (id, name, code, sort, status, create_time) VALUES
(1, '管理员',    'ADMIN', 1, 1, NOW()),
(2, '巡检员',    'INSPECTOR', 2, 1, NOW()),
(3, '普通用户',  'USER', 3, 1, NOW());

-- 6.3 基础菜单/权限
-- 目录
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(1,  0, '系统管理',  '/system',   NULL,       NULL,               0, 'system',    1, 1, NOW()),
(2,  0, '巡检任务',  '/task',     NULL,       NULL,               0, 'task',      2, 1, NOW()),
(3,  0, '巡检点位',  '/location', NULL,       NULL,               0, 'location',  3, 1, NOW()),
(4,  0, '整改管理',  '/rectify',  NULL,       NULL,               0, 'rectify',   4, 1, NOW()),
(5,  0, 'AI分析',    '/ai',       NULL,       NULL,               0, 'ai',        5, 1, NOW());

-- 系统管理子菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(10, 1, '用户列表', '/system/user',  'system/user/index',  'system:user:list',   1, 'user',  1, 1, NOW()),
(11, 1, '角色列表', '/system/role',  'system/role/index',  'system:role:list',   1, 'role',  2, 1, NOW()),
(12, 1, '菜单列表', '/system/menu',  'system/menu/index',  'system:menu:list',   1, 'menu',  3, 1, NOW());

-- 系统管理按钮权限
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(100, 10, '新增用户', NULL, NULL, 'system:user:create', 2, NULL, 1, 1, NOW()),
(101, 10, '修改用户', NULL, NULL, 'system:user:update', 2, NULL, 2, 1, NOW()),
(102, 10, '删除用户', NULL, NULL, 'system:user:delete', 2, NULL, 3, 1, NOW()),
(103, 10, '导出用户', NULL, NULL, 'system:user:export', 2, NULL, 4, 1, NOW()),
(110, 11, '新增角色', NULL, NULL, 'system:role:create', 2, NULL, 1, 1, NOW()),
(111, 11, '修改角色', NULL, NULL, 'system:role:update', 2, NULL, 2, 1, NOW()),
(112, 11, '删除角色', NULL, NULL, 'system:role:delete', 2, NULL, 3, 1, NOW());

-- 巡检任务子菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(20, 2, '任务列表', '/task/list',  'task/index',  'task:list',   1, 'list',  1, 1, NOW());

INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(200, 20, '新增任务', NULL, NULL, 'task:create', 2, NULL, 1, 1, NOW()),
(201, 20, '修改任务', NULL, NULL, 'task:update', 2, NULL, 2, 1, NOW()),
(202, 20, '删除任务', NULL, NULL, 'task:delete', 2, NULL, 3, 1, NOW()),
(203, 20, '导入任务', NULL, NULL, 'task:import', 2, NULL, 4, 1, NOW()),
(204, 20, '导出任务', NULL, NULL, 'task:export', 2, NULL, 5, 1, NOW());

-- 巡检点位子菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(30, 3, '点位列表', '/location/list',  'location/index',  'location:list',   1, 'list',  1, 1, NOW());

INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(300, 30, '新增点位', NULL, NULL, 'location:create', 2, NULL, 1, 1, NOW()),
(301, 30, '修改点位', NULL, NULL, 'location:update', 2, NULL, 2, 1, NOW()),
(302, 30, '删除点位', NULL, NULL, 'location:delete', 2, NULL, 3, 1, NOW()),
(303, 30, '导入点位', NULL, NULL, 'location:import', 2, NULL, 4, 1, NOW()),
(304, 30, '导出点位', NULL, NULL, 'location:export', 2, NULL, 5, 1, NOW());

-- 整改管理子菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(40, 4, '整改列表', '/rectify/list',  'rectify/index',  'rectify:list',   1, 'list',  1, 1, NOW());

INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(400, 40, '新增整改', NULL, NULL, 'rectify:create', 2, NULL, 1, 1, NOW()),
(401, 40, '修改整改', NULL, NULL, 'rectify:update', 2, NULL, 2, 1, NOW()),
(402, 40, '删除整改', NULL, NULL, 'rectify:delete', 2, NULL, 3, 1, NOW());

-- AI分析子菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(50, 5, 'AI记录', '/ai/list',  'ai/index',  'ai:list',   1, 'list',  1, 1, NOW());

INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_time) VALUES
(500, 50, '删除记录', NULL, NULL, 'ai:delete', 2, NULL, 1, 1, NOW());

-- 6.4 用户-角色关联（admin → 管理员）
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 6.5 角色-菜单关联（管理员拥有全部权限）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu WHERE status = 1;

-- 巡检员角色权限（任务 + 点位 + 整改的查看）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 2, id FROM sys_menu WHERE perms IN (
    'task:list', 'task:export',
    'location:list', 'location:export',
    'rectify:list',
    'ai:list'
);

-- 普通用户权限（仅查看）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 3, id FROM sys_menu WHERE perms IN (
    'task:list',
    'location:list',
    'rectify:list',
    'ai:list'
);

-- ============================================================================
-- 完成
-- ============================================================================
-- 账号信息
--   用户名: admin
--   密码:   admin123
--   角色:   管理员（拥有全部权限）
--
-- 如需要重新生成 BCrypt 密码哈希，可运行：
--   mvn dependency:copy -Dartifact=org.mindrot:jbcrypt:0.4 -DoutputDirectory=/tmp/bcrypt
--   jshell --class-path /tmp/bcrypt/jbcrypt-0.4.jar
--   > import org.mindrot.jbcrypt.BCrypt;
--   > System.out.println(BCrypt.hashpw("your_password", BCrypt.gensalt(10)));
-- ============================================================================
