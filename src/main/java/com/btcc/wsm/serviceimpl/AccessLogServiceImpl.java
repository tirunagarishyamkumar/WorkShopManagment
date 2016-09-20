package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.AccessLog;
import com.btcc.wsm.repository.AccessLogRepository;
import com.btcc.wsm.service.AccessLogService;
import com.btcc.wsm.util.AccessLogActivity;
import com.btcc.wsm.util.AccessLogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by siva on 10/9/2016.
 */
public class AccessLogServiceImpl implements AccessLogService {


    private static final Logger logger = LoggerFactory.getLogger(AccessLogServiceImpl.class);

    @Autowired
    private AccessLogRepository accessLogRepository;

    public List<AccessLog> findAll() {
        // TODO Auto-generated method stub
        List<AccessLog> resultList = (List<AccessLog>) accessLogRepository.findAll();
        return resultList;
    }

    public void log(AccessLogActivity accessLogActivity, AccessLogLevel accessLogLevel,
                    int actorUserId, String actorUserName, String description) {

            AccessLog accessLog = new AccessLog();
            Date currentDate = new Date();
            accessLog.setActionDate(currentDate);
            accessLog.setActivity(accessLogActivity.getActivity());
            accessLog.setLevel(accessLogLevel.getLevel());
            accessLog.setDescription(description);
            accessLog.setActorUserId(actorUserId);
            accessLog.setActorUsername(actorUserName);
            accessLog.setDeleted(false);
            accessLogRepository.save(accessLog);

        }

}
