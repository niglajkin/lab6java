import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * A custom typed {@link Set} implementation for {@link CoffeeProduct}.
 *
 * <p>Internal structure: singly linked list (C3 = 1 for record book number 9085).</p>
 * <p>This set enforces uniqueness using {@link Object#equals(Object)} of elements.</p>
 *
 * <p>Constructors required by the lab:</p>
 * <ul>
 *   <li>Empty constructor</li>
 *   <li>Constructor with a single element</li>
 *   <li>Constructor with a standard collection</li>
 * </ul>
 */
public final class CoffeeProductLinkedSet implements Set<CoffeeProduct>
{
    private Node head;
    private int size;

    /**
     * Creates an empty set.
     */
    public CoffeeProductLinkedSet()
    {
        this.head = null;
        this.size = 0;
    }

    /**
     * Creates a set containing exactly one element.
     *
     * @param element element to add
     * @throws IllegalArgumentException if element is null
     */
    public CoffeeProductLinkedSet(CoffeeProduct element)
    {
        this();
        add(Objects.requireNonNull(element, "element must not be null"));
    }

    /**
     * Creates a set initialized with elements from an existing collection.
     *
     * @param elements source collection
     * @throws IllegalArgumentException if elements is null or contains null
     */
    public CoffeeProductLinkedSet(Collection<? extends CoffeeProduct> elements)
    {
        this();
        Objects.requireNonNull(elements, "elements must not be null");
        addAll(elements);
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public boolean contains(Object o)
    {
        if (o == null)
        {
            return false;
        }

        Node cur = head;
        while (cur != null)
        {
            if (o.equals(cur.value))
            {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    @Override
    public Iterator<CoffeeProduct> iterator()
    {
        return new LinkedIterator();
    }

    @Override
    public Object[] toArray()
    {
        Object[] arr = new Object[size];
        int i = 0;

        Node cur = head;
        while (cur != null)
        {
            arr[i] = cur.value;
            i++;
            cur = cur.next;
        }

        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        Objects.requireNonNull(a, "array must not be null");

        int needed = size;
        T[] result = a.length >= needed ? a : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), needed);

        int i = 0;
        Node cur = head;
        while (cur != null)
        {
            result[i] = (T) cur.value;
            i++;
            cur = cur.next;
        }

        if (result.length > needed)
        {
            result[needed] = null;
        }

        return result;
    }

    @Override
    public boolean add(CoffeeProduct coffeeProduct)
    {
        Objects.requireNonNull(coffeeProduct, "element must not be null");

        if (contains(coffeeProduct))
        {
            return false;
        }

        head = new Node(coffeeProduct, head);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        if (o == null || head == null)
        {
            return false;
        }

        if (o.equals(head.value))
        {
            head = head.next;
            size--;
            return true;
        }

        Node prev = head;
        Node cur = head.next;

        while (cur != null)
        {
            if (o.equals(cur.value))
            {
                prev.next = cur.next;
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        Objects.requireNonNull(c, "collection must not be null");

        for (Object x : c)
        {
            if (!contains(x))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends CoffeeProduct> c)
    {
        Objects.requireNonNull(c, "collection must not be null");

        boolean changed = false;
        for (CoffeeProduct p : c)
        {
            if (p == null)
            {
                throw new IllegalArgumentException("collection must not contain null elements");
            }
            if (add(p))
            {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        Objects.requireNonNull(c, "collection must not be null");

        boolean changed = false;

        Node prev = null;
        Node cur = head;

        while (cur != null)
        {
            if (!c.contains(cur.value))
            {
                changed = true;
                size--;

                if (prev == null)
                {
                    head = cur.next;
                    cur = head;
                    continue;
                }
                else
                {
                    prev.next = cur.next;
                    cur = prev.next;
                    continue;
                }
            }

            prev = cur;
            cur = cur.next;
        }

        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        Objects.requireNonNull(c, "collection must not be null");

        boolean changed = false;
        for (Object x : c)
        {
            while (remove(x))
            {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear()
    {
        head = null;
        size = 0;
    }

    /**
     * Two sets are equal if they contain the same elements (standard {@link Set} contract).
     *
     * @param o other object
     * @return true if equal
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Set<?> other))
        {
            return false;
        }
        if (other.size() != this.size)
        {
            return false;
        }
        return containsAll(other);
    }

    /**
     * Hash code is defined as the sum of hash codes of elements (standard {@link Set} contract).
     *
     * @return hash code
     */
    @Override
    public int hashCode()
    {
        int h = 0;
        Node cur = head;
        while (cur != null)
        {
            h += cur.value.hashCode();
            cur = cur.next;
        }
        return h;
    }

    private static final class Node
    {
        private final CoffeeProduct value;
        private Node next;

        private Node(CoffeeProduct value, Node next)
        {
            this.value = value;
            this.next = next;
        }
    }

    private final class LinkedIterator implements Iterator<CoffeeProduct>
    {
        private Node prev;
        private Node cur;
        private Node next;
        private boolean canRemove;

        private LinkedIterator()
        {
            this.prev = null;
            this.cur = null;
            this.next = head;
            this.canRemove = false;
        }

        @Override
        public boolean hasNext()
        {
            return next != null;
        }

        @Override
        public CoffeeProduct next()
        {
            if (next == null)
            {
                throw new NoSuchElementException();
            }

            prev = cur;
            cur = next;
            next = next.next;
            canRemove = true;

            return cur.value;
        }

        @Override
        public void remove()
        {
            if (!canRemove)
            {
                throw new IllegalStateException("remove() cannot be called now");
            }

            if (cur == head)
            {
                head = head.next;
            }
            else
            {
                if (prev == null)
                {
                    throw new IllegalStateException("iterator state is invalid");
                }
                prev.next = cur.next;
            }

            size--;
            cur = prev;
            canRemove = false;
        }
    }
}
