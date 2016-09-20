package com.anny.wsm.service;

import com.anny.wsm.model.AccessLog;
import com.anny.wsm.util.AccessLogActivity;
import com.anny.wsm.util.AccessLogLevel;

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
