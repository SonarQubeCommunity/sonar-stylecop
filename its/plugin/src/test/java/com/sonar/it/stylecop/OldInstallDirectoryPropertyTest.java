/*
 * SonarSource :: StyleCop :: ITs :: Plugin
 * Copyright (C) 2014 SonarSource
 * sonarqube@googlegroups.com
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
package com.sonar.it.stylecop;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.BuildResult;
import com.sonar.orchestrator.build.SonarRunner;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class OldInstallDirectoryPropertyTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  @BeforeClass
  public static void init() {
    orchestrator.resetData();
  }

  @Test
  public void test() {
    SonarRunner build = Tests.createSonarRunner()
      .setProjectDir(new File("projects/OldInstallDirectoryPropertyTest/"))
      .setProjectKey("project")
      .setProjectName("project")
      .setProjectVersion("1.0")
      .setSourceDirs(".")
      .setProperty("sonar.modules", "Project1")
      .setProperty("sonar.stylecop.msBuildPath", orchestrator.getConfiguration().getString("sonar.stylecop.msBuildPath"))
      .setProperty("sonar.stylecop.projectFilePath", new File("projects/OldInstallDirectoryPropertyTest/Project1/Project1.csproj").getAbsolutePath())
      .setProperty("sonar.stylecop.installDirectory", new File(orchestrator.getConfiguration().getString("sonar.stylecop.styleCopDllPath")).getParentFile().getAbsolutePath())
      .setProperty("sonar.sourceEncoding", "UTF-8")
      .setProfile("profile");

    BuildResult buildResult = orchestrator.executeBuild(build);
    assertThat(buildResult.getLogs()).contains("Use the new property \"sonar.stylecop.styleCopDllPath\" instead of the deprecated \"sonar.stylecop.installDirectory\".");
  }

}
