package com.btcc.wsm.web.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import com.btcc.wsm.model.SystemAuditTrail;
import org.primefaces.model.SelectableDataModel;

public class SystemAuditTrailDataModel extends ListDataModel<SystemAuditTrail>
		implements SelectableDataModel<SystemAuditTrail> {

	public SystemAuditTrailDataModel() {

	}

	public SystemAuditTrailDataModel(List<SystemAuditTrail> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	public SystemAuditTrail getRowData(String rowKey) {

		List<SystemAuditTrail> SystemAuditTrail = (List<SystemAuditTrail>) getWrappedData();
		Integer rowKeyInt = Integer.parseInt(rowKey);
		for (SystemAuditTrail systemAuditTrail : SystemAuditTrail) {
			if (systemAuditTrail.getId() == rowKeyInt) {
				return systemAuditTrail;
			}
		}
		return null;
	}

	public Object getRowKey(SystemAuditTrail systemAuditTrail) {

		return systemAuditTrail.getId();
	}
}
