package com.kodeklubben.miniprojekt.repositories;
import com.kodeklubben.miniprojekt.models.UserModel;
import com.kodeklubben.miniprojekt.models.WishListModel;
import com.kodeklubben.miniprojekt.models.WishModel;
import com.kodeklubben.miniprojekt.services.DatabaseConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class WishListRepository {

    private DatabaseConnectionManager dcm = new DatabaseConnectionManager("eu-west.connect.psdb.cloud", "n9yvymfj507ekkjlhtkd", "pscale_pw_iJs7WNJ0mxHfCdADkqSRM1GGcSUK5GgNiKGcgzgSz5m");

    private static final String GET_WISH_LISTS = "SELECT id, name, userId  FROM wishLists WHERE userId=?";
    private static final String GET_WISHES = "SELECT id, name, link FROM wishes WHERE wishListId=?";
    private static final String GET_USER = "SELECT name FROM users WHERE id=?";
    private static final String CREATE_USER = "INSERT into users (name, email, password) VALUES(?,?,?)";

    public ArrayList<WishListModel> getWishLists(long userId) {
        try(PreparedStatement statement = dcm.getConnection().prepareStatement(GET_WISH_LISTS)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<WishListModel> wishLists = new ArrayList<>();
            while (resultSet.next()) {
                wishLists.add(new WishListModel(resultSet.getString("name"), getWishes(resultSet.getLong("id"))));
            }
            return wishLists;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<WishModel> getWishes(long wishListId) {
        try (PreparedStatement statement = dcm.getConnection().prepareStatement(GET_WISH_LISTS)) {
            statement.setLong(1, wishListId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<WishModel> wishes = new ArrayList<>();
            while (resultSet.next()) {
                wishes.add(new WishModel(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("link")));
            }
            return wishes;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getAllUser(long userId) {
        try(PreparedStatement statement = dcm.getConnection().prepareStatement(GET_USER)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> wishLists = new ArrayList<>();
            while (resultSet.next()) {
                wishLists.add(resultSet.getString("name"));
            }
            return wishLists;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void userLogin(UserModel userModel) {
    }

    public void addUserToDatabase(UserModel userModel) throws SQLException {
        try (PreparedStatement statement = dcm.getConnection().prepareStatement(CREATE_USER)) {
            statement.setString(1, userModel.getName());
            statement.setString(2, userModel.getEmail());
            statement.setString(3, userModel.getPassword());
            ResultSet resultSet = statement.executeQuery();
        }
    }
}
