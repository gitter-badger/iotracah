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

package com.caricah.iotracah.core.worker.state.messages;


import com.caricah.iotracah.core.worker.state.messages.base.IOTMessage;

/**
 * @author <a href="mailto:bwire@caricah.com"> Peter Bwire </a>
 */
public final class ConnectMessage extends IOTMessage {

    public static final String MESSAGE_TYPE = "CONNECT";

    private final boolean dup;
    private final int qos;
    private final boolean retain;
    private final String protocolName;
    private final int protocalLevel;
    private final boolean cleanSession;
    private final boolean annonymousSession;
    private String userName;
    private String password;
    private int keepAliveTime;
    private final String sourceHost;
    private boolean hasWill;
    private boolean retainWill;
    private int willQos;
    private String willTopic;
    private String willMessage;
    private String clientId;






    public static ConnectMessage from( boolean dup, int qos, boolean retain, String protocolName,
                                       int protocalLevel, boolean cleanSession, boolean annonymousSession, String clientIdentifier,
                                       String userName, String password, int keepAliveTime, String sourceHost

    ) {

        return new ConnectMessage(dup, qos, retain, protocolName, protocalLevel, cleanSession, annonymousSession,
                clientIdentifier, userName, password, keepAliveTime, sourceHost);
    }

    private ConnectMessage(boolean dup, int qos, boolean retain, String protocolName, int protocalLevel,
                           boolean cleanSession, boolean annonymousSession, String clientIdentifier, String userName, String password,
                           int keepAliveTime, String sourceHost) {
        setMessageType(MESSAGE_TYPE);
        this.dup = dup;
        this.qos = qos;
        this.retain = retain;
        this.protocolName = protocolName;
        this.protocalLevel = protocalLevel;
        this.cleanSession = cleanSession;
        setClientId(clientIdentifier);
        this.userName = userName;
        this.password = password;
        this.keepAliveTime = keepAliveTime;
        this.sourceHost = sourceHost;
        this.annonymousSession = annonymousSession;
    }



    public boolean isDup() {
        return dup;
    }

    public int getQos() {
        return qos;
    }

    public boolean isRetain() {
        return retain;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public int getProtocalLevel() {
        return protocalLevel;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public boolean isAnnonymousSession() {
        return annonymousSession;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public boolean isHasWill() {
        return hasWill;
    }

    public void setHasWill(boolean hasWill) {
        this.hasWill = hasWill;
    }

    public boolean isRetainWill() {
        return retainWill;
    }

    public void setRetainWill(boolean retainWill) {
        this.retainWill = retainWill;
    }

    public int getWillQos() {
        return willQos;
    }

    public void setWillQos(int willQos) {
        this.willQos = willQos;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        this.willTopic = willTopic;
    }

    public String getWillMessage() {
        return willMessage;
    }

    public void setWillMessage(String willMessage) {
        this.willMessage = willMessage;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    @Override
    public String toString() {
        return getClass().getName() + '['
                + "messageId=" + getMessageId() +","
                + "username=" + getUserName() +","
                + "clientId=" + getClientId() +","
                + "isCleanSession=" + isCleanSession() +","
                +  ']';
    }
}
