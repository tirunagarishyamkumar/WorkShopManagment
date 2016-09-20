package com.btcc.wsm.service;

import com.btcc.wsm.model.SystemAuditTrail;
import com.btcc.wsm.util.SystemAuditTrailActivity;
import com.btcc.wsm.util.SystemAuditTrailLevel;

import java.util.List;

/**
 * Created by sivak_000 on 10/9/2016.
 */
public interface SystemAuditTrailRecordService {

    public void log(SystemAuditTrailActivity systemAuditTrailActivity, SystemAuditTrailLevel systemAuditTrailLevel,
                    int userId, String username, String description);

    List<SystemAuditTrail> findAll();
}
