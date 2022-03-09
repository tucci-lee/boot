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
boot-site - 前端 \
core - 项目的重要组成 \
common - application层按实际情况引用（注意maven包的scope）\
bootstrap - 启动包 \
application - DDD中的接口层和应用层 \
domain - DDD中的领域层和基础设施层 \

- Application：对外暴露的是VO，不能暴露 Entity 
- Domain：对外暴露的是Entity，不能暴露 DO
