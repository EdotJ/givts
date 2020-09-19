package com.givts.app.controller;

import com.givts.app.AppApplication;
import com.givts.app.config.TestingDatabaseConfig;
import com.givts.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = {AppApplication.class, TestingDatabaseConfig.class, UserService.class})
public abstract class ControllerTestBase {
    @Mock
    HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    public abstract void testGetAll() throws Exception;

    public abstract void testGetSingle() throws Exception;

    public abstract void testDelete() throws Exception;

    public abstract void testUpdate() throws Exception;

    public abstract void testInsert() throws Exception;

    public abstract void testFailedInsert() throws Exception;
}
