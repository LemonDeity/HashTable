public class Node {
    Object key;
    Object value;
    Node(){
        key = null;
        value = null;
    }
    Node(Object k, Object v){
        key  = k;
        value = v;
    }
    public Object get(){
        return value;
    }
    public String toString(){
        return "{"+key+", "+value+"}";
    }
}
