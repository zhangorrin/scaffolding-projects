# 工作流使用方法
- - -
## 1. 配置
在application.yml当中配置相关参数
```
spring:
  redis:
    host: localhost
    port: 6379
    database: 5
```

## 2. 使用方法

### 2.1 原生使用方法
```java
    @Autowired
	private RedisTemplate redisTemplate;
```
缓存注解:
使用@EnableCaching(mode = AdviceMode.ASPECTJ),同时引入RedissonCacheConfig配置

### 2.2 Redisson使用方法

[详细使用方法](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95)
