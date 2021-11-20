package com.localbrand.utils;

import com.localbrand.entity.RoleDetail;
import com.localbrand.entity.User;
import com.localbrand.repository.RoleDetailRepository;
import com.localbrand.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Role_Utils {

    private final UserRepository userRepository;
    private final RoleDetailRepository roleDetailRepository;

    public Boolean checkRole(String email, Integer idModule, Integer idAction){

        User user = this.userRepository.findFirstByEmailEqualsIgnoreCase(email).orElse(null);

        if(Objects.isNull(user)){
            return false;
        }

        RoleDetail roleDetail = this.roleDetailRepository.findByIdRoleAndIdModuleAndIdAction(user.getIdRole(), idModule.longValue()
                , idAction).orElse(null);

        if(Objects.isNull(roleDetail)){
            return false;
        }else{
            return roleDetail.getAccept();
        }

    };
}
