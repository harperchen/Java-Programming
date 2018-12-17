import java.util.*;

public class Queue<E> extends Stack<E> {
    public final int dump = 10;
    private Stack<E> stk;

    public Queue() {
        stk = new Stack<E>();
        System.out.println("Initial Queue Class!");
    }

    public boolean add(E e) throws IllegalStateException, ClassCastException,
            NullPointerException, IllegalArgumentException {
        try {
            if (e == null) {
                throw new NullPointerException();
            }
            if ((size() == dump) && !stk.empty()) {
                throw new IllegalStateException();
            }
            if (size() == dump) {
                E obj;
                while (!empty()) {
                    obj = remove(size() - 1);
                    stk.push(obj);
                }
            }
            push(e);
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            throw excp;
        }
        return true;
    }

    public boolean offer(E e) throws ClassCastException, NullPointerException,
            IllegalArgumentException {
        boolean ret = false;
        try {
            if (e == null) { //NullPointerException
                throw new NullPointerException();
            }
            if ((size() == dump) && !stk.empty()) {
                return false;
            }
            if (size() == dump) {
                E obj;
                while (!empty()) {
                    obj = remove(size() - 1);
                    stk.push(obj);
                }
            }
            push(e);
            ret = true;
        } catch (Exception excp) {
            System.out.print(excp.getMessage() + "\n");
            ret = false;
            throw excp;
        } finally {
            return ret;
        }
    }

    public E remove() throws NoSuchElementException {
        E obj;
        try {
            if (stk.empty() && empty()) {
                throw new NoSuchElementException();
            }
            if (stk.empty()) {
                while (!empty()) {
                    obj = remove(size() - 1);
                    stk.push(obj);
                }
            }
            obj = stk.remove(stk.size() - 1);
        } catch (NoSuchElementException excp) {
            System.out.println(excp.getMessage());
            throw excp;
        }
        return obj;
    }

    public E poll() {
        try {
            if (stk.empty() && empty()) {
                throw new NoSuchElementException();
            }
            E obj;
            if (stk.empty()) {
                while (!empty()) {
                    obj = remove(size() - 1);
                    stk.push(obj);
                }
            }
            obj = stk.remove(stk.size() - 1);
            return obj;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return null;
        }
    }

    public E element() throws NoSuchElementException {
        try {
            if (stk.empty() && empty()) {
                throw new NoSuchElementException();
            }
            E obj;
            if (stk.empty()) {
                while (!empty()) {
                    obj = remove(size() - 1);
                    stk.push(obj);
                }
            }
            obj = stk.peek();
            return obj;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            throw excp;
        }
    }

    public E peek() {
        try {
            if (stk.empty() && empty()) {
                throw new NoSuchElementException();
            }
            E obj;
            if (stk.empty()) {
                while (!empty()) {
                    obj = super.remove(size() - 1);
                    stk.push(obj);
                }
            }
            obj = stk.peek();
            return obj;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return null;
        }
    }

    @Override
    public synchronized boolean addAll(Collection<? extends E> c) throws NullPointerException {
        boolean ret = true;
        if (c == null) {
            throw new NullPointerException();
        }
        Object[] a = c.toArray();
        for (Object e : a) {
            ret = offer((E) e);
            if (!ret) {
                return ret;
            }
        }
        return true;
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (o instanceof Queue) {
            return super.equals(o) && stk.equals(((Queue) o).stk);
        }
        return false;
    }

    @Override
    public synchronized Object clone() {
        Queue<E> q = (Queue<E>) super.clone();
        q.stk = (Stack<E>) stk.clone();
        return q;
    }

    public static void main(String[] args) {
        Queue<Integer> integers = new Queue<Integer>();
        Queue<String> strings = new Queue<String>();
        Collection<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 17, 18, 19, 20));
        Collection<String> strList = new ArrayList<>(Arrays.asList("xxx", "yyy", "zzz"));
        boolean int_ret = integers.addAll(intList);
        boolean str_ret = strings.addAll(strList);
        System.out.print("integers.equals(strings) is " + integers.equals(strings) + "\n");
        Queue<Integer> others = (Queue<Integer>) integers.clone();
        System.out.print("integers.equals(others) is " + integers.equals(others) + "\n");
        System.out.print("integers == others is " + (integers == others) + "\n");
        for (int i = 0; i < 20; i++) {
            Integer obj = integers.remove();
            System.out.print(obj + " ");
        }
        System.out.print("\n");

        System.out.print("integers.add(1) is " + integers.add(1) + "\n");
        System.out.print("integers.offer(2) is " + integers.offer(2) + "\n");
        try {
            System.out.print("integers.peek() is " + integers.peek() + "\n");
            System.out.print("integers.element() is " + integers.element() + "\n");
        } catch (Exception e) {
            System.out.print(e.toString() + "\n");
        }
        try {
            System.out.print("integers.poll() is " + integers.poll() + "\n");
            System.out.print("integers.remove() is " + integers.remove() + "\n");
        } catch (Exception e) {
            System.out.print(e.toString() + "\n");
        }
        integers.addAll(intList);
        try {
            System.out.print("integers.offer(5) is " + integers.offer(5) + "\n");
        } catch (Exception e) {
            System.out.print(e.toString() + "\n");
        }
        try {
            System.out.print("integers.add(5) is " + integers.add(5) + "\n");
        } catch (Exception e) {
            System.out.print(e.toString() + "\n");
        }
        try {
            System.out.print("integers.offer(null) is " + integers.offer(null) + "\n");
        } catch (Exception e) {
            System.out.print(e.toString() + "\n");
        }
        try {
            System.out.print("integers.add(null) is " + integers.add(null) + "\n");
        } catch (Exception e) {
            System.out.print(e.toString() + "\n");
        }
    }
}
