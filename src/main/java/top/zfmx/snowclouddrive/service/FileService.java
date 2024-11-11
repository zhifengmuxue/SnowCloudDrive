package top.zfmx.snowclouddrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zfmx.snowclouddrive.entity.File;

import java.util.List;

public interface FileService
        extends IService<File> {
    void deleteById(Integer id);
    List<File> listByOwnAndFold(Integer userId, Integer folderId);

}
