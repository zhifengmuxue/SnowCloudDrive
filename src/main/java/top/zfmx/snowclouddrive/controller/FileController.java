package top.zfmx.snowclouddrive.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zfmx.snowclouddrive.SnowCloudDriveApplication;
import top.zfmx.snowclouddrive.service.FileFolderService;
import top.zfmx.snowclouddrive.service.FileService;
import top.zfmx.snowclouddrive.service.SysUserService;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件控制器
 * 用于处理文件上传、下载、重命名、删除等请求
 * @see SysUserService
 * @version 0.0.2
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Value("${SnowCloudDrive.file.path}")
    private String path;
    private final SysUserService sysUserService;
    private final FileService fileService;
    private final FileFolderService fileFolderService;
    @Autowired
    public FileController(SysUserService sysUserService, FileService fileService, FileFolderService fileFolderService) {
        this.sysUserService = sysUserService;
        this.fileService = fileService;
        this.fileFolderService = fileFolderService;
    }

    /**
     * 处理文件上传请求
     * @param fileName 文件名
     * @param file 文件
     * @param breadcrumb 文件路径
     * @return 重定向到文件所在文件夹
     * @throws IOException IO异常
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("fileName") String fileName,
                         @RequestParam("fileInput") MultipartFile file,
                         @RequestParam(value = "breadcrumb", required = false) String breadcrumb)
            throws IOException {
        top.zfmx.snowclouddrive.entity.File fileEntity = new top.zfmx.snowclouddrive.entity.File();
        if(!file.isEmpty()) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String savePath = path +"\\"+ user.getUsername() + "\\" + breadcrumb;

            File filePath = new File(savePath + fileName);
            if(!filePath.getParentFile().exists()) {
                if(!filePath.getParentFile().mkdirs())
                    throw new IOException("创建目录失败");
            }

            fileEntity.setPath(savePath + fileName);
            fileEntity.setFilename(fileName);
            fileEntity.AutoSize(file.getSize());
            fileEntity.setOwnerId(sysUserService.getIdByUsername(user.getUsername()));
            String parentPath = savePath.substring(0, savePath.lastIndexOf("\\"));
            if (parentPath.equals(path + "\\" + user.getUsername())) {
                fileEntity.setFolderId(null);
            } else {
                fileEntity.setFolderId(fileFolderService.getFolderIdByPath(parentPath));
            }
            fileService.save(fileEntity);
            file.transferTo(filePath);
        }
        if(fileEntity.getFolderId() == null) {
            return "redirect:/";
        }
        return "redirect:/?folderId=" + fileEntity.getFolderId();
    }

    /**
     * 处理文件下载请求
     * @param fileId 文件ID
     * @param userAgent 用户代理
     * @return 文件下载
     * @throws IOException IO异常
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("fileid") String fileId,
                                           @RequestHeader("User-Agent") String userAgent) throws IOException {
        top.zfmx.snowclouddrive.entity.File file = fileService.getById(fileId);
        File downFile = new File(file.getPath());

        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentLength(downFile.length());
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        String filename = URLEncoder.encode(file.getFilename(), StandardCharsets.UTF_8);
        if(userAgent.indexOf("MSIE") > 0) {
            builder.header("Content-Disposition", "attachment; filename=" + filename);
        }else {
            builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        }
        return builder.body(FileUtils.readFileToByteArray(downFile));
    }

    /**
     * 处理文件重命名请求
     * @param fileId 文件ID
     * @param newName 新文件名
     * @return 重定向到文件所在文件夹
     */
    @PostMapping("/rename")
    public String rename(@RequestParam("fileId") String fileId,
                         @RequestParam("newName") String newName) {
        top.zfmx.snowclouddrive.entity.File file = fileService.getById(fileId);
        String path = file.getPath();
        String newPath = path.substring(0, path.lastIndexOf("\\") + 1) + newName;
        File newFile = new File(newPath);
        File oldFile = new File(path);
        if (oldFile.renameTo(newFile)) {
            file.setFilename(newName);
            file.setPath(newPath);
            fileService.updateById(file);
        }
        if (file.getFolderId() == null) {
            return "redirect:/";
        }
        return "redirect:/?folderId=" + file.getFolderId();
    }

    /**
     * 处理文件删除请求
     * @param fileId 文件ID
     * @return 重定向到文件所在文件夹
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("deleteFileId") String fileId) {
        top.zfmx.snowclouddrive.entity.File file = fileService.getById(fileId);
        File deleteFile = new File(file.getPath());
        if (deleteFile.delete()) {
            fileService.deleteById(Integer.valueOf(fileId));
        }
        return "redirect:/?folderId=" ;
    }

    /**
     * 处理文件分享请求
     * @param userAgent 用户代理
     * @param id 文件ID
     * @return 文件下载
     * @throws IOException IO异常
     */
    @GetMapping("/share")
    public ResponseEntity<byte[]> share(@RequestHeader("User-Agent") String userAgent,
                                        @RequestParam("id") Integer id) throws IOException {
        top.zfmx.snowclouddrive.entity.File file = fileService.getById(id);
        File downFile = new File(file.getPath());

        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentLength(downFile.length());
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        String filename = URLEncoder.encode(file.getFilename(), StandardCharsets.UTF_8);
        if(userAgent.indexOf("MSIE") > 0) {
            builder.header("Content-Disposition", "attachment; filename=" + filename);
        }else {
            builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        }
        return builder.body(FileUtils.readFileToByteArray(downFile));
    }
}
