/*
 * Copyright 2013 Olivier Croisier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.binout.restangular.server.rest.application;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.InstantiateException;
import org.restlet.ext.jaxrs.JaxRsApplication;
import org.restlet.ext.jaxrs.ObjectFactory;

/**
 * = Application launcher
 *
 * Main method parameters :
 *
 * - args[0] is the port used by application
 *
 * TIP : default port is +8000+
 *
 */
public class RestangularServer {

    private final Component component;

    public static void main(String[] args) throws Exception {
        int port = 8000;
        if (args != null && args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new RestangularServer(port).component.start();
    }

    public RestangularServer(int port) {
        CdiObjectFactory objectFactory = bootCdi();
        component = initRestlet(port, objectFactory);

        System.out.println("Server started on port " + port);
        System.out.println("Application is now available on http://localhost:"+port+"/web/index.html");
    }

    private static Component initRestlet(int port, CdiObjectFactory objectFactory) {
        Component component = new Component();
        Server server = new Server(Protocol.HTTP, port);
        component.getServers().add(server);

        component.getClients().add(Protocol.CLAP);
        component.getDefaultHost().attach("/web", new RestangularApplication());

        JaxRsApplication jaxRsApplication = new JaxRsApplication(component.getContext().createChildContext());
        jaxRsApplication.add(new TodoServiceApplication());
        jaxRsApplication.setObjectFactory(objectFactory);
        component.getDefaultHost().attach("/rest", jaxRsApplication);
        return component;
    }

    private static CdiObjectFactory bootCdi() {
        CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        ContextControl contextControl = cdiContainer.getContextControl();
        contextControl.startContexts();
        return new CdiObjectFactory();
    }

    private static class CdiObjectFactory implements ObjectFactory {

        @Override
        public <T> T getInstance(Class<T> aClass) throws InstantiateException {
            return BeanProvider.getContextualReference(aClass, false);
        }
    }
}
