package managers.history;

import tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    public static HashMap<Integer, Node<Task>> getNodeHistory() {
        return LinkedHistoryList.nodeHistory;
    }

    @Override
    public void add(Task task){
        LinkedHistoryList.linkLast(task);
        LinkedHistoryList.nodeHistory.put(task.getId(), LinkedHistoryList.getTail());

    }

    @Override
    public ArrayList<Task> getHistory(){
        return LinkedHistoryList.getTasks();
    }

    @Override
    public void remove(int id){
        LinkedHistoryList.nodeHistory.remove(id);
    }


    public static class LinkedHistoryList<T>{
        private static final HashMap<Integer, Node<Task>> nodeHistory = new HashMap<>();
        static Node<Task> head;
        static Node<Task> tail;
        static int size = 0;

        public static void linkLast(Task element) {
            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(oldTail, element, null);
            tail = newNode;

            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.setNext(newNode);
            }

            if(nodeHistory.containsKey(element.getId())) {
                removeNode(nodeHistory.get(element.getId()));
            }

            size++;
        }

        public static ArrayList<Task> getTasks(){
            ArrayList<Task> history = new ArrayList<>();

            if (!(head == null)) {
                Node<Task> node = head;
                while (!(node == null)) {
                    history.add(node.getData());
                    node = node.getNext();
                }
            }
            Collections.reverse(history);
            return history;
        }

        public static void removeNode(Node<Task> node){
            if (!(node == null)) {
                if ((InMemoryHistoryManager.getNodeHistory().containsKey(node.getData().getId()))) {
                    Node<Task> prevNode = node.getPrev();
                    Node<Task> nextNode = node.getNext();
                    if (prevNode != null) {
                        prevNode.setNext(nextNode);
                    }
                    if (nextNode != null) {
                        nextNode.setPrev(prevNode);
                    }
                    nodeHistory.remove(node.getData().getId());
                    if (getHead() == node) {
                        setHead(nextNode);
                    } else if (getTail() == node) {
                        setTail(nextNode);
                    }
                }
            }
        }



        public static Node<Task> getTail() {
            return tail;
        }

        public static Node<Task> getHead() {
            return head;
        }

        public static void setHead(Node<Task> head) {
            LinkedHistoryList.head = head;
        }

        public static void setTail(Node<Task> tail) {
            LinkedHistoryList.tail = tail;
        }

        public int getSize() {
            return size;
        }

        @Override
        public String toString() {
            return "LinkedHistoryList{" +
                    "head=" + head +
                    ", tail=" + tail +
                    ", size=" + size +
                    '}';
        }
    }

}