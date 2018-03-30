package sg.edu.nus.iss.pt5.photolearnapp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import sg.edu.nus.iss.pt5.photolearnapp.model.RecordId;

/**
 * Created by Liang Entao on 20/3/18.
 */
public abstract class BaseEntityDAO<T extends IEntity> {
    final protected DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    protected DatabaseReference mEntityRef;
    private Class<T> mTClass;

    // public methods
    public BaseEntityDAO(String refKey, Class<T> tClass) {
        this.mEntityRef = mRootRef.child(refKey);
        this.mTClass = tClass;
    }

    public void save(T obj) {
        // Insert single record
        if (obj.getId() == null || obj.getId().isEmpty()) {
            obj.setId(UUID.randomUUID().toString());
        }
        mEntityRef.child(obj.getId()).setValue(obj);
    }

    public void save(Iterable<T> objects) {
        // Insert multiple records
        Map<String, Object> objList = new HashMap<String, Object>();
        for (T obj : objects) {
            if (obj.getId() == null || obj.getId().isEmpty()) {
                obj.setId(UUID.randomUUID().toString());
            }
            objList.put(obj.getId(), obj);
        }
        mEntityRef.updateChildren(objList);
    }

    @Deprecated
    public void update(T obj) {
        try {
            Map<String, Object> objAttrs = getAttrs(obj);

            // TODO: need validation on whether the key already exists
            DatabaseReference entityRef = mEntityRef.child(obj.getId());
            entityRef.updateChildren(objAttrs);
        } catch (InvalidPropertiesFormatException e) {
            Log.e("Invalid Property", e.getMessage());
        }
    }

    @Deprecated
    public void update(String id, Map<String, Object> attrs) {
        mEntityRef.child(id).updateChildren(attrs);
    }

    public void getObject(String objId, DAOResultListener<T> resultListener) {
        ValueEventListener valueListener = createChildEventListener(objId, resultListener);
        mEntityRef.child(objId).addValueEventListener(valueListener);
    }

    public void getObjects(DAOResultListener<Iterable<T>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.addValueEventListener(childrenListener);
    }

    public void getObjects(List<String> objIds, DAOResultListener<Iterable<T>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(objIds, resultListener);
        mEntityRef.addValueEventListener(childrenListener);
    }

    public void delete(T obj) {
        mEntityRef.child(obj.getId()).removeValue();
    }

    public void deleteById(String objId) {
        mEntityRef.child(objId).removeValue();
    }

    public void delete(Iterable<T> objects) {
        Map<String, Object> objList = new HashMap<String, Object>();
        for (T obj : objects) {
            objList.put(obj.getId(), null);
        }
        mEntityRef.updateChildren(objList);
    }

    public void deleteById(Iterable<String> objIds) {
        Map<String, Object> objList = new HashMap<String, Object>();
        for (String objId : objIds) {
            objList.put(objId, null);
        }
        mEntityRef.updateChildren(objList);
    }

    // protected methods
    protected ValueEventListener createChildEventListener(final String objId, final DAOResultListener<T> resultListener) {
        // ValueEventListener is operating on the entire children list under this dbReference
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEntityRef.child(objId).removeEventListener(this);
                T obj = dataSnapshot.getValue(mTClass);
                resultListener.OnDAOReturned(obj);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    protected ValueEventListener createChildrenEventListener(final DAOResultListener<Iterable<T>> resultListener) {
        // ValueEventListener is operating on the entire children list under this dbReference
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEntityRef.removeEventListener(this);
                List<T> list = new ArrayList<T>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    T obj = child.getValue(mTClass);
                    list.add(obj);
                }
                resultListener.OnDAOReturned(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    protected ValueEventListener createChildrenEventListener(final List<String> objIds, final DAOResultListener<Iterable<T>> resultListener) {
        // ValueEventListener is operating on the entire children list under this dbReference
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mEntityRef.removeEventListener(this);
                List<T> list = new ArrayList<T>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    T obj = child.getValue(mTClass);
                    if (objIds == null || objIds.contains(obj.getId())) {
                        list.add(obj);
                    }
                }
                resultListener.OnDAOReturned(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    /**
     * Create IModel interface ensure the implementation of getId().
     *
     * @param obj
     * @return
     * @throws InvalidPropertiesFormatException
     */
    @Deprecated()
    protected String getIdValue(T obj) throws InvalidPropertiesFormatException {
        return String.valueOf(getId(obj).getValue());
    }

    @Deprecated
    protected Map.Entry<String, Object> getId(T obj) throws InvalidPropertiesFormatException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field classField : fields) {
            if (classField.isAnnotationPresent(RecordId.class)) {
                try {
                    classField.setAccessible(true);
                    return new HashMap.SimpleEntry<String, Object>(classField.getName(), classField.get(obj));
                } catch (IllegalAccessException e) {
                    throw new InvalidPropertiesFormatException("Fail to access attribute: " + classField.getName());
                } finally {
                    classField.setAccessible(false);
                }
            }
        }
        throw new InvalidPropertiesFormatException("@RecordId is not defined in " + obj.getClass().getName() + " class.");
    }

    @Deprecated
    protected Map<String, Object> getAttrs(T obj) throws InvalidPropertiesFormatException {
        Map<String, Object> objAttrs = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field classField : fields) {
            try {
                classField.setAccessible(true);
                objAttrs.put(classField.getName(), classField.get(obj));
            } catch (IllegalAccessException e) {
                throw new InvalidPropertiesFormatException("Fail to access attribute: " + classField.getName());
            } finally {
                classField.setAccessible(false);
            }
        }
        return objAttrs;
    }


    // private methods
    private String getIdName(T obj) throws InvalidPropertiesFormatException {
        return getId(obj).getKey();
    }

    private Set<String> getAttrNames(T obj) throws InvalidPropertiesFormatException {
        return getAttrs(obj).keySet();
    }
}
