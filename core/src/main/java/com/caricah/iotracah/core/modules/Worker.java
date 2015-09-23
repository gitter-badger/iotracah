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

package com.caricah.iotracah.core.modules;

import com.caricah.iotracah.core.worker.state.messages.base.IOTMessage;
import com.caricah.iotracah.core.modules.base.IOTBaseHandler;
import com.caricah.iotracah.core.modules.base.server.ServerRouter;
import com.caricah.iotracah.core.worker.state.Messenger;
import com.caricah.iotracah.core.worker.state.SessionResetManager;
import com.caricah.iotracah.system.BaseSystemHandler;

/**
 * @author <a href="mailto:bwire@caricah.com"> Peter Bwire </a>
 * @version 1.0 8/10/15
 */
public abstract class Worker extends IOTBaseHandler {


    private Datastore datastore;

    private Messenger messenger;

    private ServerRouter serverRouter;

    private SessionResetManager sessionResetManager;

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public ServerRouter getServerRouter() {
        return serverRouter;
    }

    public void setServerRouter(ServerRouter serverRouter) {
        this.serverRouter = serverRouter;
    }

    public SessionResetManager getSessionResetManager() {
        return sessionResetManager;
    }

    public void setSessionResetManager(SessionResetManager sessionResetManager) {
        this.sessionResetManager = sessionResetManager;
    }

    /**
     * Sole receiver of all messages from the servers.
     *
     * @param IOTMessage
     */
    @Override
    public void onNext(IOTMessage IOTMessage) {

    }

    /**
     * Internal method to handle all activities related to ensuring the worker routes
     * responses or new messages to the server for connected devices to receive their messages.
     *
     * @param IOTMessage
     */
    public final void pushToServer(IOTMessage IOTMessage){

        getServerRouter().route(IOTMessage.getCluster(), IOTMessage.getNodeId(), IOTMessage);

    }

    @Override
    public int compareTo(BaseSystemHandler baseSystemHandler) {

        if(null == baseSystemHandler)
            throw new NullPointerException("You can't compare a null object.");

        if(baseSystemHandler instanceof Worker)
            return 0;
        else if(baseSystemHandler instanceof Server)
            return 1;
        else
            return -1;
    }
}