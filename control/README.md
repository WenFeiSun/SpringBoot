server.port = 8092
#下面两行用户去掉重定向后url后面显示JSESSIONID问题
server.servlet.session.tracking-modes = cookie
server.servlet.session.cookie.http-only = true

resources.static-locations = classpath:static/*/**

#数据库连接配置信息
#spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/XIAO_QU?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=abc123
#spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

#mybatis配置
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.sun.common.entity
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl

#redis配置
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=127.0.0.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=abc123
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=20
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=-1ms
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=10
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=1000ms

#=============== producer  =======================
spring.kafka.producer.bootstrap-servers=127.0.0.1:9092
spring.kafka.producer.retries=1
spring.kafka.producer.batch-size=16384
spring.kafka.producer.buffer-memory=33554432
spring.kafka.producer.properties.max.requst.size=2097152
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

#=============== consumer  =======================
spring.kafka.consumer.bootstrap-servers=127.0.0.1:9092
spring.kafka.consumer.group-id=0
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=true
spring.kafka.consumer.auto-commit-interval=100
#=======set comsumer max fetch.byte 2*1024*1024=============
spring.kafka.consumer.properties.max.partition.fetch.bytes=2097152