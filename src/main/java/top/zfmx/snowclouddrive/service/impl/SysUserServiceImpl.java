package top.zfmx.snowclouddrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.zfmx.snowclouddrive.config.DBUserDetailManagement;
import top.zfmx.snowclouddrive.entity.SysUser;
import top.zfmx.snowclouddrive.mapper.SysUserMapper;
import top.zfmx.snowclouddrive.service.SysUserService;
/**
 * 用户服务实现类
 * 用于实现用户的创建、查询等操作
 * @author zfmx
 * @version 0.0.1
 */
@Service
public class SysUserServiceImpl
        extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    private final PasswordEncoder passwordEncoder;
    private final DBUserDetailManagement dbUserDetailManagement;
    private final SysUserMapper sysUserMapper;
    @Autowired
    public SysUserServiceImpl(PasswordEncoder passwordEncoder, DBUserDetailManagement dbUserDetailManagement, SysUserMapper sysUserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.dbUserDetailManagement = dbUserDetailManagement;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 判断用户是否存在
     * @param username 用户名
     * @return 是否存在
     */
    @Override
    public boolean userExists(String username) {
        return dbUserDetailManagement.userExists(username);
    }

    /**
     * 创建用户
     * @param user 用户
     */
    @Override
    public void createUser(SysUser user) {
        UserDetails userDetails = User.withUsername(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles("USER")
                .build();
        dbUserDetailManagement.createUser(userDetails);
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    @Override
    public Integer getIdByUsername(String username){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername, username);
        return sysUserMapper.selectOne(queryWrapper).getId();
    }
}

