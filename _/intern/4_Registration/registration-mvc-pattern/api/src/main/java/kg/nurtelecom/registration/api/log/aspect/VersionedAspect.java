package kg.nurtelecom.registration.api.log.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import kg.nurtelecom.registration.api.log.annotation.Versioned;
import kg.nurtelecom.registration.api.util.SecurityUtil;
import kg.nurtelecom.registration.api.log.service.VersionService;
import kg.nurtelecom.registration.common.enums.Action;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class VersionedAspect {
    private final VersionService service;

    private final ObjectMapper objectMapper;
    private final ExpressionParser parser;
    private final EvaluationContext context;

    public VersionedAspect(VersionService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
        parser = new SpelExpressionParser();
        context = new StandardEvaluationContext();
    }

    @Around("@annotation(kg.nurtelecom.registration.api.log.annotation.Versioned)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Versioned versioned = method.getAnnotation(Versioned.class);

        try {
            Object result = joinPoint.proceed();
            Object[] args = joinPoint.getArgs();

            versioning(versioned, result, args);
            return result;
        } catch (Throwable e) {
            throw e;
        }
    }

    public void versioning(Versioned versioned, Object result, Object[] args) {
        Class<?> clazz = versioned.entity();
        Action action = versioned.action();
        long dataId = getDataId(versioned.entityId(), result, args);
        String username = SecurityUtil.getCurrentUsername();
        String changes = convertObjectToJson(result);
        service.versioning(clazz, action, username, dataId, changes);
    }

    private Long getDataId(String expression, Object result, Object[] args) {
        context.setVariable("return", result);
        context.setVariable("parameters", args);
        return parser.parseExpression(expression).getValue(context, Long.class);
    }

    public String convertObjectToJson(Object object) {
        if (object == null) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException jpe) {
            return "{}";
        }
    }
}
