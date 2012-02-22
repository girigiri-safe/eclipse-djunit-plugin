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
package jp.co.dgic.testing.common.coverage;

import com.jcoverage.coverage.Instrumentation;

public class CoverageEntry {

	private String name;
	private double branchCoverageRate;
	private long files;
	private long lines;
	private long hitLines;

	public CoverageEntry(String name) {
		this.name = name;
	}

	public long getBranchCoverageRate() {
		if (files == 0) {
			return 100;
		}
		return (long) Math.round((double) getBranchCoverage() * (double) 100d);
	}

	public double getBranchCoverage() {
		return getScaledRate(branchCoverageRate / (double) files);
	}

	public void setBranchCoverage(double branchCoverage) {
		if (files == 0) {
			branchCoverageRate = branchCoverage;
			return;
		}
		branchCoverageRate = branchCoverage * (double) files;
	}

	public String getName() {
		return name;
	}

	public long getLineCoverageRate() {
		if (lines == 0) {
			return 100;
		}
		return (long) Math.round((double) getLineCoverage() * (double) 100.0);
	}

	public double getLineCoverage() {
		if (lines == 0) {
			return 1d;
		}
		return getScaledRate((double) hitLines / (double) lines);
	}

	public int getLengthOfLineCoverageRate() {
		return new Long(getLineCoverageRate()).toString().length();
	}

	public void addBranchCoverageRate(double branchCoverageRate) {
		this.branchCoverageRate += branchCoverageRate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addHitLines(long hitLines) {
		this.hitLines += hitLines;
	}

	public void addLines(long lines) {
		this.lines += lines;
	}

	public long getFiles() {
		return files;
	}

	public long getLines() {
		return lines;
	}

	public long getHitLines() {
		return hitLines;
	}

	public void addInstrumentation(Instrumentation i) {
		addHitLines(i.getCoverage().keySet().size());
		addLines(i.getSourceLineNumbers().size());

// version 0.8.5
//		if (i.getLineCoverageRate() > 0d) {
			addBranchCoverageRate(i.getBranchCoverageRate());
//		}

		files++;

	}

	public void addInstrumentation(CoverageEntry entry) {
		addBranchCoverageRate(entry.getBranchCoverage());
		addLines(entry.getLines());
		addHitLines(entry.getHitLines());
		files++;
	}

	private double getScaledRate(double rate) {
		Double d = new Double(rate);
		String string = d.toString();
		int index = string.indexOf('.');
		if (index < 0) return rate;
		if (string.substring(index + 1).length() < 2) return rate;
		return Double.valueOf(string.substring(0, index + 3)).doubleValue();
	}

	public String toString() {
		return name
			+ " ["
			+ getLineCoverage()
			+ "("
			+ getLineCoverageRate()
			+ "%)] ["
			+ getBranchCoverage()
			+ "("
			+ getBranchCoverageRate()
			+ "%)] "
			+ super.toString();
	}
}
