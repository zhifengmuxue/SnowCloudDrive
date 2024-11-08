package top.zfmx.snowclouddrive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SysUser implements Serializable{
    @Serial
    private static final long serialVersionUID = 65535L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @Length(min = 2,max = 20)
    private final String username;

    @Length(min = 6,max = 20)
    private final String password;
    
    @Email
    private final String email;

    private final String role;
    private final boolean isEnable;
    private final LocalDateTime createTime;
    private final LocalDateTime updateTime;

}
