package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.model.SystemAuditTrail;
import com.btcc.wsm.repository.SystemAuditTrailRepository;
import com.btcc.wsm.util.SystemAuditTrailActivity;
import com.btcc.wsm.util.SystemAuditTrailLevel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by siva on 10/9/2016.
 */
public class SystemAuditTrailRecordServiceImpl implements SystemAuditTrailRecordService {

    @Autowired
    SystemAuditTrailRepository systemAuditTrailRecordRepository;

    public void log(SystemAuditTrailActivity systemAuditTrailActivity, SystemAuditTrailLevel systemAuditTrailLevel,
                    int userId, String username, String description) {

            SystemAuditTrail systemAuditTrail = new SystemAuditTrail();
            Date currentDate = new Date();
            systemAuditTrail.setDate(currentDate);
            systemAuditTrail.setActivity(systemAuditTrailActivity.getActivity());
            systemAuditTrail.setLogLevel(systemAuditTrailLevel.getLevel());
            systemAuditTrail.setActorUsername(username);
            systemAuditTrail.setActorUserId(userId);
            systemAuditTrail.setDescription(description);
            systemAuditTrail.setDeleted(false);
            systemAuditTrailRecordRepository.save(systemAuditTrail);


    }


    public List<SystemAuditTrail> findAll() {

        List<SystemAuditTrail> systemAuditTrail =  systemAuditTrailRecordRepository.findALL();
        return systemAuditTrail;
    }

}
