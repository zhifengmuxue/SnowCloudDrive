package top.zfmx.snowclouddrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zfmx.snowclouddrive.entity.File;
import top.zfmx.snowclouddrive.mapper.FileMapper;
import top.zfmx.snowclouddrive.service.FileService;

import java.util.List;

/**
 * 文件服务实现类
 * 用于实现文件的上传、下载、删除等操作
 * @author zfmx
 * @version 0.0.1
 */
@Service
public class FileServiceImpl
        extends ServiceImpl<FileMapper, File>
        implements FileService {
    private final FileMapper fileMapper;
    @Autowired
    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    @Override
    public void deleteById(Integer id) {
        fileMapper.deleteById(id);
    }

    @Override
    public List<File> listByOwnAndFold(Integer userId, Integer folderId) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(File::getOwnerId, userId);
        if (folderId != null) {
            queryWrapper.lambda().eq(File::getFolderId, folderId);
        } else {
            queryWrapper.lambda().isNull(File::getFolderId);
        }
        return fileMapper.selectList(queryWrapper);
    }


}
