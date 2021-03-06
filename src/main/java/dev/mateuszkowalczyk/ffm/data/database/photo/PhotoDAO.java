package dev.mateuszkowalczyk.ffm.data.database.photo;

import dev.mateuszkowalczyk.ffm.data.DatabaseService;
import dev.mateuszkowalczyk.ffm.data.database.Dao;
import dev.mateuszkowalczyk.ffm.data.database.person.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhotoDAO implements Dao<Photo> {
    private List<Photo> photoList = new ArrayList<>();
    private static PhotoDAO instance;
    private DatabaseService databaseService = DatabaseService.getInstance();

    private PhotoDAO() {
    }

    public static PhotoDAO getInstance() {
        if (instance == null) {
            instance = new PhotoDAO();
        }

        return instance;
    }

    @Override
    public Optional<Photo> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Photo> getAll() {
        return this.getAll(false);
    }

    @Override
    public List<Photo> getAll(boolean refresh) {
        if (this.photoList.size() == 0 || refresh) {
            this.photoList.clear();

            String sql = "SELECT * FROM photos";

            try {
                PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    var photo = new Photo(resultSet);

                    this.photoList.add(photo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return this.photoList;
    }

    @Override
    public void add(Photo photo) {

        String sql = "INSERT INTO photos(path, fileName, cachedPath) values (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, photo.getPath());
            preparedStatement.setString(2, photo.getFileName());
            preparedStatement.setString(3, photo.getCachedPath());
            preparedStatement.executeUpdate();
            sql = "select id from photos order by id desc limit 1";

            ResultSet resultSet = this.databaseService.getConnection().prepareStatement(sql).executeQuery();

            while (resultSet.next()) {
                photo.setId(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Photo findByPath(String path) {
        String sql = "SELECT * FROM photos WHERE path = ?";
        Photo photo = null;

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, path);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                photo = new Photo(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photo;
    }

    @Override
    public void update(Photo photo) {

    }

    @Override
    public void delete(Photo photo) {
        String sql = "DELETE FROM photos WHERE id = ?";

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, photo.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Photo> getAllForPerson(Person person) {
        List<Photo> photoListForPerson = new ArrayList<>();

        String sql = "SELECT * FROM photos p inner join face f on p.id == f.photoId WHERE f.personId  = ?";

        try {
            PreparedStatement preparedStatement = this.databaseService.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, person.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var photo = new Photo(resultSet);

                photoListForPerson.add(photo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photoListForPerson;
    }
}
