package top.snowclouddrive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.zfmx.snowclouddrive.SnowCloudDriveApplication;

@SpringBootTest(classes = SnowCloudDriveApplication.class)
public class EncodeTest {
    @Test
    public void passwordTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Hello Security Develop Framework");
        System.out.println(passwordEncoder.encode("040207"));
    }
}
