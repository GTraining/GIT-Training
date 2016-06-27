package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.example.jason.jason_workshop_3.ChartLibrary.RealmDemoData;
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

public class RealmDemoDataRealmProxy extends RealmDemoData
    implements RealmObjectProxy {

    static final class RealmDemoDataColumnInfo extends ColumnInfo {

        public final long valueIndex;
        public final long openIndex;
        public final long closeIndex;
        public final long highIndex;
        public final long lowIndex;
        public final long bubbleSizeIndex;
        public final long stackValuesIndex;
        public final long xIndexIndex;
        public final long xValueIndex;
        public final long someStringFieldIndex;

        RealmDemoDataColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(10);
            this.valueIndex = getValidColumnIndex(path, table, "RealmDemoData", "value");
            indicesMap.put("value", this.valueIndex);

            this.openIndex = getValidColumnIndex(path, table, "RealmDemoData", "open");
            indicesMap.put("open", this.openIndex);

            this.closeIndex = getValidColumnIndex(path, table, "RealmDemoData", "close");
            indicesMap.put("close", this.closeIndex);

            this.highIndex = getValidColumnIndex(path, table, "RealmDemoData", "high");
            indicesMap.put("high", this.highIndex);

            this.lowIndex = getValidColumnIndex(path, table, "RealmDemoData", "low");
            indicesMap.put("low", this.lowIndex);

            this.bubbleSizeIndex = getValidColumnIndex(path, table, "RealmDemoData", "bubbleSize");
            indicesMap.put("bubbleSize", this.bubbleSizeIndex);

            this.stackValuesIndex = getValidColumnIndex(path, table, "RealmDemoData", "stackValues");
            indicesMap.put("stackValues", this.stackValuesIndex);

            this.xIndexIndex = getValidColumnIndex(path, table, "RealmDemoData", "xIndex");
            indicesMap.put("xIndex", this.xIndexIndex);

            this.xValueIndex = getValidColumnIndex(path, table, "RealmDemoData", "xValue");
            indicesMap.put("xValue", this.xValueIndex);

            this.someStringFieldIndex = getValidColumnIndex(path, table, "RealmDemoData", "someStringField");
            indicesMap.put("someStringField", this.someStringFieldIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmDemoDataColumnInfo columnInfo;
    private RealmList<RealmFloat> stackValuesRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("value");
        fieldNames.add("open");
        fieldNames.add("close");
        fieldNames.add("high");
        fieldNames.add("low");
        fieldNames.add("bubbleSize");
        fieldNames.add("stackValues");
        fieldNames.add("xIndex");
        fieldNames.add("xValue");
        fieldNames.add("someStringField");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmDemoDataRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmDemoDataColumnInfo) columnInfo;
    }

    @Override
    @SuppressWarnings("cast")
    public float getValue() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.valueIndex);
    }

    @Override
    public void setValue(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.valueIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public float getOpen() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.openIndex);
    }

    @Override
    public void setOpen(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.openIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public float getClose() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.closeIndex);
    }

    @Override
    public void setClose(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.closeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public float getHigh() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.highIndex);
    }

    @Override
    public void setHigh(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.highIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public float getLow() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.lowIndex);
    }

    @Override
    public void setLow(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.lowIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public float getBubbleSize() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.bubbleSizeIndex);
    }

    @Override
    public void setBubbleSize(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.bubbleSizeIndex, value);
    }

    @Override
    public RealmList<RealmFloat> getStackValues() {
        realm.checkIfValid();
        // use the cached value if available
        if (stackValuesRealmList != null) {
            return stackValuesRealmList;
        } else {
            LinkView linkView = row.getLinkList(columnInfo.stackValuesIndex);
            stackValuesRealmList = new RealmList<RealmFloat>(RealmFloat.class, linkView, realm);
            return stackValuesRealmList;
        }
    }

    @Override
    public void setStackValues(RealmList<RealmFloat> value) {
        realm.checkIfValid();
        LinkView links = row.getLinkList(columnInfo.stackValuesIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            if (!linkedObject.isValid()) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (linkedObject.realm != this.realm) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            links.add(linkedObject.row.getIndex());
        }
    }

    @Override
    @SuppressWarnings("cast")
    public int getxIndex() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.xIndexIndex);
    }

    @Override
    public void setxIndex(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.xIndexIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getxValue() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.xValueIndex);
    }

    @Override
    public void setxValue(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.xValueIndex);
            return;
        }
        row.setString(columnInfo.xValueIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getSomeStringField() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.someStringFieldIndex);
    }

    @Override
    public void setSomeStringField(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.someStringFieldIndex);
            return;
        }
        row.setString(columnInfo.someStringFieldIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmDemoData")) {
            Table table = transaction.getTable("class_RealmDemoData");
            table.addColumn(RealmFieldType.FLOAT, "value", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.FLOAT, "open", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.FLOAT, "close", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.FLOAT, "high", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.FLOAT, "low", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.FLOAT, "bubbleSize", Table.NOT_NULLABLE);
            if (!transaction.hasTable("class_RealmFloat")) {
                RealmFloatRealmProxy.initTable(transaction);
            }
            table.addColumnLink(RealmFieldType.LIST, "stackValues", transaction.getTable("class_RealmFloat"));
            table.addColumn(RealmFieldType.INTEGER, "xIndex", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "xValue", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "someStringField", Table.NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_RealmDemoData");
    }

    public static RealmDemoDataColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmDemoData")) {
            Table table = transaction.getTable("class_RealmDemoData");
            if (table.getColumnCount() != 10) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 10 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 10; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmDemoDataColumnInfo columnInfo = new RealmDemoDataColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("value")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'value' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("value") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'value' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.valueIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'value' does support null values in the existing Realm file. Use corresponding boxed type for field 'value' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("open")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'open' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("open") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'open' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.openIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'open' does support null values in the existing Realm file. Use corresponding boxed type for field 'open' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("close")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'close' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("close") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'close' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.closeIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'close' does support null values in the existing Realm file. Use corresponding boxed type for field 'close' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("high")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'high' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("high") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'high' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.highIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'high' does support null values in the existing Realm file. Use corresponding boxed type for field 'high' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("low")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'low' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("low") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'low' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.lowIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'low' does support null values in the existing Realm file. Use corresponding boxed type for field 'low' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("bubbleSize")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'bubbleSize' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("bubbleSize") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'bubbleSize' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.bubbleSizeIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'bubbleSize' does support null values in the existing Realm file. Use corresponding boxed type for field 'bubbleSize' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("stackValues")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'stackValues'");
            }
            if (columnTypes.get("stackValues") != RealmFieldType.LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'RealmFloat' for field 'stackValues'");
            }
            if (!transaction.hasTable("class_RealmFloat")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_RealmFloat' for field 'stackValues'");
            }
            Table table_6 = transaction.getTable("class_RealmFloat");
            if (!table.getLinkTarget(columnInfo.stackValuesIndex).hasSameSchema(table_6)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'stackValues': '" + table.getLinkTarget(columnInfo.stackValuesIndex).getName() + "' expected - was '" + table_6.getName() + "'");
            }
            if (!columnTypes.containsKey("xIndex")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'xIndex' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("xIndex") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'xIndex' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.xIndexIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'xIndex' does support null values in the existing Realm file. Use corresponding boxed type for field 'xIndex' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("xValue")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'xValue' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("xValue") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'xValue' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.xValueIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'xValue' is required. Either set @Required to field 'xValue' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("someStringField")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'someStringField' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("someStringField") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'someStringField' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.someStringFieldIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'someStringField' is required. Either set @Required to field 'someStringField' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmDemoData class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmDemoData";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmDemoData createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmDemoData obj = realm.createObject(RealmDemoData.class);
        if (json.has("value")) {
            if (json.isNull("value")) {
                throw new IllegalArgumentException("Trying to set non-nullable field value to null.");
            } else {
                obj.setValue((float) json.getDouble("value"));
            }
        }
        if (json.has("open")) {
            if (json.isNull("open")) {
                throw new IllegalArgumentException("Trying to set non-nullable field open to null.");
            } else {
                obj.setOpen((float) json.getDouble("open"));
            }
        }
        if (json.has("close")) {
            if (json.isNull("close")) {
                throw new IllegalArgumentException("Trying to set non-nullable field close to null.");
            } else {
                obj.setClose((float) json.getDouble("close"));
            }
        }
        if (json.has("high")) {
            if (json.isNull("high")) {
                throw new IllegalArgumentException("Trying to set non-nullable field high to null.");
            } else {
                obj.setHigh((float) json.getDouble("high"));
            }
        }
        if (json.has("low")) {
            if (json.isNull("low")) {
                throw new IllegalArgumentException("Trying to set non-nullable field low to null.");
            } else {
                obj.setLow((float) json.getDouble("low"));
            }
        }
        if (json.has("bubbleSize")) {
            if (json.isNull("bubbleSize")) {
                throw new IllegalArgumentException("Trying to set non-nullable field bubbleSize to null.");
            } else {
                obj.setBubbleSize((float) json.getDouble("bubbleSize"));
            }
        }
        if (json.has("stackValues")) {
            if (json.isNull("stackValues")) {
                obj.setStackValues(null);
            } else {
                obj.getStackValues().clear();
                JSONArray array = json.getJSONArray("stackValues");
                for (int i = 0; i < array.length(); i++) {
                    com.example.jason.jason_workshop_3.ChartLibrary.RealmFloat item = RealmFloatRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    obj.getStackValues().add(item);
                }
            }
        }
        if (json.has("xIndex")) {
            if (json.isNull("xIndex")) {
                throw new IllegalArgumentException("Trying to set non-nullable field xIndex to null.");
            } else {
                obj.setxIndex((int) json.getInt("xIndex"));
            }
        }
        if (json.has("xValue")) {
            if (json.isNull("xValue")) {
                obj.setxValue(null);
            } else {
                obj.setxValue((String) json.getString("xValue"));
            }
        }
        if (json.has("someStringField")) {
            if (json.isNull("someStringField")) {
                obj.setSomeStringField(null);
            } else {
                obj.setSomeStringField((String) json.getString("someStringField"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmDemoData createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmDemoData obj = realm.createObject(RealmDemoData.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("value")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field value to null.");
                } else {
                    obj.setValue((float) reader.nextDouble());
                }
            } else if (name.equals("open")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field open to null.");
                } else {
                    obj.setOpen((float) reader.nextDouble());
                }
            } else if (name.equals("close")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field close to null.");
                } else {
                    obj.setClose((float) reader.nextDouble());
                }
            } else if (name.equals("high")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field high to null.");
                } else {
                    obj.setHigh((float) reader.nextDouble());
                }
            } else if (name.equals("low")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field low to null.");
                } else {
                    obj.setLow((float) reader.nextDouble());
                }
            } else if (name.equals("bubbleSize")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field bubbleSize to null.");
                } else {
                    obj.setBubbleSize((float) reader.nextDouble());
                }
            } else if (name.equals("stackValues")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setStackValues(null);
                } else {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.jason.jason_workshop_3.ChartLibrary.RealmFloat item = RealmFloatRealmProxy.createUsingJsonStream(realm, reader);
                        obj.getStackValues().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("xIndex")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field xIndex to null.");
                } else {
                    obj.setxIndex((int) reader.nextInt());
                }
            } else if (name.equals("xValue")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setxValue(null);
                } else {
                    obj.setxValue((String) reader.nextString());
                }
            } else if (name.equals("someStringField")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setSomeStringField(null);
                } else {
                    obj.setSomeStringField((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmDemoData copyOrUpdate(Realm realm, RealmDemoData object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static RealmDemoData copy(Realm realm, RealmDemoData newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmDemoData realmObject = realm.createObject(RealmDemoData.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setValue(newObject.getValue());
        realmObject.setOpen(newObject.getOpen());
        realmObject.setClose(newObject.getClose());
        realmObject.setHigh(newObject.getHigh());
        realmObject.setLow(newObject.getLow());
        realmObject.setBubbleSize(newObject.getBubbleSize());

        RealmList<RealmFloat> stackValuesList = newObject.getStackValues();
        if (stackValuesList != null) {
            RealmList<RealmFloat> stackValuesRealmList = realmObject.getStackValues();
            for (int i = 0; i < stackValuesList.size(); i++) {
                RealmFloat stackValuesItem = stackValuesList.get(i);
                RealmFloat cachestackValues = (RealmFloat) cache.get(stackValuesItem);
                if (cachestackValues != null) {
                    stackValuesRealmList.add(cachestackValues);
                } else {
                    stackValuesRealmList.add(RealmFloatRealmProxy.copyOrUpdate(realm, stackValuesList.get(i), update, cache));
                }
            }
        }

        realmObject.setxIndex(newObject.getxIndex());
        realmObject.setxValue(newObject.getxValue());
        realmObject.setSomeStringField(newObject.getSomeStringField());
        return realmObject;
    }

    public static RealmDemoData createDetachedCopy(RealmDemoData realmObject, int currentDepth, int maxDepth, Map<RealmObject, CacheData<RealmObject>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmDemoData> cachedObject = (CacheData) cache.get(realmObject);
        RealmDemoData standaloneObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return cachedObject.object;
            } else {
                standaloneObject = cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            standaloneObject = new RealmDemoData();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmObject>(currentDepth, standaloneObject));
        }
        standaloneObject.setValue(realmObject.getValue());
        standaloneObject.setOpen(realmObject.getOpen());
        standaloneObject.setClose(realmObject.getClose());
        standaloneObject.setHigh(realmObject.getHigh());
        standaloneObject.setLow(realmObject.getLow());
        standaloneObject.setBubbleSize(realmObject.getBubbleSize());

        // Deep copy of stackValues
        if (currentDepth == maxDepth) {
            standaloneObject.setStackValues(null);
        } else {
            RealmList<RealmFloat> managedstackValuesList = realmObject.getStackValues();
            RealmList<RealmFloat> standalonestackValuesList = new RealmList<RealmFloat>();
            standaloneObject.setStackValues(standalonestackValuesList);
            int nextDepth = currentDepth + 1;
            int size = managedstackValuesList.size();
            for (int i = 0; i < size; i++) {
                RealmFloat item = RealmFloatRealmProxy.createDetachedCopy(managedstackValuesList.get(i), nextDepth, maxDepth, cache);
                standalonestackValuesList.add(item);
            }
        }
        standaloneObject.setxIndex(realmObject.getxIndex());
        standaloneObject.setxValue(realmObject.getxValue());
        standaloneObject.setSomeStringField(realmObject.getSomeStringField());
        return standaloneObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmDemoData = [");
        stringBuilder.append("{value:");
        stringBuilder.append(getValue());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{open:");
        stringBuilder.append(getOpen());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{close:");
        stringBuilder.append(getClose());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{high:");
        stringBuilder.append(getHigh());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{low:");
        stringBuilder.append(getLow());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{bubbleSize:");
        stringBuilder.append(getBubbleSize());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{stackValues:");
        stringBuilder.append("RealmList<RealmFloat>[").append(getStackValues().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{xIndex:");
        stringBuilder.append(getxIndex());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{xValue:");
        stringBuilder.append(getxValue() != null ? getxValue() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{someStringField:");
        stringBuilder.append(getSomeStringField() != null ? getSomeStringField() : "null");
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
        RealmDemoDataRealmProxy aRealmDemoData = (RealmDemoDataRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmDemoData.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmDemoData.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmDemoData.row.getIndex()) return false;

        return true;
    }

}
