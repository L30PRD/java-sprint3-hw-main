package managers.history;

import tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    LinkedHistoryList<Task> lk = new LinkedHistoryList<>();

    public HashMap<Integer, Node<Task>> getNodeHistory() {
        return lk.nodeHistory;
    }

    @Override
    public void add(Task task){
        lk.linkLast(task);
    }

    @Override
    public ArrayList<Task> getHistory(){
        return lk.getTasks();
    }

    @Override
    public void remove(int id){
        lk.remove(id);
    }

    public static class LinkedHistoryList<T>{
        private final HashMap<Integer, Node<Task>> nodeHistory = new HashMap<>();
        private Node<Task> head;
        private Node<Task> tail;
        private int size = 0;

        public void linkLast(Task element) {
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

            nodeHistory.put(element.getId(), tail);

            size++;
        }

        public ArrayList<Task> getTasks(){
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

        public void remove(int id){
            nodeHistory.remove(id);
        }

        public void removeNode(Node<Task> node){
            if (!(node == null)) {
                if ((nodeHistory.containsKey(node.getData().getId()))) {
                    Node<Task> prevNode = node.getPrev();
                    Node<Task> nextNode = node.getNext();
                    if (prevNode != null) {
                        prevNode.setNext(nextNode);
                    }
                    if (nextNode != null) {
                        nextNode.setPrev(prevNode);
                    }
                    nodeHistory.remove(node.getData().getId());
                    if (head == node) {
                        head = nextNode;
                    } else if (tail == node) {
                        tail = nextNode;
                    }
                }
            }
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