package com.msc.rpc.api.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author mSc
 * @version 1.0
 * @Package com.msc.rpc.api.domian
 * @Description: 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String userName;
}
