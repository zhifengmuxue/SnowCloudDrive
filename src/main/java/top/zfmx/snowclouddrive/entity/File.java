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
@NoArgsConstructor
@AllArgsConstructor
public class File implements Serializable {
    @Serial
    private static final long serialVersionUID = 65535L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String filename;
    private String path;
    private String size;
    private Integer ownerId;
    private Integer folderId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public void AutoSize(long size) {
        if (size > 1024 * 1024) {
            this.size = size / 1024 / 1024 + " MB";
        } else if (size > 1024) {
            this.size = size / 1024 + " KB";
        } else {
            this.size = size + " B";
        }
    }

}
