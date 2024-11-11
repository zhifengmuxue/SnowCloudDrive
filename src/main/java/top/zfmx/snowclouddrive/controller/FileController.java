package top.zfmx.snowclouddrive.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.zfmx.snowclouddrive.entity.FileFolder;
import top.zfmx.snowclouddrive.service.FileFolderService;
import top.zfmx.snowclouddrive.service.FileService;
import top.zfmx.snowclouddrive.service.SysUserService;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {
    private final String path = "D:\\project\\SnowCloudDrive\\files";
    private final SysUserService sysUserService;
    private final FileService fileService;
    private final FileFolderService fileFolderService;
    @Autowired
    public FileController(SysUserService sysUserService, FileService fileService, FileFolderService fileFolderService) {
        this.sysUserService = sysUserService;
        this.fileService = fileService;
        this.fileFolderService = fileFolderService;
    }

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
            System.out.println(parentPath);
            if (parentPath.equals(path + "\\" + user.getUsername())) {
                fileEntity.setFolderId(null);
            } else {
                fileEntity.setFolderId(fileFolderService.getFolderIdByPath(parentPath));
            }
            fileService.save(fileEntity);
            file.transferTo(filePath);
        }
        return "redirect:/?folderId=" + fileEntity.getFolderId();
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("fileid") String fileId,
                                           @RequestHeader("User-Agent") String userAgent) throws IOException {
        top.zfmx.snowclouddrive.entity.File file = fileService.getById(fileId);
        String filename = file.getFilename();
        String filePath = file.getPath();

        File downFile = new File(filePath);

        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentLength(downFile.length());
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        filename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        if(userAgent.indexOf("MSIE") > 0) {
            builder.header("Content-Disposition", "attachment; filename=" + filename);
        }else {
            builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        }
        return builder.body(FileUtils.readFileToByteArray(downFile));
    }

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
        return "redirect:/?folderId=" + file.getFolderId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("deleteFileId") String fileId) {
        top.zfmx.snowclouddrive.entity.File file = fileService.getById(fileId);
        File deleteFile = new File(file.getPath());
        if (deleteFile.delete()) {
            fileService.deleteById(Integer.valueOf(fileId));
        }
        return "redirect:/?folderId=" ;
    }
}
