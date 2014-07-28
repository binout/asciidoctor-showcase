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

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;

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
public class RestangularComponent extends Component {

    public static void main(String[] args) throws Exception {
        int port = 8000;
        if (args != null && args[0] != null) {
            port = Integer.parseInt(args[0]);
        }
        new RestangularComponent(port).start();
    }

    public RestangularComponent(int port) {
        Server server = new Server(Protocol.HTTP, port);
        getServers().add(server);

        getClients().add(Protocol.CLAP);
        getDefaultHost().attach("/web", new RestangularApplication());

        JaxRsApplication jaxRsApplication = new JaxRsApplication(getContext());
        jaxRsApplication.add(new TodoServiceApplication());
        getDefaultHost().attach("/rest", jaxRsApplication);

        System.out.println("Server started on port 8000.");
        System.out.println("Application is now available on http://localhost:8000/web/index.html");
    }
}
