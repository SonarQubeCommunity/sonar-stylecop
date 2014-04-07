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
import org.junit.Test;
import org.sonar.api.config.PropertyDefinition;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class StyleCopPluginTest {

  @Test
  public void test() {
    List extensions = new StyleCopPlugin().getExtensions();

    assertThat(nonProperties(extensions)).containsOnly(
      StyleCopRuleRepository.class,
      StyleCopSensor.class);

    assertThat(propertyKeys(extensions)).containsOnly(
      "sonar.stylecop.msBuildPath",
      "sonar.stylecop.styleCopDllPath",
      "sonar.stylecop.projectFilePath",
      "sonar.stylecop.timeoutMinutes",
      "sonar.stylecop.ignoredHungarianPrefixes");
  }

  private List nonProperties(List extensions) {
    ImmutableList.Builder builder = ImmutableList.builder();
    for (Object extension : extensions) {
      if (!(extension instanceof PropertyDefinition)) {
        builder.add(extension);
      }
    }
    return builder.build();
  }

  private List<String> propertyKeys(List extensions) {
    ImmutableList.Builder<String> builder = ImmutableList.builder();
    for (Object extension : extensions) {
      if (extension instanceof PropertyDefinition) {
        PropertyDefinition property = (PropertyDefinition) extension;
        builder.add(property.key());
      }
    }
    return builder.build();
  }

}
