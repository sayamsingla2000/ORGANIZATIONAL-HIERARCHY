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
    //Node node = new Node(data);
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
        Node node ;
    }
}

class storeAVL {
    private Node root;

    public storeAVL() {
        root = null;
    }

    public void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    public int height(Node n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    public Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }
    public Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }
    public Node rebalance(Node z) {
        updateHeight(z);
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
    public void insert_avl(int data) {
        root = insert( root, data);
    }
    public Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        } else if (node.id > key) {
            node.left = insert(node.left, key);
        } else if (node.id < key) {
            node.right = insert(node.right, key);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }
    private Node mostLeftChild(Node node) {
        Node current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    public void delete_avl(int data) {
        root = delete( root, data);
    }
    public Node delete(Node node, int key) {
        if (node == null) {
            return node;
        } else if (node.id > key) {
            node.left = delete(node.left, key);
        } else if (node.id < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.id = mostLeftChild.id;
                node.right = delete(node.right, node.id);
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

    private Node search(Node r, int value) {


//        Node_avl current = root;
//        while (current != null) {
//            if (current.data == key) {
//                break;
//            }
//            current = current.data < key ? current.right : current.left;
//        }
//        return current.data;

        if (value == r.id) {
            return r;
        }
        if (value < r.id) {
            if (r.left != null) {
                return search(r.left, value);
            }
        } else {
            if (r.right != null) {
                return search(r.right, value);
            }
        }

        return r;

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
        Node node = root.find(id);
        if(node == null) throw new IllegalIDException("No such id exist");

        return node.level;

    }

    public void hireOwner(int id) throws NotEmptyException{
        if(!isEmpty()) throw new NotEmptyException("Tree is not Empty ");


        avl.insert_avl(id);
        //System.out.println(avl.search(id));
        root = new Node(id);
        root.parent = null;
        root.level++;

        Size++;

    }

    public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("owner is not defined");


        //if(avl.search(bossid) == Integer.parseInt(null))
        //avl.insert_avl(id);

        //System.out.println("ffff  "+ avl.search(bossid));
        Node node = avl.search(bossid);
        //System.out.println("ffff  "+ avl.search(bossid).id);
        Node node2 = root.find(bossid);
        if(node== null) throw new IllegalIDException("no such bossid exist");
        Node value = new Node(id);
        node.insert_child(value);
        avl.insert_avl(id);
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
        //Node node = root.find(id);
        Node node = avl.search(id);
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
        //Node node = root.find(id);
        Node node = avl.search(id);
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