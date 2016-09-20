package com.btcc.wsm.web.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import com.btcc.wsm.model.Role;
import org.primefaces.model.SelectableDataModel;

public class RoleDataModel extends ListDataModel<Role> implements
		SelectableDataModel<Role> {

	public RoleDataModel() {

	}

	public RoleDataModel(List<Role> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	public Role getRowData(String rowKey) {

		List<Role> roleList = (List<Role>) getWrappedData();
		Integer rowKeyInt = Integer.parseInt(rowKey);
		for (Role role : roleList) {
			if (role.getId() == rowKeyInt) {
				return role;
			}
		}
		return null;
	}

	public Object getRowKey(Role role) {

		return role.getId();
	}
}
