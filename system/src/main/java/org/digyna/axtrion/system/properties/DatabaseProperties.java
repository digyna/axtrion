package org.digyna.axtrion.system.properties;

import lombok.Data;
import lombok.Singular;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {
    /**
     * Lets you choose which connection group to use if no other is specified.
     */
    private String defaultGroup = "default";

    /**
     * The default database connection.
     *
     * @var Map<string, DbGroup>
     */
    @Singular("group")
    private Map<String, DbGroup> groups = new HashMap<>() {{
        put("default", DbGroup.builder().build());
    }};
}
