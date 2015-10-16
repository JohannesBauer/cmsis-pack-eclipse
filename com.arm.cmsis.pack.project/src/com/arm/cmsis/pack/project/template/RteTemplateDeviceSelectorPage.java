/*******************************************************************************
* Copyright (c) 2015 ARM Ltd. and others
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* ARM Ltd and ARM Germany GmbH - Initial API and implementation
*******************************************************************************/

package com.arm.cmsis.pack.project.template;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.ui.templateengine.IWizardDataPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;

import com.arm.cmsis.pack.CpPlugIn;
import com.arm.cmsis.pack.ICpPackManager;
import com.arm.cmsis.pack.info.ICpDeviceInfo;
import com.arm.cmsis.pack.project.Messages;
import com.arm.cmsis.pack.rte.devices.IRteDeviceItem;
import com.arm.cmsis.pack.ui.wizards.RteDeviceSelectorPage;

/**
 *  Device selector page for new project wizard
 */
public class RteTemplateDeviceSelectorPage extends RteDeviceSelectorPage implements IWizardDataPage {

	protected IWizardPage next;
	
	
	public RteTemplateDeviceSelectorPage() {
		super();
	}

	@Override
	public Map<String, String> getPageData() {
		Map<String, String> data = null;
		
		ICpDeviceInfo deviceInfo  = getDeviceInfo();
		if(deviceInfo != null) {
			data = deviceInfo.attributes().getAttributesAsMap();
			RteProjectTemplate.setSelectedDeviceInfo(deviceInfo);
		}
		if(data == null) {
			data = new HashMap<String,String>();
		}
		return data;
	}

	@Override
	public void createControl(Composite parent) {

		ICpPackManager packManager = CpPlugIn.getPackManager();
		if(packManager == null) {
			updateStatus(Messages.RteTemplateDeviceSelectorPage_NoPackManagerIsAvailble);
			return;
		}
			
		IRteDeviceItem devices = packManager.getDevices();
		setDevices(devices);
		// 	this will update status
		setDeviceInfo(RteProjectTemplate.getSelectedDeviceInfo());

		super.createControl(parent);
		if(devices == null || !devices.hasChildren()){
			updateStatus(Messages.RteTemplateDeviceSelectorPage_NoDevicesAreAvailable);
		}
	}

	
	@Override
	public void setNextPage(IWizardPage next) {
		this.next= next;
	}

	@Override
	public IWizardPage getNextPage() {
		if(next != null) {
			return next;
		}
		return super.getNextPage();
	}

}