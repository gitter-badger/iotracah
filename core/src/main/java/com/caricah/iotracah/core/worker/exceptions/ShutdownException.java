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

package com.caricah.iotracah.core.worker.exceptions;


import com.caricah.iotracah.core.worker.state.messages.base.IOTMessage;
import com.caricah.iotracah.exceptions.UnRetriableException;

/**
 * @author <a href="mailto:bwire@caricah.com"> Peter Bwire </a>
 */
public class ShutdownException extends UnRetriableException {

    private IOTMessage response = null;
    private final boolean disconnect;

    public ShutdownException(IOTMessage response){
        this.response = response;
        this.disconnect = false;
    }

    /**
     * Creates a new instance.
     */
    public ShutdownException(boolean disconnect) {
        this.disconnect = disconnect;
    }
 /**
     * Creates a new instance.
     */
    public ShutdownException() {
        this.disconnect = false;
    }

    /**
     * Creates a new instance.
     */
    public ShutdownException(String message, Throwable cause) {
        super(message, cause);
        this.disconnect = false;
    }

    /**
     * Creates a new instance.
     */
    public ShutdownException(String message) {
        super(message);
        this.disconnect = false;
    }

    /**
     * Creates a new instance.
     */
    public ShutdownException(Throwable cause) {
        super(cause);
        this.disconnect = false;
    }


    public IOTMessage getResponse() {
        return response;
    }

    public boolean isDisconnect() {
        return disconnect;
    }
}