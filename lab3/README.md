<div style = "font-size: 2em;"><center >MOM消息队列技术</center></div>

<div style = "font-size: 1.2em;"><center >学号：16030130096   姓名：田宝林</center></div>

可以查看demo.gif进行效果的查看。



# 使用的包：

`activemq-all-5.15.9.jar`：MQ包

`gnujaxp-1.0.0.jar`:用于画图

`jcommon-1.0.23.jar`:用于画图

`jfreechart-1.0.19.jar`:用于画图



# 流程：

1. 打开apache的MQ服务
2. 打开接收端接受消息
3. 打开服务端发送消息



# 具体的工作

最主要在`MyListener.java`里面要求的功能。

每一次都能从服务端接收到消息，然后进行相应的处理，其中实现了`RealTimeChart.java`模块来实现实时展示结果。

