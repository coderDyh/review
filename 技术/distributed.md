分布式事务

刚性事务：
   强一致性，、cp理论（一致性，分区容错）
   应用场景：  
        银行交易结算：跨行扣款交易、资金拆借、发工资
        航空机票：座位预留、票价计算和扣除、乘客信息登记
        企业库存：库存记录

   
   技术方案：2pc  or 3pc
     xa协议（tm（事务管理器）和rm（资源管理器））
     2pc：prepare、commit
     3pc：3PC在2PC的第一阶段和第二阶段中插入一个准备阶段。
             CanCommit：询问是否可以执行事务提交操作
	      PreCommit：询问是否执行预提交操作
               DoCommit：
     缺点：不解决2pc的阻塞问题，但引入数据不一致问题
     
      Java事务规范：
          JTA：java  事务API
          JTS：Java事务服务


柔性事务
   最终一致性
   
   应用场景：
      电商：库存、物流。支付
      金融：跨行转账，保证最终一致性
      用户管理：积分信息、用户权限等
   
   技术方案：
       业务层技术方案
       TCC补偿（try-confirm-cancel）

       缺点： 
           1.空回滚，解决方案执行try的方法
           2.数据幂等，解决方案：记录事务执行状态
           3.悬挂，即超时。解决方案：通过执行状态判断
   
      Saga:本地事务
           1.基于事件方式：串行执行，按照顺序，监听事件状态
           2.基于命令方式：leader协调


Mq事务消息
    producer 发送half 消息，mq server 发送ack确认；
    producer执行逻辑，发送commit到mq server，mq server接受，标记可消费，consumer可以消费该half消息
     mq收到rollback，把half消息删除，consumer不可消费该消息。




