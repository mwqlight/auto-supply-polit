package com.supplychain.supplychain.service.impl;

import com.supplychain.supplychain.dto.request.LoginRequest;
import com.supplychain.supplychain.dto.response.LoginResponse;
import com.supplychain.supplychain.entity.User;
import com.supplychain.supplychain.repository.UserRepository;
import com.supplychain.supplychain.service.AuthService;
import com.supplychain.supplychain.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 查找用户
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 生成JWT令牌
        String token = jwtUtil.generateToken(user.getUsername());

        // 将令牌存储到Redis中
        redisTemplate.opsForValue().set(
            "TOKEN_" + token,
            user.getUsername(),
            expiration,
            TimeUnit.MILLISECONDS
        );

        return new LoginResponse(token, expiration);
    }

    @Override
    public void logout(String token) {
        // 从Redis中移除令牌
        redisTemplate.delete("TOKEN_" + token);
    }
}