package org.digyna.axtrion.system.config;

import org.digyna.axtrion.system.properties.DbGroup;

public interface DatabaseConfig {
    String getDefaultGroup();
    DbGroup getGroup(String name);
}
