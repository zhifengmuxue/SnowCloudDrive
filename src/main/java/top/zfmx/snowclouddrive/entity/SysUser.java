package top.zfmx.snowclouddrive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "用户名不能为空")
    @Size(min = 6, message = "用户名长度至少为6个字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度至少为6个字符")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "密码必须包含至少一个特殊字符和数字")
    private String password;

    private String role;
    private boolean isEnable;
    private final LocalDateTime createTime;
    private final LocalDateTime updateTime;

}
