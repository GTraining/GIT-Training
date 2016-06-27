package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.example.jason.jason_workshop_3.ChartLibrary.RealmFloat;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RealmFloatRealmProxy extends RealmFloat
    implements RealmObjectProxy {

    static final class RealmFloatColumnInfo extends ColumnInfo {

        public final long floatValueIndex;

        RealmFloatColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(1);
            this.floatValueIndex = getValidColumnIndex(path, table, "RealmFloat", "floatValue");
            indicesMap.put("floatValue", this.floatValueIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmFloatColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("floatValue");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmFloatRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmFloatColumnInfo) columnInfo;
    }

    @Override
    @SuppressWarnings("cast")
    public float getFloatValue() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.floatValueIndex);
    }

    @Override
    public void setFloatValue(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.floatValueIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmFloat")) {
            Table table = transaction.getTable("class_RealmFloat");
            table.addColumn(RealmFieldType.FLOAT, "floatValue", Table.NOT_NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_RealmFloat");
    }

    public static RealmFloatColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmFloat")) {
            Table table = transaction.getTable("class_RealmFloat");
            if (table.getColumnCount() != 1) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 1 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 1; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmFloatColumnInfo columnInfo = new RealmFloatColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("floatValue")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'floatValue' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("floatValue") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'floatValue' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.floatValueIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'floatValue' does support null values in the existing Realm file. Use corresponding boxed type for field 'floatValue' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmFloat class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmFloat";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmFloat createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmFloat obj = realm.createObject(RealmFloat.class);
        if (json.has("floatValue")) {
            if (json.isNull("floatValue")) {
                throw new IllegalArgumentException("Trying to set non-nullable field floatValue to null.");
            } else {
                obj.setFloatValue((float) json.getDouble("floatValue"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmFloat createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmFloat obj = realm.createObject(RealmFloat.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("floatValue")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field floatValue to null.");
                } else {
                    obj.setFloatValue((float) reader.nextDouble());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmFloat copyOrUpdate(Realm realm, RealmFloat object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static RealmFloat copy(Realm realm, RealmFloat newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmFloat realmObject = realm.createObject(RealmFloat.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setFloatValue(newObject.getFloatValue());
        return realmObject;
    }

    public static RealmFloat createDetachedCopy(RealmFloat realmObject, int currentDepth, int maxDepth, Map<RealmObject, CacheData<RealmObject>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmFloat> cachedObject = (CacheData) cache.get(realmObject);
        RealmFloat standaloneObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return cachedObject.object;
            } else {
                standaloneObject = cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            standaloneObject = new RealmFloat();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmObject>(currentDepth, standaloneObject));
        }
        standaloneObject.setFloatValue(realmObject.getFloatValue());
        return standaloneObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmFloat = [");
        stringBuilder.append("{floatValue:");
        stringBuilder.append(getFloatValue());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealmFloatRealmProxy aRealmFloat = (RealmFloatRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmFloat.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmFloat.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmFloat.row.getIndex()) return false;

        return true;
    }

}
