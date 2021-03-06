package me.prettyprint.cassandra.model;

import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.StringSerializer;

/**
 * Extracts a type T from the given bytes, or vice a versa.
 * 
 * In cassandra column names and column values (and starting with 0.7.0 row keys) are all byte[].
 * To allow type safe conversion in java and keep all conversion code in one place we define the 
 * Serializer interface.
 * Implementors of the interface define type conversion according to their domains. A predefined 
 * set of common serializers can be found in the serializers package, for example 
 * {@link StringSerializer}.
 * 
 * @author Ran Tavory 
 *
 * @param <T> The type to which data extraction should work.
 */
public interface Serializer<T> {

  /**
   * Extract bytes from the obj of type T
   * @param obj
   * @return
   */
  public byte[] toBytes(T obj);
  
  /**
   * Extract an object of type T from the bytes.
   * @param bytes
   * @return
   */
  public T fromBytes(byte[] bytes);
  
  public List<byte[]> toBytesList(List<T> list);

  public List<T> fromBytesList(List<byte[]> list);

  public <V> Map<byte[], V> toBytesMap(Map<T, V> map);

  public <V> Map<T, V> fromBytesMap(Map<byte[], V> map);

}
