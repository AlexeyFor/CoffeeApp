package by.training.coffeeproject.dao;

import by.training.coffeeproject.entity.UserInfo;

public interface UserInfoDao extends Dao<UserInfo> {

	String takeUserNameByID(Integer ID) throws DaoException;
}
