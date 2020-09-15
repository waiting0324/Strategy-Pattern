# 策略模式



## 1. 思維導圖

<img src="https://raw.githubusercontent.com/waiting0324/TyporaImg/master/image-20200915181149988.png" alt="image-20200915181149988" style="zoom:67%;" />



**當存在以下情況的時候，可以考慮使用策略模式 :**

1. 某段程式碼存在過多的 if - else 語句
2. if - else 中的執行邏輯過於複雜，希望用類進行封裝
3. 希望使用類來封裝條件表達式



**一句話理解策略模式 :**

​	調用接口方法來使用策略模式，具體邏輯參考接口實現類



## 2. 結構

<img src="https://raw.githubusercontent.com/waiting0324/TyporaImg/master/image-20200915094355315.png" alt="image-20200915094355315" style="zoom: 50%;" />

## 3. 代碼示例 - 使用註解優雅替換掉 if - else



### 3.1 GitHub 地址

[GitHub 地址](https://github.com/waiting0324/Strategy-Pattern)



### 3.2 目錄結構

<img src="https://raw.githubusercontent.com/waiting0324/TyporaImg/master/image-20200915110808998.png" alt="image-20200915110808998" style="zoom:67%;" />



### 3.3 類圖

![image-20200915125327640](https://raw.githubusercontent.com/waiting0324/TyporaImg/master/image-20200915125327640.png)





### 3.4 具體代碼



#### 策略屬性類

用於定義根據那些屬性，來選擇哪個具體策略，此示例主要根據 (1)訂單來源 (2)付款方式 來選擇具體策略

```java
@Data
public class Order {

    /**
     * 訂單來源
     */
    private String source;
    /**
     * 付款方式
     */
    private String payMethod;
    /**
     * 訂單編號
     */
    private String code;
    /**
     * 訂單金額
     */
    private BigDecimal amount;

}
```





#### 註解類

定義註解屬性與作用範圍，用於標註在策略實現類上

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface OrderHandlerType {

    String source();

    String payMethod();

}
```





#### 註解實現類

重寫 equals()、hashCode() 方法，定義只要 source、payMethod 屬性相同，就視為同一個對象。

會在上下文角色中做使用。

```java
public class OrderHandlerTypeImpl implements OrderHandlerType {

    private String source;
    private String payMethod;

    public OrderHandlerTypeImpl(String source, String payMethod) {
        this.source = source;
        this.payMethod = payMethod;
    }


    @Override
    public String source() {
        return source;
    }

    @Override
    public String payMethod() {
        return payMethod;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return OrderHandlerType.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderHandlerType)) {
            return false;
        }
        OrderHandlerType other = (OrderHandlerType) obj;
        return source.equals(other.source()) && payMethod.equals(other.payMethod());
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode += (127 * "source".hashCode()) ^ source.hashCode();
        hashCode += (127 * "payMethod".hashCode()) ^ payMethod.hashCode();
        return hashCode;
    }
}
```





#### 策略接口類

定義策略實現類的共通方法

```java
public interface OrderHandler {

    void handle(Order order);

}
```





#### 策略實現類

與註解類結合，定義具體策略

```java
@OrderHandlerType(source = "pc", payMethod = "creditCard")
public class PCOrderHandler implements OrderHandler {
    @Override
    public void handle(Order order) {
        System.out.println("處理 PC端 信用卡 付款訂單");
    }
}


@OrderHandlerType(source = "mobile", payMethod = "wechat")
public class MobileOrderHandler implements OrderHandler {
    @Override
    public void handle(Order order) {
        System.out.println("處理 手機端 WeChat 付款訂單");
    }
}
```





#### 上下文角色

負責呼叫策略接口，來調用具體策略

```java
@Service
public class OrderService {

    // 註解類 與 策略實現類 映射結果
    private Map<OrderHandlerType, OrderHandler> orderHandleMap;

    @Autowired
    public void setOrderHandleMap(List<OrderHandler> orderHandlers) { // 注入各種類型的訂單處理類
        // 將 註解類 與 策略實現類 進行映射
        orderHandleMap = orderHandlers.stream().collect(
                Collectors.toMap(orderHandler ->
                        // 找出 策略實現類 上的 OrderHandlerType 註解
                        AnnotationUtils.findAnnotation(orderHandler.getClass(), OrderHandlerType.class),
                        // 將 value(策略實現類) 進行處理
                        v -> v,
                        // 如果 key(OrderHandlerType註解) 有對應到多個 value(策略實現類) 則選擇 v1(第一個)
                        (v1, v2) -> v1));
    }

    public void orderService(Order order) {
        // ...一些前置處理

        // 根據 傳入的訂單屬性 找到對應的 註解類
        OrderHandlerType orderHandlerType = new OrderHandlerTypeImpl(order.getSource(), order.getPayMethod());
        // 根據 註解類 找到 策略實現類
        OrderHandler orderHandler = orderHandleMap.get(orderHandlerType);
        // 調用策略實現類方法
        orderHandler.handle(order);

        // ...一些後置處理
    }
}
```



#### 測試類

```java
@SpringBootTest
class StrategyApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {

        Order order = new Order();
        order.setSource("pc");
        order.setPayMethod("creditCard");

        orderService.orderService(order);

    }

}
```



**運行結果**

![image-20200915114547380](https://raw.githubusercontent.com/waiting0324/TyporaImg/master/image-20200915114547380.png)




