/*
 * SonarQube StyleCop Plugin
 * Copyright (C) 2014 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.stylecop;

import com.google.common.collect.ImmutableList;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import java.util.List;

public class StyleCopPlugin extends SonarPlugin {

  public static final String LANGUAGE_KEY = "cs";
  public static final String REPOSITORY_KEY = "stylecop";

  public static final String STYLECOP_MSBUILD_PATH_PROPERTY_KEY = "sonar.stylecop.msBuildPath";
  public static final String STYLECOP_DLL_PATH_PROPERTY_KEY = "sonar.stylecop.styleCopDllPath";
  public static final String STYLECOP_PROJECT_FILE_PATH_PROPERTY_KEY = "sonar.stylecop.projectFilePath";
  public static final String STYLECOP_TIMEOUT_MINUTES_PROPERTY_KEY = "sonar.stylecop.timeoutMinutes";
  public static final String STYLECOP_OLD_INSTALL_DIRECTORY_PROPERTY_KEY = "sonar.stylecop.installDirectory";

  private static final String CATEGORY = "C#";
  private static final String SUBCATEGORY = "StyleCop";

  @Override
  public List getExtensions() {
    return ImmutableList.of(
      StyleCopRuleRepository.class,
      StyleCopSensor.class,

      PropertyDefinition.builder(STYLECOP_MSBUILD_PATH_PROPERTY_KEY)
        .name("Path to MsBuild.exe")
        .description("Example: C:/Program Files/MSBuild/12.0/Bin/MsBuild.exe")
        .defaultValue("C:/Program Files/MSBuild/12.0/Bin/MsBuild.exe")
        .category(CATEGORY)
        .subCategory(SUBCATEGORY)
        .onQualifiers(Qualifiers.PROJECT, Qualifiers.MODULE)
        .build(),
      PropertyDefinition.builder(STYLECOP_DLL_PATH_PROPERTY_KEY)
        .name("Path to StyleCop.dll")
        .description("Example: C:/Program Files/StyleCop 4.7/StyleCop.dll")
        .defaultValue("C:/Program Files/StyleCop 4.7/StyleCop.dll")
        .category(CATEGORY)
        .subCategory(SUBCATEGORY)
        .onQualifiers(Qualifiers.PROJECT, Qualifiers.MODULE)
        .build(),
      PropertyDefinition.builder(STYLECOP_PROJECT_FILE_PATH_PROPERTY_KEY)
        .name("Project file")
        .description("Example: C:/Users/MyUser/Documents/Visual Studio 2013/Projects/MyProject/Project1/Project1.csproj")
        .category(CATEGORY)
        .subCategory(SUBCATEGORY)
        .onlyOnQualifiers(Qualifiers.PROJECT, Qualifiers.MODULE)
        .build(),
      PropertyDefinition.builder(STYLECOP_TIMEOUT_MINUTES_PROPERTY_KEY)
        .name("Timeout in minutes")
        .description("Example: 60 for a one hour timeout")
        .defaultValue("60")
        .category(CATEGORY)
        .subCategory(SUBCATEGORY)
        .onQualifiers(Qualifiers.PROJECT, Qualifiers.MODULE)
        .build());
  }

}
