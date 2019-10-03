import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
public class HashTable{
    private class Node{
        private Object key;
        private Object value;
        private boolean removed;
        Node(){
            value = null;
            key = null;
            removed = false;
        }

        Node(Object v, Object k){
            value = v;
            key = k;
            removed = false;
        }

        void remove(){
            removed = true;
        }

        Object getValue(){
            if (!removed){
                return value;
            }
            return "Node is removed";
        }

        public String getKey(){
            return key.toString();
        }

        public String toString(){
            if (!removed){
                return "{"+key+", "+value+"}";
            }
            return "{}";
        }
    }
    int init;
    Node[] arr;
    int probes = 0;
    int size = 0;
    public HashTable(){
        init = 101;
        arr = new Node[init];
    }
    //assume initCap is prime
    public HashTable(int initCap){
        init = initCap;
        arr = new Node[init];
    }

    public Object put(String key, Object value){
        Node n = new Node(value,key);
        int ind = HashCode(key);
        Node j = arr[ind];
        if (j == null){
            arr[ind] = n;
        }
        else{
            while(arr[ind] != null){
                ind++;
                if (arr.length - 1 == ind){
                    ind = 0;
                }
            }
            arr[ind] = n;
        }
        size++;
        return n;
    }

    public Object putQ(String key, Object value){
        Node n = new Node(value,key);
        int ind = HashCodeQuadratic(key);
        arr[ind] = n;
        size++;
        return n;
    }

    public Object putL(String key, Object value){
        Node n = new Node(value, key);
        int ind = HashCodeGet(key);
        //needs to insert Linked List
        size++;
        return n;
    }


    //sets removed field inside of node to true meaning
    //when this node is looked at it will not return any value
    //and will not count as part of size future values will
    //overwrite this index
    public Object remove(int index){
        if (arr[index] != null){
            arr[index].remove();
        }
        size--;
        return arr[index];
    }

    public Object get(Node n){
        //first case means the Node you'r elooking for is at expected index
        //second case means Node is not at expected index need to do linear probing
        Node h  = new Node();
        if (arr[HashCode(n.getKey())] == n){
            return n;
        }
        else{
            int g = HashCode(n.getKey());
            while(true){
                if (arr[g] == n){
                    return n;
                }
                else if(arr[g+1] == null){
                    break;
                }
            }
        }
        return h;
    }

    public Object get(String key){
        int index = HashCodeGet(key);
        boolean f = false;
        while(true){
            if (arr[index].getKey().equals(key)){
                return arr[index];
            }
            index++;
            if (index == arr.length-1 && !f){
                index = 0;
                f = true;
            }
            else if(index == arr.length-1 && f){
                break;
            }
        }
        if (!f){
            return arr[index];
        }
        else{
            return "Your data doesn't exist I'm sorry";
        }
    }

    public Object get(String key, String value){
        return get(new Node(value, key));
    }

    public Object get(int index){
        return arr[index];
    }

    public int HashCode(String key){
        //this method will return index based on sum of unicode values in actual data
        int index = 0;
        char[] charAr = key.toCharArray();
        int lenOfArr = charAr.length;
        for(int i = 0; i < charAr.length; i++){
            if (i % 2 == 0){
                index += (Math.pow(i,4))*(charAr[i]-48);
            }
            else{
                index += (Math.pow(i,2))*(charAr[i]-48);
            }
        }
        index %= init;
        int h = index;
        return checkIndex(index);
    }
    //this private method is for internal Hashing, specifically for checking get methods and making sure it's values it returns are correct
    private int HashCodeGet(String key){
        int index = 0;
        char[] charAr = key.toCharArray();
        int lenOfArr = charAr.length;
        for(int i = 0; i < charAr.length; i++){
            if (i % 2 == 0){
                index += (Math.pow(i,4))*(charAr[i]-48);
            }
            else{
                index += (Math.pow(i,2))*(charAr[i]-48);
            }
        }
        index %= init;
        int h = index;
        return h;
    }

    public int HashCodeQuadratic(String key){
        int orIndex = HashCodeGet(key);
        int index = checkIndexQuadratic(orIndex);
        return index;
    }

    public int checkIndexQuadratic(int index){
        //i equals the number of collsions when searching for index to put value
        int i = 1;
        //the condition is meant to keep the number of probes under half of the Tables Length
        while(arr[index] != null && i < ((double)init/2.0)){
            if (arr[(int)(index+Math.pow(i,2))%arr.length] == null){
                return (int)((index+Math.pow(i,2))%arr.length);
            }
            i++;
            probes++;
        }
        return index;
    }

    public Object Quadget(String key){
        int index = HashCodeGet(key);
        int i = 0;
        while(!(arr[(int)((index + Math.pow(i,2))%arr.length)].getKey().equals(key))){
            i++;
        }
        return arr[(int)((index + Math.pow(i,2))%arr.length)];
    }

    public int HashCode(int key){
        int n = key %= init;
        return checkIndex(n);
    }

    public int checkIndex(int index){
        while(arr[index] != null){
            if (index == arr.length-1){
                index = -1;
            }
            probes++;
            index++;
        }
        return index;
    }

    public String toString(){
        String str = "";
        for(int i = 0; i < arr.length; i++){
            if (arr[i] != null){
                str += arr[i]+"\n";
            }
        }
        return str;
    }

    public static void main(String [] args) throws IOException{
        //for finding times
        //long start = System.currentTimeMillis();
        //char of ' ' "Space" equals 32

        loopAVG("Large Data Set.txt",1);
        loopAVG("Large Data Set.txt",.9);
        loopAVG("Large Data Set.txt",.8);
        loopAVG("Large Data Set.txt",.5);
        loopAVG("Large Data Set.txt",.1);

        loopAVG("Successful Search.txt",1);
        loopAVG("Successful Search.txt",.9);
        loopAVG("Successful Search.txt",.8);
        loopAVG("Successful Search.txt",.5);
        loopAVG("Successful Search.txt",.1);

        loopAVG("Unsuccessful Search.txt",1);
        loopAVG("Unsuccessful Search.txt",.9);
        loopAVG("Unsuccessful Search.txt",.8);
        loopAVG("Unsuccessful Search.txt",.5);
        loopAVG("Unsuccessful Search.txt",.1);

        ArrayList<String> list = new ArrayList<String>();
        Scanner input = new Scanner(new File("Large Data Set.txt"));
        while(input.hasNext()){
            list.add(input.nextLine());
        }

        HashTable Table = new HashTable(50000);
        while(list.size() > 0){
            String str = list.remove(0);
            String num = str.substring(0,17);
            String make = cutSpace(str.substring(23,67));
            int year = Integer.parseInt(str.substring(18,22));
            String country = str.substring(67);
            Table.putQ(num, new Car(num,make,year,country));
        }
        //System.out.println(Table.Quadget("LZYZY13YUQX465006"));

    }
    //change size for different ALPHA Values
    public static void loopAVG(String file, double alpha) throws IOException{
        double avgTime = 0;
        double avgProbes = 0;
        int count = 0;
        ArrayList<String> list = new ArrayList<String>();
        do {
            Scanner input = new Scanner(new File(file));
            while(input.hasNext()){
                list.add(input.nextLine());
            }

            HashTable Table = new HashTable((int)(list.size()/alpha));
            long start = System.nanoTime();
            while(list.size() > 0){
                String str = list.remove(0);
                String num = str.substring(0,17);
                String make = cutSpace(str.substring(23,67));
                int year = Integer.parseInt(str.substring(18,22));
                String country = str.substring(67);
                Table.put(num, new Car(num,make,year,country));
            }
            long stop = System.nanoTime();
            avgTime += (stop-start);
            avgProbes += Table.probes;
            count++;
        }while(count < 20);
        avgTime /= (count+1);
        avgProbes /=(double)(count+1);
        System.out.println("Alpha: "+alpha);
        System.out.println("Average Time for "+file+": "+avgTime+" nanoseconds");
        System.out.println("Average Probes for "+file+": "+avgProbes+"\n");
    }

    public static String cutSpace(String str){
        boolean h = false;
        for(int i  = 0; i < str.length(); i++){
            if (str.charAt(i) == 32 && !h){
                h = true;
            }
            else if (str.charAt(i) == 32 && h){
                return str.substring(0,i-1);
            }else{
                h = false;
            }
        }
        return str;
    }
}

