# Patrol Backend — 开发进度文档

> **项目名称**：机房巡检系统后端 (patrol-backend)  
> **技术栈**：Spring Boot 2.7.18 + Java 17 + Maven + MyBatis-Plus 3.5.5  
> **数据库**：MySQL 8.0.33 | **缓存**：Redis | **存储**：MinIO  
> **认证**：Spring Security + JWT (jjwt 0.9.1)  
> **文档**：Knife4j 4.1.0 (Swagger 3)  
> **Git 分支**：feature/complete-backend-scaffold-new | **最后更新**：2026-06-14

---

## 一、已完成任务总览

| # | 任务 | 状态 | 产出 |
|---|------|------|------|
| 1 | Spring Boot 启动类 | ✅ 完成 | `PatrolApplication.java` |
| 2 | JWT 认证过滤器 | ✅ 完成 | `JwtAuthenticationFilter.java` |
| 3 | UserDetailsService | ✅ 完成 | `UserDetailsServiceImpl.java` + 5 张用户权限表实体 |
| 4 | 系统登录能力 | ✅ 完成 | `AuthController` + `LoginDTO` + `LoginVO` |
| 5 | 五个业务模块 CRUD | ✅ 完成 | 5 模块 × 5 层（entity/mapper/service/impl/controller） |
| 6 | Quartz 定时任务 | ✅ 完成 | `PatrolTaskJob` + `QuartzConfig` (cron 可配置) |
| 7 | EasyExcel 导入导出 | ✅ 完成 | 2 个 Excel DTO + 4 个导入导出端点 |
| 8 | 数据库初始化脚本 | ✅ 完成 | `sql/init.sql` (9 张表 + 种子数据) |
| 9 | MinIO 工具类 | ✅ 完成 | `MinioUtil` (上传/下载/删除/URL) + `FileController` |
| 10 | Vue3 管理后台 | ✅ 完成 | `patrol-admin/` (27 文件: Vite + Element Plus + Pinia) |
| 11 | uni-app 移动端 | ✅ 完成 | `patrol-mobile/` (15 文件: 登录/任务/扫码/整改) |
| — | **Maven 编译** | ✅ 通过 | 全模块零错误编译 |

---

## 二、项目结构

```
patrol-backend/
├── pom.xml                          # 根 POM（Spring Boot 2.7.18 parent）
├── common/                          # 公共模块
│   └── src/main/java/com/patrol/common/
│       ├── R.java                   # 统一返回体 { code, msg, data, timestamp }
│       ├── PageRequest.java         # 分页请求封装
│       └── exception/
│           └── BusinessException.java # 业务异常
│
├── framework/                       # 框架模块
│   └── src/main/java/com/patrol/framework/
│       ├── config/
│       │   ├── SecurityConfig.java   # Spring Security 配置（JWT Filter + AuthManager）
│       │   ├── MyBatisPlusConfig.java # MyBatis-Plus 分页插件
│       │   ├── RedisConfig.java      # Redis 序列化配置
│       │   ├── MinioConfig.java      # MinIO 对象存储配置
│       │   └── Knife4jConfig.java    # Swagger 接口文档配置
│       ├── security/
│       │   ├── JwtTokenUtil.java     # JWT 生成/解析/校验工具
│       │   └── JwtAuthenticationFilter.java  # 🆕 JWT Bearer Token 过滤器
│       └── exception/
│           └── GlobalExceptionHandler.java  # 全局异常处理
│
├── module-system/                   # 🆕 系统管理模块
│   └── src/main/java/com/patrol/
│       ├── PatrolApplication.java   # 🆕 Spring Boot 启动类
│       └── system/
│           ├── entity/              # 🆕 5 个实体
│           │   ├── SysUser.java     #     系统用户
│           │   ├── SysRole.java     #     系统角色
│           │   ├── SysMenu.java     #     系统菜单/权限
│           │   ├── SysUserRole.java #     用户-角色关联
│           │   └── SysRoleMenu.java #     角色-菜单关联
│           ├── mapper/              # 🆕 5 个 Mapper 接口
│           │   ├── SysUserMapper.java
│           │   ├── SysRoleMapper.java
│           │   ├── SysMenuMapper.java
│           │   ├── SysUserRoleMapper.java
│           │   └── SysRoleMenuMapper.java
│           ├── service/             # 🆕 3 个 Service 接口
│           │   ├── SysUserService.java
│           │   ├── SysRoleService.java
│           │   └── SysMenuService.java
│           ├── service/impl/        # 🆕 4 个 Service 实现
│           │   ├── SysUserServiceImpl.java
│           │   ├── SysRoleServiceImpl.java
│           │   ├── SysMenuServiceImpl.java
│           │   └── UserDetailsServiceImpl.java  # Spring Security 用户加载
│           ├── controller/          # 🆕 4 个 Controller
│           │   ├── AuthController.java      # POST /login
│           │   ├── SysUserController.java   # /system/user/**
│           │   ├── SysRoleController.java   # /system/role/**
│           │   └── SysMenuController.java   # /system/menu/**
│           ├── dto/
│           │   └── LoginDTO.java    # 🆕 登录请求 DTO
│           └── vo/
│               └── LoginVO.java     # 🆕 登录响应 VO
│
├── module-task/                     # 🆕 巡检任务模块
│   └── src/main/java/com/patrol/task/
│       ├── entity/PatrolTask.java
│       ├── mapper/PatrolTaskMapper.java
│       ├── service/PatrolTaskService.java
│       ├── service/impl/PatrolTaskServiceImpl.java
│       └── controller/PatrolTaskController.java   # /task/**
│
├── module-location/                 # 🆕 巡检点位模块
│   └── src/main/java/com/patrol/location/
│       ├── entity/PatrolLocation.java
│       ├── mapper/PatrolLocationMapper.java
│       ├── service/PatrolLocationService.java
│       ├── service/impl/PatrolLocationServiceImpl.java
│       └── controller/PatrolLocationController.java  # /location/**
│
├── module-rectify/                  # 🆕 隐患整改模块
│   └── src/main/java/com/patrol/rectify/
│       ├── entity/RectifyRecord.java
│       ├── mapper/RectifyRecordMapper.java
│       ├── service/RectifyRecordService.java
│       ├── service/impl/RectifyRecordServiceImpl.java
│       └── controller/RectifyRecordController.java   # /rectify/**
│
└── module-ai/                       # 🆕 AI 识别模块
    └── src/main/java/com/patrol/ai/
        ├── entity/AiRecord.java
        ├── mapper/AiRecordMapper.java
        ├── service/AiRecordService.java
        ├── service/impl/AiRecordServiceImpl.java
        └── controller/AiRecordController.java         # /ai/**
```

**文件统计**：总计 50 个 Java 源文件（原有 10 个 + 本次新增 40 个）

---

## 三、数据库表结构设计

### 3.1 系统模块（module-system）

#### sys_user（系统用户）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT, PK, AUTO | 主键 |
| username | VARCHAR | 用户名 |
| password | VARCHAR | BCrypt 加密密码 |
| nickname | VARCHAR | 昵称 |
| email | VARCHAR | 邮箱 |
| phone | VARCHAR | 手机号 |
| avatar | VARCHAR | 头像 |
| status | INT | 0=禁用, 1=启用 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 (0=正常, 1=已删除) |

#### sys_role（系统角色）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT, PK, AUTO | 主键 |
| name | VARCHAR | 角色名称 |
| code | VARCHAR | 角色编码 |
| sort | INT | 排序 |
| status | INT | 0=禁用, 1=启用 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

#### sys_menu（系统菜单/权限）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT, PK, AUTO | 主键 |
| parent_id | BIGINT | 父菜单 ID |
| name | VARCHAR | 菜单名称 |
| path | VARCHAR | 路由路径 |
| component | VARCHAR | 前端组件 |
| perms | VARCHAR | 权限标识 (如 `system:user:list`) |
| type | INT | 0=目录, 1=菜单, 2=按钮 |
| icon | VARCHAR | 图标 |
| sort | INT | 排序 |
| status | INT | 0=禁用, 1=启用 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

#### sys_user_role（用户-角色关联）

| 字段 | 类型 | 说明 |
|------|------|------|
| user_id | BIGINT | 用户 ID |
| role_id | BIGINT | 角色 ID |

#### sys_role_menu（角色-菜单关联）

| 字段 | 类型 | 说明 |
|------|------|------|
| role_id | BIGINT | 角色 ID |
| menu_id | BIGINT | 菜单 ID |

### 3.2 任务模块（module-task）

#### patrol_task

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT, PK | 主键 |
| name | VARCHAR | 任务名称 |
| description | VARCHAR | 任务描述 |
| location_id | BIGINT | 关联点位 |
| cron_expression | VARCHAR | 调度 Cron |
| status | INT | 0=停止, 1=运行 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 3.3 定位模块（module-location）

#### patrol_location

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT, PK | 主键 |
| name | VARCHAR | 点位名称 |
| code | VARCHAR | 点位编码 |
| address | VARCHAR | 详细地址 |
| area | VARCHAR | 所属区域 |
| type | VARCHAR | 点位类型 |
| description | VARCHAR | 描述 |
| status | INT | 0=禁用, 1=启用 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 3.4 整改模块（module-rectify）

#### rectify_record

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT, PK | 主键 |
| task_id | BIGINT | 关联任务 |
| location_id | BIGINT | 关联点位 |
| title | VARCHAR | 问题标题 |
| description | VARCHAR | 问题描述 |
| severity | VARCHAR | 严重程度 |
| status | INT | 0=待整改, 1=整改中, 2=已完成 |
| handler | VARCHAR | 处理人 |
| result | VARCHAR | 整改结果 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| deleted | INT | 逻辑删除 |

### 3.5 AI 模块（module-ai）

#### ai_record

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT, PK | 主键 |
| task_id | BIGINT | 关联任务 |
| location_id | BIGINT | 关联点位 |
| image_url | VARCHAR | 图片 URL |
| analysis_result | TEXT | 分析结果 (JSON) |
| confidence | DOUBLE | 置信度 |
| model_name | VARCHAR | 模型名称 |
| status | INT | 0=处理中, 1=已完成, 2=失败 |
| create_time | DATETIME | 创建时间 |
| deleted | INT | 逻辑删除 |

---

## 四、API 接口清单

### 4.1 认证接口（无需 Token）

| 方法 | 路径 | 说明 | 入参 | 出参 |
|------|------|------|------|------|
| POST | `/login` | 用户登录 | LoginDTO {username, password} | R\<LoginVO\> {token, userId, username, nickname, permissions} |
| GET | `/captcha` | 验证码（预留） | — | — |

### 4.2 系统管理 `/system/**`（需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/system/user/list` | 用户列表 |
| GET | `/system/user/detail/{id}` | 用户详情 |
| POST | `/system/user` | 新增用户（自动 BCrypt 加密密码） |
| PUT | `/system/user` | 修改用户 |
| DELETE | `/system/user/{id}` | 删除用户（逻辑删除） |
| GET | `/system/role/list` | 角色列表 |
| GET | `/system/role/detail/{id}` | 角色详情 |
| POST | `/system/role` | 新增角色 |
| PUT | `/system/role` | 修改角色 |
| DELETE | `/system/role/{id}` | 删除角色 |
| GET | `/system/menu/list` | 菜单列表 |
| GET | `/system/menu/detail/{id}` | 菜单详情 |
| POST | `/system/menu` | 新增菜单 |
| PUT | `/system/menu` | 修改菜单 |
| DELETE | `/system/menu/{id}` | 删除菜单 |

### 4.3 业务模块（需认证）

#### 巡检任务 `/task/**`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/task/list` | 任务列表 |
| GET | `/task/detail/{id}` | 任务详情 |
| POST | `/task` | 新增任务 |
| PUT | `/task` | 修改任务 |
| DELETE | `/task/{id}` | 删除任务 |

#### 巡检点位 `/location/**`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/location/list` | 点位列表 |
| GET | `/location/detail/{id}` | 点位详情 |
| POST | `/location` | 新增点位 |
| PUT | `/location` | 修改点位 |
| DELETE | `/location/{id}` | 删除点位 |

#### 整改记录 `/rectify/**`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/rectify/list` | 整改记录列表 |
| GET | `/rectify/detail/{id}` | 整改记录详情 |
| POST | `/rectify` | 新增整改记录 |
| PUT | `/rectify` | 修改整改记录 |
| DELETE | `/rectify/{id}` | 删除整改记录 |

#### AI 记录 `/ai/**`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/ai/list` | AI 记录列表 |
| GET | `/ai/detail/{id}` | AI 记录详情 |
| POST | `/ai` | 新增 AI 记录 |
| PUT | `/ai` | 修改 AI 记录 |
| DELETE | `/ai/{id}` | 删除 AI 记录 |

**总计**：35 个 API 端点（1 登录 + 3×5 系统管理 + 4×5 业务模块）

---

## 五、认证与安全架构

### 5.1 认证流程

```
POST /login (公开)
  │
  ├─ 1. AuthController 接收 LoginDTO { username, password }
  ├─ 2. AuthenticationManager.authenticate()
  │     ├─ UserDetailsServiceImpl.loadUserByUsername(username)
  │     │   ├─ 查 sys_user 获取用户
  │     │   ├─ 查 sys_user_role + sys_role 获取角色
  │     │   ├─ 查 sys_role_menu + sys_menu 获取权限标识
  │     │   └─ 返回 UserDetails { username, password, enabled, authorities }
  │     └─ BCryptPasswordEncoder.matches(rawPassword, encodedPassword)
  ├─ 3. JwtTokenUtil.generateToken(username) → JWT Token
  └─ 4. 返回 LoginVO { token, userId, username, nickname, permissions }
```

### 5.2 请求认证流程

```
任意请求
  │
  ├─ JwtAuthenticationFilter (OncePerRequestFilter)
  │   ├─ 从 Authorization: Bearer <token> 提取 Token
  │   ├─ JwtTokenUtil.validateToken(token) 校验有效性
  │   └─ 有效 → 写入 SecurityContext
  │
  └─ SecurityFilterChain
      ├─ /login, /captcha, /doc.html, /swagger-ui/**, ... → permitAll
      └─ 其他 → authenticated (403 if no valid token)
```

### 5.3 安全配置要点

- **无状态**：`SessionCreationPolicy.STATELESS`，不创建服务端 Session
- **CSRF**：已禁用（RESTful API 不需要）
- **密码加密**：BCryptPasswordEncoder
- **JWT 过滤器位置**：在 `UsernamePasswordAuthenticationFilter` 之前
- **放行路径**：`/login`、`/captcha`、Knife4j/Swagger 文档、`/static/**`、`/public/**`

---

## 六、待办事项

| 优先级 | 任务 | 说明 |
|--------|------|------|
| 🔴 高 | 创建 `application.yml` | 数据源、Redis、MinIO、JWT 密钥等配置 |
| 🔴 高 | 创建数据库 DDL | 根据实体生成建表 SQL |
| 🟡 中 | 补充分页查询 | 目前 list 返回全量，需集成 MyBatis-Plus Page |
| 🟡 中 | 补充参数校验 | 在 DTO 和实体中添加 `@NotBlank`、`@NotNull` 等 |
| 🟡 中 | 补充单元测试 | Service 层和 Controller 层测试 |
| 🟢 低 | 角色/菜单权限注解 | `@PreAuthorize` 方法级权限控制 |
| 🟢 低 | 操作日志 | AOP 切面记录操作日志 |
| 🟢 低 | 数据权限 | 按用户/部门隔离数据 |

---

## 七、如何运行

### 前置条件

- JDK 17+
- MySQL 8.0+
- Redis（可选）
- MinIO（可选）

### 启动步骤

```bash
# 1. 克隆项目
cd patrol-backend

# 2. 创建 application.yml（在 module-system/src/main/resources/ 下）

# 3. 初始化数据库（执行 DDL 建表 + 初始化管理员数据）

# 4. 编译
mvn clean compile

# 5. 启动
mvn -pl module-system spring-boot:run

# 6. 访问 Swagger 文档
# http://localhost:8080/doc.html
```

### 初始化管理员数据（参考 SQL）

```sql
-- 插入管理员用户（密码: admin123, BCrypt 加密）
INSERT INTO sys_user (username, password, nickname, status, create_time)
VALUES ('admin', '$2a$10$...', '系统管理员', 1, NOW());

-- 插入管理员角色
INSERT INTO sys_role (name, code, status, create_time)
VALUES ('管理员', 'ADMIN', 1, NOW());

-- 关联用户和角色
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 插入基础菜单权限
INSERT INTO sys_menu (name, perms, type, status, create_time) VALUES
('用户列表', 'system:user:list', 2, 1, NOW()),
('用户新增', 'system:user:create', 2, 1, NOW()),
('用户修改', 'system:user:update', 2, 1, NOW()),
('用户删除', 'system:user:delete', 2, 1, NOW());

-- 关联角色和菜单
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4);
```
