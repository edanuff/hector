package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createColumnPath;
import me.prettyprint.cassandra.service.Keyspace;

// like a simple get operation
// may return a Column or a SuperColumn
public final class ColumnQuery<K,N,V> extends AbstractQuery<K,N,V,HColumn<N,V>> implements Query<HColumn<N,V>> {

  private K key;
  private N name;

  /*package*/ ColumnQuery(KeyspaceOperator keyspaceOperator, Serializer<K> keySerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspaceOperator, keySerializer, nameSerializer, valueSerializer);
  }

  public ColumnQuery<K,N,V> setKey(K key) {
    this.key = key;
    return this;
  }

  public ColumnQuery<K,N,V> setName(N name) {
    this.name = name;
    return this;
  }


  public Result<HColumn<N, V>> execute() {
    return new Result<HColumn<N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<HColumn<N, V>>() {
        
          public HColumn<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            try {
              org.apache.cassandra.thrift.Column thriftColumn =
                ks.getColumn(keySerializer.toBytes(key), createColumnPath(columnFamilyName, name, columnNameSerializer));
              return new HColumn<N, V>(thriftColumn, columnNameSerializer, valueSerializer);
            } catch (NotFoundException e) {
              return null;
            }
          }
        }), this);
  }


  public String toString() {
    return "ColumnQuery(" + key + "," + name + ")";
  }
}
