package com.anny.wsm.serviceimpl;

import com.anny.wsm.model.SystemAuditTrail;
import com.anny.wsm.repository.SystemAuditTrailRepository;
import com.anny.wsm.service.SystemAuditTrailRecordService;
import com.anny.wsm.util.SystemAuditTrailActivity;
import com.anny.wsm.util.SystemAuditTrailLevel;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
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
