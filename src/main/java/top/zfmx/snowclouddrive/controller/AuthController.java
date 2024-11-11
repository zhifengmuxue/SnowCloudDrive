package top.zfmx.snowclouddrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.zfmx.snowclouddrive.entity.SysUser;
import top.zfmx.snowclouddrive.service.SysUserService;

@Controller
public class AuthController {

    private final SysUserService sysUserService;

    @Autowired
    public AuthController(SysUserService sysUserService, PasswordEncoder passwordEncoder) {
        this.sysUserService = sysUserService;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/register")
    public String register(@Param("username") String username,
                         @Param("password") String password,
                         Model model){
        System.out.println("arrive here");
        if (sysUserService.userExists(username)) {
            model.addAttribute("registerError", "用户名已存在");
            return "login";
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);

        sysUserService.createUser(sysUser);
        return "login";
    }


}
