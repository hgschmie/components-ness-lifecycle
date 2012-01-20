/**
 * Copyright (C) 2012 Ness Computing, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.likeness.lifecycle.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.likeness.lifecycle.DefaultLifecycle;
import com.likeness.lifecycle.Lifecycle;

/**
 * Very simple module to allow declarative inclusion of the Lifecycle.
 */
public class LifecycleModule extends AbstractModule
{
    private final Class<? extends Lifecycle> lifecycleClass;

    public LifecycleModule()
    {
        this(DefaultLifecycle.class);
    }

    public LifecycleModule(final Class<? extends Lifecycle> lifecycleClass)
    {
        this.lifecycleClass = lifecycleClass;
    }

    @Override
    public void configure()
    {
        bind(Lifecycle.class).to(lifecycleClass).in(Scopes.SINGLETON);

        LifecycleAnnotationFinder finder = new LifecycleAnnotationFinder();

        // Enable @OnStage lifecycle declarations
        bind (LifecycleAnnotationFinder.class).toInstance(finder);
        bind (LifecycleAnnotationWirer.class).asEagerSingleton();
        binder().bindListener(Matchers.any(), finder);
    }
}
