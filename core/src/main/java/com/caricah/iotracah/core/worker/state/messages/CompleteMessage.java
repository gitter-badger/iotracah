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
public final class CompleteMessage extends IOTMessage {

    public static final String MESSAGE_TYPE = "PUBCOMP";

    private final int qos =0;


    public static CompleteMessage from(long messageId) {
        if (messageId < 1) {
            throw new IllegalArgumentException("messageId: " + messageId + " (expected: > 1)");
        }
        return new CompleteMessage(messageId);
    }

    private CompleteMessage(long messageId) {
        setMessageType(MESSAGE_TYPE);
        setMessageId(messageId);
    }




    public int getQos() {
        return qos;
    }

    @Override
    public String toString() {
        return getClass().getName() + '[' + "messageId=" + getMessageId() + ']';
    }
}
