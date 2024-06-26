/*
 * Copyright 2000-2016 JetBrains s.r.o.
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
package com.intellij.execution.junit2.refactoring;

import com.intellij.execution.JUnitBundle;
import com.intellij.refactoring.migration.PredefinedMigrationProvider;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.net.URL;


public final class JUnit5Migration implements PredefinedMigrationProvider {

  @NotNull
  @Override
  public URL getMigrationMap() {
    return JUnit5Migration.class.getResource("JUnit4__5.xml");
  }

  @Override
  public @Nls String getDescription() {
    return JUnitBundle.message("junit5.migration.description");
  }
}
