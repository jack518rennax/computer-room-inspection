-- ============================================================================
-- 机房设备巡检数字化管控系统 — 初始化数据脚本
-- Script:   sql/patrol_db_init_data.sql
-- Requires: sql/patrol_db_schema.sql (DDL must be executed first)
-- Database: patrol_db
-- Date:     2026-06-14
-- ============================================================================
-- 默认管理员账号: admin / admin123
-- BCrypt 密文:   $2a$10$ewkCwe/oF78BLeHgjlYsHOBEL7KtWoc4KfEgMdnYheAtSbZcVRqES
-- ============================================================================

USE patrol_db;

-- ============================================================================
-- 第一部分：角色初始化
-- ============================================================================
-- 清理旧数据（按外键依赖顺序）
DELETE FROM sys_role_menu;
DELETE FROM sys_user_role;
DELETE FROM sys_menu;
DELETE FROM sys_role;
DELETE FROM sys_user WHERE username = 'admin';

-- ----------------------------
-- 1.1 系统角色（3 个）
-- ----------------------------
INSERT INTO sys_role (id, role_name, role_code, sort, status, create_by, create_time) VALUES
(1, '超级管理员', 'ADMIN',     1, 1, 'SYSTEM', NOW()),
(2, '巡检员',     'INSPECTOR', 2, 1, 'SYSTEM', NOW()),
(3, '审核员',     'AUDITOR',   3, 1, 'SYSTEM', NOW());


-- ============================================================================
-- 第二部分：管理员用户
-- ============================================================================
-- 明文密码: admin123
-- BCrypt:   $2a$10$ewkCwe/oF78BLeHgjlYsHOBEL7KtWoc4KfEgMdnYheAtSbZcVRqES
INSERT INTO sys_user (id, username, password, real_name, nickname, mobile, role_id, status, create_by, create_time)
VALUES (1, 'admin', '$2a$10$ewkCwe/oF78BLeHgjlYsHOBEL7KtWoc4KfEgMdnYheAtSbZcVRqES',
        '系统管理员', '管理员', '13800000000', 1, 1, 'SYSTEM', NOW());

-- admin → 超级管理员
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);


-- ============================================================================
-- 第三部分：菜单 & 按钮权限（6 个一级目录 → 16 个二级菜单 → 88 个按钮）
-- ============================================================================

-- ============================
-- 3.0 一级目录
-- ============================
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1, 0, '系统管理',   '/system',  NULL,  NULL, 0, 'Setting',  1, 1, 'SYSTEM', NOW()),
(2, 0, '巡检管理',   '/patrol',  NULL,  NULL, 0, 'Monitor',  2, 1, 'SYSTEM', NOW()),
(3, 0, '隐患整改',   '/danger',  NULL,  NULL, 0, 'Warning',  3, 1, 'SYSTEM', NOW()),
(4, 0, '预警管理',   '/warn',    NULL,  NULL, 0, 'Bell',     4, 1, 'SYSTEM', NOW()),
(5, 0, 'AI巡检',     '/ai',      NULL,  NULL, 0, 'Cpu',      5, 1, 'SYSTEM', NOW()),
(6, 0, '文件管理',   '/file',    NULL,  NULL, 0, 'Folder',   6, 1, 'SYSTEM', NOW());

-- ============================
-- 3.1 系统管理 → 二级菜单
-- ============================
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(10, 1, '用户管理', '/system/user', 'system/user/index',   'system:user:list',   1, 'User',     1, 1, 'SYSTEM', NOW()),
(11, 1, '角色管理', '/system/role', 'system/role/index',   'system:role:list',   1, 'Avatar',   2, 1, 'SYSTEM', NOW()),
(12, 1, '菜单管理', '/system/menu', 'system/menu/index',   'system:menu:list',   1, 'Menu',     3, 1, 'SYSTEM', NOW()),
(13, 1, '字典管理', '/system/dict', 'system/dict/index',   'system:dict:list',   1, 'Notebook', 4, 1, 'SYSTEM', NOW()),
(14, 1, '操作日志', '/system/log',  'system/log/index',    'system:log:list',    1, 'Document', 5, 1, 'SYSTEM', NOW());

-- 用户管理 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(100, 10, '用户详情', NULL, NULL, 'system:user:detail',   2, NULL, 1, 1, 'SYSTEM', NOW()),
(101, 10, '新增用户', NULL, NULL, 'system:user:create',   2, NULL, 2, 1, 'SYSTEM', NOW()),
(102, 10, '修改用户', NULL, NULL, 'system:user:update',   2, NULL, 3, 1, 'SYSTEM', NOW()),
(103, 10, '删除用户', NULL, NULL, 'system:user:delete',   2, NULL, 4, 1, 'SYSTEM', NOW()),
(104, 10, '导出用户', NULL, NULL, 'system:user:export',   2, NULL, 5, 1, 'SYSTEM', NOW()),
(105, 10, '重置密码', NULL, NULL, 'system:user:resetPwd', 2, NULL, 6, 1, 'SYSTEM', NOW());

-- 角色管理 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(200, 11, '角色详情', NULL, NULL, 'system:role:detail', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(201, 11, '新增角色', NULL, NULL, 'system:role:create', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(202, 11, '修改角色', NULL, NULL, 'system:role:update', 2, NULL, 3, 1, 'SYSTEM', NOW()),
(203, 11, '删除角色', NULL, NULL, 'system:role:delete', 2, NULL, 4, 1, 'SYSTEM', NOW());

-- 菜单管理 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(300, 12, '菜单详情', NULL, NULL, 'system:menu:detail', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(301, 12, '新增菜单', NULL, NULL, 'system:menu:create', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(302, 12, '修改菜单', NULL, NULL, 'system:menu:update', 2, NULL, 3, 1, 'SYSTEM', NOW()),
(303, 12, '删除菜单', NULL, NULL, 'system:menu:delete', 2, NULL, 4, 1, 'SYSTEM', NOW());

-- 字典管理 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(400, 13, '字典详情', NULL, NULL, 'system:dict:detail', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(401, 13, '新增字典', NULL, NULL, 'system:dict:create', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(402, 13, '修改字典', NULL, NULL, 'system:dict:update', 2, NULL, 3, 1, 'SYSTEM', NOW()),
(403, 13, '删除字典', NULL, NULL, 'system:dict:delete', 2, NULL, 4, 1, 'SYSTEM', NOW());

-- 操作日志 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(500, 14, '日志详情', NULL, NULL, 'system:log:detail', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(501, 14, '删除日志', NULL, NULL, 'system:log:delete', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(502, 14, '导出日志', NULL, NULL, 'system:log:export', 2, NULL, 3, 1, 'SYSTEM', NOW());

-- ============================
-- 3.2 巡检管理 → 二级菜单
-- ============================
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(20, 2, '巡检计划', '/patrol/plan',   'patrol/plan/index',   'patrol:plan:list',   1, 'Calendar', 1, 1, 'SYSTEM', NOW()),
(21, 2, '巡检任务', '/patrol/task',   'patrol/task/index',   'patrol:task:list',   1, 'Clock',    2, 1, 'SYSTEM', NOW()),
(22, 2, '巡检点位', '/patrol/point',  'patrol/point/index',  'patrol:point:list',  1, 'Location', 3, 1, 'SYSTEM', NOW()),
(23, 2, '二维码管理','/patrol/qrcode','patrol/qrcode/index', 'patrol:qrcode:list', 1, 'Picture',  4, 1, 'SYSTEM', NOW());

-- 巡检计划 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(600, 20, '计划详情', NULL, NULL, 'patrol:plan:detail',  2, NULL, 1, 1, 'SYSTEM', NOW()),
(601, 20, '新增计划', NULL, NULL, 'patrol:plan:create',  2, NULL, 2, 1, 'SYSTEM', NOW()),
(602, 20, '修改计划', NULL, NULL, 'patrol:plan:update',  2, NULL, 3, 1, 'SYSTEM', NOW()),
(603, 20, '删除计划', NULL, NULL, 'patrol:plan:delete',  2, NULL, 4, 1, 'SYSTEM', NOW()),
(604, 20, '生成任务', NULL, NULL, 'patrol:plan:genTask', 2, NULL, 5, 1, 'SYSTEM', NOW());

-- 巡检任务 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(700, 21, '任务详情', NULL, NULL, 'patrol:task:detail',   2, NULL,  1, 1, 'SYSTEM', NOW()),
(701, 21, '新增任务', NULL, NULL, 'patrol:task:create',   2, NULL,  2, 1, 'SYSTEM', NOW()),
(702, 21, '修改任务', NULL, NULL, 'patrol:task:update',   2, NULL,  3, 1, 'SYSTEM', NOW()),
(703, 21, '删除任务', NULL, NULL, 'patrol:task:delete',   2, NULL,  4, 1, 'SYSTEM', NOW()),
(704, 21, '派发任务', NULL, NULL, 'patrol:task:dispatch', 2, NULL,  5, 1, 'SYSTEM', NOW()),
(705, 21, '接单',     NULL, NULL, 'patrol:task:accept',   2, NULL,  6, 1, 'SYSTEM', NOW()),
(706, 21, '开始巡检', NULL, NULL, 'patrol:task:start',    2, NULL,  7, 1, 'SYSTEM', NOW()),
(707, 21, '完成巡检', NULL, NULL, 'patrol:task:complete', 2, NULL,  8, 1, 'SYSTEM', NOW()),
(708, 21, '导出任务', NULL, NULL, 'patrol:task:export',   2, NULL,  9, 1, 'SYSTEM', NOW());

-- 巡检点位 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(800, 22, '点位详情', NULL, NULL, 'patrol:point:detail', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(801, 22, '新增点位', NULL, NULL, 'patrol:point:create', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(802, 22, '修改点位', NULL, NULL, 'patrol:point:update', 2, NULL, 3, 1, 'SYSTEM', NOW()),
(803, 22, '删除点位', NULL, NULL, 'patrol:point:delete', 2, NULL, 4, 1, 'SYSTEM', NOW()),
(804, 22, '导出点位', NULL, NULL, 'patrol:point:export', 2, NULL, 5, 1, 'SYSTEM', NOW());

-- 二维码管理 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(900, 23, '二维码详情', NULL, NULL, 'patrol:qrcode:detail',   2, NULL, 1, 1, 'SYSTEM', NOW()),
(901, 23, '生成二维码', NULL, NULL, 'patrol:qrcode:create',   2, NULL, 2, 1, 'SYSTEM', NOW()),
(902, 23, '删除二维码', NULL, NULL, 'patrol:qrcode:delete',   2, NULL, 3, 1, 'SYSTEM', NOW()),
(903, 23, '下载二维码', NULL, NULL, 'patrol:qrcode:download', 2, NULL, 4, 1, 'SYSTEM', NOW());

-- ============================
-- 3.3 隐患整改 → 二级菜单
-- ============================
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(30, 3, '隐患记录', '/danger/record',  'danger/record/index',  'patrol:danger:list',   1, 'Warning', 1, 1, 'SYSTEM', NOW()),
(31, 3, '整改工单', '/rectify/order',  'rectify/order/index',  'patrol:rectify:list',  1, 'Edit',    2, 1, 'SYSTEM', NOW());

-- 隐患记录 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1000, 30, '隐患详情', NULL, NULL, 'patrol:danger:detail',   2, NULL, 1, 1, 'SYSTEM', NOW()),
(1001, 30, '新增隐患', NULL, NULL, 'patrol:danger:create',   2, NULL, 2, 1, 'SYSTEM', NOW()),
(1002, 30, '修改隐患', NULL, NULL, 'patrol:danger:update',   2, NULL, 3, 1, 'SYSTEM', NOW()),
(1003, 30, '删除隐患', NULL, NULL, 'patrol:danger:delete',   2, NULL, 4, 1, 'SYSTEM', NOW()),
(1004, 30, '审核隐患', NULL, NULL, 'patrol:danger:audit',    2, NULL, 5, 1, 'SYSTEM', NOW()),
(1005, 30, '生成工单', NULL, NULL, 'patrol:danger:genOrder', 2, NULL, 6, 1, 'SYSTEM', NOW()),
(1006, 30, '关闭隐患', NULL, NULL, 'patrol:danger:close',    2, NULL, 7, 1, 'SYSTEM', NOW()),
(1007, 30, '导出隐患', NULL, NULL, 'patrol:danger:export',   2, NULL, 8, 1, 'SYSTEM', NOW());

-- 整改工单 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1100, 31, '工单详情', NULL, NULL, 'patrol:rectify:detail',  2, NULL, 1, 1, 'SYSTEM', NOW()),
(1101, 31, '新增工单', NULL, NULL, 'patrol:rectify:create',  2, NULL, 2, 1, 'SYSTEM', NOW()),
(1102, 31, '修改工单', NULL, NULL, 'patrol:rectify:update',  2, NULL, 3, 1, 'SYSTEM', NOW()),
(1103, 31, '删除工单', NULL, NULL, 'patrol:rectify:delete',  2, NULL, 4, 1, 'SYSTEM', NOW()),
(1104, 31, '开始整改', NULL, NULL, 'patrol:rectify:start',   2, NULL, 5, 1, 'SYSTEM', NOW()),
(1105, 31, '提交整改', NULL, NULL, 'patrol:rectify:submit',  2, NULL, 6, 1, 'SYSTEM', NOW()),
(1106, 31, '验收通过', NULL, NULL, 'patrol:rectify:accept',  2, NULL, 7, 1, 'SYSTEM', NOW()),
(1107, 31, '验收驳回', NULL, NULL, 'patrol:rectify:reject',  2, NULL, 8, 1, 'SYSTEM', NOW());

-- ============================
-- 3.4 预警管理 → 二级菜单
-- ============================
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(40, 4, '预警规则', '/warn/rule',    'warn/rule/index',    'warn:rule:list',   1, 'SetUp',   1, 1, 'SYSTEM', NOW()),
(41, 4, '预警记录', '/warn/record',  'warn/record/index',  'warn:record:list', 1, 'Tickets', 2, 1, 'SYSTEM', NOW());

-- 预警规则 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1200, 40, '规则详情', NULL, NULL, 'warn:rule:detail',   2, NULL, 1, 1, 'SYSTEM', NOW()),
(1201, 40, '新增规则', NULL, NULL, 'warn:rule:create',   2, NULL, 2, 1, 'SYSTEM', NOW()),
(1202, 40, '修改规则', NULL, NULL, 'warn:rule:update',   2, NULL, 3, 1, 'SYSTEM', NOW()),
(1203, 40, '删除规则', NULL, NULL, 'warn:rule:delete',   2, NULL, 4, 1, 'SYSTEM', NOW()),
(1204, 40, '启用规则', NULL, NULL, 'warn:rule:enable',   2, NULL, 5, 1, 'SYSTEM', NOW());

-- 预警记录 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1300, 41, '记录详情', NULL, NULL, 'warn:record:detail', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(1301, 41, '删除记录', NULL, NULL, 'warn:record:delete', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(1302, 41, '处理预警', NULL, NULL, 'warn:record:handle', 2, NULL, 3, 1, 'SYSTEM', NOW()),
(1303, 41, '忽略预警', NULL, NULL, 'warn:record:ignore', 2, NULL, 4, 1, 'SYSTEM', NOW()),
(1304, 41, '派发工单', NULL, NULL, 'warn:record:dispatch',2, NULL, 5, 1, 'SYSTEM', NOW());

-- ============================
-- 3.5 AI巡检 → 二级菜单
-- ============================
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(50, 5, 'AI识别结果', '/ai/result', 'ai/result/index', 'ai:result:list', 1, 'DataLine', 1, 1, 'SYSTEM', NOW());

-- AI识别结果 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1400, 50, '结果详情', NULL, NULL, 'ai:result:detail', 2, NULL, 1, 1, 'SYSTEM', NOW()),
(1401, 50, '删除结果', NULL, NULL, 'ai:result:delete', 2, NULL, 2, 1, 'SYSTEM', NOW()),
(1402, 50, '导出结果', NULL, NULL, 'ai:result:export', 2, NULL, 3, 1, 'SYSTEM', NOW());

-- ============================
-- 3.6 文件管理 → 二级菜单
-- ============================
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(60, 6, '文件管理', '/file/manage', 'file/manage/index', 'file:manage:list', 1, 'FolderOpened', 1, 1, 'SYSTEM', NOW());

-- 文件管理 → 按钮
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, status, create_by, create_time) VALUES
(1500, 60, '文件详情', NULL, NULL, 'file:manage:detail',   2, NULL, 1, 1, 'SYSTEM', NOW()),
(1501, 60, '上传文件', NULL, NULL, 'file:manage:upload',   2, NULL, 2, 1, 'SYSTEM', NOW()),
(1502, 60, '删除文件', NULL, NULL, 'file:manage:delete',   2, NULL, 3, 1, 'SYSTEM', NOW()),
(1503, 60, '下载文件', NULL, NULL, 'file:manage:download', 2, NULL, 4, 1, 'SYSTEM', NOW());


-- ============================================================================
-- 第四部分：角色-菜单关联
-- ============================================================================

-- ----------------------------
-- 4.1 ADMIN — 拥有全部菜单和按钮权限
-- ----------------------------
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu WHERE status = 1;

-- ----------------------------
-- 4.2 INSPECTOR（巡检员）— 任务/点位查看、隐患上报、AI结果查看
-- ----------------------------
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 2, id FROM sys_menu WHERE perms IN (
    -- 巡检管理（查看）
    'patrol:plan:list', 'patrol:plan:detail',
    'patrol:task:list', 'patrol:task:detail',
    'patrol:task:accept', 'patrol:task:start', 'patrol:task:complete',
    'patrol:point:list', 'patrol:point:detail',
    'patrol:qrcode:list', 'patrol:qrcode:detail',
    -- 隐患记录（查看和上报）
    'patrol:danger:list', 'patrol:danger:detail',
    'patrol:danger:create',
    -- AI 结果（查看）
    'ai:result:list', 'ai:result:detail',
    -- 文件管理（上传和下载）
    'file:manage:list', 'file:manage:detail',
    'file:manage:upload', 'file:manage:download'
);

-- ----------------------------
-- 4.3 AUDITOR（审核员）— 隐患审核、工单验收、预警处理
-- ----------------------------
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 3, id FROM sys_menu WHERE perms IN (
    -- 巡检管理（查看）
    'patrol:plan:list', 'patrol:plan:detail',
    'patrol:task:list', 'patrol:task:detail',
    'patrol:point:list', 'patrol:point:detail',
    -- 隐患记录（审核、生成工单、关闭）
    'patrol:danger:list', 'patrol:danger:detail',
    'patrol:danger:audit', 'patrol:danger:genOrder', 'patrol:danger:close',
    -- 整改工单（查看、验收）
    'patrol:rectify:list', 'patrol:rectify:detail',
    'patrol:rectify:accept', 'patrol:rectify:reject',
    -- 预警记录（处理、忽略、派单）
    'warn:record:list', 'warn:record:detail',
    'warn:record:handle', 'warn:record:ignore', 'warn:record:dispatch',
    -- AI 结果（查看）
    'ai:result:list', 'ai:result:detail',
    -- 文件管理（查看、下载）
    'file:manage:list', 'file:manage:detail', 'file:manage:download'
);


-- ============================================================================
-- 第五部分：字典初始化
-- ============================================================================
DELETE FROM sys_dict_data;
DELETE FROM sys_dict_type;

-- ----------------------------
-- 5.1 字典类型
-- ----------------------------
INSERT INTO sys_dict_type (id, dict_name, dict_code, status, remark, create_by, create_time) VALUES
(1, '隐患等级',      'danger_level',   1, '隐患等级：0一般 1严重 2紧急',                       'SYSTEM', NOW()),
(2, '任务状态',      'task_status',    1, '巡检任务状态',                                       'SYSTEM', NOW()),
(3, '隐患状态',      'danger_status',  1, '隐患记录状态',                                       'SYSTEM', NOW()),
(4, '整改状态',      'rectify_status', 1, '整改工单状态',                                       'SYSTEM', NOW()),
(5, '启停状态',      'enable_status',  1, '通用启停状态：0停用 1启用',                         'SYSTEM', NOW()),
(6, '预警状态',      'warn_status',    1, '预警记录状态：0待处理 1已忽略 2已派单',              'SYSTEM', NOW()),
(7, '设备类型',      'device_type',    1, '巡检点位设备类型',                                   'SYSTEM', NOW()),
(8, '预警级别',      'alarm_level',    1, '预警级别：0信息 1警告 2严重',                        'SYSTEM', NOW()),
(9, 'AI结果状态',    'ai_status',      1, 'AI识别结果状态：0处理中 1已完成 2失败',              'SYSTEM', NOW()),
(10,'审核状态',      'audit_status',   1, '通用审核状态',                                       'SYSTEM', NOW());

-- ----------------------------
-- 5.2 字典数据
-- ----------------------------

-- 隐患等级 danger_level
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, status, create_by, create_time) VALUES
(100, 'danger_level', '一般', '0', 1, 1, 'SYSTEM', NOW()),
(101, 'danger_level', '严重', '1', 2, 1, 'SYSTEM', NOW()),
(102, 'danger_level', '紧急', '2', 3, 1, 'SYSTEM', NOW());

-- 任务状态 task_status
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(200, 'task_status', '待派发', '0', 1, 'default',   1, 'SYSTEM', NOW()),
(201, 'task_status', '待接单', '1', 2, 'info',      1, 'SYSTEM', NOW()),
(202, 'task_status', '巡检中', '2', 3, 'warning',   1, 'SYSTEM', NOW()),
(203, 'task_status', '已完成', '3', 4, 'success',   1, 'SYSTEM', NOW()),
(204, 'task_status', '超期',   '4', 5, 'danger',    1, 'SYSTEM', NOW());

-- 隐患状态 danger_status
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(300, 'danger_status', '待审核', '0', 1, 'default',   1, 'SYSTEM', NOW()),
(301, 'danger_status', '待派单', '1', 2, 'info',      1, 'SYSTEM', NOW()),
(302, 'danger_status', '已派单', '2', 3, 'warning',   1, 'SYSTEM', NOW()),
(303, 'danger_status', '待整改', '3', 4, 'warning',   1, 'SYSTEM', NOW()),
(304, 'danger_status', '待验收', '4', 5, 'info',      1, 'SYSTEM', NOW()),
(305, 'danger_status', '关闭',   '5', 6, 'success',   1, 'SYSTEM', NOW());

-- 整改状态 rectify_status
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(400, 'rectify_status', '待整改',   '0', 1, 'default',   1, 'SYSTEM', NOW()),
(401, 'rectify_status', '整改中',   '1', 2, 'warning',   1, 'SYSTEM', NOW()),
(402, 'rectify_status', '待验收',   '2', 3, 'info',      1, 'SYSTEM', NOW()),
(403, 'rectify_status', '验收通过', '3', 4, 'success',   1, 'SYSTEM', NOW()),
(404, 'rectify_status', '验收驳回', '4', 5, 'danger',    1, 'SYSTEM', NOW());

-- 启停状态 enable_status
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(500, 'enable_status', '停用', '0', 1, 'danger',  1, 'SYSTEM', NOW()),
(501, 'enable_status', '启用', '1', 2, 'success', 1, 'SYSTEM', NOW());

-- 预警状态 warn_status
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(600, 'warn_status', '待处理', '0', 1, 'danger',   1, 'SYSTEM', NOW()),
(601, 'warn_status', '已忽略', '1', 2, 'info',     1, 'SYSTEM', NOW()),
(602, 'warn_status', '已派单', '2', 3, 'success',  1, 'SYSTEM', NOW());

-- 设备类型 device_type
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, status, create_by, create_time) VALUES
(700, 'device_type', '空调',      'AC',       1, 1, 'SYSTEM', NOW()),
(701, 'device_type', 'UPS电源',   'UPS',      2, 1, 'SYSTEM', NOW()),
(702, 'device_type', '配电柜',    'PDC',      3, 1, 'SYSTEM', NOW()),
(703, 'device_type', '服务器',    'SERVER',   4, 1, 'SYSTEM', NOW()),
(704, 'device_type', '网络设备',  'NETWORK',  5, 1, 'SYSTEM', NOW()),
(705, 'device_type', '温湿度传感器','SENSOR',  6, 1, 'SYSTEM', NOW());

-- 预警级别 alarm_level
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(800, 'alarm_level', '信息', '0', 1, 'info',    1, 'SYSTEM', NOW()),
(801, 'alarm_level', '警告', '1', 2, 'warning', 1, 'SYSTEM', NOW()),
(802, 'alarm_level', '严重', '2', 3, 'danger',  1, 'SYSTEM', NOW());

-- AI结果状态 ai_status
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(900, 'ai_status', '处理中', '0', 1, 'warning',  1, 'SYSTEM', NOW()),
(901, 'ai_status', '已完成', '1', 2, 'success',  1, 'SYSTEM', NOW()),
(902, 'ai_status', '失败',   '2', 3, 'danger',   1, 'SYSTEM', NOW());

-- 审核状态 audit_status
INSERT INTO sys_dict_data (id, dict_code, label, value, sort, css_class, status, create_by, create_time) VALUES
(1000, 'audit_status', '待审核', '0', 1, 'default',  1, 'SYSTEM', NOW()),
(1001, 'audit_status', '已通过', '1', 2, 'success',  1, 'SYSTEM', NOW()),
(1002, 'audit_status', '已驳回', '2', 3, 'danger',   1, 'SYSTEM', NOW());


-- ============================================================================
-- 完成
-- ============================================================================
-- 账号: admin / admin123 (BCrypt)
-- 角色: ADMIN（全部权限）/ INSPECTOR（巡检上报）/ AUDITOR（审核验收）
-- 菜单: 6 目录 + 16 菜单 + 80 按钮 = 102 条权限
-- 字典: 10 类型 + 43 条数据
-- ============================================================================
