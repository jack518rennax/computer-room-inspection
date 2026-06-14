package com.patrol.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.patrol.common.R;
import com.patrol.framework.security.JwtTokenUtil;
import com.patrol.system.dto.LoginDTO;
import com.patrol.system.entity.SysUser;
import com.patrol.system.mapper.SysUserMapper;
import com.patrol.system.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录认证 Controller
 *
 * @author patrol-team
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final SysUserMapper sysUserMapper;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public R<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // 1. 认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );

            // 2. 查询用户信息
            SysUser user = sysUserMapper.selectOne(
                    new LambdaQueryWrapper<SysUser>()
                            .eq(SysUser::getUsername, loginDTO.getUsername()));

            // 3. 提取权限（排除 ROLE_ 前缀的角色编码）
            List<String> permissions = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(p -> !p.startsWith("ROLE_"))
                    .collect(Collectors.toList());

            // 4. 生成 JWT Token
            String token = jwtTokenUtil.generateToken(loginDTO.getUsername());

            // 5. 构建响应
            LoginVO vo = LoginVO.builder()
                    .token(token)
                    .userId(user != null ? user.getId() : null)
                    .username(loginDTO.getUsername())
                    .nickname(user != null ? user.getNickname() : null)
                    .permissions(permissions)
                    .build();

            return R.ok("登录成功", vo);

        } catch (BadCredentialsException e) {
            return R.error(401, "用户名或密码错误");
        } catch (AuthenticationException e) {
            return R.error(401, "认证失败: " + e.getMessage());
        }
    }
}
