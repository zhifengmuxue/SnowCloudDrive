package top.zfmx.snowclouddrive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    private final boolean isEnable;
    private final LocalDateTime createTime;
    private final LocalDateTime updateTime;

}
