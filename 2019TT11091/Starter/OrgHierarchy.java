import java.io.*; 
import java.util.*; 

// Tree node
class Node {

    int id;
    Vector<Node> children = new Vector<>();
    Node parent;
    int level;

    public Node(int key){

        this.id = key ;
        this.parent = null;
        this.level = 0;
        this.children  = new Vector<>();

    }
    public void insert_child(Node key){
        if(!children.contains(key)){
            this.children.add(key);}
        key.parent = this;
        key.level = this.level +1;

    }
    public void delete_child(Node key){
        if(children.contains(key)){
            this.children.remove(this.children.indexOf(key));}
        key.parent = null;
        key.level = -1;
    }

    public Node find(int key){

        if(this.id==key) {
            return this;
        }
        for(Node n:this.children){
            if(n.id==key){
                return n;
            }
            Node k = n.find(key);
            if(k!=null){
                return k;
                }
            }
        return null;
    }

    public Vector<Node> get_boss(){
        Node node = this.parent;
        Vector<Node> list = new Vector<Node>();
        while(node != null){
            list.add(node);
            node = node.parent;
        }
      return list;
    }
}

class Node_avl{
    Node_avl left, right;
    int data;
    int height;
    Node node;

    public Node_avl()
    {
        left = null;
        right = null;
        data = 0;
        height = 0;
        Node node ;
    }

    public Node_avl(int n ,Node node)
    {
        this.left = null;
        this.right = null;
        this.data = n;
        this.height = 0;
        this.node = node;
    }
}

class storeAVL {
    Node_avl root;

    public storeAVL() {
        root = null;
    }

    public void newHeight(Node_avl n) {
        int a;
        if (height(n.left)>= height(n.right)){
            a = height(n.left);
        } else {
            a = height(n.right);
        }
        n.height = 1 + a;
    }

    public int height(Node_avl n) {
        if(n==null){
            return -1;
        }
        else{
            return n.height;}
    }

    public int getBalance(Node_avl n) {
        if(n==null){
            return 0;
        }
        else{
            return height(n.right) - height(n.left);}
    }

    public Node_avl rotateRight(Node_avl y) {
        Node_avl x = y.left;
        Node_avl z = x.right;
        x.right = y;
        y.left = z;
        newHeight(y);
        newHeight(x);
        return x;
    }
    public Node_avl rotateLeft(Node_avl y) {
        Node_avl x = y.right;
        Node_avl z = x.left;
        x.left = y;
        y.right = z;
        newHeight(y);
        newHeight(x);
        return x;
    }
    public Node_avl rebalance(Node_avl z) {
        newHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right))
                z = rotateRight(z);
            else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }
    public void insert_avl(int data,Node n ) {
        root = insert( root, data, n );

    }
    public Node_avl insert(Node_avl node, int key, Node n) {
        if (node == null) {
            return new Node_avl(key,n );
        } else if (node.data > key) {
            node.left = insert(node.left, key, n);
        } else if (node.data < key) {
            node.right = insert(node.right, key, n );
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }
    private Node_avl mostLeftChild(Node_avl node) {
        Node_avl current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    public  void delete_avl(int data ) {
        root = delete( root, data);
    }
    public Node_avl delete(Node_avl node, int key) {
        if (node == null) {
            return node;
        } else if (node.data > key) {
            node.left = delete(node.left, key);
        } else if (node.data < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node_avl mostLeftChild = mostLeftChild(node.right);
                node.data = mostLeftChild.data;
                node.node = mostLeftChild.node;
                node.right = delete(node.right, node.data);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    public Node search(int val) {
        return search(root, val);
    }

    private Node  search(Node_avl r, int value) {

        if (value == r.data) {
            return r.node;
        }
        if (value < r.data) {
            if (r.left != null) {
                return search(r.left, value);
            }
        } else {
            if (r.right != null) {
                return search(r.right, value);
            }
        }
        return null;
    }
}


public class OrgHierarchy implements OrgHierarchyInterface{

    //root node
    Node root;
    storeAVL avl = new storeAVL() ;
    int Size = 0;


    public boolean isEmpty(){
        return Size == 0;
    }

    public int size(){
        return Size;
    }

    public int level(int id) throws IllegalIDException, EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("Tree is empty");
        Node node = avl.search(id);

        if(node == null) throw new IllegalIDException("No such id exist");
        return node.level;
    }

    public void hireOwner(int id) throws NotEmptyException{
        if(!isEmpty()) throw new NotEmptyException("Tree is not Empty ");

        root = new Node(id);
        root.parent = null;
        root.level++;
        avl.insert_avl(id,root);
        Size++;
    }

    public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("owner is not defined");


        Node check = avl.search(id);
        if(check!=null ) throw new IllegalIDException("same ID exist");

        Node node = avl.search(bossid);

        if(node == null) throw new IllegalIDException("no such bossid exist");

        Node value = new Node(id);
        node.insert_child(value);

        avl.insert_avl(id,value);
        Size++;

    }

    :)

    public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("tree is empty");

        Node node1 = avl.search(id);

        if(node1== root) throw new IllegalIDException("cant fire owner");

        
        if(node1==null) throw new IllegalIDException("No such ID exist");
        if(!node1.children.isEmpty()) throw new IllegalIDException("it is managing some id");
        

        node1.parent.delete_child(node1);

        avl.delete_avl(id);
        Size--;
    }

    public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
        
        if(isEmpty()) throw new EmptyTreeException("Tree is empty");
        if(id == root.id) throw new IllegalIDException("you cannot fire owner");
        Node node1 = avl.search(id);
        Node node2 = avl.search(manageid);

        if(node1==null|| node2==null) throw new IllegalIDException("No such ID exist");
        for(Node n: node1.children){
            node2.insert_child(n);
        }
        avl.delete_avl(id);

        node1.parent.delete_child(node1);
        node1.parent = null;
        Size--;
    }

    public int boss(int id) throws IllegalIDException,EmptyTreeException{
        if(isEmpty()) throw new EmptyTreeException("tree is empty");

        if(id == root.id){ return -1;}
        
        Node node = avl.search(id);
        if(node == null) throw new IllegalIDException("No such ID exist");
        return node.parent.id;

    }

    public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
        if(isEmpty()) throw new EmptyTreeException("Tree is empty");

        if(id1== root.id || id2==root.id){ return -1;}
        
//        Node node1 = root.find(id1);
//        Node node2 = root.find(id2);

        Node node1 = avl.search(id1);
        Node node2 = avl.search(id2);

        if(node1==null || node2==null) throw new IllegalIDException("No such ID exist");

        Vector<Node> boss1 = node1.get_boss();
        Vector<Node> boss2 = node2.get_boss();

        Node node3 = null;

        for(Node n:boss1){
            Node node4 = n;
            if(boss2.contains(node4)){
                node3 = node4;
                break; }
        }
        return node3.id;
    }

    public Vector collect(int l ,int id) {

        Node node = avl.search(id);
        Vector<Integer> value = new Vector<>();

        for (Node n : node.children) {
            if (n.level == l) {
                value.add(n.id);
            } else {
                Vector k = collect(l, n.id);
                if(k!=null)
                    value.addAll(k);}
            }
            Collections.sort(value);
            return value;
        }


    public String toString(int id) throws IllegalIDException, EmptyTreeException {
        if (isEmpty()) throw new EmptyTreeException("tree is empty");

        Node node = avl.search(id);
        if (node == null) throw new IllegalIDException("ID doesn't exist");
        
        String S = null;
        S = id + "," ;
        Vector a= null;
        int temp = node.level + 1;

        while (!collect(temp,id).isEmpty()) {
            a = collect(temp,id);
            temp++;
            for (int k = 0; k < a.size()-1; k++) {
                S += a.get(k) + " "; }
                S += a.get(a.size()-1);
                S += ",";
        }
        S= S.substring(0,S.length()-1);
        return S;

    }
}
