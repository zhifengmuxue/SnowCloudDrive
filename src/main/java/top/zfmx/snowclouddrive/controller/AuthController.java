package top.zfmx.snowclouddrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zfmx.snowclouddrive.entity.SysUser;
import top.zfmx.snowclouddrive.service.SysUserService;
/**
 * 用户认证控制器
 * 用于处理用户登录和注册请求
 * @author zfmx
 * @version 0.0.2
 */
@Controller
public class AuthController {

    private final SysUserService sysUserService;

    @Autowired
    public AuthController(SysUserService sysUserService, PasswordEncoder passwordEncoder) {
        this.sysUserService = sysUserService;
    }

    /**
     * 处理登录请求
     * @param model Model对象
     * @return 登录页面
     */
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("isRegister", false);
        return "login";
    }

    /**
     * 处理注册请求
     * @param username 用户名
     * @param password 密码
     * @param model Model对象
     * @return 登录页面
     */
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model) {
        // 检查用户名是否存在
        if (sysUserService.userExists(username)) {
            model.addAttribute("registerError", "用户名已存在");
            model.addAttribute("isRegister", true); // 设置为注册模式
            return "login"; // 返回同一页面
        }
        // 检查用户名的有效性
        if (username.length() < 6) {
            model.addAttribute("registerError", "用户名长度至少为6个字符");
            model.addAttribute("isRegister", true); // 设置为注册模式
            return "login";
        }
        // 检查密码的有效性
        if (!isPasswordValid(password)) {
            model.addAttribute("registerError", "密码无效：必须至少包含一个特殊字符和数字");
            model.addAttribute("isRegister", true); // 设置为注册模式
            return "login";
        }

        // 创建用户
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);

        sysUserService.createUser(sysUser);
        model.addAttribute("isRegister", false); // 切换到登录模式
        return "login";
    }
    /**
     * 检查密码是否有效
     * @param password 密码
     * @return 密码是否有效
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 6 &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()_+\\-=]");
    }
}
