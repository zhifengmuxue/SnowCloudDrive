package top.zfmx.snowclouddrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zfmx.snowclouddrive.entity.File;

@Mapper
public interface FileMapper
        extends BaseMapper<File> {
}
