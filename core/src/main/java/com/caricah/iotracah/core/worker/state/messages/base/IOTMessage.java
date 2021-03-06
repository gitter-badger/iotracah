/*
 *
 * Copyright (c) 2015 Caricah <info@caricah.com>.
 *
 * Caricah licenses this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 *  OF ANY  KIND, either express or implied.  See the License for the specific language
 *  governing permissions and limitations under the License.
 *
 *
 *
 *
 */

package com.caricah.iotracah.core.worker.state.messages.base;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author <a href="mailto:bwire@caricah.com"> Peter Bwire </a>
 * @version 1.0 6/1/15
 */
public class IOTMessage implements Serializable {

    private UUID nodeId;
    private String cluster;
    private String authKey;
    private Serializable connectionId;

    @QuerySqlField(orderedGroups={
            @QuerySqlField.Group(name = "partition_clientid_msgid_inbound_idx", order = 4)
    })
    private long messageId;

    private String messageType;

    private Protocal protocal;

    private Serializable sessionId;

   public Serializable getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Serializable connectionId) {
        this.connectionId = connectionId;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Serializable getSessionId() {
        return sessionId;
    }

    public void setSessionId(Serializable sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getNodeId() {
        return nodeId;
    }

    public void setNodeId(UUID nodeId) {
        this.nodeId = nodeId;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Protocal getProtocal() {
        return protocal;
    }

    public void setProtocal(Protocal protocal) {
        this.protocal = protocal;
    }

    public void copyBase(IOTMessage iotMessage) {

        setProtocal(iotMessage.getProtocal());
        setSessionId(iotMessage.getSessionId());
        setAuthKey(iotMessage.getAuthKey());
        setConnectionId(iotMessage.getConnectionId());
        setNodeId(iotMessage.getNodeId());
        setCluster(iotMessage.getCluster());

    }


    @Override
    public String toString() {
        return getClass().getName() + '[' + "messageId=" + getMessageId() + ']';
    }
}
