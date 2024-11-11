package top.zfmx.snowclouddrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/fileFolder")
public class FileFolderController {

    private final String path = "D:\\project\\SnowCloudDrive\\files";
    private final SysUserService sysUserService;
    private final FileFolderService fileFolderService;
    @Autowired
    public FileFolderController(SysUserService sysUserService, FileFolderService fileFolderService) {
        this.sysUserService = sysUserService;
        this.fileFolderService = fileFolderService;
    }

    @PostMapping("/create")
    public String create(@RequestParam("folderName") String folderName,
                         @RequestParam(value = "currentFolderId", required = false) Integer currentFolderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer OwnId = sysUserService.getIdByUsername(user.getUsername());
        String savePath = path + "\\" + user.getUsername() + "\\" + folderName;
        File directory = new File(savePath);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("无法创建文件夹: " + savePath);
            }
            fileFolderService.createFolder(folderName, savePath, OwnId, currentFolderId);
        }
        return "redirect:/?folderId=" + currentFolderId;
    }

    @PostMapping("/rename")
    public String rename(@RequestParam("newFolderName") String folderName,
                         @RequestParam("folderId") Integer folderId) {
        FileFolder fileFolder = fileFolderService.getById(folderId);
        String oldPath = fileFolder.getPath();
        String newPath = oldPath.substring(0, oldPath.lastIndexOf("\\") + 1) + folderName;
        System.out.println(oldPath + " " + newPath);
        File newFile = new File(newPath);
        File oldFile = new File(oldPath);
        if (oldFile.renameTo(newFile)) {
            fileFolder.setFolderName(folderName);
            fileFolder.setPath(newPath);
            fileFolderService.updateById(fileFolder);
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("deleteFolderId") Integer folderId) {
        FileFolder fileFolder = fileFolderService.getById(folderId);
        String path = fileFolder.getPath();
        File file = new File(path);

        if (file.exists()) {
            deleteRecursively(file);
        }

        fileFolderService.removeById(folderId);
        return "redirect:/";
    }

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
