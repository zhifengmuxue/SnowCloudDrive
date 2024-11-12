package top.zfmx.snowclouddrive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件夹实体类
 * 用于存储文件夹信息
 * 包括文件夹名、路径、所有者ID、父文件夹ID、创建时间、更新时间
 * @author zfmx
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileFolder implements Serializable {
    @Serial
    private static final long serialVersionUID = 65535L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String folderName;
    private String path;
    private Integer ownerId;
    private Integer parentId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
