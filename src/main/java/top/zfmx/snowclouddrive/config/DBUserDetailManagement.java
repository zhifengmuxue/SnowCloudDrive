package top.zfmx.snowclouddrive.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import top.zfmx.snowclouddrive.entity.SysUser;
import top.zfmx.snowclouddrive.mapper.SysUserMapper;
/**
 * 从数据库中获取用户信息
 * 用于Spring Security的用户信息管理
 * @author zfmx
 * @version 0.0.2
 */
@Component
public class DBUserDetailManagement implements UserDetailsManager, UserDetailsPasswordService {

    private final SysUserMapper systemUserMapper;

    @Autowired
    public DBUserDetailManagement( SysUserMapper systemUserMapper) {
        this.systemUserMapper = systemUserMapper;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    /**
     * 创建用户
     * @param user 用户信息
     */
    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            throw new IllegalArgumentException("用户已存在");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(user.getUsername());
        sysUser.setPassword(user.getPassword());
        sysUser.setRole(user.getAuthorities().toString());
        sysUser.setEnable(true);
        systemUserMapper.insert(sysUser);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    /**
     * 判断用户是否存在
     * @param username 用户名
     * @return 用户是否存在
     */
    @Override
    public boolean userExists(String username) {
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(SysUser::getUsername, username);
        SysUser user = systemUserMapper.selectOne(userQueryWrapper);
        return user != null;
    }

    /**
     * 通过用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(SysUser::getUsername, username);
        SysUser user = systemUserMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isEnable())
                .credentialsExpired(false)
                .accountLocked(false)
                .roles(user.getRole())
                .build();
    }
}
