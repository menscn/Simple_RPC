package com.msc.rpc.autoconfig;

import com.msc.rpc.config.ApplicationConfig;
import com.msc.rpc.config.ClusterConfig;
import com.msc.rpc.config.ProtocolConfig;
import com.msc.rpc.config.RegistryConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.autoconfig
 * @Description:
 */
@ConfigurationProperties(prefix = "rpc")
@Data
public class RPCProperties {
    private ApplicationConfig applicationConfig;

    private ProtocolConfig protocolConfig;

    private ClusterConfig clusterConfig;

    private RegistryConfig registryConfig;
}