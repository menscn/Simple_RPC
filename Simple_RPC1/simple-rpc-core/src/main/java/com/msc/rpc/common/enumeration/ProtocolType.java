package com.msc.rpc.common.enumeration;

import com.msc.rpc.common.enumeration.support.ExtensionBaseType;
import com.msc.rpc.protocol.api.Protocol;
import com.msc.rpc.protocol.wind.WindProtocol;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.common.enumeration
 * @Description: 协议类型
 * 只有wind的可选
 */
public enum ProtocolType implements ExtensionBaseType<Protocol> {
    WIND(new WindProtocol());     //Wind协议

    private Protocol protocol;

    ProtocolType(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public Protocol getInstance() {
        return protocol;
    }
}