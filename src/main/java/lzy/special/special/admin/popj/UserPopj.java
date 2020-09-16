package lzy.special.special.admin.popj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPopj extends BasePopj {

    private  Integer id;
    private  Integer  state;  //0位禁止 1为正常
    private  Integer  codeResult;
    private  String username;
    private  String password;
    private  String UserPhone;
    private  String avatar;
    private Integer sex;


}
