package org.digyna.axtrion.system.common;

import jakarta.annotation.Nonnull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) {
        SpringContext.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return SpringContext.context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return SpringContext.context.getBean(clazz);
    }
}
