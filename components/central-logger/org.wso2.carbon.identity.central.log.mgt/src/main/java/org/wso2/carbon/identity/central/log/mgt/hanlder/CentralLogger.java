/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.central.log.mgt.hanlder;

import org.wso2.carbon.identity.core.bean.context.MessageContext;
import org.wso2.carbon.identity.event.IdentityEventException;
import org.wso2.carbon.identity.event.event.Event;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;
import org.wso2.carbon.utils.CarbonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.wso2.carbon.CarbonConstants.DISABLE_LEGACY_LOGS;
import static org.wso2.carbon.CarbonConstants.LogEventConstants;
import static org.wso2.carbon.identity.event.IdentityEventConstants.Event.PUBLISH_AUDIT_LOG;
import static org.wso2.carbon.identity.event.IdentityEventConstants.Event.PUBLISH_DIAGNOSTIC_LOG;

/**
 * Central log event handler for audit, and diagnostic logs.
 */
public class CentralLogger extends AbstractEventHandler {

    @Override
    public boolean isEnabled(MessageContext messageContext) {

        if (super.isEnabled(messageContext)) {
            return !Boolean.parseBoolean(System.getProperty(DISABLE_LEGACY_LOGS));
        }
        return false;
    }

    @Override
    public void handleEvent(Event event) throws IdentityEventException {

        String eventName = event.getEventName();
        Map<String, Object> eventProperties = event.getEventProperties();

        // This central log event handler handles only audit logs and diagnostic logs.
        switch (eventName) {
            case PUBLISH_AUDIT_LOG:
                CarbonUtils.publishAuditLogs(eventProperties);
                break;
            case PUBLISH_DIAGNOSTIC_LOG:
                break;
            default:
                //TODO
        }
    }

//    private void handleAuditLogs(Map<String, Object> eventProperties) {
//
//        Map<String, String> auditLogProperties = new HashMap<>();
//        auditLogProperties.put(LogEventConstants.LOG_ID, generateLogUUID());
//        auditLogProperties.put(LogEventConstants.CLIENT_COMPONENT, eventProperties.get(LogEventConstants.CLIENT_COMPONENT).toString());
//        auditLogProperties.put(LogEventConstants.INITIATOR_ID, eventProperties.get(LogEventConstants.INITIATOR_ID).toString());
//        auditLogProperties.put(LogEventConstants.INITIATOR_NAME, eventProperties.get(LogEventConstants.INITIATOR_NAME).toString());
//        auditLogProperties.put(LogEventConstants.INITIATOR_TYPE, eventProperties.get(LogEventConstants.INITIATOR_TYPE).toString());
//        auditLogProperties.put(LogEventConstants.EVENT_TYPE, eventProperties.get(LogEventConstants.EVENT_TYPE).toString());
//        auditLogProperties.put(LogEventConstants.TARGET_ID, eventProperties.get(LogEventConstants.TARGET_ID).toString());
//        auditLogProperties.put(LogEventConstants.TARGET_NAME, eventProperties.get(LogEventConstants.TARGET_NAME).toString());
//        auditLogProperties.put(LogEventConstants.TARGET_TYPE, eventProperties.get(LogEventConstants.TARGET_TYPE).toString());
//        auditLogProperties.put(LogEventConstants.DATA_CHANGE, eventProperties.get(LogEventConstants.DATA_CHANGE).toString());
//        CarbonUtils.publishAuditLogs(auditLogProperties);
//    }

    private void handleDiagnosticLogs() {

    }

    private String generateLogUUID() {

        return UUID.randomUUID().toString();
    }
}
