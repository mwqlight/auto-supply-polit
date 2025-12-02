package com.supplychain.supplychain.controller;

import com.supplychain.supplychain.dto.request.LoginRequest;
import com.supplychain.supplychain.dto.response.LoginResponse;
import com.supplychain.supplychain.service.AuthService;
import com.supplychain.supplychain.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "认证接口", description = "用户认证相关接口")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "用户登录", description = "用户登录获取访问令牌")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);
            return ResponseEntity.ok(ResponseUtil.success(loginResponse));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseUtil.error(401, "登录失败: " + e.getMessage()));
        }
    }

    @Operation(summary = "用户登出", description = "用户登出系统")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            if (token != null) {
                authService.logout(token);
            }
            return ResponseEntity.ok(ResponseUtil.success("登出成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseUtil.error("登出失败: " + e.getMessage()));
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}