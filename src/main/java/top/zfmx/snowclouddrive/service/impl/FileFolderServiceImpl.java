package top.zfmx.snowclouddrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zfmx.snowclouddrive.entity.FileFolder;
import top.zfmx.snowclouddrive.mapper.FileFolderMapper;
import top.zfmx.snowclouddrive.service.FileFolderService;

import java.util.List;

/**
 * 文件夹服务实现类
 * 用于实现文件夹的创建、查询等操作
 * @author zfmx
 * @version 0.0.1
 */
@Service
public class FileFolderServiceImpl
        extends ServiceImpl<FileFolderMapper, FileFolder>
        implements FileFolderService {
    private final FileFolderMapper fileFolderMapper;

    @Autowired
    public FileFolderServiceImpl(FileFolderMapper fileFolderMapper) {
        this.fileFolderMapper = fileFolderMapper;
    }

    @Override
    public void createFolder(String folderName, String path, Integer ownId, Integer parentId) {
        FileFolder fileFolder = new FileFolder();
        fileFolder.setFolderName(folderName);
        fileFolder.setOwnerId(ownId);
        fileFolder.setParentId(parentId);
        fileFolder.setPath(path);
        fileFolderMapper.insert(fileFolder);
    }

    @Override
    public List<FileFolder> listByParentId(Integer folderId) {
        QueryWrapper<FileFolder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileFolder::getParentId, folderId);
        return fileFolderMapper.selectList(queryWrapper);
    }

    @Override
    public List<FileFolder> listByOwnAndFold(Integer userId, Integer folderId) {
        QueryWrapper<FileFolder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileFolder::getOwnerId, userId);
        if (folderId != null) {
            queryWrapper.lambda().eq(FileFolder::getParentId, folderId);
        } else {
            queryWrapper.lambda().isNull(FileFolder::getParentId);
        }
        return fileFolderMapper.selectList(queryWrapper);
    }

    @Override
    public Integer getFolderIdByPath(String parentPath) {
        QueryWrapper<FileFolder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileFolder::getPath, parentPath);
        return fileFolderMapper.selectOne(queryWrapper).getId();
    }

}
