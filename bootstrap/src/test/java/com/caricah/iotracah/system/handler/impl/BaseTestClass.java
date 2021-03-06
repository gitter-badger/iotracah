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

package com.caricah.iotracah.system.handler.impl;

import org.apache.log4j.BasicConfigurator;
import org.junit.*;
import org.junit.rules.ExpectedException;

import org.junit.Rule;



/**
 * @author <a href="mailto:bwire@caricah.com"> Peter Bwire </a>
 * @version 1.0 8/9/15
 */
public abstract class BaseTestClass {


    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() throws Exception {

        //Configure logging.
        BasicConfigurator.configure();


        internalSetUp();
    }

    public abstract void internalSetUp() throws Exception;

    @After
    public void tearDown() throws Exception {

        internalTearDown();
    }

    public abstract void internalTearDown() throws Exception;
    }
