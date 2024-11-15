package top.zfmx.snowclouddrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.zfmx.snowclouddrive.entity.File;
import top.zfmx.snowclouddrive.entity.FileFolder;
import top.zfmx.snowclouddrive.service.FileFolderService;
import top.zfmx.snowclouddrive.service.FileService;
import top.zfmx.snowclouddrive.service.SysUserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 主页控制器
 * 用于处理主页的请求
 * 包括文件夹和文件的展示
 * @author zfmx
 * @version 0.0.1
 */
@Controller
public class IndexController {

    private final FileFolderService fileFolderService;
    private final FileService fileService;
    private final SysUserService sysUserService;

    @Autowired
    public IndexController(FileFolderService fileFolderService, FileService fileService, SysUserService sysUserService) {
        this.fileFolderService = fileFolderService;
        this.fileService = fileService;
        this.sysUserService = sysUserService;
    }

    /**
     * 主页
     * @param folderId 文件夹ID
     * @param model 模型
     * @return 主页视图
     */
    @GetMapping("/")
    public String index(@RequestParam(value = "folderId", required = false) Integer folderId, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer ownId = sysUserService.getIdByUsername(user.getUsername());

        List<FileFolder> folders = fileFolderService.listByOwnAndFold(ownId,null);
        List<File> files = fileService.listByOwnAndFold(ownId, null);

        FileFolder currentFolder;
        List<FileFolder> breadcrumb = new ArrayList<>();

        if (folderId != null) {
            currentFolder = fileFolderService.getById(folderId);
            folders = fileFolderService.listByOwnAndFold(ownId, folderId);
            files = fileService.listByOwnAndFold(ownId, folderId);

            // 构建面包屑导航
            FileFolder folder = currentFolder;
            while (folder != null) {
                breadcrumb.add(folder);
                folder = fileFolderService.getById(folder.getParentId());
            }

            // 如果当前有文件夹，确保反转顺序，这样从根到子文件夹依次排列
            Collections.reverse(breadcrumb);

            List<FileFolder> subFolders = fileFolderService.listByParentId(folderId);
            model.addAttribute("subFolders", subFolders);
        } else {
            currentFolder = null;
        }

        model.addAttribute("currentFolder", currentFolder);
        model.addAttribute("files", files);
        model.addAttribute("folders", folders);
        model.addAttribute("breadcrumb", breadcrumb);

        return "index";
    }

}