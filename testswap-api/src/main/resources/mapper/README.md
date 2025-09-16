# Mapper XML 占位

如需启用数据库与 MyBatis-Plus XML 映射，请在此目录下添加 `*.xml` 文件，例如：

- `UserMapper.xml`
- `ForkRecordMapper.xml`

并在 `application.yml` 中确保：

```
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
```
