package com.btcc.wsm.service;

import com.btcc.wsm.model.AccessLog;
import com.btcc.wsm.util.AccessLogActivity;
import com.btcc.wsm.util.AccessLogLevel;

import java.util.List;

/**
 * Created by sivak_000 on 10/9/2016.
 */
public interface AccessLogService {

    List<AccessLog> findAll();

    //AccessLog create(AccessLog accessLog);
    public void log(AccessLogActivity accessLogActivity, AccessLogLevel accessLogLevel,
                    int actorUserId, String actorUserName, String description);

}
