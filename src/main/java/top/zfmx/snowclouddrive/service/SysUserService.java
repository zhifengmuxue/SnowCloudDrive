package top.zfmx.snowclouddrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zfmx.snowclouddrive.entity.SysUser;

public interface SysUserService
        extends IService<SysUser> {
    boolean userExists(String username);
    void createUser(SysUser user);
    Integer getIdByUsername(String username);
}
