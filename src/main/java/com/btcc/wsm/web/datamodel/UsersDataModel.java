package com.btcc.wsm.web.datamodel;

import com.btcc.wsm.model.Users;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

public class UsersDataModel extends ListDataModel<Users> implements
		SelectableDataModel<Users> {

	public UsersDataModel() {

	}

	public UsersDataModel(List<Users> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	public Users getRowData(String rowkey) {
		List<Users> UsersList = (List<Users>) getWrappedData();
		Integer rowKeyInt = Integer.parseInt(rowkey);
		for (Users users : UsersList) {
			if (users.getId() == rowKeyInt) {
				return users;
			}
		}

		return null;
	}

	public Object getRowKey(Users Users) {

		return Users.getId();
	}

}
