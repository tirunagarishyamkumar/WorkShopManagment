package com.anny.wsm.web.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.anny.wsm.model.Role;

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
