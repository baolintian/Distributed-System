This task refers to web service.



实现Web Service服务端的一般流程：

1. 用常用高级编程语言（例如Java）定义Web服务接口

2. 根据Java定义的Web服务接口生成WSDL（中间件自动做）

3. 定义实现接口的Web服务实现类

4. 将Web服务实现类绑定到Web服务器

5. 将Web服务注册的UDDI中心

实现Web Service客户端的一般流程：

1. 从UDDI中心查找的目标Web服务的接口定义（WSDL）

2. 根据WSDL生成Web服务代理类(WSDL to Java)

3. 利用Web服务代理类调用Web服务接口中定义的具体方法



WSDL：生成服务的中间语言



# demo1

Finish task2 using WS.

![](https://ws1.sinaimg.cn/large/a7ded905ly1g319ucga8tg21bg0ob7wl.gif)



# demo2

query the weather

![](https://ws1.sinaimg.cn/large/a7ded905ly1g319ulrd6kg21bg0ob7wh.gif)



# demo3

query the detail information about one's telephone

![](https://ws1.sinaimg.cn/large/a7ded905ly1g319usjyfcg21bg0ob1kx.gif)