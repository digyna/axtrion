package org.digyna.axtrion.system.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbPool {
    @Builder.Default
    private Integer maximumPoolSize = 20;
    @Builder.Default
    private Integer minimumIdle=5;
    @Builder.Default
    private Integer connectionTimeoutMs=30000;
    @Builder.Default
    private Integer idleTimeoutMs=600000;
    @Builder.Default
    private Integer maxLifetimeMs=1800000;
    @Builder.Default
    private String poolName = "Hikari";
    @Builder.Default
    private boolean autoCommit = true;
    @Builder.Default
    private Integer leakDetectionThresholdMs=0;
}
