/**
 * Copyright (C)2004 dGIC Corporation.
 *
 * This file is part of djUnit plugin.
 *
 * djUnit plugin is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * djUnit plugin is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with djUnit plugin; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 */
package jp.co.dgic.eclipse.jdt.internal.coverage.report.html;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;

import jp.co.dgic.testing.common.util.DJUnitUtil;

import com.jcoverage.coverage.Instrumentation;
import com.jcoverage.coverage.reporting.Main;

public class HtmlReportGenerator {

	static File serializationFile;

	static File[] srcDirs;

	static File destDir;

	private static MockProgressMonitor mockMonitor = new MockProgressMonitor();

	public static void main(String[] args) throws Exception {
		generate(args, mockMonitor);
	}

	public static void generate(String[] args, IDJUnitProgressMonitor monitor) throws Exception {

		// serFile
		serializationFile = new File(args[1]);

		if (!serializationFile.exists()) {
			throw new Exception("[ERROR] serialization file " + serializationFile + " does not exist");
		}
		if (serializationFile.isDirectory()) {
			throw new Exception("[ERROR] serialization file " + serializationFile + " cannot be a directory");
		}

		// source directories
		srcDirs = getSourceDirectories(args[3]);

		if (srcDirs.length == 0) {
			throw new Exception("[ERROR] source directory argument value");
		}

		for (int i = 0; i < srcDirs.length; i++) {
			if (!srcDirs[i].exists()) {
				throw new Exception("[ERROR] source directory " + srcDirs[i] + " does not exist");
			}
			if (srcDirs[i].isFile()) {
				throw new Exception("[ERROR] source directory " + srcDirs[i] + " should be a directory, not a file");
			}
		}

		// output directory
		destDir = new File(args[5]);

		if (destDir.exists() && destDir.isFile()) {
			throw new Exception("[ERROR] destination directory " + destDir + " already exists and is a file");
		}
		destDir.mkdirs();

		// Copy gifs
		File imagesDir = new File(destDir, "images");
		imagesDir.mkdirs();
		copyResource("red.gif", imagesDir);
		copyResource("green.gif", imagesDir);

		HtmlReportDriver driver = new HtmlReportDriver(srcDirs);

		InputStream is = new FileInputStream(serializationFile);
		ObjectInputStream objects = new ObjectInputStream(is);
		Map coverageData = (Map) objects.readObject();
		int total = coverageData.size();
		int count = 1;

		monitor.beginTask("(0/" + total + ")", total);

		for (Iterator it = coverageData.entrySet().iterator(); it.hasNext();) {
			if (monitor.isCanceled()) {
				break;
			}
			Map.Entry entry = (Map.Entry) it.next();
			monitor.setTaskName("(" + count + "/" + total + ")");
			monitor.subTask(entry.getKey().toString());
			driver.addInstrumentation((String) entry.getKey(), (Instrumentation) entry.getValue());
			count++;
			monitor.worked(1);
		}

		driver.generate(destDir);

		monitor.done();
	}

	static String toPackage(String clzName) {
		int i = clzName.lastIndexOf('.');
		if (i == -1) {
			return "default";
		} else {
			return clzName.substring(0, i);
		}
	}

	static byte[] buf = new byte[2 ^ 12];

	static void copyResource(String resname, File dir) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(dir, resname));
		InputStream in = new BufferedInputStream(Main.class.getResourceAsStream(resname));
		while (true) {
			int n = in.read(buf, 0, buf.length);
			if (n == -1) {
				break;
			}

			fos.write(buf, 0, n);
		}
		in.close();
		fos.close();
	}

	private static File[] getSourceDirectories(String sourceDirectories) {
		if (sourceDirectories == null) {
			return new File[0];
		}

		String[] directoryPaths = splitPath(sourceDirectories);
		File[] dirs = new File[directoryPaths.length];

		for (int i = 0; i < dirs.length; i++) {
			dirs[i] = new File(directoryPaths[i]);
		}

		return dirs;
	}

	private static String[] splitPath(String sourceDirectories) {
		return DJUnitUtil.splitValue(sourceDirectories);
	}

	private static class MockProgressMonitor implements IDJUnitProgressMonitor {

		public void beginTask(String name, int totalWork) {
		}

		public void done() {
		}

		public void internalWorked(double work) {
		}

		public boolean isCanceled() {
			return false;
		}

		public void setCanceled(boolean value) {
		}

		public void setTaskName(String name) {
		}

		public void subTask(String name) {
		}

		public void worked(int work) {
		}
	}
}