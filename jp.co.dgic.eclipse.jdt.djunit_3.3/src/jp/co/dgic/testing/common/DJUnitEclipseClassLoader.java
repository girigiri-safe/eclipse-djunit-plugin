/**
 * Copyright (C)2004 dGIC Corporation.
 * 
 * This file is part of djUnit plugin.
 * 
 * djUnit plugin is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * djUnit plugin is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * jcoverage; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 *  
 */
package jp.co.dgic.testing.common;

import jp.co.dgic.eclipse.jdt.internal.junit.runner.MessageSender;
import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitMessages;

public class DJUnitEclipseClassLoader extends DJUnitClassLoader {

	/** MessageSender */
	protected MessageSender messageSender;

	public DJUnitEclipseClassLoader(ClassLoader parent) {
		super(parent);
	}

	public void initialize(String[] args) {
		// set client host & port
		messageSender = new MessageSender();
		messageSender.initialize(args);
		messageSender.connect();
	}

	public void shutDownMessageSender() {
		if (messageSender != null) {
			messageSender.shutDown();
		}
	}

	public void sendMessage(String message) {
		if (message == null) {
			return;
		}
		if (messageSender != null) {
			messageSender.sendMessage(message);
		}
	}

	protected byte[] getModifiedClass(String name) throws ClassNotFoundException {
		byte[] bytes = super.getModifiedClass(name);
		sendMessage(DJUnitMessages.getFormattedString("DJUnitTestCaseClassLoader.message.modifiedclass", name));
		return bytes;
	}
}