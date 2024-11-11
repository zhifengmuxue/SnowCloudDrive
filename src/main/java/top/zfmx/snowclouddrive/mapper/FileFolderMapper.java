package top.zfmx.snowclouddrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zfmx.snowclouddrive.entity.FileFolder;

@Mapper
public interface FileFolderMapper
        extends BaseMapper<FileFolder> {
}
