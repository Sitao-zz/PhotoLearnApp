package sg.edu.nus.iss.pt5.photolearnapp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Set;

import sg.edu.nus.iss.pt5.photolearnapp.model.RecordId;

/**
 * Created by Liang Entao on 20/3/18.
 */
public abstract class BaseDAO<T extends Object> {
    final protected DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private Class<T> mTClass;
    private DatabaseReference mObjRef;

    // public methods
    public BaseDAO(String refKey, Class<T> tClass) {
        this.mTClass = tClass;
        this.mObjRef = mRootRef.child(refKey);
    }

    public void save(T obj) {
        try {
            // Insert single record
            mObjRef.child(getIdValue(obj)).setValue(obj);
        } catch (InvalidPropertiesFormatException e) {
            Log.e("Invalid Property", e.getMessage());
        }
    }

    public void save(Iterable<T> objects) {
        try {
            // Insert multiple records
            Map<String, Object> objList = new HashMap<String, Object>();
            for (T obj : objects) {
                objList.put(getIdValue(obj), obj);
            }
            mObjRef.updateChildren(objList);
        } catch (InvalidPropertiesFormatException e) {
            Log.e("Invalid Property", e.getMessage());
        }
    }

    public void update(T obj) {
        try {
            Map<String, Object> objAttrs = getAttrs(obj);

            // TODO: need validation on whether the key already exists
            DatabaseReference objRef = mObjRef.child(getIdValue(obj));
            objRef.updateChildren(objAttrs);
        } catch (InvalidPropertiesFormatException e) {
            Log.e("Invalid Property", e.getMessage());
        }
    }

    public void update(String id, Map<String, Object> attrs) {
        mObjRef.child(id).updateChildren(attrs);
    }

    public void getObject(String objId, DAOResultListener<T> resultListener) {
        ValueEventListener valueListener = createValueEventListener(objId, resultListener);
        mObjRef.child(objId).addValueEventListener(valueListener);
    }

    public void delete(T obj) {
        try {
            mObjRef.child(getIdValue(obj)).removeValue();
        } catch (InvalidPropertiesFormatException e) {
            Log.e("Invalid Property", e.getMessage());
        }
    }

    public void delete(String objId) {
        mObjRef.child(objId).removeValue();
    }

    // protected methods
    protected abstract String getIdValue(T obj) throws InvalidPropertiesFormatException;

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
    private ValueEventListener createValueEventListener(final String objId, final DAOResultListener<T> resultListener) {
        // ValueEventListener is operating on the entire children list under this dbReference
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mObjRef.child(objId).removeEventListener(this);
                T obj = dataSnapshot.getValue(mTClass);
                resultListener.OnDAOReturned(obj);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    private String getIdName(T obj) throws InvalidPropertiesFormatException {
        return getId(obj).getKey();
    }

    private Set<String> getAttrNames(T obj) throws InvalidPropertiesFormatException {
        return getAttrs(obj).keySet();
    }
}
