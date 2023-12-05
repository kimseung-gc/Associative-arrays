package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Seunghyeon (Hyeon) Kim
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    // Making an empty array of pairs with default capacity.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
      DEFAULT_CAPACITY); 
    // Making another variable with size which is declared as 0.
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> temp = new AssociativeArray<K, V>();
    for(int i = 0; i < this.size; i++){
      temp.set(this.pairs[i].key, this.pairs[i].value);
    }
    // returning a cloned AssociativeArray
    return temp; 
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    // Initializing the returning variable with "{ " as the start of
    // an array is visualized as "{ "
    String ret = "{ ";
    // for each of the variable, add the variable converted to string
    for(int i = 0; i < this.size; i++){
      // treat null as an exception, and add the key to the returning string
      ret += (this.pairs[i].key==null)? "null" : this.pairs[i].key;
      // separate the key and value with a column
      ret += ": ";
      // treat null as an exception, and add value of the key
      ret += (this.pairs[i].value==null)? "null" : this.pairs[i].value;
      // separate each element of the associative array with ","
      // treat the last element as an exception, and add a space only in that case.
      ret += (i != this.size-1)? ", " : " ";
    }
    // return ret with closing curly bracket.
    return ret + "}";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) {
    try {
      // attempt to find a key, and save the index of the key as ind
      int ind = find(key);
      // if successful, set the key with the intended value.
      this.pairs[ind] = null;
      this.pairs[ind] = new KVPair<K, V>(key, value);
    } catch (Exception e) {
      // when key is not found,
      // check whether the array is full
      if(this.isFull()){
        // if it is full, expand the array
        this.expand();
        // then add the elements that has to be set as a new pair.
        this.pairs[this.size] = new KVPair<K, V>(key, value);
        // when done adding, increment the size.
        this.size++;
        return;
      }
      // when it is not full, simply set the last element of the 
      // array with the key and value that is to be set
      this.pairs[this.size] = new KVPair<K, V>(key, value);
      // then increment the size
      this.size++;
    }
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key does not appear in the associative
   *                              array.
   */
  public V get(K key) throws KeyNotFoundException {
    if(!hasKey(key)){
      throw new KeyNotFoundException();
    }
    for(int i = 0; i<this.size; i++){
      if(this.pairs[i].key != null){
        if(this.pairs[i].key.equals(key)){
          return this.pairs[i].value;
        }
      }else{
        if(this.pairs[i].key == key){
          return this.pairs[i].value;
        }
      }
    }
    return null;
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) {
    try {
      // attempt to find the key
      find(key);
      // when it is found, return true
      return true;
    } catch (Exception e) {
      // if the key is not found, return false.
      return false;
    }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    try{
      // attempt to find the key, and save as a variable ind
      int ind = find(key);
      // when found, remove the pair at that index
      this.pairs[ind] = null;
      // then iterate through the array to shift the elements left.
      for(int i = ind+1; i<this.size; i++){
        // shifting the rest of the array left.
        this.pairs[i-1] = this.pairs[i]; 
        // deleting the moved element.
        this.pairs[i] = null;
      }
      // decrement the size.
      this.size--;
    }catch(Exception e){
      // when nothing is found, finish the function without modifying anything.
      return;
    }
  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Determines whether the AssociativeArray is full.
   * @return int
   */
  private boolean isFull(){
    // return whether the array is full (length == size)
    return (this.pairs.length == size());
  } // isFull()

  /**
   * Expand the underlying array.
   */
  private void expand() {
    // redeclare pairs as a copied array of current pairs, but extended with DEFAULT_CAPACITY
    // more elements.
    this.pairs = java.util.Arrays.copyOf(this.pairs, 2*DEFAULT_CAPACITY);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    for(int i = 0; i < this.size; i++){
      if(this.pairs[i].key != null){
        if(this.pairs[i].key.equals(key)){
          return i;
        }
      }else{
        if(this.pairs[i].key == key){
          return i;
        }
      }
    }
    throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray