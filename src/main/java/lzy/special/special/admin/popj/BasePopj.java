package lzy.special.special.admin.popj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePopj implements Serializable {

    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String updataUser;

}
