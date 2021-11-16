package com.localbrand.repository;

import com.localbrand.entity.RoleDetail;
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
    Optional<RoleDetail> findByIdRoleAndIdModuleAndIdAction(Integer idRole, Integer idModule, Integer idAction);
}