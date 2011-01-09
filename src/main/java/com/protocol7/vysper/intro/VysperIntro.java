package com.protocol7.vysper.intro;
/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */


import java.io.File;

import org.apache.vysper.mina.TCPEndpoint;
import org.apache.vysper.storage.StorageProviderRegistry;
import org.apache.vysper.storage.inmemory.MemoryStorageProviderRegistry;
import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.authorization.AccountManagement;
import org.apache.vysper.xmpp.modules.extension.xep0049_privatedata.PrivateDataModule;
import org.apache.vysper.xmpp.modules.extension.xep0054_vcardtemp.VcardTempModule;
import org.apache.vysper.xmpp.modules.extension.xep0119_xmppping.XmppPingModule;
import org.apache.vysper.xmpp.modules.extension.xep0202_entity_time.EntityTimeModule;
import org.apache.vysper.xmpp.server.XMPPServer;

public class VysperIntro {

    public static void main(String[] args) throws Exception {

        XMPPServer server = new XMPPServer("vysper.org");
        
        StorageProviderRegistry providerRegistry = new MemoryStorageProviderRegistry();

        AccountManagement accountManagement = (AccountManagement) providerRegistry.retrieve(AccountManagement.class);

        Entity user = EntityImpl.parseUnchecked("user@vysper.org");
        accountManagement.addUser(user, "password");

        server.setStorageProviderRegistry(providerRegistry);

        server.addEndpoint(new TCPEndpoint());

        server.setTLSCertificateInfo(new File("keystore.jks"), "boguspw");

        server.start();
        System.out.println("Vysper server is running...");

        server.addModule(new EntityTimeModule());
        server.addModule(new VcardTempModule());
        server.addModule(new XmppPingModule());
        server.addModule(new PrivateDataModule());
    }
}
