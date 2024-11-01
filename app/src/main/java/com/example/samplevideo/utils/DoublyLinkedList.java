package com.example.samplevideo.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements List<E> {

	private GFNode<E> first;
	private GFNode<E> last;
	private int size;

	public DoublyLinkedList() {
		first = null;
		last = null;
		size = 0;
	}

	@Override
	public boolean add(E e) {
		final GFNode<E> l = last;
		final GFNode<E> newNode = new GFNode<>(l, e, null);
		last = newNode;
		if (l == null) first = newNode;
		else l.next = newNode;
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		checkIndex(index);
		if (index == size) {
			add(element);
		} else {
			GFNode<E> pred = node(index);
			final GFNode<E> succ = pred.next;
			final GFNode<E> newNode = new GFNode<>(pred, element, succ);
			pred.next = newNode;
			if (succ == null) {
				last = newNode;
			} else {
				succ.prev = newNode;
			}
			size++;
		}
	}

	@Override
	public E remove(int index) {
		checkIndex(index);
		GFNode<E> x = node(index);
		final E element = x.item;
		final GFNode<E> next = x.next;
		final GFNode<E> prev = x.prev;

		if (prev == null) {
			first = next;
		} else {
			prev.next = next;
			x.prev = null;
		}

		if (next == null) {
			last = prev;
		} else {
			next.prev = prev;
			x.next = null;
		}

		x.item = null;
		size--;
		return element;
	}

	@Override
	public E get(int index) {
		checkIndex(index);
		return node(index).item;
	}

	@Override
	public E set(int index, E element) {
		checkIndex(index);
		GFNode<E> x = node(index);
		E oldVal = x.item;
		x.item = element;
		return oldVal;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	private GFNode<E> node(int index) {
		// assert isElementIndex(index);

		if (index < (size >> 1)) {
			GFNode<E> x = first;
			for (int i = 0; i < index; i++)
				x = x.next;
			return x;
		} else {
			GFNode<E> x = last;
			for (int i = size - 1; i > index; i--)
				x = x.prev;
			return x;
		}
	}

	private E unlink(GFNode<E> x) {
		// 暂存待移除节点的信息
		final E element = x.item;

		//得到前后节点
		final GFNode<E> next = x.next;
		final GFNode<E> prev = x.prev;

		// 移除
		if (prev == null) {
			first = next;
		} else {
			prev.next = next;
			x.prev = null;
		}

		if (next == null) {
			last = prev;
		} else {
			next.prev = prev;
			x.next = null;
		}

		// 帮助 GC（垃圾收集器）收集
		x.item = null;

		// 列表数量自减
		size--;

		return element;
	}

	@Override
	public boolean contains(@Nullable Object o) {
		if (o == null) return false;
		E oToE;
		try {
			oToE = (E) o;
		} catch (ClassCastException classCastException) {
			return false;
		}
		for (E node : this) {
			if (o == node) return true;
			if (oToE.equals(node)) return true;
		}
		return false;
	}

	// ...

	@NonNull
	@Override
	public Object[] toArray() {
		return toArray(new Object[0]);
	}

	@NonNull
	@Override
	public <T> T[] toArray(@NonNull T[] a) {
		List<E> l = new ArrayList<>(this);
		return l.toArray(a);
	}

	@Override
	public boolean remove(@Nullable Object o) {
		E oToE;
		try {
			oToE = (E) o;
		} catch (ClassCastException classCastException) {
			return false;
		}
		for (GFNode<E> node = this.first; node != last; node = node.next) {
			if (node.item != oToE) continue;
			return unlink(node) != null;
		}
		return false;
	}

	@Override
	public boolean containsAll(@NonNull Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(@NonNull Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, @NonNull Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(@NonNull Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(@NonNull Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		if(size==0)return;

	}

	@Override
	public int indexOf(@Nullable Object o) {
		E oToE;
		try {
			oToE = (E) o;
		} catch (ClassCastException classCastException) {
			return -1;
		}
		int i = 0;
		for (E item : this) {
			if (item == o) return i;
			i++;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(@Nullable Object o) {
		E oToE;
		try {
			oToE = (E) o;
		} catch (ClassCastException classCastException) {
			return -1;
		}
		int i = 0;
		int maxIndex = -1;
		for (E item : this) {
			if (item == oToE) maxIndex = i;
			if (item.equals(oToE)) maxIndex = i;
			i++;
		}
		return maxIndex;
	}

	@NonNull
	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@NonNull
	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@NonNull
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		int i = 0;
		List<E> retList=new LinkedList<>();
		for (E node : this) {
			if (i < fromIndex) continue;
			if (i > toIndex) continue;
			retList.add(node);
			i++;
		}
		return retList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		GFNode<E> current = first;
		while (current != null) {
			sb.append(current.item);
			if (current.next != null) {
				sb.append(", ");
			}
			current = current.next;
		}
		sb.append(']');
		return sb.toString();
	}

	//以上是补充的实现

	private class Itr implements Iterator<E> {
		private GFNode<E> lastReturned;
		private GFNode<E> next;
		private int nextIndex;
		private int expectedModCount = size;

		Itr() {
			next = first;
			nextIndex = 0;
		}

		public boolean hasNext() {
			return nextIndex < size;
		}

		public E next() {
			checkForComodification();
			if (!hasNext()) throw new NoSuchElementException();

			lastReturned = next;
			next = next.next;
			nextIndex++;
			return lastReturned.item;
		}

		public void remove() {
			if (lastReturned == null) throw new IllegalStateException();

			checkForComodification();

			GFNode<E> lastNext = lastReturned.next;
			unlink(lastReturned);
			if (next == lastReturned) next = lastNext;
			else nextIndex--;
			lastReturned = null;
			expectedModCount--;
		}

		final void checkForComodification() {
			if (size != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	// 这里省略了其他方法的实现
}