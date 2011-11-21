package com.example.dal.factories;

public enum DAOFactoryType {
	POSTGRE, MS_SQL;

	public String toString() {
		switch (this) {
			case POSTGRE: {
				return "Posgre";
			}
			case MS_SQL: {
				return "MS SQL";
			}
			default: {
				return "Unknown DAOFactory type";
			}
		}
	}
}
