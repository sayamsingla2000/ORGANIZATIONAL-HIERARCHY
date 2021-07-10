import java.util.*;

// Tree node
class Node {

    int id;
    Vector<Node> children = new Vector<>();
    Node parent;
    int level;

    public Node(int key){
        // Node node = new Node();
        this.id = key ;
        this.parent = null;
        this.level = 0;
        this.children  = new Vector<>();
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
        root = new Node(id);
        root.parent = null;
        root.level++;

        Size++;

    }

    public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{

        if(isEmpty()) throw new EmptyTreeException("owner is not defined");
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
