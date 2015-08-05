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

public class TimeoutTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  @BeforeClass
  public static void init() {
    orchestrator.resetData();
  }

  @Test
  public void should_fail_with_instant_timeout() {
    SonarRunner build = Tests.createSonarRunner()
      .setProjectDir(new File("projects/TimeoutTest/"))
      .setProjectKey("project")
      .setProjectName("project")
      .setProjectVersion("1.0")
      .setSourceDirs(".")
      .setProperty("sonar.modules", "Project1")
      .setProperty("sonar.stylecop.msBuildPath", orchestrator.getConfiguration().getString("sonar.stylecop.msBuildPath"))
      .setProperty("sonar.stylecop.styleCopDllPath", orchestrator.getConfiguration().getString("sonar.stylecop.styleCopDllPath"))
      .setProperty("sonar.stylecop.projectFilePath", new File("projects/TimeoutTest/Project1/Project1.csproj").getAbsolutePath())
      .setProperty("sonar.stylecop.timeoutMinutes", "0")
      .setProperty("sonar.sourceEncoding", "UTF-8")
      .setProfile("profile");

    BuildResult buildResult = orchestrator.executeBuildQuietly(build);
    assertThat(buildResult.getStatus()).isEqualTo(1);
    assertThat(buildResult.getLogs()).contains("StyleCop's execution timed out. Increase the timeout by setting \"sonar.stylecop.timeoutMinutes\" property.");
  }

}
