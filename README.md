# PushScript
一个简单的推送程序，过期后会主动推送请求
# 使用方法
格式：数据|过期时间
端口：1992
# 例子
socket_connect($socket,'localhost', 1992);
socket_write($socket, "abc|1563091761421");
