package com.patrol.system.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录响应 VO
 *
 * @author patrol-team
 */
@Data
@Builder
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** JWT Token */
    private String token;

    /** 用户 ID */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickname;

    /** 权限标识集合 */
    private List<String> permissions;
}
