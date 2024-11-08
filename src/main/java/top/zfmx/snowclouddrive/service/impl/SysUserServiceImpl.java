package top.zfmx.snowclouddrive.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zfmx.snowclouddrive.entity.SysUser;
import top.zfmx.snowclouddrive.mapper.SysUserMapper;
import top.zfmx.snowclouddrive.service.SysUserService;

@Service
public class SysUserServiceImpl
        extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {
}

