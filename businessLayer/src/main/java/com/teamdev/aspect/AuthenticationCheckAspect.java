package com.teamdev.aspect;

import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.AuthenticationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationCheckAspect {

    @Autowired
    private AuthenticationService authenticationService;

    @Pointcut(
            value = "execution(* com.teamdev.service.*.*(..))" +
                    " && !execution(* com.teamdev.service.AuthenticationService.signUp(..)) " +
                    " && !execution(* com.teamdev.service.AuthenticationService.logIn(..)) " +
                    " && !execution(* com.teamdev.service.AuthenticationService.checkToken(..)) " +
                    "&& args(accessToken,userId, ..)"
    )
    public void authenticationPointcut(String accessToken, UserId userId) {}

    @Around(value = "authenticationPointcut(accessToken, userId)")
    public Object validationAspect(ProceedingJoinPoint joinPoint, String accessToken, UserId userId) throws Throwable {

        authenticationService.checkToken(accessToken, userId);
        return joinPoint.proceed();
    }
}
