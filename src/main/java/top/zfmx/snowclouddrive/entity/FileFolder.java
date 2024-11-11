package top.zfmx.snowclouddrive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
