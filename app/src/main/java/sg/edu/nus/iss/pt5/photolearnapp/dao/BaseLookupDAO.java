package sg.edu.nus.iss.pt5.photolearnapp.dao;

/**
 * Created by Liang Entao on 24/3/18.
 */
public abstract class BaseLookupDAO<K extends ILookupable, V extends Object, D extends BaseEntityDAO> {
    private BaseEntityDAO mEntityDao;

    // public methods
    public BaseLookupDAO(D entityDao) {
        this.mEntityDao = entityDao;
    }

    public void save() {

    }

    public void update() {

    }

    public Iterable<V> lookup(K key) {
        return null;
    }

    public void delete() {

    }
}
