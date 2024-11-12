package top.zfmx.snowclouddrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.zfmx.snowclouddrive.entity.FileFolder;

import java.util.List;

public interface FileFolderService
        extends IService<FileFolder> {
    void createFolder(String folderName, String path, Integer ownId, Integer parentId);
    List<FileFolder> listByParentId(Integer folderId);
    List<FileFolder> listByOwnAndFold(Integer userId, Integer folderId);
    Integer getFolderIdByPath(String parentPath);
}
