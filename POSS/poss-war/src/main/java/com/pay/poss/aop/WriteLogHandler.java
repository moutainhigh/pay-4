//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.pay.poss.aop;


import com.pay.poss.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Component
@Aspect
public class WriteLogHandler {
    private static Logger logger = LoggerFactory.getLogger(WriteLogHandler.class);
    private static String lineSplit = "\n|\t\t\t";

    public WriteLogHandler() {
    }

    @Pointcut("@annotation(com.pay.poss.aop.WriteLog) and execution(* com.pay...*.*（..）)")
    public void pointCut() {
    }

    public void before(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        this.printStart(stringBuilder);
        stringBuilder.append(lineSplit);
        stringBuilder.append("进入方法:");
        this.buildMethodInfo(joinPoint, stringBuilder);
        this.printEnd(stringBuilder);
        logger.info(stringBuilder.toString());
    }

    public void afterReturn(JoinPoint joinPoint, Object result) {
        StringBuilder stringBuilder = new StringBuilder();
        this.printStart(stringBuilder);
        stringBuilder.append(lineSplit);
        stringBuilder.append("退出方法:");
        this.buildMethodInfo(joinPoint, stringBuilder);
        stringBuilder.append(lineSplit);
        stringBuilder.append("返回值:" + JsonUtil.entity2Json(result));
        this.printEnd(stringBuilder);
        logger.info(stringBuilder.toString());
    }

    public void afterException(JoinPoint joinPoint, Throwable ex) {
        if(this.isPublic(joinPoint)) {
            StringBuilder stringBuilder = new StringBuilder();
            this.printStart(stringBuilder);
            stringBuilder.append(lineSplit);
            stringBuilder.append("发生异常:");
            this.buildMethodInfo(joinPoint, stringBuilder);
            this.printEnd(stringBuilder);
            logger.warn(stringBuilder.toString());
        }
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        boolean hasException = false;
        StringBuilder stringBuilder = new StringBuilder();
        this.printStart(stringBuilder);
        stringBuilder.append(lineSplit);
        stringBuilder.append("执行方法:");
        this.buildMethodInfo(joinPoint, stringBuilder);
        stringBuilder.append(lineSplit);

        Object var7;
        try {
            Object ex = joinPoint.proceed();
            stringBuilder.append("返回值:" + JsonUtil.entity2Json(ex));
            var7 = ex;
        } catch (Throwable var11) {
            stringBuilder.append("发生异常:");
            throw var11;
        } finally {
            stringBuilder.append(lineSplit);
            stringBuilder.append("耗时(微秒):");
            stringBuilder.append(System.currentTimeMillis() - startTime);
            this.printEnd(stringBuilder);
            logger.info(stringBuilder.toString());
        }

        return var7;
    }

    private boolean isPublic(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        String modify = Modifier.toString(method.getModifiers());
        return "public".equals(modify);
    }

    private void buildMethodInfo(JoinPoint joinPoint, StringBuilder stringBuilder) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        stringBuilder.append(method.toGenericString());
        stringBuilder.append(lineSplit);
        stringBuilder.append("参数依次为:");

        for(int i = 0; i < joinPoint.getArgs().length; ++i) {
            stringBuilder.append(lineSplit);
            stringBuilder.append("\t\t");
            stringBuilder.append(i);
            stringBuilder.append(":");
            stringBuilder.append(JsonUtil.entity2Json(joinPoint.getArgs()[i]));
        }

    }

    private void printStart(StringBuilder stringBuilder) {
        stringBuilder.append("\n+-----------------");
    }

    private void printEnd(StringBuilder stringBuilder) {
        stringBuilder.append("\n\\__________________");
    }
}
