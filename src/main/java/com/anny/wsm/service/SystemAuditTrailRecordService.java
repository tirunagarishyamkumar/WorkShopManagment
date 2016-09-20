package com.anny.wsm.service;

import com.anny.wsm.model.SystemAuditTrail;
import com.anny.wsm.util.SystemAuditTrailActivity;
import com.anny.wsm.util.SystemAuditTrailLevel;

import java.util.List;

/**
 * Created by sivak_000 on 10/9/2016.
 */
public interface SystemAuditTrailRecordService {

    public void log(SystemAuditTrailActivity systemAuditTrailActivity, SystemAuditTrailLevel systemAuditTrailLevel,
                    int userId, String username, String description);

    List<SystemAuditTrail> findAll();
}
