# 工程简介

[线上演示地址](http://boot.2cci.cn) \
账号：admin \
密码：123456 \
springboot + shiro \
前后端分离：前端使用vue + elementui

### 实现功能

1. 用户管理
2. 角色管理
3. 资源管理
4. 部门管理
5. 登陆日志
6. 操作日志
7. druid监控
8. 定时任务

### 项目架构

[DDD介绍](https://domain-driven-design.org): 项目基于[DDD的项目示例](https://github.com/domain-driven-design/ddd-lite-example) \
boot-web - 前端 \
boot-serve - 后端 \
start - 启动包 \
app - DDD中的接口层和应用层 \
domain - DDD中的领域层和基础设施层 \

### 开发规范

#### 类命名
| 名称         | 说明                     |
|------------|------------------------|
| DO         | DataObject 与数据库表一一对应的类 |
| Entity     | domain层接收和返回的对象        |
| Convertor  | 转换器 DO与Entity相互转换      |
| Body       | 前端传入的body              |
| Query      | 前端查询传入的query           |
| VO         | ViewObject 返回给前端的对象    |
| Assembler  | 组装器 Entity/DO组装成VO     |
| Service    | domain层的服务             |
| AppService | Application层的服务        |

- Application：对外暴露的是VO，不能暴露 Entity
- Domain：对外暴露的是Entity，不能暴露 DO

#### 方法命名
| 名称     | 说明      |
|--------|---------|
| get    | 获取单个数据  |
| list   | 获取多个数据  |
| page   | 分页查询    |
| create | 创建数据    |
| update | 更新数据    |
| delete | 删除数据    |
| save   | 创建或更新数据 |
| select | 持久层查询数据 |
| insert | 持久层插入数据 |
| update | 持久层更新数据 |
| delete | 持久层删除数据 |
    