package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.example.jason.jason_workshop_3.ChartLibrary.Score;
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

public class ScoreRealmProxy extends Score
    implements RealmObjectProxy {

    static final class ScoreColumnInfo extends ColumnInfo {

        public final long totalScoreIndex;
        public final long scoreNrIndex;
        public final long playerNameIndex;

        ScoreColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.totalScoreIndex = getValidColumnIndex(path, table, "Score", "totalScore");
            indicesMap.put("totalScore", this.totalScoreIndex);

            this.scoreNrIndex = getValidColumnIndex(path, table, "Score", "scoreNr");
            indicesMap.put("scoreNr", this.scoreNrIndex);

            this.playerNameIndex = getValidColumnIndex(path, table, "Score", "playerName");
            indicesMap.put("playerName", this.playerNameIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final ScoreColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("totalScore");
        fieldNames.add("scoreNr");
        fieldNames.add("playerName");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    ScoreRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (ScoreColumnInfo) columnInfo;
    }

    @Override
    @SuppressWarnings("cast")
    public float getTotalScore() {
        realm.checkIfValid();
        return (float) row.getFloat(columnInfo.totalScoreIndex);
    }

    @Override
    public void setTotalScore(float value) {
        realm.checkIfValid();
        row.setFloat(columnInfo.totalScoreIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getScoreNr() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.scoreNrIndex);
    }

    @Override
    public void setScoreNr(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.scoreNrIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getPlayerName() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.playerNameIndex);
    }

    @Override
    public void setPlayerName(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.playerNameIndex);
            return;
        }
        row.setString(columnInfo.playerNameIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_Score")) {
            Table table = transaction.getTable("class_Score");
            table.addColumn(RealmFieldType.FLOAT, "totalScore", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.INTEGER, "scoreNr", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "playerName", Table.NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_Score");
    }

    public static ScoreColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_Score")) {
            Table table = transaction.getTable("class_Score");
            if (table.getColumnCount() != 3) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 3 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 3; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final ScoreColumnInfo columnInfo = new ScoreColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("totalScore")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'totalScore' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("totalScore") != RealmFieldType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'float' for field 'totalScore' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.totalScoreIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'totalScore' does support null values in the existing Realm file. Use corresponding boxed type for field 'totalScore' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("scoreNr")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'scoreNr' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("scoreNr") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'scoreNr' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.scoreNrIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'scoreNr' does support null values in the existing Realm file. Use corresponding boxed type for field 'scoreNr' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("playerName")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'playerName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("playerName") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'playerName' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.playerNameIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'playerName' is required. Either set @Required to field 'playerName' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Score class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Score";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static Score createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        Score obj = realm.createObject(Score.class);
        if (json.has("totalScore")) {
            if (json.isNull("totalScore")) {
                throw new IllegalArgumentException("Trying to set non-nullable field totalScore to null.");
            } else {
                obj.setTotalScore((float) json.getDouble("totalScore"));
            }
        }
        if (json.has("scoreNr")) {
            if (json.isNull("scoreNr")) {
                throw new IllegalArgumentException("Trying to set non-nullable field scoreNr to null.");
            } else {
                obj.setScoreNr((int) json.getInt("scoreNr"));
            }
        }
        if (json.has("playerName")) {
            if (json.isNull("playerName")) {
                obj.setPlayerName(null);
            } else {
                obj.setPlayerName((String) json.getString("playerName"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static Score createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        Score obj = realm.createObject(Score.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("totalScore")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field totalScore to null.");
                } else {
                    obj.setTotalScore((float) reader.nextDouble());
                }
            } else if (name.equals("scoreNr")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field scoreNr to null.");
                } else {
                    obj.setScoreNr((int) reader.nextInt());
                }
            } else if (name.equals("playerName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setPlayerName(null);
                } else {
                    obj.setPlayerName((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Score copyOrUpdate(Realm realm, Score object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static Score copy(Realm realm, Score newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        Score realmObject = realm.createObject(Score.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setTotalScore(newObject.getTotalScore());
        realmObject.setScoreNr(newObject.getScoreNr());
        realmObject.setPlayerName(newObject.getPlayerName());
        return realmObject;
    }

    public static Score createDetachedCopy(Score realmObject, int currentDepth, int maxDepth, Map<RealmObject, CacheData<RealmObject>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<Score> cachedObject = (CacheData) cache.get(realmObject);
        Score standaloneObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return cachedObject.object;
            } else {
                standaloneObject = cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            standaloneObject = new Score();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmObject>(currentDepth, standaloneObject));
        }
        standaloneObject.setTotalScore(realmObject.getTotalScore());
        standaloneObject.setScoreNr(realmObject.getScoreNr());
        standaloneObject.setPlayerName(realmObject.getPlayerName());
        return standaloneObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Score = [");
        stringBuilder.append("{totalScore:");
        stringBuilder.append(getTotalScore());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{scoreNr:");
        stringBuilder.append(getScoreNr());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{playerName:");
        stringBuilder.append(getPlayerName() != null ? getPlayerName() : "null");
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
        ScoreRealmProxy aScore = (ScoreRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aScore.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aScore.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aScore.row.getIndex()) return false;

        return true;
    }

}
