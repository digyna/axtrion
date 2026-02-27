package org.digyna.axtrion.system.properties;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbGroup {
    @Builder.Default
    private String hostname = "localhost";
    @Builder.Default
    private String username = "";
    @Builder.Default
    private String password = "";
    @Builder.Default
    private String database = "";
    @Builder.Default
    private String DBDriver = "PostgreSQL";
    @Builder.Default
    private boolean DBDebug = true;
    @Builder.Default
    private String charset = "utf8";
    @Builder.Default
    private Integer port = 5432;
    public DbPool pool = DbPool.builder().build();
}
