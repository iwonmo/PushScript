# PushScript
一个简单的推送程序，过期后会主动推送请求
# 使用方法
格式：JSON

端口：1992

key:存储标识

data:存储数据

time:过期时间

utime:更新时间 推送消息后，下次推送时间 = 当前时间 + 更新时间

type:消息类型 
```ini
none:过期推送删除 
uptime:推送后更新推送时间持续推送
del:删除对应Key
```
# 增加数据
```java
        try {
            JsonObject jsonObject = (JsonObject) new JsonObject();
            jsonObject.addProperty("key", UUID.randomUUID().toString());
            jsonObject.addProperty("data", generateString(new Random(), SOURCES, 15));
            jsonObject.addProperty("time", System.currentTimeMillis() + 10000);
            jsonObject.addProperty("utime", 120000);
            jsonObject.addProperty("type", "uptime");
            _string = (jsonObject.toString());
        } catch (Exception e) {
        }
```
# 编译
gradle jar
# 详细
https://www.iwonmo.com/archives/1575.html