import java.util.*;

// Tree node
class Node {

    int id;
    Vector<Node> children = new Vector<>();
    Node parent;
    int level;
    Node left, right;
    int height;

    public Node(int key){
        // Node node = new Node();
        this.id = key ;
        this.parent = null;
        this.level = 0;
        this.children  = new Vector<>();
        left = null;
        right = null;
        height = 0;
        //this.max = 2;

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
    Node node = new Node(data);
    public Node_avl()
    {
        left = null;
        right = null;
        data = 0;
        height = 0;
    }

    public Node_avl(int n)
    {
        this.left = null;
        this.right = null;
        this.data = n;
        this.height = 0;
        Node node;
    }
}

class storeAVL {
    private Node root1;

    public storeAVL() {
        root1 = null;
    }

    public boolean isEmpty() {
        return root1 == null;
    }

    public void insert_avl(int data) {
        root1 = insert(data, root1);
    }

    private int height(Node t) {
        return t == null ? -1 : t.height;
    }

    private int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }

    private Node insert(int x, Node t) {
        if (t == null)
            t = new Node(x);
        else if (x < t.id) {
            t.left = insert(x, t.left);
            if (height(t.left) - height(t.right) == 2)
                if (x < t.left.id)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithLeftChild(t);
        } else if (x > t.id) {
            t.right = insert(x, t.right);
            if (height(t.right) - height(t.left) == 2)
                if (x > t.right.id)
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithRightChild(t);
        } else
            ;
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private Node rotateWithLeftChild(Node k2) {
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private Node rotateWithRightChild(Node k1) {
        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }

    private Node doubleWithLeftChild(Node k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node doubleWithRightChild(Node k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    public Node search(int val) {
        return search(root1, val);
    }

    private Node search(Node r, int key) {


        Node current = root1;
        while (current != null) {
            if (current.id == key) {
                break;
            }
            //current = current.id < key ? current.right : current.left;
            if(key< current.id){
                current = current.left;
            }
            if(key > current.id){
                current = current.right;
            }
        }
        return current;
//        if (value == r.node.id) {
//            return r.node;
//        }
//        if (value < r.data) {
//            if (r.left != null) {
//                return search(r.left, value);
//            }
//        } else {
//            if (r.right != null) {
//                return search(r.right, value);
//            }
//        }
//
//        return r.node;

    }

}

public class OrgHierarchy implements OrgHierarchyInterface{

    //root node
    Node root;
    int Size = 0;


    public boolean isEmpty(){
        return Size == 0;
    }

    public int size(){
        return Size;
    }

    public int level(int id) throws IllegalIDException, EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("Tree is empty");
        Node node = root.find(id);
        if(node == null) throw new IllegalIDException("No such id exist");

        return node.level;

    }

    public void hireOwner(int id) throws NotEmptyException{
        if(!isEmpty()) throw new NotEmptyException("Tree is not Empty ");

     storeAVL avl = new storeAVL();
     avl.insert_avl(id);
     System.out.println(" - "+avl.search(id).id);
        root = new Node(id);
        root.parent = null;
        root.level++;

        Size++;

    }

    public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("owner is not defined");

        storeAVL avl = new storeAVL();
        //if(avl.search(bossid) == Integer.parseInt(null))
        //avl.insert_avl(id);
        System.out.println("hhh");
        System.out.println("ffff  "+ avl.search(bossid).id);

        Node node = root.find(bossid);
        if(node== null) throw new IllegalIDException("no such bossid exist");
        Node value = new Node(id);
        node.insert_child(value);
        Size++;

    }

    public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("tree is empty");
        Node node1 = root.find(id);
        if(node1== root) throw new IllegalIDException("cant fire owner");
        if(node1==null) throw new IllegalIDException("No such ID exist");

        node1.parent.delete_child(node1);

        Size--;
    }
    public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
        if(id == root.id) throw new IllegalIDException("you cannot fire owner");
        if(isEmpty()) throw new EmptyTreeException("Tree is empty");
        Node node1 = root.find(id);
        Node node2 = root.find(manageid);
        if(node1==null|| node2==null) throw new IllegalIDException("No such ID exist");
        for(Node n: node1.children){
            node2.insert_child(n);
        }
        node1.parent.delete_child(node1);
        Size--;

    }

    public int boss(int id) throws IllegalIDException,EmptyTreeException{
        //your implementation
        if(id == root.id){ return -1;}
        if(isEmpty()) throw new EmptyTreeException("tree is empty");
        Node node = root.find(id);
        if(node == null) throw new IllegalIDException("No such ID exist");
        return node.parent.id;
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
        //your implementation
        if(id1== root.id || id2==root.id){ return -1;}
        if(isEmpty()) throw new EmptyTreeException("Tree is empty");

        Node node1 = root.find(id1);
        Node node2 = root.find(id2);

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
        Node node = root.find(id);
        Vector<Integer> value = new Vector<>();

        for (Node n : node.children) {
            // Vector<Integer> a = new Vector<>();
            if (n.level == l) {
                value.add(n.id);
                //value.addAll(a);
            } else {
                Vector k = collect(l, n.id);
                //System.out.println(k.toString());
                if(k!=null)
                    value.addAll(k);}
            }
            Collections.sort(value);
        //System.out.println(" ff "+ value.toString());
            return value;
        }



    public String toString(int id) throws IllegalIDException, EmptyTreeException {
        //your implementation
        Node node = root.find(id);

        if (node == null) throw new IllegalIDException("ID doesn't exist");
        if (isEmpty()) throw new EmptyTreeException("tree is empty");
      // Vector m = collect(node.level+53,id);
     //  System.out.println("hh "+m.toString());

        String S = null;
        S = id + "," ;
        Vector a= null;
        int temp = node.level + 1;
        //a = collect(temp,id);

        while (!collect(temp,id).isEmpty()) {
            //for (Node n : node.children) {
               // System.out.println(n.id);
                a = collect(temp,id);
               // System.out.println(a.toString());
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