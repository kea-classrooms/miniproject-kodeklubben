package com.kodeklubben.miniprojekt.repositories;
import com.kodeklubben.miniprojekt.models.WishListModel;
import com.kodeklubben.miniprojekt.models.WishModel;
import com.kodeklubben.miniprojekt.services.DatabaseConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

@Repository
public class WishListRepository {

    private DatabaseConnectionManager dcm = new DatabaseConnectionManager("eu-west.connect.psdb.cloud", "n9yvymfj507ekkjlhtkd", "pscale_pw_iJs7WNJ0mxHfCdADkqSRM1GGcSUK5GgNiKGcgzgSz5m");


    private static final String GET_WISH_LISTS = "SELECT id, name, userId  FROM wishLists WHERE userId=?";
    private static final String GET_WISHES = "SELECT id, name, link FROM wishes WHERE wishListId=?";
    private static final String GET_USER = "SELECT name FROM users WHERE id=?";

    private static final String GET_ID_FROM_LOGIN = "SELECT id FROM users WHERE email=? AND password=?";

    private static final String GET_LATEST_USERID = "SELECT id FROM users ORDER BY id DESC LIMIT 1";
    private static final String GET_LATEST_WISH_LIST_ID = "SELECT id FROM wishLists ORDER BY id DESC LIMIT 1";
    private static final String GET_LATEST_WISH_ID = "SELECT id FROM wishes ORDER BY id DESC LIMIT 1";

    private static final String INSERT_NEW_USER = "INSERT INTO users(id, name, email, password) VALUES (?, ?, ?, ?)";
    private static final String INSERT_NEW_WISH_LIST = "INSERT INTO wishLists(id, name, userId) VALUES (?, ?, ?)";
    private static final String INSERT_NEW_WISH = "INSERT INTO wishes(id, name, link, wishListId, reservedById) VALUES (?, ?, ?, ?, ?)";

    public String getUserName(int id) {
        try (PreparedStatement preparedStatement = dcm.getConnection().prepareStatement(GET_USER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getIdFromAuthentication(String email, String password) {
        try (PreparedStatement preparedStatement = dcm.getConnection().prepareStatement(GET_ID_FROM_LOGIN)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<WishListModel> getWishLists(int userId) {
        try(PreparedStatement statement = dcm.getConnection().prepareStatement(GET_WISH_LISTS)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<WishListModel> wishLists = new ArrayList<>();
            while (resultSet.next()) {
                wishLists.add(new WishListModel(resultSet.getString("name"), getWishes(resultSet.getInt("id"))));
            }
            return wishLists;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<WishModel> getWishes(int wishListId) {
        try (PreparedStatement statement = dcm.getConnection().prepareStatement(GET_WISHES)) {
            statement.setLong(1, wishListId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<WishModel> wishes = new ArrayList<>();
            while (resultSet.next()) {
                wishes.add(new WishModel(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("link")));
            }
            return wishes;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getAllUser(int userId) {
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

    public int insertNewUser(String name, String email, String password) {
        int lastUserId = 0;
        try (PreparedStatement preparedStatement = dcm.getConnection().prepareStatement(GET_LATEST_USERID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lastUserId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (PreparedStatement userInsertionStatement = dcm.getConnection().prepareStatement(INSERT_NEW_USER)) {
            userInsertionStatement.setInt(1, lastUserId + 1);
            userInsertionStatement.setString(2, name);
            userInsertionStatement.setString(3, email);
            userInsertionStatement.setString(4, password);
            userInsertionStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return lastUserId;
    }

    public int insertNewWishList(String name, int userId) {
        int lastWishListId = 0;
        try (PreparedStatement preparedStatement = dcm.getConnection().prepareStatement(GET_LATEST_WISH_LIST_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lastWishListId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (PreparedStatement userInsertionStatement = dcm.getConnection().prepareStatement(INSERT_NEW_WISH_LIST)) {
            userInsertionStatement.setLong(1, lastWishListId +1);
            userInsertionStatement.setString(2, name);
            userInsertionStatement.setLong(3, userId);
            userInsertionStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return lastWishListId;
    }

    public int insertNewWish(String name, String link, int wishListId, int userId) {
        int lastWishId = 0;
        try (PreparedStatement preparedStatement = dcm.getConnection().prepareStatement(GET_LATEST_WISH_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lastWishId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (PreparedStatement userInsertionStatement = dcm.getConnection().prepareStatement(INSERT_NEW_WISH)) {
            //id, name, link, wishListId, reservedById
            userInsertionStatement.setLong(1, lastWishId + 1);
            userInsertionStatement.setString(2, name);
            userInsertionStatement.setString(3, link);
            userInsertionStatement.setLong(4, wishListId);
            userInsertionStatement.setLong(5, userId);
            userInsertionStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return lastWishId;
    }

    public ArrayList<WishListModel> addTestData() {
        Random random = new Random();
        String[] firstNames = {"Adam", "Bernhard", "Casper", "Katrine", "Emilie", "Louise", "Inga", "Johanne", "Peter"};
        String[] lastNames = {"Jørgensen", "Hansen", "Jappe", "Hage", "Boogie", "Østergaard", "Vestergaard", "Frederiksen", "Christiansen", "Danielsen"};
        ArrayList<WishListModel> wishLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String randomName = firstNames[random.nextInt(10)] + " " +  lastNames[random.nextInt(10)];

            //create user and receive userId
            int userId = insertNewUser(randomName, randomName + "@kea.dk", randomName + "password");

            //create wishList and receive wishListId
            int wishListId = insertNewWishList(randomName + "'s juleønsker", userId);

            //create wish
            insertNewWish("Solcreme", "https://www.matas.dk/derma-sollotion-spf30-200-ml", wishListId, userId);
        }
        return wishLists;
    }
}
