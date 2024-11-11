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
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SysUser implements Serializable{
    @Serial
    private static final long serialVersionUID = 65535L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;

    private String role;
    private boolean isEnable;
    private final LocalDateTime createTime;
    private final LocalDateTime updateTime;

}
