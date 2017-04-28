/*
 * SonarQube Python Plugin
 * Copyright (C) 2011-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.python;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.sonar.python.PythonCheck.PreciseIssue;
import org.sonar.python.parser.PythonParser;

public class TestPythonVisitorRunner {

  private TestPythonVisitorRunner() {
  }

  public static void scanFile(File file, PythonVisitor... visitors) {
    PythonVisitorContext context = createContext(file);
    for (PythonVisitor visitor : visitors) {
      visitor.scanFile(context);
    }
  }

  public static List<PreciseIssue> scanFileForIssues(File file, PythonCheck check) {
    return check.scanFileForIssues(createContext(file));
  }

  private static PythonVisitorContext createContext(File file) {
    Parser<Grammar> parser = PythonParser.create(new PythonConfiguration(StandardCharsets.UTF_8));
    AstNode rootTree = parser.parse(file);
    return new PythonVisitorContext(rootTree, file, StandardCharsets.UTF_8);
  }

}