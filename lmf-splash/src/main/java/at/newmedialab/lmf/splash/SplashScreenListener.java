/**
 * Copyright (C) 2013 Salzburg Research.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.newmedialab.lmf.splash;

import java.awt.GraphicsEnvironment;
import java.awt.SplashScreen;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * Hide the Splash screen when Tomcat has started.
 * <p/>
 * Author: Sebastian Schaffert
 */
public class SplashScreenListener extends SplashScreenUpdaterBase implements LifecycleListener {

    /**
     * Acknowledge the occurrence of the specified event.
     *
     * @param event LifecycleEvent that has occurred
     */
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if(event.getType().equals(Lifecycle.AFTER_START_EVENT)) {
            if(!GraphicsEnvironment.isHeadless()) {
                SplashScreen splashScreen = SplashScreen.getSplashScreen();
                if(splashScreen != null) {
                    try {
                        splashScreen.close();
                    } catch (IllegalStateException e) {
                        
                    }
                }
            }
        } else if(event.getType().equals(Lifecycle.BEFORE_START_EVENT)) {
            if(!GraphicsEnvironment.isHeadless()) {
                showStatus("Starting Apache Tomcat ...");
                showProgress(0);
            }
        }
    }

}