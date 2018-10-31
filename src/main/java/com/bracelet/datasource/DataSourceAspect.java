package com.bracelet.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
/**
 * 有{@DataSourceChange}注解的方法，调用时会切换到指定的数据源
 * @author
 * @date 2017年10月18日
 */
@Aspect
public class DataSourceAspect {
    @Pointcut(value = "@annotation(com.bracelet.datasource.DataSourceChange)")
    private void changeDS() {
    }
    //@annotation：用于匹配当前执行方法持有指定注解的方法；
    //@DataSourceChange(slave = true)

    @Around(value = "changeDS() ", argNames = "pjp")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        DataSourceChange annotation = method.getAnnotation(DataSourceChange.class);
        boolean selectedDataSource = false;
        try {
            if (null != annotation) {
                selectedDataSource = true;
                if (annotation.slave()) {
                    DynamicDataSource.useSlave();
                } else {
                    DynamicDataSource.useMaster();
                }
            }
            retVal = pjp.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (selectedDataSource) {
                DynamicDataSource.reset();
            }
        }
        return retVal;
    }
}