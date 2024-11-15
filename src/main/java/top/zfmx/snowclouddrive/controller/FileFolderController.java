package top.zfmx.snowclouddrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zfmx.snowclouddrive.entity.FileFolder;
import top.zfmx.snowclouddrive.service.FileFolderService;
import top.zfmx.snowclouddrive.service.SysUserService;

import java.io.File;

/**
 * 文件夹控制器
 * 用于处理文件夹的创建、重命名、删除等操作
 * @author zfmx
 * @version 0.0.1
 */
@Controller
@RequestMapping("/fileFolder")
public class FileFolderController {

    @Value("${SnowCloudDrive.file.path}")
    private String path;

    private final SysUserService sysUserService;
    private final FileFolderService fileFolderService;
    @Autowired
    public FileFolderController(SysUserService sysUserService, FileFolderService fileFolderService) {
        this.sysUserService = sysUserService;
        this.fileFolderService = fileFolderService;
    }

    /**
     * 创建文件夹
     * @param folderName 文件夹名称
     * @param breadcrumb 文件夹路径
     * @return 重定向到文件夹路径
     */
    @PostMapping("/create")
    public String create(@RequestParam("folderName") String folderName,
                         @RequestParam("breadcrumb") String breadcrumb) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(breadcrumb);
        Integer OwnId = sysUserService.getIdByUsername(user.getUsername());
        String savePath = path + "\\" + user.getUsername() + "\\" + breadcrumb + folderName;

        File directory = new File(savePath);
        String parentPath = savePath.substring(0, savePath.lastIndexOf("\\"));
        Integer currentFolderId = null;
        if (!parentPath.equals(path + "\\" + user.getUsername()))
            currentFolderId = fileFolderService.getFolderIdByPath(parentPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("无法创建文件夹: " + savePath);
            }
            fileFolderService.createFolder(folderName, savePath, OwnId, currentFolderId);
        }
        if (currentFolderId == null) {
            return "redirect:/";
        }
        return "redirect:/?folderId=" + currentFolderId;
    }

    /**
     * 重命名文件夹
     * @param folderName 新文件夹名称
     * @param folderId 文件夹ID
     * @return 重定向到文件夹路径
     */
    @PostMapping("/rename")
    public String rename(@RequestParam("newFolderName") String folderName,
                         @RequestParam("folderId") Integer folderId) {
        FileFolder fileFolder = fileFolderService.getById(folderId);
        String oldPath = fileFolder.getPath();
        String newPath = oldPath.substring(0, oldPath.lastIndexOf("\\") + 1) + folderName;
        File newFile = new File(newPath);
        File oldFile = new File(oldPath);
        if (oldFile.renameTo(newFile)) {
            fileFolder.setFolderName(folderName);
            fileFolder.setPath(newPath);
            fileFolderService.updateById(fileFolder);
        }
        return "redirect:/?folderId=" + fileFolder.getParentId();
    }
    /**
     * 删除文件夹
     * @param folderId 文件夹ID
     * @return 重定向到文件夹路径
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("deleteFolderId") Integer folderId) {
        FileFolder fileFolder = fileFolderService.getById(folderId);
        String path = fileFolder.getPath();
        File file = new File(path);

        if (file.exists()) {
            deleteRecursively(file);
        }

        fileFolderService.removeById(folderId);
        if (fileFolder.getParentId() ==null){
            return "redirect:/";
        }
        return "redirect:/?folderId=" + fileFolder.getParentId();
    }

    /**
     * 递归删除文件夹
     * @param file 文件夹
     */
    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursively(f);
                }
            }
        }
        if (!file.delete()) {
            throw new RuntimeException("无法删除文件或文件夹: " + file.getAbsolutePath());
        }
    }
}
