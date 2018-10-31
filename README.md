项目打包  mvn package 生成war包 直接放在tomcat 的webapp 下运行tomcat就可以了

如果直接在myeclipse运行直接tomcat:run

配置读写分离需要在接口中的方法上增加下面这句注解，实现里也要加
    @DataSourceChange(slave = true)

http://localhost:8080/bracelet-server/druid/index.html
可查看druid的一些详细信息  

在查询的方法上增加
@DataSourceChange(slave = true)

