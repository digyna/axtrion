package org.digyna.axtrion.system.config;

import lombok.RequiredArgsConstructor;
import org.digyna.axtrion.system.properties.DatabaseProperties;
import org.digyna.axtrion.system.properties.DbGroup;

@RequiredArgsConstructor
public class DatabaseConfigDefault implements DatabaseConfig {
    private final DatabaseProperties properties;

    @Override
    public String getDefaultGroup() {
        return properties.getDefaultGroup();
    }

    @Override
    public DbGroup getGroup(String name) {
        var group = this.properties.getGroups().get(name);
        if (group == null) throw new IllegalArgumentException("DB group not found: " + name);
        return group;
    }
}
