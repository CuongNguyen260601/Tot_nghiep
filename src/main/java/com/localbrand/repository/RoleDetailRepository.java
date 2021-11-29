package com.localbrand.repository;

import com.localbrand.entity.RoleDetail;
import com.localbrand.entity.RoleMapModule;
import com.localbrand.entity.RoleModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleDetailRepository extends JpaRepository<RoleDetail, Long>, JpaSpecificationExecutor<RoleDetail> {


    @Query(
            " select new RoleModule (ma.idModule, ma.idAction) from RoleDetail r " +
                    " join ModuleAction ma on r.idModuleAction = ma.idModuleAction " +
                    " join Module m on m.idModule = ma.idModule " +
                    " where r.accept = true and r.idRole = :idRole and m.idModule = :idModule"
    )
    List<RoleModule> findByIdRoleAndIdModule(Integer idRole, Integer idModule);

    @Query(
            " select r from RoleDetail r " +
                    " join ModuleAction ma on r.idModuleAction = ma.idModuleAction " +
                    " join Module m on m.idModule = ma.idModule " +
                    " where r.idRole = :idRole and m.idModule = :idModule and ma.idAction = :idAction"
    )
    Optional<RoleDetail> findByIdRoleAndIdModuleAndIdAction(Integer idRole, Long idModule, Integer idAction);

    @Query(
            "select new RoleMapModule (m.idModule, m.nameModule, a.idAction, a.nameAction, r.idRole, r.nameRole, rd.idRoleDetail, rd.accept) " +
                    " from RoleDetail rd " +
                    " join Role r on rd.idRole = r.idRole " +
                    " join ModuleAction ma on ma.idModuleAction = rd.idModuleAction " +
                    " join Module m on ma.idModule = m.idModule " +
                    " join Action a on a.idAction = ma.idAction" +
                    " where r.idRole = 2 "+
                    " order by m.idModule ASC "
    )
    List<RoleMapModule> findAllRoleDetail();
}