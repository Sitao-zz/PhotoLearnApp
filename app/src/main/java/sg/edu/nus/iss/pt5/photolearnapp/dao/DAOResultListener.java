package sg.edu.nus.iss.pt5.photolearnapp.dao;

/**
 * Created by Liang Entao on 20/3/18.
 */
public interface DAOResultListener<R extends Object> {
    void OnDAOReturned(R obj);
}
